package pl.pvkk.profit.gpw.connection;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.jsoup.nodes.Element;

import pl.pvkk.profit.domain.shares.Share;

public class SharesBasicDataGetter extends GpwUrlConnector {

	private final static String URL = "https://www.gpw.pl/ajaxindex.php?action=GPWIndexes&start=ajaxPortfolio&format=html&isin=PL9999999995";
	
	public SharesBasicDataGetter() throws IOException {
		super(URL);
	}
	
	/** 
	 * @return shares set with basic data just isin and name
	 */
	public Set<Share> getAllShareBasicData() {
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
