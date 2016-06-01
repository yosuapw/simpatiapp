
package dao;

import java.util.List;

import model.Cart;
import net.binggl.ninja.mongodb.MongoDB;

import org.bson.types.ObjectId;

import com.google.inject.Inject;

public class BookDAOImpl implements BookDAO {
	private MongoDB mongoDB;

	@Inject
	public BookDAOImpl(MongoDB mongoDB) {
		this.mongoDB = mongoDB;
	}

	@Override
	public String save(Cart cart) {
		// TODO Auto-generated method stub
		String id = null;
		if (cart.getId() == null) {
			ObjectId newId = new ObjectId();
			cart.setId(newId);
			id = newId.toString();
		} else {
			id = cart.getId().toString();
		}
		mongoDB.save(cart);
		return id.toString();
	}

	@Override
	public Cart findByID(String id) {
		// TODO Auto-generated method stub
		return mongoDB.findById(new ObjectId(id), Cart.class);
	}

	@Override
	public List<Cart> findByStatus(String status) {
		// TODO Auto-generated method stub
		return mongoDB.getDatastore()
				.find(Cart.class, "payment.status", status).asList();
	}
}
