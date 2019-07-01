package za.co.yakka.ejb;

import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import za.co.yakka.jpa.Quote;
import za.co.yakka.utilities.DbManager;

@Stateless
public class QuotePersistenceEJB {
	
	Quote qInfo;
	DbManager dbManager;
	String persistenceUnit;
	static Logger logger4j;

	public QuotePersistenceEJB() {
		persistenceUnit = "client";
		dbManager = DbManager.getInstance();
		logger4j = Logger.getLogger(QuotePersistenceEJB.class);
	}
	
	public void addQuoteInformation(	
			String uuid,
			int clientId,
			String sourceCurrencyCode,
			String targetCurrencyCode,
			double sourceCurrencyRate,
			double targetCurrencyRate,
			double currencyAdjustmentFactor,
			double amount
			) {
		
		qInfo = new Quote(currencyAdjustmentFactor,
							clientId,
							amount,
							sourceCurrencyCode,
							sourceCurrencyRate,
							targetCurrencyCode,
							targetCurrencyRate,
							uuid);

		try {
			
			dbManager.openEntityManagerConnection(persistenceUnit);
			dbManager.getEntityManager().getTransaction().begin();
			dbManager.getEntityManager().persist(qInfo);
			dbManager.getEntityManager().getTransaction().commit();
			logger4j.debug("Added quote to DB");
			
		}catch(Exception e) {
			
			e.printStackTrace();
			
		}finally {
		
			dbManager.closeEntityManagerConnection();
			logger4j.info("Closed DB Connection");

		}
		
	}

}
