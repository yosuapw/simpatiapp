package dao;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

import conf.Module;

public class DailyTourDAOTest extends TestBase {
    
    @Inject
    private DailyTourDAO dailyTourDAO;
    
    
    @Test
    public void countAll() {
        assertTrue(dailyTourDAO.countAll() > 0);
    }
}
