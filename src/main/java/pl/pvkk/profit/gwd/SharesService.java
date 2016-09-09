package pl.pvkk.profit.gwd;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

@Service
public class SharesService {
	
	private String stockIndexUrl;	
	
	private Elements getSharesFromUrl() throws IOException {
		//document parsing uffff
		Connection connect = Jsoup.connect(stockIndexUrl);
		Document content = connect.get();
		Elements allElements = content.getElementsByTag("html");
		Document table = Jsoup.parse(allElements.text());
		Elements allShares = table.getElementsByTag("tr");
		
		//need to remove first and last "tr" object from table
		allShares.remove(0);
		allShares.remove(allShares.size()-1);
		
		return allShares;
	}
	
	public double findSharePriceByName(String name) throws IOException {
		this.stockIndexUrl = StockIndexUrl.ALL.toString();
		for(Element element : getSharesFromUrl()) {
			if(name.equals(element.child(1).text())){
				return Double.parseDouble(element.child(5).text());
			}
		}
		//Share has not found
		return 0;
	}
	
	public List<Share> findShares(String stockIndexUrl) throws IOException {
		this.stockIndexUrl = stockIndexUrl;
		List<Share> shares = new LinkedList<Share>();
		
		
		for(Element element : getSharesFromUrl() ){
			shares.add(new Share.Builder(element.child(1).text())
					.shortcut(element.child(2).text())
					.currency(element.child(3).text())
					.lastTransactionTime(element.child(4).text())
					.referencePrice(element.child(5).text())
					.theoreticalOpenPrice(element.child(6).text())
					.open(element.child(7).text())
					.low(element.child(8).text())
					.high(element.child(9).text())
					.lastClosing(element.child(10).text())
					.change(element.child(11).text())
					.cumulatedVolume(element.child(12).text())
					.cumulatedValue(element.child(13).text())
					.build());
		}	
		return shares;
	}
}
