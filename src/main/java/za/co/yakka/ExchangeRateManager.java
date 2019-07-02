package za.co.yakka;

import javax.ejb.Stateless;
import feign.Feign;
import feign.gson.GsonDecoder;
import za.co.yakka.ejb.ExchangeRateAdjustmentEJB;
import za.co.yakka.model.ResponseModel;
import za.co.yakka.utilities.ExchangeRateInterface;

import java.util.Map;

@Stateless
public class ExchangeRateManager {

	ExchangeRateAdjustmentEJB exRateAdj;

	ResponseModel response;

	ExchangeRateInterface exchangeRateService;
	
	public ExchangeRateManager() {
		exRateAdj = new ExchangeRateAdjustmentEJB();
		response = new ResponseModel();

		exchangeRateService = Feign.builder()
				.decoder(new GsonDecoder())
				.target(ExchangeRateInterface.class,"https://api.exchangeratesapi.io");

	}

	public ResponseModel exchangeRateQuote(
										  String sourceCurrency,
										  String targetCurrency,
										  String sourceAmount){

		Map<String, Double> responseMap = exchangeRateService.
															 exchangeRate(sourceCurrency, targetCurrency).getRates();

		double sourceCurrencyValue = responseMap.get(sourceCurrency);
		double targetCurrencyValue = responseMap.get(targetCurrency);
		double sourceAmountD = Double.parseDouble(sourceAmount);

		if(sourceCurrencyValue < targetCurrencyValue ) {

			response.setExchangeRate(targetCurrencyValue);
			response.setTargetAmount(sourceAmountD * targetCurrencyValue);
			response.setSourceCurrency(sourceCurrencyValue);

		}
		else if(sourceCurrencyValue > targetCurrencyValue) {

			response.setExchangeRate(targetCurrencyValue);
			response.setTargetAmount(sourceAmountD / sourceCurrencyValue);
			response.setSourceCurrency(sourceCurrencyValue);

		}else {

			response.setExchangeRate(1.);
			response.setTargetAmount(1.);
			response.setSourceCurrency(1.);

		}

		return response;
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
