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
		// TODO Auto-generated method stub
		return mongoDB.findAll(DailyTour.class);
	}
    
}
