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
	
	public double getQurrentQuotationPrice(Share share) {
		CurrentQuotation quotation = share.getCurrentQuotation();
		return quotation.getPrice();
	}
}
