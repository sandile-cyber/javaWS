package za.co.yakka;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;

import ejb.ClientEJB;
import jpa.Client;

@Path("/client")
public class ClientAPI {

	@Inject
	ClientEJB clientEJB;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("getClient/{id}")
	public Client getClient(@PathParam("id") int id) {
		return clientEJB.getClient(id);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("helloWorld")
	public String helloWorld() {
		return "Hello world \n";
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getClientList")
	public List<Client> getClientList() {

		return clientEJB.getAll();

	}

	@POST
	@Path("/addClient")
	public void addClient(@QueryParam("id") String id, @QueryParam("name") String name) {
		clientEJB.addClient(Integer.parseInt(id), name);
	}

	@PUT
	@Path("/updateClient")
	public void updateClient(@QueryParam("id") String id, @QueryParam("name") String name) {
		clientEJB.updateClient(Integer.parseInt(id), name);
	}

	@GET
	@Path("/getCurrencyCodes")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getCurrencyCodes() {

		String results;
		StringBuffer response = null;

		try {
			// Changed to new Currency API service
			URL url = new URL("https://api.exchangeratesapi.io/latest?base=GBP");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			int responseCode = connection.getResponseCode();
			System.out.println("The response code is "+ responseCode);
			if ((responseCode >= 200) && (responseCode < 300)) {

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
		
		System.out.println("This is what is going on here");
		if (response != null) {
			System.out.println("response is not nulls");
			return parseJSONObject(response);

		}

		return null;

	}

	private List<String> parseJSONObject(StringBuffer apiResponse) {

		JSONObject responseJSONObject = new JSONObject(apiResponse.toString());
		JSONObject currencyCodes = (JSONObject) responseJSONObject.get("rates");

		Iterator keyIterator = currencyCodes.keys();
		List<String> keyList = new ArrayList<String>();

		while (keyIterator.hasNext()) {
			String key = (String) keyIterator.next();
			keyList.add(key);
		}

		return keyList;

	}

}
