package controllers;

public class Helper {

	static final String CART_SESSION = "CART";
	static final String PAYMENT_VALIDATION_LINK = "/book/validate/";

	public static String constructCartSession(String cartId) {
		return CART_SESSION + cartId;
	}

	public static String constructValidationForEmail(String contextPath, String link) {
		return contextPath + PAYMENT_VALIDATION_LINK
				+ link;
	}

	public static String constructValidation() {
		return java.util.UUID.randomUUID().toString();
	}

	public static String constructBookingNumber() {
		return java.util.UUID.randomUUID().toString();
	}

}
