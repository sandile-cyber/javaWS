package za.co.yakka;

import java.util.ArrayList;
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

import org.json.JSONObject;

import ejb.ClientEJB;
import jpa.Client;

@Path("/client")
public class ClientAPI {


	@Inject
	ClientEJB clientEJB;


	APIUtil utilities;

	
	public ClientAPI() {
		super();
		// TODO Auto-generated constructor stub
		utilities = new APIUtil();
		
	}
	
	
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
	public List<Client> getClientList() {

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
		clientEJB.updateClient(Integer.parseInt(id), name);
	}

	@GET
	@Path("/getCurrencyCodes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCurrencyCodes() {

		String results;
		StringBuffer response = null;

		response = utilities.invokeAPI("https://api.exchangeratesapi.io/latest?base=GBP");
		
		if (response != null) {
			
			return utilities.parseJSONObject(response);

		}

		return null;

	}
	
	@POST
	@Path("/getExchangeRateQuote")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Double> getExchangeRateQuote(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String sourceAmount){
		
		StringBuffer responseBuffer = utilities.invokeAPI("https://api.exchangeratesapi.io/latest?symbols="
								+ sourceCurrency + ","
								+ targetCurrency);
		
		return utilities.exchangeRates(responseBuffer, sourceCurrency, targetCurrency, sourceAmount );
	}
	
	

}
