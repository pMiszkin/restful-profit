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
		Document content = getContentFromUrl(INDICES_URL);
		Document table = Jsoup.parse(content.getElementsByTag("html").text(), "ISO-8859-9");
		Elements td = table.getElementsByClass("left");
		td.remove(0); //remove table header
		return td;
	}
	
	/** 
	 * @return map as map<isin, shareName>
	 * @throws IOException
	 */
	public Map<String, String> getAllShareNamesFromUrl() throws IOException {
		Document content = getContentFromUrl(ALL_SHARES_URL);
		Element table = content.getElementById("footable");
		Element tbody = table.getElementsByTag("tbody").first();
		Map<String, String> shares = new HashMap<String, String>();
		for(Element tr : tbody.children())
			shares.put(tr.child(1).text(), tr.child(0).text());
		return shares;
	}
	
	public Elements getCurrentQuotationsAndStockIndices(String isin) throws IOException {
		Document content = getContentFromUrl(CURR_QUOTATION_URL+isin);
		Element tbody = content.getElementsByTag("tbody").first();
		return tbody.children();
	}
	
	public QuotationsHistory getArchiveQuotations(String isin) throws IOException {
		Document content = getContentFromUrl(CHART1_URL+isin+CHART2_URL);
		String body = content.getElementsByTag("body").text();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(body.substring(1, body.length()-1), QuotationsHistory.class);
	}
	
	private Document getContentFromUrl(String url) throws IOException {
		Connection connect = Jsoup.connect(url).userAgent(USER_AGENT);
		return connect.get();
	}
}
