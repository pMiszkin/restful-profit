package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import javax.annotation.PostConstruct;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.shares.ArchiveQuotation;
import pl.pvkk.profit.shares.CurrentQuotation;
import pl.pvkk.profit.shares.Share;
import pl.pvkk.profit.shares.SharesDao;
import pl.pvkk.profit.shares.StockIndex;


/**
 * This class downloads shares, quotations and stock indices from another site - gpw.pl
 * Here is really heavy @PostConstruct method so you can just comment it but
 * you won't have filled "Share" and "StockIndices" database tables
 */
@Service
public class GpwSharesDownloader {

	@Autowired
	private SharesDao sharesDao;
	@Autowired
	private GpwConnector gpwConnector;
	//private final String CHART_URL = "https://www.gpw.pl/chart.php?req=O:8:%22stdClass%22:1:{s:1:%220%22;O:8:%22stdClass%22:4:{s:4:%22from%22;i:368135;s:4:%22mode%22;s:1:%22D%22;s:2:%22to%22;i:416608;s:4:%22isin%22;s:12:%22";
	//private final String CHART_URL2 = "%22;}}%C3%97tamp=1499789609646";
	//private final String QUOTATIONS_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWQuotations&start=showTable&tab=all&lang=EN&full=0";
	//private final String STOCK_URL = "https://www.gpw.pl/portfele_indeksow";
	//private final String INDICES_URL = "https://www.gpw.pl/ajaxindex.php?action=GPWListaSp&start=quotationsTab&gls_isin=";
	
	
	
	/**
	 * Data is now stored in files added to the project
	 * @throws SQLException 
	 * @throws IOException
	 */
	
	@PostConstruct
	public void addShares() throws SQLException {
		try {
			setAllStockIndices();
			addShares(gpwConnector.getAllShareNamesFromUrl());
			addCurrentQuotationsAndStockIndices();
			//update all time quotations
			getAllArchiveQuotations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*@Scheduled(fixedRate = 900000, initialDelay = 900000)
	public void updateQuotations() {
		try {
			updateShares(getAllSharesFromUrl());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/
	
	private void setAllStockIndices() throws IOException {
		for(Element element : gpwConnector.getAllStockIndices()) {
			StockIndex stockIndex = new StockIndex();
			stockIndex.setName(element.text());
			stockIndex.setUrl(element.child(0).attr("href"));
			sharesDao.addStockIndex(stockIndex);
		}
	}
	
	private void addShares(Map<String, String> shares) {
		shares.forEach((isin, name) -> {
			Share share = new Share();
			share.setIsin(isin);
			share.setName(name);
			sharesDao.createShare(share);
		});
	}
	
	private void addCurrentQuotationsAndStockIndices() {
		Date date = new Date();
		List<Share> shares = sharesDao.findAllShares();
		
		shares.stream().forEach(share -> {
				try {
					Elements elements = gpwConnector.getCurrentQuotationsAndStockIndices(share.getIsin());
					//save indices
					System.out.println(share.getName()+" "+share.getIsin());
					List<String> indices = new LinkedList<String>();
					elements.first().getElementsByTag("a").forEach(index -> indices.add(index.text()));
					share.setIndices(indices);
					//save quotations
					CurrentQuotation quotation = new CurrentQuotation();
					quotation.setDate(date);
					//parser
					Function<Integer, Double> getValue = index -> {
						try {
							return Double.parseDouble(elements.get(index).child(1)
									.text()
									.replace(",",".")
									.replace("\u00A0", ""));  //no-break space
						} catch(NumberFormatException e) { return (double) 0; }
					};

						
					quotation.setPrice(getValue.apply(2));
					String change = elements.get(3).child(1).text();
					quotation.setChange(Double.parseDouble(change.substring(change.length()-6, change.length()-2)));
					quotation.setBid(getValue.apply(4));
					quotation.setAsk(getValue.apply(5));
					quotation.setMin(getValue.apply(6));
					quotation.setMax(getValue.apply(7));
					quotation.setVolume(getValue.apply(8));
					quotation.setValue(getValue.apply(9));
					
					share.setCurrentQuotation(quotation);
					sharesDao.updateCurrentQuotationInShare(share, quotation);
										
				} catch (IOException e) { e.printStackTrace(); }
				
		});
	}	

	
	//get all time quotations from history
	private void getAllArchiveQuotations() throws IOException {
		List<Share> shares = sharesDao.findAllShares();
		for( Share share : shares ) {
			QuotationsHistory qHistory = gpwConnector.getArchiveQuotations(share.getIsin());
			List<ArchiveQuotation> quotations = new LinkedList<ArchiveQuotation>();
			
			for(Data qData : qHistory.getData()) {
				ArchiveQuotation quotation = new ArchiveQuotation();
				Date date = new Date();
				date.setTime(qData.getT()*360L);
				quotation.setDate(date);
				quotation.setPrice(qData.getC());
				//quotation.setOpen(qData.getO());   WUT
				quotation.setMax(qData.getH());
				quotation.setMin(qData.getL());
				quotation.setVolume(qData.getV());
				
				quotations.add(quotation);
			}
			
			share.setArchiveQuotations(quotations);
			sharesDao.updateArchiveQuotationsInShare(share, quotations);
		}
	}
}
