package controllers;

public class Helper {

	static final String CART_SESSION = "CART";

	public static String constructCartSession(String cartId) {
		return CART_SESSION + cartId;
	}

	public static String constructValidation() {
		return java.util.UUID.randomUUID().toString();
	}

}
