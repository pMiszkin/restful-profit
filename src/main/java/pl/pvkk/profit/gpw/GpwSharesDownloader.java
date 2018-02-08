package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.domain.shares.ArchivalQuotation;
import pl.pvkk.profit.domain.shares.CurrentQuotation;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.shares.StockIndex;
import pl.pvkk.profit.shares.SharesDao;
import pl.pvkk.profit.shares.SharesService;


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
	private SharesService sharesService;
	@Autowired
	private GpwConnector gpwConnector;
	
	/**
	 * This is the "init" method for download and save all shares quotations
	 * @throws IOException
	 */
	public void addShares() {
		try {
			setAllStockIndices();
			addShares(gpwConnector.getAllShareNamesFromUrl());
			addCurrentQuotationsAndStockIndices();
			getAllArchiveQuotations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 900000, initialDelay = 900000)
	public void updateQuotations() {
		addCurrentQuotationsAndStockIndices();
	}
	
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
				//move current quotation to archive quotation
				if(share.getCurrentQuotation()!=null) {
					List<ArchivalQuotation> archivalQuotations = new LinkedList<>();
					ArchivalQuotation archive = sharesService.convertToArchive(share.getCurrentQuotation());
					archivalQuotations.add(archive);
				}
				try {
					Elements elements = gpwConnector.getCurrentQuotationsAndStockIndices(share.getIsin());
					//save indices
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
					
					String currency = elements.get(1).child(0).text();
					quotation.setCurrency(currency.substring(currency.indexOf("(")+1, currency.indexOf(")")));
					quotation.setPrice(getValue.apply(2));
					String change = elements.get(3).child(1).text();
					quotation.setChange(Double.parseDouble(change.substring(change.indexOf("(")+1, change.indexOf(")")-1)));
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
	
	/**
	 * get all time quotations from quotation history
	 * @throws IOException
	 */
	private void getAllArchiveQuotations() throws IOException {
		List<Share> shares = sharesDao.findAllShares();
		for( Share share : shares ) {
			ArchivalQuotationHistory quotationHistory = gpwConnector.getArchiveQuotations(share.getIsin());
			List<ArchivalQuotationUnformatted> unformatted = quotationHistory.getArchivalQuotationUnformatted();
			List<ArchivalQuotation> quotations = unformatted.stream()
					.map(sharesService::convertToArchive)
					.collect(Collectors.toList());
			share.setArchiveQuotations(quotations);
			sharesDao.updateArchiveQuotationsInShare(share, quotations);
		}
	}
}
