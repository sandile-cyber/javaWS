package za.co.yakka;

import javax.inject.Inject;

import ejb.ExchangeRateAdjustmentBean;

public class ExchangeRateManager {

	
	ExchangeRateAdjustmentBean exRateAdj; 
	
	public ExchangeRateManager() {
		super();	
		exRateAdj = new ExchangeRateAdjustmentBean();
	}
	
	public double adjustRate(String ClientId,
							 String sourceCurrency,
							 String targetCurrency, String amount){
		
		double sourceCurrencyAdjustment = exRateAdj.getAdjustmentRate(sourceCurrency);
		double targetCurrencyAdjustment = exRateAdj.getAdjustmentRate(targetCurrency);
		
		double amountd = Double.parseDouble(amount);
		int ClientIdint = Integer.parseInt(amount);
		
		if (sourceCurrencyAdjustment > targetCurrencyAdjustment) {
		
			System.out.println(sourceCurrencyAdjustment * ClientIdint);
			System.out.println(amountd);
			
			return amountd + (sourceCurrencyAdjustment * ClientIdint);
	
		}

		System.out.println(sourceCurrencyAdjustment * ClientIdint);
		System.out.println(amountd);
		
		return amountd - (targetCurrencyAdjustment * ClientIdint);
		
	}
		
}
