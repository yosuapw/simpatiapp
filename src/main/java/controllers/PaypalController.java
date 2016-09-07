package controllers;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import ninja.Result;
import ninja.Results;

import org.apache.log4j.Logger;

import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Details;
import com.paypal.api.payments.Item;
import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payee;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import com.paypal.base.rest.PayPalResource;

public class PaypalController { 
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(PaypalController.class);

		
		public Result coba() {
			Result result = Results.html();
			String clientId = "AUxPSA2AL6-sHrUwK1txlCSrhXcSAhyzLS4XuBeOUbj3-v-wBB4564BydjKsT55Xz8SyXjBN6mifflxZ";
			String clientSecret = "EF8KP0AVnUoHaNPgJLLor5AgJK97EenZCmRLeoDzZyHHz97zmccKjiiSj7DOb4ciWmwu_dCcg1vzZWrj";
			InputStream is = PaypalController.class
					.getResourceAsStream("/sdk_config.properties");
			try {
				PayPalResource.initConfig(is);
				Payment createdpayment = null;
				
				APIContext apiContext = null;

				
				APIContext context = new APIContext(clientId, clientSecret, "sandbox");
				
				
				Details details = new Details();
				details.setShipping("1");
				details.setSubtotal("5");
				details.setTax("1");
				
				Amount amount = new Amount();
				amount.setCurrency("USD");
				amount.setTotal("7");
				amount.setDetails(details);
				
				Transaction transaction = new Transaction();
				transaction.setAmount(amount);
				transaction.setDescription("this is the payment description.");
				
				Item item = new Item();
				item.setName("Ground coffe 40 oz")
					.setQuantity("1")
					.setCurrency("USD")
					.setPrice("5");
				
				ItemList itemList = new ItemList();
				List<Item> items = new ArrayList<Item>();
				items.add(item);
				itemList.setItems(items);
				
				transaction.setItemList(itemList);
				List<Transaction> transactions = new ArrayList<Transaction>();
				transactions.add(transaction);
				
				Payer payer = new Payer();
				payer.setPaymentMethod("paypal");
				
				Payee payee = new Payee();
				payee.setAccountNumber("77024194599");
				payee.setEmail("yosua.merchant@gmail.com");
				
				Payment payment = new Payment();
				payment.setIntent("sale");
				payment.setPayer(payer);
			//	payment.setPayee(payee);
				payment.setTransactions(transactions);
				
				
				RedirectUrls redirectUrls = new RedirectUrls();
				String guid = UUID.randomUUID().toString().replaceAll("-", "");
				redirectUrls.setCancelUrl("http://localhost:8080/");
				redirectUrls.setReturnUrl("http://localhost:8080/");
				
				payment.setRedirectUrls(redirectUrls);
				
				createdpayment = payment.create(context);
				
				LOGGER.info("Created payment with id = " + createdpayment.getId() +
						" and status = " + createdpayment.getState());
				
				Iterator<Links> links = createdpayment.getLinks().iterator();
				while(links.hasNext()) {
					Links link = links.next();
					if (link.getRel().equalsIgnoreCase("approval_url")) {
						return Results.redirect(link.getHref());
					}
				}
				
				
			} catch (PayPalRESTException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
}
