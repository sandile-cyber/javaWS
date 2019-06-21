package za.co.yakka;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ejb.ClientEJB;
import jpa.Client;

@Path("/client")
public class ClientAPI {

		@Inject
		ClientEJB clientEJB;
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("getClient/{id}")
		public Client getClient(@PathParam("id") int id) {
			return clientEJB.getClient(id);
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("helloWorld")
		public String helloWorld() {
			return "Hello world \n";
		}
		
		@GET
		@Produces(MediaType.APPLICATION_JSON)
		@Path("/getClientList")
		public List<Client>  getClientList(){
		
			return clientEJB.getAll();
		
		}
		
		@POST
		@Path("/addClient")
		public void addClient(@QueryParam("id") String id, @QueryParam("name") String name) {
			clientEJB.addClient(Integer.parseInt(id), name);
		}
		
		@PUT
		@Path("/updateClient")
		public void updateClient(@QueryParam("id") String id, @QueryParam("name") String name) {
			System.out.println("============= "+id);
			clientEJB.updateClient(Integer.parseInt(id), name);
		}
		
		
	
}
