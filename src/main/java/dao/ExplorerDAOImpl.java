package dao;

import java.util.List;

import model.Explorer;
import net.binggl.ninja.mongodb.MongoDB;

import com.google.inject.Inject;

public class ExplorerDAOImpl implements ExplorerDAO {
	private MongoDB mongoDB;

	@Inject
	private ExplorerDAOImpl(MongoDB mongoDB) {
		this.mongoDB = mongoDB;
	}

	@Override
	public List<Explorer> getAll() {
		// TODO Auto-generated method stub
		return mongoDB.findAll(Explorer.class);
	}

	@Override
	public Explorer findByLink(String link) {
		// TODO Auto-generated method stub
		return mongoDB.getDatastore().find(Explorer.class).field("link")
				.equal(link).get();
	}

}
