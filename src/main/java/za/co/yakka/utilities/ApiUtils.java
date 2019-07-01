package za.co.yakka.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONObject;


public class ApiUtils {
	
	static Logger logger;
	
	public ApiUtils() {
		super();
		
		logger = Logger.getLogger(ApiUtils.class);
		
	}

	public List<String> parseJSONObject(StringBuffer apiResponse) {
		
		logger.debug("Parsing String Object contained in string buffer");
		
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
		
	public StringBuffer invokeAPI(String inputUrl) {
		
			logger.debug("Performing GET request to inputURL");
		
			String results;
			StringBuffer response = null;
	
			try {
				URL url = new URL(inputUrl);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");

				int responseCode = connection.getResponseCode();
				
				logger.debug("GET Response Code: "+ responseCode);
				
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
