package controllers;

import java.util.List;

import model.Cart;
import model.Payment;
import ninja.Result;
import ninja.Results;

import com.google.inject.Inject;

import dao.BookDAO;

public class AdminController {
	
	@Inject
	BookDAO bookDAO;

	public Result waitConfirmation() {
		Result result = Results.html();
		return result;
	}

	public Result paymentConfirmation() {
		List<Cart> cart = bookDAO.findByStatuses();
		return Results.json().render(cart);
	}

	public Result confirmItem(Payment payment) {
		Cart data1 = bookDAO.findByLink(payment.getLink());
		data1.getPayment().setStatus("fullyPaid");
		bookDAO.save(data1);

		return Results.json().render(data1);
	}
}
