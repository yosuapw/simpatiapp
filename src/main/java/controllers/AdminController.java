package controllers;

import java.util.List;

import model.Cart;
import model.Payment;
import ninja.Context;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import pojo.User;

import com.google.inject.Inject;

import dao.BookDAO;
import filter.SecureFilter2;

public class AdminController {
	
	private static final String USERNAME = "username";
	private static final String USERNAME_VALUE = "theBoss";
	private static final String PASSWORD = "password";
	private static final String PASSWORD_VALUE = "iniAdalahBoss";
	
	@Inject
	BookDAO bookDAO;
	
	public Result login() {
		Result result = Results.html();
		return result;
	}
	
	public Result doLogin(User user, Context context) {

        // if we got no cookies we break:
        if (context.getSession() == null
                || context.getSession().get(USERNAME) == null) {
        	
        	if (USERNAME_VALUE.equals(user.getUsername()) && 
        			PASSWORD_VALUE.equals(user.getPassword())) {
        		
        		context.getSession().put(USERNAME, USERNAME_VALUE);

                return Results.html().template("views/AdminController/waitConfirmation.ftl.html");
        	}
            return Results.html().template("views/AdminController/login.ftl.html");

        } else {
            return Results.html().template("views/AdminController/waitConfirmation.ftl.html");
        }
	}

	@FilterWith(SecureFilter2.class)
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
