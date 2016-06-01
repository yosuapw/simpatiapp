package controllers;

import ninja.Result;
import ninja.Results;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class MailController {
	MailService mailService;

	Test test;

	@Inject
	public MailController(MailService mailService, Test test) {
		super();
		this.mailService = mailService;
		this.test = test;
	}

	public Result mail() throws Exception {
		// mailService.sendMail();
		test.sendMail();
		return Results.json().render(Results.ok());
	}

}