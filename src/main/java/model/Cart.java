package model;

import java.io.Serializable;
import java.util.List;

import net.binggl.ninja.mongodb.MorphiaModel;

import org.mongodb.morphia.annotations.Entity;

@Entity(value = "cart", noClassnameStored = true)
public class Cart extends MorphiaModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private VeritransResult veritransResult;

	private List<CartItem> cartItems;

	private PersonDetail personDetail;

	private Payment payment;

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public PersonDetail getPersonDetail() {
		return personDetail;
	}

	public void setPersonDetail(PersonDetail personDetail) {
		this.personDetail = personDetail;
	}

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	public VeritransResult getVeritransResult() {
		return veritransResult;
	}

	public void setVeritransResult(VeritransResult veritransResult) {
		this.veritransResult = veritransResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
