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

	// ** FOR JSON REQUESTS **

	public Result book(@PathParam("id") String id,
			@PathParam("link") String link) {
		// Result result = Results.html();
		String cartId = context.getSession().get("cartId");
		Cart cart = null;
		if (cartId == null) {
			cart = new Cart();
		} else {
			cart = bookDAO.findByID(cartId);
		}

		CartItem cartItem = new CartItem("excursion", 10);
		List<CartItem> cartItems = new ArrayList<CartItem>();
		cartItems.add(cartItem);

		if (cart.getCartItems() == null) {
			cart.setCartItems(new ArrayList<CartItem>());
		}

		cart.getCartItems().add(cartItem);

		String generatedCartId = bookDAO.save(cart);

		if (cartId == null)
			context.getSession().put("cartId", generatedCartId);

		return Results.json().render(cart);
	}

	public Result book2(@PathParam("id") String id,
			@PathParam("link") String link, Cart cartItems) {
		// Result result = Results.html();
		String cartId = context.getSession().get("cartId");
		Cart cart = null;
		if (cartId == null) {
			cart = new Cart();
		} else {
			cart = bookDAO.findByID(cartId);
		}

		CartItem cartItem = new CartItem("excursion", 10);


		return Results.json().render(cart);
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
