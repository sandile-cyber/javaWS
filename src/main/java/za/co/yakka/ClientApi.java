package za.co.yakka;

import java.util.*;

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
import org.apache.log4j.Logger;

import za.co.yakka.cache.GuavaCache;
import za.co.yakka.ejb.ClientEJB;
import za.co.yakka.jpa.Client;
import za.co.yakka.model.ResponseModel;


@Path("/")
@Stateful
public class ClientApi {

	@Inject
	private ClientEJB clientEJB;

	@Inject
	private GuavaCache guavaCache;

	@Inject
	private ExchangeRateManager exchangeRateManager;

	static Logger logger = Logger.getLogger(ClientApi.class);

	public ClientApi() {
		super();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("client/{id}")
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
	@Path("/clientList")
	public List<Client> getClientList() {

		return clientEJB.getAll();

	}

	@POST
	@Path("/client")
	public void addClient(@QueryParam("id") String id, @QueryParam("name") String name) {
		clientEJB.addClient(Integer.parseInt(id), name);
	}

	@PUT
	@Path("/client")
	public void updateClient(@QueryParam("id") String id, @QueryParam("name") String name) {
		clientEJB.updateClient(Integer.parseInt(id), name);
	}

	@GET
	@Path("/currencyCodes")
	@Produces(MediaType.APPLICATION_JSON)
	public Set<String> getCurrencyCodes() {

		String key = "GBP";

		return guavaCache.getCurrencyCodes(key);

	}

	@POST
	@Path("/exchangeRateQuote")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel getExchangeRateQuote(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String sourceAmount){

		return exchangeRateManager.exchangeRateQuote(sourceCurrency, targetCurrency, sourceAmount );

	}

	@POST
	@Path("/adjustedExchangeRate")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel getAdjustedExchangeRate(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String amount){

		return clientEJB.getAdjustedExchangeRate(id,sourceCurrency,targetCurrency,amount);

	}
}
