package helper;

import java.util.UUID;

public class CheckoutHelper {

	public static String generateVerificationLink() {

		return UUID.randomUUID().toString().replaceAll("-", "");
	}

}
