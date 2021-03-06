package controllers;


import id.co.veritrans.mdk.v1.VtGatewayConfig;
import id.co.veritrans.mdk.v1.VtGatewayConfigBuilder;
import id.co.veritrans.mdk.v1.VtGatewayFactory;
import id.co.veritrans.mdk.v1.config.EnvironmentType;
import id.co.veritrans.mdk.v1.exception.RestClientException;
import id.co.veritrans.mdk.v1.gateway.VtWeb;
import id.co.veritrans.mdk.v1.gateway.model.Address;
import id.co.veritrans.mdk.v1.gateway.model.CustomerDetails;
import id.co.veritrans.mdk.v1.gateway.model.TransactionDetails;
import id.co.veritrans.mdk.v1.gateway.model.VtResponse;
import id.co.veritrans.mdk.v1.gateway.model.vtweb.VtWebChargeRequest;
import id.co.veritrans.mdk.v1.gateway.model.vtweb.VtWebParam;

import java.util.UUID;

import model.Cart;
import model.VeritransNotif;
import model.VeritransResult;
import ninja.Result;
import ninja.Results;
import ninja.cache.NinjaCache;
import ninja.params.Param;
import ninja.session.Session;

import org.apache.log4j.Logger;

import com.google.inject.Inject;

import dao.BookDAO;

public class BastetController {
	
	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(BastetController.class);
	
	final String PAID_UNVERIFIED = "paid_unverified";

	private BookDAO bookDAO;

	private NinjaCache ninjaCache;

	private Session session;
	
	@Inject
	public BastetController(Session session, NinjaCache ninjaCache, BookDAO bookDAO){
		this.session = session;
		this.bookDAO = bookDAO;
		this.ninjaCache = ninjaCache;
	}
	
	public Result asd() {	// this demonstrate configuring proxy settings using method chaining from the builder class
		
		VtGatewayConfigBuilder vtGatewayConfigBuilder = new VtGatewayConfigBuilder();
		vtGatewayConfigBuilder.setServerKey("VT-server--DKnApbVGMb9DJQeAYTQCu_B");
		vtGatewayConfigBuilder.setClientKey("VT-client-HDXeZLmF6qosm1UB");
		// config for sandbox environment
		vtGatewayConfigBuilder.setEnvironmentType(EnvironmentType.SANDBOX);
		VtGatewayConfig vtGatewayConfig = vtGatewayConfigBuilder.createVtGatewayConfig();
		VtGatewayFactory vtGatewayFactory = new VtGatewayFactory(vtGatewayConfig);
		VtWeb vtWeb = vtGatewayFactory.vtWeb();
		VtWebChargeRequest vtWebChargeRequest = new VtWebChargeRequest();
		//setVtWebChargeRequestValues(vtWebChargeRequest);
		vtWebChargeRequest.setVtWeb(new VtWebParam());
		vtWebChargeRequest.getVtWeb().setCreditCardUse3dSecure(false);
		setVtDirectChargeRequestValues(vtWebChargeRequest);
		VtResponse vtResponse;
		try {
			vtResponse = vtWeb.charge(vtWebChargeRequest);
			if (vtResponse.getStatusCode().equals("201")) {
			    // Redirect user to redirecturl param [vtResponse.getRedirectUrl()]
				return Results.redirect(vtResponse.getRedirectUrl());
			} else {
			    // Handle denied / unexpected response
			}
		} catch (RestClientException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return null;
	}

	
	public void setVtDirectChargeRequestValues(VtWebChargeRequest vtDirectChargeRequest) {
	    vtDirectChargeRequest.setTransactionDetails(new TransactionDetails());
	    vtDirectChargeRequest.setCustomerDetails(new CustomerDetails());

	    vtDirectChargeRequest.getTransactionDetails().setOrderId(UUID.randomUUID().toString());
	    vtDirectChargeRequest.getTransactionDetails().setGrossAmount(10000l);

	    vtDirectChargeRequest.getCustomerDetails().setEmail("yosua161@gmail.com");
	    vtDirectChargeRequest.getCustomerDetails().setFirstName("firstName");
	    vtDirectChargeRequest.getCustomerDetails().setPhone("0123456789");
	    vtDirectChargeRequest.getCustomerDetails().setBillingAddress(new Address());

	    Address billingAddress = vtDirectChargeRequest.getCustomerDetails().getBillingAddress();
	    billingAddress.setAddress("Random Street 6A");
	    billingAddress.setCity("Jakarta Pusat");
	    billingAddress.setPostalCode("12210");
	}
	
	public Result success(Session session, @Param("order_id") String orderId, 
			@Param("status_code") String statusCode,
			@Param("transaction_status") String transactionStatus) {

		String cartId = session.get("cartId");
		Cart cart = null;
		if (cartId != null){
			cart = ninjaCache.get(cartId, Cart.class);
			
			if (cart == null || cart.getPayment() == null) {
				cart = bookDAO.findByID(cartId);
			}
		} 
		if (cart == null) cart = bookDAO.findByOrderId(orderId);
		
		if (cart != null) {
			cart.setVeritransResult(new VeritransResult(orderId, statusCode, transactionStatus));
			cart.getPayment().setStatus(PAID_UNVERIFIED);
			bookDAO.save(cart);
			// Empty the cartId after all completed
			session.put("cartId", null);
		}

        return Results.html();
	}
	
	public Result noti(VeritransNotif notif) {
	   /* try {
	        ServletInputStream inputStream = req.getInputStream();
	        VtResponse vtResponse = VtResponse.deserializeJson(inputStream);

	        // if necessary, we can utilize VtDirect's or VtWeb's Check Transaction Status Feature to make sure the notification is really coming from Veritrans
	        String orderId = vtResponse.getOrderId();
	        vtResponse = vtDirect.status(orderId); // we used VtDirect in this example, however we can use VtWeb too

	        if (vtResponse.getTransactionStatus() == TransactionStatus.SETTLED) {
	            // handle settled / successful charge request
	        } else {
	            // handle denied / unexpected response
	        }
	    } catch (JsonDeserializeException e) {
	        // handle JSON deserialization error
	        ...
	    } finally {
	        resp.setStatus(HttpServletResponse.SC_OK);
	    }*/
        return Results.html();
	}
	
	
	
}
