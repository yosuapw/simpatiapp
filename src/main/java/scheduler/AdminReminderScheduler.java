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

import dao.BookDAO;

@Singleton
public class AdminReminderScheduler {


	final String CONFIRM_PAYMENT = "confirmPayment";

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
		List<Cart> cartList = bookDAO.findByStatus(CONFIRM_PAYMENT);

		
			try {
				for (Cart cart : cartList) {
					sendMail(cart);
					cart.getPayment().setStatus("confirmPaymentEmailed");
					bookDAO.save(cart);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("FAILED TO UPDATE FROM CONFIRM_PAYMENT TO CONFIRM_PAYMENT_EMAILED");
			}
	}

	public void sendMail(Cart cart) throws Exception {

		Mail mail = mailProvider.get();

		// fill the mail with content:
		mail.setSubject("subject");

		mail.setFrom("yosua161@gmail.com");

		mail.addReplyTo("yosua161@gmail.com");

		mail.setCharset("utf-8");
		mail.addHeader("header1", "value1");
		mail.addHeader("header2", "value2");

		mail.addTo("yosua161@gmail.com");

		mail.setBodyHtml("some payment need to be verified");

		mail.setBodyText("bodyText");

		postoffice.send(mail);
	}
}
