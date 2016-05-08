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

	List<CartItem> cartItems;

	public List<CartItem> getCartItems() {
		return cartItems;
	}

	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
}
