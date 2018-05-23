package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import pl.pvkk.profit.shares.Share;

public class SharesBasicDataGetter {

	private final static String URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=ajaxPortfolio&format=html&isin=PL9999999995";
	
	/** 
	 * @return shares set with basic data just isin and name
	 * @throws IOException 
	 */
	public Set<Share> getAllShareBasicData() throws IOException {
		Document content = GpwUrlConnector.getPageContent(URL);
		Element table = content.getElementById("footable");
		Element tbody = table.getElementsByTag("tbody").first();
		Set<Share> shares = new HashSet<>();
		for(Element tr : tbody.children()) {
			Share share = new Share();
			share.setIsin(tr.child(1).text());
			share.setName(tr.child(0).text());
			shares.add(share);
		}
		return shares;
	}

}
