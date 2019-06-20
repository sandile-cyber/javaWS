package za.co.yakka;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/rest")
public class App extends Application {
    // All request scoped resources and providers
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(app2.class);
        return classes;
    }

    // all singleton resources and providers
    @Override
    public Set<Object> getSingletons() {
        Set<Object> singletons = new HashSet<>();
        return singletons;
    }
}