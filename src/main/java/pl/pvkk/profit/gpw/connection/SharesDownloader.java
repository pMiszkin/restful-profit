package pl.pvkk.profit.gpw.connection;

import static pl.pvkk.profit.gpw.connection.GpwUrlConnector.getPageContent;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import pl.pvkk.profit.shares.Share;

public class SharesDownloader {

	private final static String URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=ajaxPortfolio&format=html&isin=PL9999999995";
	
	private SharesDownloader() {}
	
	/** 
	 * @return shares set with basic data just isin and name
	 * @throws IOException 
	 */
	public static Set<Share> download() throws IOException {
		Elements tableRows = extractRows(getPageContent(URL));
		Set<Share> shares = new HashSet<>();
		for(Element tr : tableRows) {
			shares.add(createShare(tr));
		}
		return shares;
	}
	
	private static Elements extractRows(Document content) {
		Element table = content.getElementById("footable");
		Element tbody = table.getElementsByTag("tbody").first();
		return tbody.children();
	}
	
	private static Share createShare(Element row) {
		Share share = new Share();
		share.setIsin(row.child(1).text());
		share.setName(row.child(0).text());
		return share;
	}
}
