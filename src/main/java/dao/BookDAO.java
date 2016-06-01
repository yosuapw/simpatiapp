package dao;

import java.util.List;

import model.Cart;

public interface BookDAO {
	String save(Cart cart);

	Cart findByID(String id);

	List<Cart> findByStatus(String status);
}
