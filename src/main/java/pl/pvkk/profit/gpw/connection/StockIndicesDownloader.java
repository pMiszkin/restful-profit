package pl.pvkk.profit.gpw.connection;

import static pl.pvkk.profit.gpw.connection.GpwUrlConnector.getPageContent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.pvkk.profit.shares.StockIndex;

public final class StockIndicesDownloader {

	private final static String URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=showTable&tab=all&lang=PL";

	private StockIndicesDownloader() {}
	
	public static Set<StockIndex> download() throws IOException {
		Elements tableRows = extractRows(getPageContent(URL));
		Set<StockIndex> indices = new HashSet<>();
		//TODO convert into stream?
		for(Element row : tableRows) {
			indices.add(createStockIndex(row));
		}
		return indices;
	}
	
	private static Elements extractRows(Document content) {
		Elements html = content.getElementsByTag("html");
		Document table = Jsoup.parse(html.text(), "ISO-8859-9");
		Elements td = table.getElementsByClass("left");
		td.remove(0);  // remove table header
		return td;
	}
	
	private static StockIndex createStockIndex(Element row) {
		return new StockIndex.Builder()
				.setName(row.text())
				.setUrl(row.child(0).attr("href"))
				.build();
	}
}
