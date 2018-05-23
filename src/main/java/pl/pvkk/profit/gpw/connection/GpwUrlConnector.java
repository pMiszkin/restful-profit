package pl.pvkk.profit.gpw.connection;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


//TODO fcuk inheritance
class GpwUrlConnector {

	private final static String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
	
	private GpwUrlConnector() {}
	
	public static Document getPageContent(String url) throws IOException  {
		Connection connect = Jsoup.connect(url).userAgent(USER_AGENT);
		return connect.get();
	}
}
