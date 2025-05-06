package web_scraping;

import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTable;
import com.gargoylesoftware.htmlunit.html.HtmlTableCell;
import com.gargoylesoftware.htmlunit.html.HtmlTableRow;

public class CountriesWikipedia {
	
	private static final String url = "https://en.wikipedia.org/wiki/List_of_countries_and_dependencies_by_population";
	
	public static void main(String[] args) throws Exception {
		
		HtmlPage page;
		
		try (final WebClient webClient = new WebClient(BrowserVersion.getDefault())) {
            webClient.getOptions().setCssEnabled(false);
            webClient.getOptions().setJavaScriptEnabled(false);

			page = webClient.getPage(url);
		}
		
		// get table using a unique feature on the webpage via XPath
		final HtmlTable countriesTable = (HtmlTable) page.getByXPath("//table[@class='wikitable sortable mw-datatable sort-under static-row-numbers sticky-header col1left col5left']").toArray()[0];
		
		for (HtmlTableRow row : countriesTable.getRows()) {
			List<HtmlTableCell> cells = row.getCells();
			if (cells.size() >= 2) { // Ensure there are at least two columns
				String location = cells.get(0).asText();
				String population = cells.get(1).asText();
				System.out.println("" + location + " " + population);
			}
		}
	}
}
