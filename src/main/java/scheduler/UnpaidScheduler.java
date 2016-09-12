package scheduler;

import java.util.List;
import java.util.concurrent.TimeUnit;

import model.Cart;
import model.CartItem;
import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.scheduler.Schedule;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import dao.BookDAO;

@Singleton
public class UnpaidScheduler {

	final String PAID_UNVERIFIED = "paid_unverified";

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
		List<Cart> cartList = bookDAO.findByStatus(PAID_UNVERIFIED);

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
		StringBuilder sb = new StringBuilder();
		sb.append("<!DOCTYPE html PUBLIC '-//W3C//DTD HTML 4.01 Transitional//EN'");
		sb.append("   'http://www.w3.org/TR/html4/loose.dtd'>");
		sb.append("<html lang='en'>");
		sb.append("<head>");
		sb.append("  <meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
		sb.append("  <meta name='viewport' content='width=device-width, initial-scale=1'> <!-- So that mobile will display zoomed in -->");
		sb.append("  <meta http-equiv='X-UA-Compatible' content='IE=edge'> <!-- enable media queries for windows phone 8 -->");
		sb.append("  <meta name='format-detection' content='telephone=no'> <!-- disable auto telephone linking in iOS -->");
		sb.append("  <title>Single Column</title>");
		sb.append("  <style>");
		sb.append("  </style>");
		sb.append("</head>");
		sb.append("<body style='margin:0; padding:0;' bgcolor='#F0F0F0' leftmargin='0' topmargin='0' marginwidth='0' marginheight='0'>");
		sb.append("");
		sb.append("<!-- 100%% background wrapper (grey background) -->");
		sb.append("<table border='0' width='100%%' height='100%%' cellpadding='0' cellspacing='0' bgcolor='#F0F0F0'>");
		sb.append("  <tr>");
		sb.append("    <td align='center' valign='top' bgcolor='#F0F0F0' style='background-color: #F0F0F0;'>");
		sb.append("");
		sb.append("      <br>");
		sb.append("");
		sb.append("      <!-- 600px container (white background) -->");
		sb.append("      <table border='0' width='600' cellpadding='0' cellspacing='0' class='container' style='width: 600px;max-width: 600px;'>");
		sb.append("        <tr>");
		sb.append("          <td class='container-padding header' align='left' style='font-family: Helvetica, Arial, sans-serif;font-size: 24px;font-weight: bold;padding-bottom: 12px;color: #3a444e;padding-left: 10px;padding-right: 24px;'>");
		sb.append("            Simpati Tour");
		sb.append("          </td>");
		sb.append("        </tr>");
		sb.append("        <tr>");
		sb.append("          <td class='container-padding content' align='left' style='padding-left: 24px;padding-right: 24px;padding-top: 12px;padding-bottom: 12px;background-color: #ffffff;'>");
		sb.append("            <br>");
		sb.append("");
		sb.append("<div class='title' style='font-family: Helvetica, Arial, sans-serif;font-size: 18px;font-weight: 600;color: #374550;'>Booking</div>");
		sb.append("<br>");
		sb.append("");
		sb.append("<div class='body-text' style='font-family: Helvetica, Arial, sans-serif;font-size: 14px;line-height: 20px;text-align: left;color: #333333;'>");
		sb.append("  your booking has been created with this booking code <strong>%s</strong>");
		sb.append("  <br><br>");
		sb.append("</div>");
		sb.append("");
		sb.append("          </td>");
		sb.append("        </tr>");
		
		

		sb.append("        <tr>");
		sb.append("        <td>");
		sb.append("<table border='1' width='600' cellpadding='0' cellspacing='0' class='container' style='width: 600px;max-width: 600px;'>");
		sb.append("			<thead>");
		sb.append("				<tr>");
		sb.append("					<th>#</th>");
		sb.append("					<th>destination</th>");
		sb.append("					<th>type</th>");
		sb.append("					<th>qty</th>");
		sb.append("					<th>date</th>");
		sb.append("					<th>amount</th>");
		sb.append("				</tr>");
		sb.append("			</thead>");
		sb.append("			<tbody>");
		int i = 1;
		for (CartItem item : cart.getCartItems()){
			sb.append("				<tr>");
			sb.append("					<td>");
			sb.append(i);
			sb.append("					</td>");
			sb.append("					<td>");
			sb.append(item.getItem());
			sb.append("					</td>");
			sb.append("					<td>");
			sb.append(item.getPriceType());
			sb.append("					</td>");
			sb.append("					<td>");
			sb.append(item.getNumber());
			sb.append("					</td>");
			sb.append("					<td>");
			sb.append(item.getBookDate());
			sb.append("					</td>");
			sb.append("					<td>");
			sb.append(item.getTotal());
			sb.append("					</td>");
			sb.append("				</tr>");
			i++;
		}
		sb.append("			</tbody>");
		sb.append("</table>");
		sb.append("  <br><br>");
		sb.append("				</td>");
		sb.append("				</tr>");
		

		sb.append("        <tr>");
		sb.append("          <td class='container-padding footer-text' align='left' style='font-family: Helvetica, Arial, sans-serif;font-size: 12px;line-height: 16px;color: #aaaaaa;padding-left: 24px;padding-right: 24px;'>");
		sb.append("            <br><br>");
		sb.append("            Simpati Tour & Travel &copy; 2016 Bali.");
		sb.append("            <br><br>");
		sb.append("");
		sb.append("            You are receiving this email because you have created a booking on our website.");
		sb.append("            <br><br>");
		sb.append("			");
		sb.append("            <span class='ios-footer'>");
		sb.append("              P Bungin II/33.<br>");
		sb.append("              Denpasar, Bali<br>");
		sb.append("            </span>");
		sb.append("            <a href='http://www.simpati.herokuapp.com' style='color: #aaaaaa;'>www.simpatiapp.com</a><br>");
		sb.append("");
		sb.append("            <br><br>");
		sb.append("");
		sb.append("          </td>");
		sb.append("        </tr>");
		sb.append("			</tbody>");
		sb.append("</table>");
		sb.append("        </tr>");
		sb.append("      </table><!--/600px container -->");
		sb.append("");
		sb.append("");
		sb.append("    </td>");
		sb.append("  </tr>");
		sb.append("</table><!--/100%% background wrapper-->");
		sb.append("");
		sb.append("</body>");
		sb.append("</html>");
		
		mail.setBodyHtml(String.format(sb.toString(),cart.getVeritransResult().getOrderId()).replace("%%", "%"));

		mail.setBodyText("bodyText");
		
		postoffice.send(mail);
	}
}
