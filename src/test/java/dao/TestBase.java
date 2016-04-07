package dao;

import org.junit.Before;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestBase {
    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {

            bind(DailyTourDAO.class).to(DailyTourDAOImpl.class);
        }
    });

    @Before
    public void setup () {
        injector.injectMembers(this);
    }
}
