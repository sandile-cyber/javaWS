package za.co.yakka.ejb;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.apache.log4j.Logger;
import za.co.yakka.ExchangeRateManager;
import za.co.yakka.jpa.Client;
import za.co.yakka.model.ResponseModel;
import za.co.yakka.utilities.DbManager;

/**
 * Session Bean implementation class ClientBean
 */

@Stateless
public class ClientEJB {

	DbManager dbManager;
	String persistenceUnit;

	@Inject
	ExchangeRateManager exchangeRateManager;

	@Inject
	private QuotePersistenceEJB quotePersistence;

	static Logger logger = Logger.getLogger(ClientEJB.class);

	public ClientEJB() {

    	dbManager = DbManager.getInstance();
    	persistenceUnit = "client";

    }
     
    public void updateClient(int id, String name) {

    	Client client = getClient(id);
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    		
    		EntityTransaction entityTransaction = dbManager.getEntityManager().getTransaction();
    		entityTransaction.begin();
    		
    		dbManager.getEntityManager().merge(client);
    		
    		entityTransaction.commit();
    		
    	}
    	
    	catch(Exception e){
    		e.printStackTrace();
    	}
    	
    	finally {
    		dbManager.closeEntityManagerConnection();
    	}
				
	}
    
    public List<Client> getAll(){
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Query q = dbManager.getEntityManager().createQuery("select c from Client c");
    	
    	List<Client> resultList = (List<Client>) q.getResultList();

    	return resultList;
    
    }
    
    public Client getClient(int id) {
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Client client = dbManager.getEntityManager().find(Client.class, id);

    	if (client == null) {
    		return null;
    	}
    	
    	dbManager.closeEntityManagerConnection();

    	return client;
    	
    }
    
    public void addClient(int id, String name) {
    	
    	dbManager.openEntityManagerConnection(persistenceUnit);
    	Client client = new Client();
    	client.setId(id);
    	client.setName(name);
    	
    	try {
    	
	    	dbManager.getEntityManager().getTransaction().begin();
	    	dbManager.getEntityManager().persist(client);
	    	dbManager.getEntityManager().getTransaction().commit();
    	
    	}

    	catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		dbManager.closeEntityManagerConnection();
    	}

    }

    public ResponseModel getAdjustedExchangeRate(int id,
												String sourceCurrency,
												String targetCurrency,
												double sourceAmount){

    	ResponseModel response = exchangeRateManager.exchangeRateQuote(sourceCurrency, targetCurrency, sourceAmount);
    	double CAF = exchangeRateManager.adjustmentRate(id, sourceCurrency, targetCurrency);

    	response.setTargetAmount( response.getTargetAmount() + CAF );


		logger.debug("Calculated Currency Adjustment Factor");

		UUID uuid = UUID.randomUUID();
		String uuidString  = uuid.toString();
		double targetCurrencyRate = response.getExchangeRate();
		double sourceCurrencyRate = response.getSourceCurrency();

		quotePersistence.addQuoteInformation(uuidString,
											id,
											sourceCurrency,
											targetCurrency,
											sourceCurrencyRate,
											targetCurrencyRate,
											CAF,
											sourceAmount);

		logger.debug("Persisted Quote Information...");

    	return response;

    }
}