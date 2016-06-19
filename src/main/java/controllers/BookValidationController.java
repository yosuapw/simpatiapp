package controllers;

import model.Cart;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import com.google.inject.Inject;

import dao.BookDAO;

public class BookValidationController {

	BookDAO bookDAO;

	@Inject
	public BookValidationController(BookDAO bookDAO) {
		super();
		this.bookDAO = bookDAO;
	}

	public Result validateBook() {
		Result result = Results.html();
		return result;
	}
	
	public Result findBookValidation(@PathParam("link") String link) {
		Cart cart = bookDAO.findByLink(link);
		return Results.json().render(cart);
	}
	
	public Result confirmPayment(@PathParam("link") String link) {
		Cart cart = bookDAO.findByLink(link);
		cart.getPayment().setStatus("confirmPayment");
		bookDAO.save(cart);
		return Results.ok();
	}
}
