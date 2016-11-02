package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.shares.Quotation;
import pl.pvkk.profit.shares.Share;
import pl.pvkk.profit.shares.SharesDao;
import pl.pvkk.profit.shares.StockIndices;


/**
 * This class is for downloading shares, quotations and stock indexes from the outside site - gpw.pl
 * Here is really heavy @PostConstruct method so you can just comment her if you respect your gpu
 * through that you won't have fill "Share" and "StockIndices" database tables
 */
@Service
public class GpwSharesDownloader {

	@Autowired
	private SharesDao sharesDao;
	
	
	private final String QUOTATIONS_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=all&lang=EN&full=0";
	private final String STOCK_URL = "https://www.gpw.pl/portfele_indeksow";
	private final String INDICES_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWListaSp&start=quotationsTab&gls_isin=";
	
	/*@PostConstruct
	public void addShares() {
		try {
			getAndSetAllStockIndices();
			updateShares(getAllSharesFromUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	private void getAndSetAllStockIndices() throws IOException {
		Connection connect = Jsoup.connect(STOCK_URL);
		Document content = connect.get();
		Elements allElements = content.getElementsByClass("menu01");
		
		for(Element element : allElements)
			for(Element e : element.children()) {
				StockIndices stockIndices = new StockIndices();
				stockIndices.setName(e.text());
				sharesDao.addStockIndex(stockIndices);
			}
	}
	
	private void updateShares(Elements allShares) {
		Date date = new Date();
		
		allShares
			.stream()
			//just cut a table head when shareName equals "Name"
			.filter(element -> !element.child(1).text().equals("Name"))
			.forEach(element -> saveShares(element, date));
	}
	
	private void saveShares(Element element, Date date){
		List<String> s = new ArrayList<String>();
		int i = 1;
		
		for(Element e : element.children()) {
			if(e.text().equals("---"))
				s.add("0");
			else
				//its not normal space. it is no-break space!
				s.add(e.text().replaceAll("\u00A0", ""));
		}
		Share share = sharesDao.findShareByShortcut(element.child(2).text());
		if(share == null) {
			share = new Share();
			share.setName(s.get(i++));
			share.setShortcut(s.get(i++));
			sharesDao.createShare(share);
		}
		
		List<String> indices = null;
		try {
			indices = getShareStockIndices(element.child(1).select("a").attr("href"));
			share.setIndices(indices);
		} catch (IOException e) { e.printStackTrace(); }
		
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
	
	private Elements getAllSharesFromUrl() throws IOException {
		//document parsing uffff
		Connection connect = Jsoup.connect(QUOTATIONS_URL);
		Document content = connect.get();
		Elements allElements = content.getElementsByTag("html");
		Document table = Jsoup.parse(allElements.text(), "ISO-8859-9");
		Elements allShares = table.getElementsByTag("tr");
		
		//need to remove first and last "tr" object from the table
		allShares.remove(0);
		allShares.remove(allShares.size()-1);
		
		return allShares;
	}
	
	private List<String> getShareStockIndices(String shareSiteUrl) throws IOException{
		String shareName = shareSiteUrl.substring(35, shareSiteUrl.length()-1);
		Connection connect = Jsoup.connect(INDICES_URL+shareName);
		Document content = connect.get();
		Elements html = content.getElementsByTag("html");
		Document document = Jsoup.parse(html.text(), "ISO-8859-9");	
		Element td = document.getElementsByTag("td").first();
		
		List<String> indices = new ArrayList<String>();
		for(Element indexName : td.children()) {
			if(!indexName.text().equals(""))
				indices.add(indexName.text());
		}
		
		return indices;
	}	
}
