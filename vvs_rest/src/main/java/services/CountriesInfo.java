package services;

import java.io.*;
import java.net.URL;
import java.util.*;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.opencsv.CSVReader;


/**
 * A Weblistener class will execute when the webapp starts
 * In this case, we will load the country CSV data into memory so that
 * the country service may work
 * 
 * @author jpn
 *
 */
@WebListener
public class CountriesInfo implements ServletContextListener {
	
	public void contextInitialized(ServletContextEvent event)  { 
		// wildfly will find the resource location for us
		URL f = getClass().getClassLoader().getResource("/data/countries.csv");	
		loadData(f.getPath());
	}
	
	public void contextDestroyed(ServletContextEvent event)  {
		return;
	}
	
	//////////////////
	
	private static Map<String, Country> data = new HashMap<>();

	public static void loadData(String strFile) {
		
		// Herein it's used openCSV, a java library for IO with CSV files
		// ref: http://opencsv.sourceforge.net/  (cf pom.xml for dependency) 
		try (CSVReader reader = new CSVReader(new FileReader(strFile))) {
			String [] row;
			while ((row = reader.readNext()) != null) {
				if (row.length!=4)
					continue; // skip incomplete lines
				try {
					data.put(row[0], 
							 new Country(row[0], 
							             Double.parseDouble(row[1]), 
							             Double.parseDouble(row[2]), 
							             row[3]));
				} catch (Exception e) {};
			}
		} catch (IOException e) {}
		
	}
	
	public static Country getCountry(String code) {
		return data.get(code);
	}

}
