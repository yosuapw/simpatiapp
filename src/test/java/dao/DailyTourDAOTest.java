package dao;

import org.junit.Test;

import com.google.inject.Inject;

public class DailyTourDAOTest extends TestBase {
    
    @Inject
    private DailyTourDAO dailyTourDAO;
    
    
    @Test
    public void countAll() {
		// assertTrue(dailyTourDAO.countAll() > 0);
    }
}
