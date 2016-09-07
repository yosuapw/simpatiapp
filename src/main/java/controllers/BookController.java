package controllers;

import id.co.veritrans.mdk.v1.VtGatewayConfig;
import id.co.veritrans.mdk.v1.VtGatewayConfigBuilder;
import id.co.veritrans.mdk.v1.VtGatewayFactory;
import id.co.veritrans.mdk.v1.config.EnvironmentType;
import id.co.veritrans.mdk.v1.exception.RestClientException;
import id.co.veritrans.mdk.v1.gateway.VtWeb;
import id.co.veritrans.mdk.v1.gateway.model.Address;
import id.co.veritrans.mdk.v1.gateway.model.CustomerDetails;
import id.co.veritrans.mdk.v1.gateway.model.TransactionDetails;
import id.co.veritrans.mdk.v1.gateway.model.VtResponse;
import id.co.veritrans.mdk.v1.gateway.model.vtweb.VtWebChargeRequest;
import id.co.veritrans.mdk.v1.gateway.model.vtweb.VtWebParam;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import model.Cart;
import model.CartItem;
import model.Excursion;
import model.Payment;
import model.PersonDetail;
import model.Tour;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.PathParam;
import ninja.session.Session;
import ninja.utils.NinjaProperties;

import org.bson.types.ObjectId;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import dao.BookDAO;
import dao.ExcursionDAO;
import dao.ExplorerDAO;
import dao.RoundTripDAO;

@Singleton
public class BookController extends BaseController {
	
	final String UNPAID = "unpaid";

	BookDAO bookDAO;

	@Inject
	public BookController(ExcursionDAO excursionDAO, ExplorerDAO explorerDAO,
			RoundTripDAO roundTripDAO, NinjaCache ninjaCache,
			NinjaProperties ninjaProperties, Context context, BookDAO bookDAO,
			Session sessionz) {
		super(excursionDAO, explorerDAO, roundTripDAO, ninjaCache,
				ninjaProperties, context, sessionz);
		this.bookDAO = bookDAO;
	}


	private Cart getCart(Session session) {
		String cartId = session.get("cartId");
		Cart cart = null;
		if (cartId == null) {
			cart = new Cart();
			ObjectId id = new ObjectId();
			cart.setId(id);
			session.put("cartId", id.toString());
			bookDAO.save(cart);
			ninjaCache.set(Helper.constructCartSession(cartId), cart, "10mn");
		} else {
			cart = ninjaCache.get(Helper.constructCartSession(cartId),
					Cart.class);
			if (cart == null) {
				cart = bookDAO.findByID(cartId);
			}
		}
		return cart;
	}

	public Result book() {
		Result result = Results.html();
		return result;
	}

	public Result cart() {
		Result result = Results.html();
		return result;
	}

	public Result checkout() {
		Result result = Results.html();
		return result;
	}

	public Result saveCheckout(PersonDetail personDetail, Session session) {
		Cart cart = getCart(session);
		return saveCheckout(personDetail, cart, session);
	}

	private Result saveCheckout(PersonDetail personDetail, Cart cart, Session session) {
		cart.setPersonDetail(personDetail);
		cart.setPayment(new Payment(Helper.constructValidation(),
				UNPAID));
		bookDAO.save(cart);
		
		// Empty the cartId after all completed
		session.put("cartId", null);
		
		return createVeritransPayment(personDetail, cart);
	}

	public Result addBook(@PathParam("id") String id,
			@PathParam("link") String link, Cart cart, Session session) {
		// Result result = Results.html();
		Cart dataCart = getCart(session);

		if (dataCart.getCartItems() == null)
			dataCart.setCartItems(new ArrayList<CartItem>());

		generateCartItemIds(cart.getCartItems());

		dataCart.getCartItems().addAll(cart.getCartItems());

		bookDAO.save(dataCart);

		ninjaCache.set(
				Helper.constructCartSession(dataCart.getId().toString()),
				dataCart, "10mn");

		return Results.json().render(
				ninjaProperties.get("fullServerName") + "/tours/" + id + "/" + link);
	}

	private void generateCartItemIds(List<CartItem> cartItems) {
		for (CartItem cartItem : cartItems) {
			cartItem.setObjectid(new ObjectId().toString());
		}
	}

	public Result allBooks(Session session) {
		Result result = Results.html();
		result.render("cart", getCart(session));
		return result;
	}

	// ** FOR JSON REQUESTS **

	public Result getCarts(Session session) {
		return Results.json().render(getCart(session));
	}

	public Result getTotalCart(Session session) {
		Cart cart = getCart(session);
		int result = 0;
		if (cart.getCartItems() != null)
			result = cart.getCartItems().size();
		return Results.json().render(result);
	}

