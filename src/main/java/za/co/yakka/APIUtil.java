package za.co.yakka;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.Stateless;

import org.json.JSONObject;

@Stateless
public class APIUtil {
		
	public APIUtil() {
		super();
	}

	public List<String> parseJSONObject(StringBuffer apiResponse) {

		JSONObject responseJSONObject = new JSONObject(apiResponse.toString());
		JSONObject currencyCodes = (JSONObject) responseJSONObject.get("rates");

		Iterator<String> keyIterator = currencyCodes.keys();
		List<String> keyList = new ArrayList<String>();

		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			keyList.add(key);
		}

		return keyList;

	}
	
	public List<Double> exchangeRates(StringBuffer apiResponseBuffer,
			String sourceCurrency,
			String targetCurrency,
			String sourceAmount){
		
		JSONObject responseJSONObject = new JSONObject(apiResponseBuffer.toString());
		JSONObject exchangeRates = (JSONObject) responseJSONObject.get("rates");
		
		double sourceCurrencyValue = exchangeRates.getDouble(sourceCurrency);
		double targetCurrencyValue = exchangeRates.getDouble(targetCurrency);
		double sourceAmountD = Double.parseDouble(sourceAmount);

		List<Double> output = new ArrayList<>();
		
		if(sourceCurrencyValue < targetCurrencyValue ) {
			
			output.add(targetCurrencyValue);
			output.add(sourceAmountD * targetCurrencyValue);
			
		}
		else if(sourceCurrencyValue > targetCurrencyValue) {
			
			output.add(targetCurrencyValue);
			output.add(sourceAmountD / sourceCurrencyValue);
			
		}else {
			output.add(1.);
			output.add(1.);
		}
		
		return output;
	
	}
	
	public StringBuffer invokeAPI(String inputUrl) {
			
			String results;
			StringBuffer response = null;
	
			try {
				URL url = new URL(inputUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				int responseCode = connection.getResponseCode();
				
				System.out.println("Response Code: "+ responseCode);
				
				if ((responseCode >= 200) && (responseCode < 400)) {
	
					BufferedReader inputBufferedReader = new BufferedReader(
							new InputStreamReader(connection.getInputStream()));
	
					response = new StringBuffer();
	
					while ((results = inputBufferedReader.readLine()) != null) {
						response.append(results);
						}
	
					inputBufferedReader.close();
	
				}
	
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return response;
		}
}
