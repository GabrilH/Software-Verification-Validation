package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/people")
public class PeopleService {

	// usage: http://localhost:8080/vvs_rest/services/people?id=joao

	@GET
	@Produces("application/xml")
	public String getPeopleReport(@QueryParam("id") String user) {
		String report="<info>NA</info>";

		switch (user) {
		  case "joao": 
			report = "<info>" + 
					    "<firstname>" + "joao" + "</firstname>" + 
					    "<lastname>" + "neto" + "</lastname>" + 
					    "<hobbies>" + 
					       "<hobby>" + "abstract games" + "</hobby>" + 
					       "<hobby>" + "movies" + "</hobby>" + 
					       "<hobby>" + "reading" + "</hobby>" + 
					    "</hobbies>" + 
					  "</info>";	
			break;
		  case "sofia": 
			report = "<info>" + 
					    "<firstname>" + "sofia" + "</firstname>" + 
					    "<lastname>" + "neto" + "</lastname>" + 
					    "<hobbies>" + 
					       "<hobby>" + "drawing" + "</hobby>" + 
					       "<hobby>" + "youtube" + "</hobby>" + 
					       "<hobby>" + "reading" + "</hobby>" + 
					    "</hobbies>" + 
					  "</info>";	
			break;
			default:
				report = "<info>" + 
					    "<firstname>" + "unknown" + "</firstname>" + 
					    "<lastname>" + "unknown" + "</lastname>" + 
					    "<hobbies>" + 
					    "</hobbies>" + 
					  "</info>";	

		}
		return report;
	}
}
