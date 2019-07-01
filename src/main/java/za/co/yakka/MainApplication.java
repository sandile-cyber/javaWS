
package za.co.yakka;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.apache.log4j.Logger;

@ApplicationPath("/clientApi")
public class MainApplication extends Application {
	final static Logger logger = Logger.getLogger(MainApplication.class);
	
	@Override
	public Set<Class<?>> getClasses() {
		
		logger.info("Application started");
		
		Set<Class<?>> classes = new HashSet<Class<?>>();
		classes.add(ClientApi.class);
		
		return classes;
		
	}
}
