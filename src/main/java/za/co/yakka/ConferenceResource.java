package za.co.yakka;
//package za.co.shongs;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.MediaType;
//
//
//@Path("/conference")
//public class ConferenceResource {
//	
//	private static List<Conference> conferences = new ArrayList<Conference>();
//	
//	static {
//		conferences.add(new Conference("33rd Degree", "March 19-21st",
//				 "http://2012.33degree.org"));
//		conferences.add(new Conference("555rd Degree", "March 19-21st",
//				 "http://2012.33degree.org"));
//		conferences.add(new Conference("8888rd Degree", "March 19-21st",
//				 "http://2012.33degree.org"));
//	}
//	
//	@GET 
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Conference> getList(){
//		return conferences;
//	}
//	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("{id}")
//	public Conference getConferenceById(@PathParam("id") int id) {
//		return conferences.get(id);
//	}
//
//}
