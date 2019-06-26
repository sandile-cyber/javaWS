
package za.co.yakka;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/api")
public class ClientApplication extends Application {

	@Override
	public Set<Class<?>> getClasses() {

		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ClientApi.class);
		
		return classes;
		
	}
}
