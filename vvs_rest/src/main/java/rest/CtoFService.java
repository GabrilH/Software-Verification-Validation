package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/c2f")
public class CtoFService {
	
	// usage: http://localhost:8080/vvs_rest/services/c2f/10

	@Path("{c}")
	@GET
	@Produces(MediaType.APPLICATION_XML) 
	public String convertCtoFfromInput(@PathParam("c") Double celsius) {
		Double fahrenheit = ((celsius * 9) / 5) + 32;

		String result = "celsius to fahrenheit converter -- REST service";
		return "<ctofservice>" + 
		  		  "<celsius>" + celsius + "</celsius>" + 
		  		  "<fahrenheit>" + fahrenheit + "</fahrenheit>" + 
		  		  "<report>" + result + "</report>" + 
		  	   "</ctofservice>";	
	}

}
