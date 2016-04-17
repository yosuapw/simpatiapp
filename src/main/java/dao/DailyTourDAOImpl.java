package dao;

import java.util.List;

import model.DailyTour;
import net.binggl.ninja.mongodb.MongoDB;

import com.google.inject.Inject;

public class DailyTourDAOImpl implements DailyTourDAO {
    private MongoDB mongoDB;
    
    
    @Inject
    private DailyTourDAOImpl(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }

	@Override
	public List<DailyTour> getAll() {
		return mongoDB.findAll(DailyTour.class);
	}

	@Override
	public DailyTour findByLink(String link) {
		return mongoDB.getDatastore().find(DailyTour.class).field("link")
				.equal(link)
				.get();
	}
    
}