	public Result deleteCarts(CartItem cartItem) {
		Cart cart = getCart(session);
		for (CartItem data : cart.getCartItems()) {
			if (data.getObjectid().equals(cartItem.getObjectid())) {
				cart.getCartItems().remove(data);
				break;
			}
		}
		bookDAO.save(cart);

		ninjaCache.set(Helper.constructCartSession(cart.getId().toString()),
				cart, "10mn");

		return Results.ok();
	}



	public Result detail(@PathParam("id") String id,
			@PathParam("link") String link) {
		Result result = Results.html();
		session.put("username", "kevin");

		if (id.equalsIgnoreCase("excursion")) {
			Excursion excursion = getExcursion(link);
			// excursionConvertion(excursion, priceType, date, number);
		} else if (id.equalsIgnoreCase("explorer")) {
			result.render("tour", getExplorer(link));
		} else {
			result.render("tour", getRoundTrip(link));
		}
		return result;
	}
	
	private CartItem excursionConvertion(Tour excursion, String priceType,
			String date,
			Integer number) {
		CartItem cartItem = new CartItem();

		switch (priceType) {
		case "adult":
			cartItem.setPrice(excursion.getPrices().getAdult());
		case "children":
			cartItem.setPrice(excursion.getPrices().getChildren());
		case "car":
			cartItem.setPrice(excursion.getPrices().getCar());
		default:
			cartItem.setPrice(0);
		}

		cartItem.setNumber(number);
		cartItem.setPriceType(priceType);
		cartItem.setType(excursion.getType());
		cartItem.setItem(excursion.getLink());
		cartItem.setAmount(number * cartItem.getPrice());
		return cartItem;
	}
	
	public Result createVeritransPayment(PersonDetail personDetail, Cart cart) {

		VtGatewayConfigBuilder vtGatewayConfigBuilder = new VtGatewayConfigBuilder();
		vtGatewayConfigBuilder.setServerKey("VT-server--DKnApbVGMb9DJQeAYTQCu_B");
		vtGatewayConfigBuilder.setClientKey("VT-client-HDXeZLmF6qosm1UB");
		// config for sandbox environment
		vtGatewayConfigBuilder.setEnvironmentType(EnvironmentType.SANDBOX);

		
		VtGatewayConfig vtGatewayConfig = vtGatewayConfigBuilder.createVtGatewayConfig();
		VtGatewayFactory vtGatewayFactory = new VtGatewayFactory(vtGatewayConfig);
		
		
		VtWeb vtWeb = vtGatewayFactory.vtWeb();
		
		VtWebChargeRequest vtWebChargeRequest = new VtWebChargeRequest();
		//setVtWebChargeRequestValues(vtWebChargeRequest);
		vtWebChargeRequest.setVtWeb(new VtWebParam());
		vtWebChargeRequest.getVtWeb().setCreditCardUse3dSecure(false);
		setVtDirectChargeRequestValues(vtWebChargeRequest, personDetail, cart);
		final VtResponse vtResponse;
		try {
			vtResponse = vtWeb.charge(vtWebChargeRequest);
			if (vtResponse.getStatusCode().equals("201")) {
			    // Redirect user to redirecturl param [vtResponse.getRedirectUrl()]
				return Results.json().render(vtResponse.getRedirectUrl());
			} else {
			    // Handle denied / unexpected response
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setVtDirectChargeRequestValues(VtWebChargeRequest vtDirectChargeRequest, PersonDetail personDetail, Cart cart) {
		
		Integer total = 0;
		if (cart.getCartItems() != null) {
			for (CartItem item : cart.getCartItems()) {
				if (item.getTotal() != null) {
					total += item.getTotal() * 100 * 15000;
				}
			}
		}
	    vtDirectChargeRequest.setTransactionDetails(new TransactionDetails());
	    vtDirectChargeRequest.setCustomerDetails(new CustomerDetails());

	    vtDirectChargeRequest.getTransactionDetails().setOrderId(UUID.randomUUID().toString());
	    vtDirectChargeRequest.getTransactionDetails().setGrossAmount(new Long(total));

	    vtDirectChargeRequest.getCustomerDetails().setEmail(personDetail.getEmail());
	    vtDirectChargeRequest.getCustomerDetails().setFirstName(personDetail.getFirstname());
	    vtDirectChargeRequest.getCustomerDetails().setPhone(personDetail.getPhone());
	    vtDirectChargeRequest.getCustomerDetails().setLastName(personDetail.getLastname());

	    Address billingAddress = new Address();
	    billingAddress.setAddress(personDetail.getAddress());
	    billingAddress.setCity(personDetail.getCity());
	    billingAddress.setPostalCode(personDetail.getPostal());
	    vtDirectChargeRequest.getCustomerDetails().setBillingAddress(billingAddress);
	}
	

}
