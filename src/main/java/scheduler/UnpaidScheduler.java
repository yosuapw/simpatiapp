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
			try {
				sendMail(cart);
				cart.getPayment().setStatus("notified");
				bookDAO.save(cart);
			} catch (Exception e) {
				System.out.println("FAILED TO UPDATE FROM UNPAID TO NOTIFIED");
				e.printStackTrace();
			}
		}
	}

	public void sendMail(Cart cart) throws Exception {

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
		
		postoffice.send(mail);
	}
}
