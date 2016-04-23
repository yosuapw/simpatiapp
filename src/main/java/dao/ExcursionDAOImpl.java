package dao;

import java.util.List;

import model.Excursion;
import net.binggl.ninja.mongodb.MongoDB;

import com.google.inject.Inject;

public class ExcursionDAOImpl implements ExcursionDAO {
    private MongoDB mongoDB;
    
    
    @Inject
    private ExcursionDAOImpl(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }

	@Override
	public List<Excursion> getAll() {
		return mongoDB.findAll(Excursion.class);
	}

	@Override
	public Excursion findByLink(String link) {
		return mongoDB.getDatastore().find(Excursion.class).field("link")
				.equal(link)
				.get();
	}
    
}
