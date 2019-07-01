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

import feign.Feign;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import org.apache.log4j.Logger;

import za.co.yakka.cache.GuavaCache;
import za.co.yakka.ejb.ClientEJB;
import za.co.yakka.ejb.QuotePersistenceEJB;
import za.co.yakka.jpa.Client;
import za.co.yakka.utilities.ApiUtils;
import za.co.yakka.utilities.ExchangeRateUtils;

@Path("/")
@Stateful
public class ClientApi {

	@Inject
	private ClientEJB clientEJB;

	@Inject
	private ApiUtils ApiUtilities;

	@Inject
	private GuavaCache guavaCache;

	@Inject
	private QuotePersistenceEJB quotePersistence;

	@Inject
	private ExchangeRateUtils exRateUtils;

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
	public List<Double> getExchangeRateQuote(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String sourceAmount){


		return exchangeRateManager.exchangeRateQuote(sourceCurrency, targetCurrency, sourceAmount );

	}

	@POST
	@Path("/adjustedExchangeRate")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Double> getAdjustedExchangeRate(
			@QueryParam("id") String id,
			@QueryParam("sourceCurrency") String sourceCurrency,
			@QueryParam("targetCurrency") String targetCurrency,
			@QueryParam("sourceAmount") String amount){

		StringBuffer responseBuffer = ApiUtilities.invokeAPI("https://api.exchangeratesapi.io/latest?symbols="
															+ sourceCurrency + ","
															+ targetCurrency);

		logger.debug(" Received response buffer from utilities ");

		List<Double> nominalRate = exRateUtils.exchangeRates(responseBuffer,
															sourceCurrency,
															targetCurrency,
															amount);

		logger.debug("Parsed response buffer and return nominal exchange rates");

		Map<String, Double> obj = new HashMap<>();

		double CAF = exchangeRateManager.adjustmentRate(id, sourceCurrency, targetCurrency);

		logger.debug("Calculated Currrency Adjustment Factor");

		UUID uuid = UUID.randomUUID();
		String uuidString  = uuid.toString();
		double sourceAmount = Double.parseDouble(amount);
		double targetCurrencyRate = nominalRate.get(0);
		double sourceCurrencyRate = nominalRate.get(2);
		int idInteger = Integer.parseInt(id);

		quotePersistence.addQuoteInformation(uuidString, idInteger, sourceCurrency,
											targetCurrency,
											sourceCurrencyRate,
											targetCurrencyRate,
											CAF,
											sourceAmount);

		logger.debug("Persisted Quote Information");

		obj.put("Exchange Rate", nominalRate.get(0));
		obj.put("Nominal Exchanged Amount", nominalRate.get(1));
		obj.put("Adjusted Exchange Amount", nominalRate.get(1) + CAF);

		return obj;

	}
}
