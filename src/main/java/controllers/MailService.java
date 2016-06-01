package controllers;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import ninja.utils.NinjaProperties;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MailService {

	NinjaProperties ninjaProperties;

	Properties props;

	Session session;

	Message message;

	@Inject
	public MailService(NinjaProperties ninjaProperties) {
		super();
		this.ninjaProperties = ninjaProperties;
	}

	public void sendMail() {

		final String username = "yosua161@gmail.com";
		final String password = "ChikodanPipi";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");

		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username, password);
					}
				});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("yosua161@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse("yosua161@gmail.com"));
			message.setSubject("Testing Subject");
			message.setText("Dear Mail Crawler,"
					+ "\n\n No spam to my email, please!");

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private void createConnection() {
		session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(ninjaProperties
						.get("mail.username"), ninjaProperties
						.get("mail.password"));
			}
		});
	}

	private void setProperties() {
		props = new Properties();
		props.put("mail.smtp.auth", ninjaProperties.get("mail.smtp.auth"));
		props.put("mail.smtp.starttls.enable",
				ninjaProperties.get("mail.smtp.starttls.enable"));
		props.put("mail.smtp.host", ninjaProperties.get("mail.smtp.host"));
		props.put("mail.smtp.port", ninjaProperties.get("mail.smtp.port"));
	}

}
