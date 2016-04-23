package dao;

import org.junit.Before;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestBase {
    protected Injector injector = Guice.createInjector(new AbstractModule() {
        @Override
        protected void configure() {

            bind(ExcursionDAO.class).to(ExcursionDAOImpl.class);
        }
    });

    @Before
    public void setup () {
        injector.injectMembers(this);
    }
}
