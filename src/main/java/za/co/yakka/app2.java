package za.co.yakka;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/app2")
public class app2 {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello() {
		return " Hey there ! ";
	}
	
}
