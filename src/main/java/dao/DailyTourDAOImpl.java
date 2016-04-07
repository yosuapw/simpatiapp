package dao;

import com.google.inject.Inject;

import model.DailyTour;
import net.binggl.ninja.mongodb.MongoDB;

public class DailyTourDAOImpl implements DailyTourDAO {
    private MongoDB mongoDB;
    
    
    @Inject
    private DailyTourDAOImpl(MongoDB mongoDB) {
        this.mongoDB = mongoDB;
    }


    @Override
    public long countAll() {
        // TODO Auto-generated method stub
        return mongoDB.countAll(DailyTour.class);
    }
    
}
