package za.co.yakka.ejb;

import za.co.yakka.jpa.CurrencyAdjustment;
import za.co.yakka.utilities.DbManager;

public class ExchangeRateAdjustmentEJB {

	DbManager dbManager;
	
	public ExchangeRateAdjustmentEJB() {
		super();
		dbManager = DbManager.getInstance();
	}

	public double getAdjustmentRate(String key){
		
		String persistenceUnit = "client";

		dbManager.openEntityManagerConnection(persistenceUnit);
		CurrencyAdjustment ca = dbManager.getEntityManager().find(CurrencyAdjustment.class, key);
		dbManager.closeEntityManagerConnection();
		
		return ca.getAdjustmentPercentage();
	
	}
}
