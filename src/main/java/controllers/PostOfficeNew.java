package controllers;

import javax.mail.internet.AddressException;

import ninja.postoffice.Mail;
import ninja.postoffice.Postoffice;
import ninja.postoffice.PostofficeConstant;
import ninja.postoffice.commonsmail.CommonsmailHelper;
import ninja.utils.NinjaProperties;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;

import com.google.inject.Inject;

public class PostOfficeNew implements Postoffice {

	private final CommonsmailHelper commonsmailHelper;

	private final String smtpHost;
	private final int smtpPort;
	private final boolean smtpSsl;
	private final String smtpUser;
	private final String smtpPassword;
	private final boolean smtpDebug;

	@Inject
	public PostOfficeNew(CommonsmailHelper commonsmailHelper,
			NinjaProperties props) {
		this.commonsmailHelper = commonsmailHelper;

		this.smtpHost = props.getOrDie(PostofficeConstant.smtpHost);

		Integer smtpPort = props.getInteger(PostofficeConstant.smtpPort);
		if (smtpPort == null) {
			this.smtpPort = 25;
		} else {
			this.smtpPort = smtpPort;
		}

		Boolean smtpSsl = props.getBoolean(PostofficeConstant.smtpSsl);
		if (smtpSsl == null) {
			this.smtpSsl = false;
		} else {
			this.smtpSsl = smtpSsl;
		}

		this.smtpUser = props.get(PostofficeConstant.smtpUser);
		this.smtpPassword = props.get(PostofficeConstant.smtpPassword);

		Boolean smtpDebug = props.getBoolean(PostofficeConstant.smtpDebug);
		if (smtpDebug == null) {
			this.smtpDebug = false;
		} else {
			this.smtpDebug = smtpDebug;
		}
	}

	// May be used for testing
	PostOfficeNew(CommonsmailHelper commonsmailHelper,
			String smtpHost, int smtpPort, boolean smtpSsl, String smtpUser,
			String smtpPassword, boolean smtpDebug) {
		this.commonsmailHelper = commonsmailHelper;
		this.smtpHost = smtpHost;
		this.smtpPort = smtpPort;
		this.smtpSsl = smtpSsl;
		this.smtpUser = smtpUser;
		this.smtpPassword = smtpPassword;
		this.smtpDebug = smtpDebug;
	}

	@Override
	public void send(Mail mail) throws EmailException, AddressException {

		// create a correct multipart email based on html / txt content:
		MultiPartEmail multiPartEmail = commonsmailHelper
				.createMultiPartEmailWithContent(mail);

		multiPartEmail.setStartTLSEnabled(true);
		multiPartEmail.setStartTLSRequired(true);

		// fill the from, to, bcc, css and all other fields:
		commonsmailHelper.doPopulateMultipartMailWithContent(multiPartEmail,
				mail);

		// set server parameters so we can send the MultiPartEmail:
		commonsmailHelper.doSetServerParameter(multiPartEmail, smtpHost,
				smtpPort, smtpSsl, smtpUser, smtpPassword, smtpDebug);

		// And send it:
		multiPartEmail.send();
	}

}
