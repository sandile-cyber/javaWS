package ejb;

import jpa.CurrencyAdjustment;
import utilities.DbManager;

public class ExchangeRateAdjustmentBean {

	DbManager dbManager;
	
	public ExchangeRateAdjustmentBean() {
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
