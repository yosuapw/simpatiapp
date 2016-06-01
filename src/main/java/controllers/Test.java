package controllers;


import javax.mail.internet.AddressException;

import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;

import org.apache.commons.mail.EmailException;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class Test {

	@Inject
	Provider<Mail> mailProvider;

	@Inject
	Postoffice postoffice;

	public void sendMail() throws Exception {

		Mail mail = mailProvider.get();

		// fill the mail with content:
		mail.setSubject("subject");

		mail.setFrom("yosua161@gmail.com");

		mail.setCharset("utf-8");
		mail.addHeader("header1", "value1");
		mail.addHeader("header2", "value2");

		mail.addTo("yosua161@gmail.com");

		mail.setBodyHtml("bodyHtml");

		mail.setBodyText("bodyText");

		// finally send the mail
		try {
			postoffice.send(mail);
		} catch (EmailException | AddressException e) {
			// ...
		}
	}

}
