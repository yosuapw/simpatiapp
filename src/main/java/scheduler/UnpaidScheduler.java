package scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Cart;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.scheduler.Schedule;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import controllers.Helper;
import dao.BookDAO;

@Singleton
public class UnpaidScheduler {

	final String UNPAID = "unpaid";

	@Inject
	Provider<Mail> mailProvider;

	@Inject
	BookDAO bookDAO;
	
	@Inject
	NinjaProperties ninjaProperties;

	@Inject
	Postoffice postoffice;

	@Schedule(delay = 60, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
	public void doStuffEach60Seconds() {
		List<Cart> cartList = bookDAO.findByStatus(UNPAID);

		for (Cart cart : cartList) {
			cart.getPayment().setStatus("NOTIFIED");
			bookDAO.save(cart);
			sendMail(cart);
		}
	}

	public void sendMail(Cart cart) {

		Mail mail = mailProvider.get();

		// fill the mail with content:
		mail.setSubject("subject");

		mail.setFrom("yosi.pws@gmail.com");

		mail.setCharset("utf-8");
		mail.addHeader("header1", "value1");
		mail.addHeader("header2", "value2");

		mail.addTo("yosua161@gmail.com");
		
		String serverName = ninjaProperties.get("fullServerName");

		mail.setBodyHtml("please verify your payment by clicking this <a href='"
				+ Helper.constructValidationForEmail(serverName, cart.getPayment().getLink()) + "'>link</a>");

		mail.setBodyText("bodyText");

		// finally send the mail
		try {
			postoffice.send(mail);
		} catch (Exception e) {
			System.out.println("failed");
		}
	}
}
