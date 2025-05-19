package rest;

import java.util.Locale;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.JSONObject;

@Path("/f2c")
public class FtoCService {

	// usage: http://localhost:8080/vvs_rest/services/f2c/10

	@Path("{f}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response convertFtoCfromInput(@PathParam("f") Double fahrenheit) {

		Double celsius =  (fahrenheit - 32)*5/9; 

		JSONObject jsonObject = new JSONObject();
		jsonObject.put("F Value", fahrenheit); 
		jsonObject.put("C Value", celsius);
		jsonObject.put("report", "fahrenheit to celsius converter REST service");

		String result = "" + jsonObject;
		return Response.status(200).entity(result).build();
	}
	
	// usage: http://localhost:8080/vvs_rest/services/f2c/html/10.0
	
	@Path("html/{f}")
	@GET
	@Produces(MediaType.TEXT_HTML + "; charset=UTF-8")
	public String convertFtoCfromInput2HTML(@PathParam("f") Double fahrenheit)  {

		Double celsius = (fahrenheit - 32)*5/9;
		
		return "<html> <head> <title>" + "Hello Jersey" + "</title> </head>" +
               "<body> <p> The temperature of " + fahrenheit + "ºF is " + 
		       String.format(Locale.ENGLISH, "%4.2f", celsius) + 
		       "ºC </p> </body> </html> ";
	}

}
