package Cache;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONObject;

public class Cache {
	private ConcurrentHashMap<String, List<String>> cacheMap;
	
	public Cache() {
		cacheMap = new ConcurrentHashMap<>();
	}
	
	public String addEntry(String key, List<String> value) {
		List<String> status = cacheMap.put(key, value);
		
		if(status == null) {
			return "initially cached";
		}
		
		return "updated cache value";

	}
	
	public String removeEntry(String key) {
		
		List<String> status = cacheMap.remove(key);
		
		if(status == null) {
			return "no value was associated with key";
		}
		
		return "removed value";
		
	}
	
	public boolean contains(String key) {
		
		return cacheMap.containsKey(key);
		
	}
	
	public List<String> getValue(String key) {
		return cacheMap.get(key);
	}
	
}
