package utilities;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;

public class exchangeRateUtils {
	
	static Logger logger;
	
	public exchangeRateUtils() {
		super();
		logger = Logger.getLogger(exchangeRateUtils.class);
	}

	public List<Double> exchangeRates(StringBuffer apiResponseBuffer,
			String sourceCurrency,
			String targetCurrency,
			String sourceAmount){
		
		logger.debug("Performing Currency Conversion");
		
		JSONObject responseJSONObject = new JSONObject(apiResponseBuffer.toString());
		JSONObject exchangeRates = (JSONObject) responseJSONObject.get("rates");
		
		double sourceCurrencyValue = exchangeRates.getDouble(sourceCurrency);
		double targetCurrencyValue = exchangeRates.getDouble(targetCurrency);
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

}
