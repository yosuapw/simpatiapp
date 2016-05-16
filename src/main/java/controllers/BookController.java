package controllers;

import java.util.ArrayList;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.Excursion;
import model.Tour;
import ninja.Context;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.PathParam;
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

	BookDAO bookDAO;

	@Inject
	public BookController(ExcursionDAO excursionDAO, ExplorerDAO explorerDAO,
			RoundTripDAO roundTripDAO, NinjaCache ninjaCache,
			NinjaProperties ninjaProperties, Context context, BookDAO bookDAO) {
		super(excursionDAO, explorerDAO, roundTripDAO, ninjaCache,
				ninjaProperties, context);
		this.bookDAO = bookDAO;
	}


	private Cart getCart() {
		String cartId = context.getSession().get("cartId");
		Cart cart = null;
		if (cartId == null) {
			cart = new Cart();
			ObjectId id = new ObjectId();
			cart.setId(id);
			context.getSession().put("cartId", id.toString());
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

	public Result addBook(@PathParam("id") String id,
			@PathParam("link") String link, Cart cart) {
		// Result result = Results.html();
		Cart dataCart = getCart();

		if (dataCart.getCartItems() == null)
			dataCart.setCartItems(new ArrayList<CartItem>());

		generateCartItemIds(cart.getCartItems());

		dataCart.getCartItems().addAll(cart.getCartItems());

		bookDAO.save(dataCart);

		ninjaCache.set(
				Helper.constructCartSession(dataCart.getId().toString()),
				dataCart, "10mn");

		return Results.json().render(
				ninjaProperties.getContextPath() + "/tours/" + id + "/" + link);
	}

	private void generateCartItemIds(List<CartItem> cartItems) {
		for (CartItem cartItem : cartItems) {
			cartItem.setObjectid(new ObjectId().toString());
		}
	}

	public Result allBooks() {
		Result result = Results.html();
		result.render("cart", getCart());
		return result;
	}

	// ** FOR JSON REQUESTS **

	public Result getCarts() {
		return Results.json().render(getCart());
	}

	public Result getTotalCart() {
		Cart cart = getCart();
		int result = 0;
		if (cart.getCartItems() != null)
			result = cart.getCartItems().size();
		return Results.json().render(result);
	}

	public Result deleteCarts(CartItem cartItem) {
		Cart cart = getCart();
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
		context.getSession().put("username", "kevin");

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

}
