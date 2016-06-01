package controllers;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Cart;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.scheduler.Schedule;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import dao.BookDAO;

@Singleton
public class Scheduler {

	final String UNPAID = "unpaid";

	@Inject
	Provider<Mail> mailProvider;

	@Inject
	BookDAO bookDAO;

	@Inject
	Postoffice postoffice;

	@Schedule(delay = 60, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
	public void doStuffEach60Seconds() {
		List<Cart> cartList = bookDAO.findByStatus(UNPAID);

		for (Cart cart : cartList) {
			cart.getPayment().setStatus("NOTIFIED");
			bookDAO.save(cart);
			sendMail();
		}
	}

	public void sendMail() {

		Mail mail = mailProvider.get();

		// fill the mail with content:
		mail.setSubject("subject");

		mail.setFrom("yosua161@gmail.com");

		mail.addReplyTo("yosua161@gmail.com");

		mail.setCharset("utf-8");
		mail.addHeader("header1", "value1");
		mail.addHeader("header2", "value2");

		mail.addTo("yosua161@gmail.com");

		mail.setBodyHtml("bodyHtml");

		mail.setBodyText("bodyText");

		// finally send the mail
		try {
			postoffice.send(mail);
		} catch (Exception e) {
			System.out.println("failed");
		}
	}
}
