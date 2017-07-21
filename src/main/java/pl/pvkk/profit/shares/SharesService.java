package pl.pvkk.profit.shares;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
	
		
	public Share findShareByIsin(String isin) {
		Share share = sharesDao.findShareById(isin.toUpperCase());

		return share;
	}
	
	public List<Share> findAllShares() {
		return sharesDao.findAllShares();
	}
	
	public boolean isShareExists(String isin){
		return sharesDao.isShareExists(isin);
	}
	
	/*
	 * Stock Indices Part
	 */
	
	public List<StockIndex> findAllIndices() {
		return sharesDao.findAllIndices();
	}
}
