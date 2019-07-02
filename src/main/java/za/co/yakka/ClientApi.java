package za.co.yakka;

import java.util.*;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import org.apache.log4j.Logger;

import za.co.yakka.cache.GuavaCache;
import za.co.yakka.customException.UserNotFoundException;
import za.co.yakka.ejb.ClientEJB;
import za.co.yakka.jpa.Client;
import za.co.yakka.jpa.Quote;
import za.co.yakka.model.ResponseModel;


@Path("/")
@Stateful
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
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
	@Consumes(MediaType.APPLICATION_JSON)
	public void addClient(Client client) {
		clientEJB.addClient(client.getId(), client.getName());
	}

	@PUT
	@Path("/client")
	public void updateClient(Client client) {
		clientEJB.updateClient(client.getId(), client.getName());
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
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseModel getExchangeRateQuote(Quote quote){

		return exchangeRateManager.exchangeRateQuote(quote.getSourceCurrency(),
													 quote.getTargetCurrency(),
													 quote.getSourceAmount());
	}

	@POST
	@Path("/adjustedExchangeRate")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public ResponseModel getAdjustedExchangeRate(Quote quote){

		return clientEJB.getAdjustedExchangeRate(quote.getClientId(),
												 quote.getSourceCurrency(),
												 quote.getTargetCurrency(),
												 quote.getSourceAmount());

	}
}
