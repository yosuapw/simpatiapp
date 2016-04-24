package dao;

import java.util.List;

import model.RoundTrip;
import net.binggl.ninja.mongodb.MongoDB;

import com.google.inject.Inject;

public class RoundTripDAOImpl implements RoundTripDAO {
    private MongoDB mongoDB;
    
    
    @Inject
    private RoundTripDAOImpl(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }

	@Override
	public List<RoundTrip> getAll() {
		return mongoDB.findAll(RoundTrip.class);
	}

	@Override
	public RoundTrip findByLink(String link) {
		return mongoDB.getDatastore().find(RoundTrip.class).field("link")
				.equal(link)
				.get();
	}
    
}
