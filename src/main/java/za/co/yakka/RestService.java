package za.co.yakka;
//package za.co.shongs;
//
//
//import javax.ws.rs.ApplicationPath;
//import javax.ws.rs.GET;
//import javax.ws.rs.Path;
//import javax.ws.rs.Produces;
//import javax.ws.rs.QueryParam;
//import javax.ws.rs.core.Application;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//
//@Path("/basic")
//public class RestService {
//
//	// http://localhost:8080/RestExample/resources/MyRestService/sayHello
//	@GET
//	@Path("/sayHello")
//	public String getHelloMsg() {
//		return "Hello World";
//	}
//
//	@GET
//	@Path("/echo")
//	public Response getEchoMsg(@QueryParam("message") String msg) {
//		return Response.ok("Your message was: " + msg).build();
//	}
//}