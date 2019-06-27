package za.co.yakka;

import javax.inject.Inject;

import ejb.ExchangeRateAdjustmentBean;

public class ExchangeRateManager {

	@Inject
	ExchangeRateAdjustmentBean exRateAdj; 
	
	public ExchangeRateManager() {
		super();	
		exRateAdj = new ExchangeRateAdjustmentBean();
	}
	
	public double adjustmentRate(String ClientId,
							 String sourceCurrency,
							 String targetCurrency){
		
		double sourceCurrencyAdjustment = exRateAdj.getAdjustmentRate(sourceCurrency);
		double targetCurrencyAdjustment = exRateAdj.getAdjustmentRate(targetCurrency);
		
		int ClientIdint = Integer.parseInt(ClientId);
		
		if (sourceCurrencyAdjustment > targetCurrencyAdjustment) {
					
			return + (sourceCurrencyAdjustment * ClientIdint);
	
		}
		
		return  - (targetCurrencyAdjustment * ClientIdint);
		
	}
		
}
