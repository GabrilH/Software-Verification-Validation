package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import services.CountriesInfo;
import services.Country;

@Path("/country")
public class CountryService {
	
	// usage: http://localhost:8080/vvs_rest/services/country?code=PT

	@GET
	@Produces("application/xml")
	public String getCountryReport(@QueryParam("code") String code) {
		Country country = CountriesInfo.getCountry(code);
		
		if (country==null)  // invalid code was received
			country = new Country("NA",0.0,0.0,"NA");
		
		return "<country>" + 
           		  "<code>" + country.getCode()      + "</code>" +  
				  "<lat>"  + country.getLatitude()  + "</lat>" +  
				  "<lon>"  + country.getLongitude() + "</lon>" +  
				  "<name>" + country.getName()      + "</name>" +
			   "</country>";  
	}
}
