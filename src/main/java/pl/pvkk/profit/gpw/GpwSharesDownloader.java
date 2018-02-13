package pl.pvkk.profit.gpw;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pl.pvkk.profit.domain.shares.ArchivalQuotation;
import pl.pvkk.profit.domain.shares.CurrentQuotation;
import pl.pvkk.profit.domain.shares.Share;
import pl.pvkk.profit.domain.shares.StockIndex;
import pl.pvkk.profit.gpw.connection.AllStockIndicesGetter;
import pl.pvkk.profit.gpw.connection.ArchivalQuotationsGetter;
import pl.pvkk.profit.gpw.connection.CurrentQuotationGetter;
import pl.pvkk.profit.gpw.connection.SharesBasicDataGetter;
import pl.pvkk.profit.shares.SharesDao;


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
	private ArchivalQuotationService archivalQuotationService;
	
	/**
	 * This is the "init" method for download and save all shares quotations
	 * @throws IOException
	 */
	@Transactional()
	public void addShares() {
		try {
			addAllStockIndices();
			addSharesBasicData();
			addCurrentQuotationsAndStockIndices();
			addAllArchiveQuotations();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Scheduled(fixedRate = 900000, initialDelay = 900000)
	public void updateQuotations() {
		addCurrentQuotationsAndStockIndices();
	}
	
	private void addAllStockIndices() throws IOException {
		AllStockIndicesGetter getter = new AllStockIndicesGetter();
		Set<StockIndex> indices = getter.getAllStockIndices();
		indices.parallelStream().forEach(sharesDao::addStockIndex);
	}
	
	private void addSharesBasicData() throws IOException {
		SharesBasicDataGetter getter = new SharesBasicDataGetter();
		Set<Share> shares = getter.getAllShareBasicData();
		shares.parallelStream().forEach(sharesDao::createShare);
	}
	
	private void addCurrentQuotationsAndStockIndices() {
		Date date = new Date();
		List<Share> shares = sharesDao.findAllShares();
		
		shares.forEach(share -> {
			//move current quotation to archive quotation
			if(share.getCurrentQuotation()!=null) {
				List<ArchivalQuotation> archivalQuotations = new LinkedList<>();
				ArchivalQuotation archive = archivalQuotationService.convertToArchive(share.getCurrentQuotation());
				archivalQuotations.add(archive);
			}
			try {
				CurrentQuotationGetter getter = new CurrentQuotationGetter(share.getIsin());
				CurrentQuotation quotation = getter.getCurrentQuotation(date);
				share.setCurrentQuotation(quotation);
				sharesDao.updateCurrentQuotationInShare(share, quotation);
			} catch (IOException e) {
				e.printStackTrace();
			}

		});
	}
	
	/**
	 * add all time quotations from quotation history
	 * @throws IOException
	 */
	private void addAllArchiveQuotations() throws IOException {
		List<Share> shares = sharesDao.findAllShares();
		for( Share share : shares ) {
			ArchivalQuotationsGetter getter = new ArchivalQuotationsGetter(share.getIsin());
			List<ArchivalQuotation> quotations = getter.getArchivalQuotations();
			share.setArchiveQuotations(quotations);
			sharesDao.updateArchiveQuotationsInShare(share, quotations);
		}
	} 
}
