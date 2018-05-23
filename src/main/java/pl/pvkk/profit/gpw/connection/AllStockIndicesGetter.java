package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.pvkk.profit.shares.StockIndex;

public class AllStockIndicesGetter {

	private final static String URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=showTable&tab=all&lang=PL";

	public Set<StockIndex> getAllStockIndices() throws IOException {
		Document content = GpwUrlConnector.getPageContent(URL);
		Elements html = content.getElementsByTag("html");
		Document table = Jsoup.parse(html.text(), "ISO-8859-9");
		Elements td = table.getElementsByClass("left");
		td.remove(0); // remove table header
		Set<StockIndex> indices = new HashSet<>();
		for(Element element : td) {
			StockIndex stockIndex = new StockIndex();
			stockIndex.setName(element.text());
			stockIndex.setUrl(element.child(0).attr("href"));
		}
		return indices;
	}
}
