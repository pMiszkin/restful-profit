package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
	
	@PostConstruct
	public boolean addShares() {
		try {
			updateShares(getSharesFromUrl(StockIndexUrl.ALL));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}	
	
	private void updateShares(Elements allShares) {
		Date date = new Date();
		for(Element element : allShares) {
			//just cut a table head when shareName equals "Name"
			if(!element.child(1).text().equals("Name")) {
				//changes for element parsing to double
				List<String> s = new LinkedList<String>();
				int i = 1;
				for(Element e : element.children()) {
					if(e.text().equals("---"))
						s.add("0");
					else
						//its not normal space. it is no-break space!
						s.add(e.text().replaceAll("\u00A0", ""));
				}
				Share share = findShareByShortcut(element.child(2).text());
				if(share == null) {
					share = new Share();
					share.setName(s.get(i++));
					share.setShortcut(s.get(i++));
					sharesDao.createShare(share);
				}
				List<Quotation> quotations = share.getQuotations();
				Quotation quotation = new Quotation();
				quotation.setDate(date);
				i = 3;
				quotation.setCurrency(s.get(i++));
				quotation.setLastTransactionTime(s.get(i++));
				quotation.setReferencePrice(Double.parseDouble(s.get(i++)));
				quotation.setTheoreticalOpenPrice(Double.parseDouble(s.get(i++)));
				quotation.setOpen(Double.parseDouble(s.get(i++)));
				quotation.setLow(Double.parseDouble(s.get(i++)));
				quotation.setHigh(Double.parseDouble(s.get(i++)));
				quotation.setLastClosing(Double.parseDouble(s.get(i++)));
				quotation.setChange(Double.parseDouble(s.get(i++)));
				quotation.setCumulatedVolume(Double.parseDouble(s.get(i++)));
				quotation.setCumulatedValue(Double.parseDouble(s.get(i++)));
				
				quotations.add(quotation);
				share.setQuotations(quotations);
				
				sharesDao.updateQuotationsInShare(share, quotation);
			}
		}	
	}
	
	private Elements getSharesFromUrl(StockIndexUrl stockIndexUrl) throws IOException {
		//document parsing uffff
		Connection connect = Jsoup.connect(stockIndexUrl.toString());
		Document content = connect.get();
		Elements allElements = content.getElementsByTag("html");
		Document table = Jsoup.parse(allElements.text(), "ISO-8859-9");
		Elements allShares = table.getElementsByTag("tr");
		
		//need to remove first and last "tr" object from the table
		allShares.remove(0);
		allShares.remove(allShares.size()-1);
		
		return allShares;
	}
	
	public Share findShareByShortcut(String shortcut) {
		Share share = sharesDao.findShareByShortcut(shortcut.toUpperCase());
		
		return share;
	}
	
	public List<Share> findShares(StockIndexUrl stockIndexUrl) {
		return sharesDao.findSharesTable(stockIndexUrl);
	}
	
	public boolean isShareExists(String shortcut){
		return sharesDao.isShareExists(shortcut);
	}
}
