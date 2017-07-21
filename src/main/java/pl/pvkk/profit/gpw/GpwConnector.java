package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;


@Service
public class GpwConnector {

	
	private final String ALL_SHARES_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=ajaxPortfolio&format=html&isin=PL9999999995";
	private final String CURR_QUOTATION_URL = "https://www.gpw.pl/ajaxindex.php?start=quotationsTab&format=html&action=GPWListaSp&gls_isin=";
	private final String CHART1_URL = "https://www.gpw.pl/chart-json.php?req=[{%22isin%22:%22";
	private final String CHART2_URL = "%22,%22mode%22:%22ARCH%22,%22from%22:null,%22to%22:null}]";
	private final String INDICES_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=showTable&tab=all&lang=PL";
	
	private final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	
	public Elements getAllStockIndices() throws IOException {
		Connection connect = Jsoup.connect(INDICES_URL).userAgent(USER_AGENT);
		Document content = connect.get();
		Document table = Jsoup.parse(content.getElementsByTag("html").text(), "ISO-8859-9");
		Elements td = table.getElementsByClass("left");
		//remove table header
		td.remove(0);
		
		return td;
	}
	
	//returns map as map<isin, shareName>
	public Map<String, String> getAllShareNamesFromUrl() throws IOException {
		Connection connect = Jsoup.connect(ALL_SHARES_URL).userAgent(USER_AGENT);
		Document content = connect.get();
		Element table = content.getElementById("footable");
		Map<String, String> shares = new HashMap<String, String>();
		Element tbody = table.getElementsByTag("tbody").first();
		for(Element tr : tbody.children())
			shares.put(tr.child(1).text(), tr.child(0).text());

		return shares;
	}
	
	public Elements getCurrentQuotationsAndStockIndices(String isin) throws IOException {
		Connection connect = Jsoup.connect(CURR_QUOTATION_URL+isin).userAgent(USER_AGENT);
		Document content = connect.get();
		Element tbody = content.getElementsByTag("tbody").first();
		
		return tbody.children();
	}
	
	public QuotationsHistory getArchiveQuotations(String isin) throws IOException {
		Connection connect = Jsoup.connect(CHART1_URL+isin+CHART2_URL).userAgent(USER_AGENT);
		Document content = connect.get();
		String body = content.getElementsByTag("body").text();
		ObjectMapper mapper = new ObjectMapper();
		
		return mapper.readValue(body.substring(1, body.length()-1), QuotationsHistory.class);
	}
}






