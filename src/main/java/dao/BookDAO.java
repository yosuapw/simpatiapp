package dao;

import model.Cart;

public interface BookDAO {
	String save(Cart cart);

	Cart findByID(String id);
}
