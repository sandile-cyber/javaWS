package za.co.yakka;

import javax.inject.Inject;

import feign.Feign;
import feign.codec.Decoder;
import feign.gson.GsonDecoder;
import za.co.yakka.ejb.ExchangeRateAdjustmentBean;
import za.co.yakka.utilities.ExchangeRateApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExchangeRateManager {

	@Inject
	ExchangeRateAdjustmentBean exRateAdj;

	ExchangeRateApi exchangeRateService;
	
	public ExchangeRateManager() {
		exRateAdj = new ExchangeRateAdjustmentBean();

		exchangeRateService = Feign.builder()
				.decoder(new GsonDecoder())
				.target(ExchangeRateApi.class,"https://api.exchangeratesapi.io");



	}

	public List<Double> exchangeRateQuote(
										  String sourceCurrency,
										  String targetCurrency,
										  String sourceAmount){

		Map<String, Double> response = (Map<String, Double>) exchangeRateService.
															 exchangeRate(sourceCurrency, targetCurrency).get("rates");

		double sourceCurrencyValue = response.get(sourceCurrency);
		double targetCurrencyValue = response.get(targetCurrency);
		double sourceAmountD = Double.parseDouble(sourceAmount);

		List<Double> output = new ArrayList<>();

		if(sourceCurrencyValue < targetCurrencyValue ) {

			output.add(targetCurrencyValue);
			output.add(sourceAmountD * targetCurrencyValue);
			output.add(sourceCurrencyValue);

		}
		else if(sourceCurrencyValue > targetCurrencyValue) {

			output.add(targetCurrencyValue);
			output.add(sourceAmountD / sourceCurrencyValue);
			output.add(sourceCurrencyValue);

		}else {

			output.add(1.);
			output.add(1.);
			output.add(1.);

		}

		return output;
	}
	
	public double adjustmentRate(String ClientId,
							 	String sourceCurrency,
							 	String targetCurrency){
		
		double sourceCurrencyAdjustment = exRateAdj.getAdjustmentRate(sourceCurrency);
		double targetCurrencyAdjustment = exRateAdj.getAdjustmentRate(targetCurrency);
		
		int ClientIdInt = Integer.parseInt(ClientId);
		
		if (sourceCurrencyAdjustment > targetCurrencyAdjustment) {
					
			return + (sourceCurrencyAdjustment * ClientIdInt);
	
		}
		
		return  - (targetCurrencyAdjustment * ClientIdInt);
		
	}
	

}
