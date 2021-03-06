package scheduler;

import java.util.List;

import model.Cart;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import dao.BookDAO;

@Singleton
public class FullyPaidScheduler {

	final String FULLY_PAID = "fullyPaid";

	@Inject
	Provider<Mail> mailProvider;

	@Inject
	BookDAO bookDAO;
	
	@Inject
	NinjaProperties ninjaProperties;

	@Inject
	Postoffice postoffice;

//	@Schedule(delay = 60, initialDelay = 5, timeUnit = TimeUnit.SECONDS)
	public void doStuffEach60Seconds() {
		List<Cart> cartList = bookDAO.findByStatus(FULLY_PAID);

			try {
				for (Cart cart : cartList) {
					sendMail(cart);
					cart.getPayment().setStatus("completed");
					bookDAO.save(cart);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("FAILED TO UPDATE FROM FULLY_PAID TO COMPLETED");
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
		
		String serverName = ninjaProperties.get("fullServerName");
		
		StringBuilder sb = new StringBuilder();
		sb.append("Please Print this form to verify that you have pay.<br/>");
		sb.append("Payment Code: A12345");
		sb.append("<table>");
		sb.append("<tr>");
		sb.append("<td>number</td><td>type</td><td>number</td><td>amount</td>");
		sb.append("</tr>");
		for (int i=1;i<=cart.getCartItems().size();i++) {
			sb.append("<tr>");
			sb.append("<td>"+i+"</td>");
			sb.append("<td>"+cart.getCartItems().get(i-1).getType()+"</td>");
			sb.append("<td>"+cart.getCartItems().get(i-1).getNumber()+"</td>");
			sb.append("<td>"+cart.getCartItems().get(i-1).getAmount()+"</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");

		mail.setBodyHtml(sb.toString());

		// finally send the mail
		postoffice.send(mail);
	}
}
