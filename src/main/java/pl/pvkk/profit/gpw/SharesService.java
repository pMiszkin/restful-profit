package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
		
	private Elements getSharesFromUrl(StockIndexUrl stockIndexUrl) throws IOException {
		//document parsing uffff
		Connection connect = Jsoup.connect(stockIndexUrl.toString());
		Document content = connect.get();
		Elements allElements = content.getElementsByTag("html");
		Document table = Jsoup.parse(allElements.text(), "ISO-8859-9");
		Elements allShares = table.getElementsByTag("tr");
		
		//need to remove first and last "tr" object from table
		allShares.remove(0);
		allShares.remove(allShares.size()-1);
		
		return allShares;
	}
	
	public Share findShareById(long id) {
		Share share = sharesDao.finShareById(id);
		if(share == null)
			return null;
		if(share.toString().equals(""))
			System.out.println("waaa");
		return share;
	}
	
	public List<Share> findShares(StockIndexUrl stockIndexUrl) {
		return sharesDao.findSharesTable(stockIndexUrl);
	}
	
	public boolean isShareExists(long id){
		return sharesDao.isShareExists(id);
	}
	
	@PostConstruct
	public boolean addShares() {
		try {
			sharesDao.updateShares(getSharesFromUrl(StockIndexUrl.ALL));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
