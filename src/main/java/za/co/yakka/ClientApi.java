package za.co.yakka;

import java.util.List;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import Cache.GuavaCache;
import ejb.ClientEJB;
import jpa.Client;
import utilities.ApiUtils;

@Path("/client")
@Stateful
public class ClientApi {

	@Inject
	ClientEJB clientEJB;

	ApiUtils utilities;
	GuavaCache guavaCache;
	
	ExchangeRateManager exchangeRateManager;

	public ClientApi() {
		super();
		utilities = new ApiUtils();
		guavaCache = new GuavaCache();		
		exchangeRateManager = new ExchangeRateManager();
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

		String key = "Currency";
	
		return guavaCache.getCurrencyCodes(key);

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
	
	@POST
	@Path("/getAdjustedExchangeRate")
	@Produces(MediaType.APPLICATION_JSON)
	public double getAdjustedExchangeRate(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String sourceAmount){
		
		return exchangeRateManager.adjustRate(id, sourceCurrency, targetCurrency, sourceAmount );
	}
}
