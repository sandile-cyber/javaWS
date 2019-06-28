package Cache;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.log4j.Logger;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import utilities.ApiUtils;

public class GuavaCache {

	LoadingCache<String, List<String>> currencyCodes;
	ApiUtils utilities;
	static Logger logger;
	
	
	public GuavaCache() {
		super();

		logger = Logger.getLogger(GuavaCache.class);
		utilities = new ApiUtils();
		
		currencyCodes = CacheBuilder.newBuilder()
				.build(new CacheLoader<String, List<String>>(){
			
					@Override
					public List<String> load(String key) throws Exception {
						
						StringBuffer response = null;
						List<String> currencyCodes;

						response = utilities.invokeAPI("https://api.exchangeratesapi.io/latest?base=GBP");
						currencyCodes = utilities.parseJSONObject(response);
						
						return currencyCodes;
						
					}
				});
	}
	
	public List<String> getCurrencyCodes(String key){
		logger.debug("Fetching from Guava Cache");
		try {
		
			return currencyCodes.get(key);
		
		}
		catch( ExecutionException e) {
		
			e.printStackTrace();
		
		}	
		
		return null;
	}
	
	
	public void addCurrencyCodesList(String key, List<String> value) {
		
		currencyCodes.put(key, value);
	
	}

	

}
