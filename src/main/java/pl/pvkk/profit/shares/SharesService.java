package pl.pvkk.profit.shares;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.domain.shares.CurrentQuotation;
import pl.pvkk.profit.domain.shares.Share;


@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
	
	public Share findShareByIsin(String isin) {
		return sharesDao.findShareById(isin.toUpperCase());
	}

	public boolean isShareExists(String isin) {
		return sharesDao.isShareExists(isin);
	}

	public double getSumOfCosts(Share share, int shareNumber) {
		double sharePrice = share.getQurrentQuotationPrice();
		return sharePrice*shareNumber;
	}
}
