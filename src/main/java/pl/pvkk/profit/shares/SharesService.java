package pl.pvkk.profit.shares;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SharesService {
	
	@Autowired
	private SharesDao sharesDao;
	
		
	public Share findShareByShortcut(String shortcut) {
		Share share = sharesDao.findShareByShortcut(shortcut.toUpperCase());
		
		return share;
	}
	
	public List<Share> findAllShares() {
		return sharesDao.findAllShares();
	}
	
	public boolean isShareExists(String shortcut){
		return sharesDao.isShareExists(shortcut);
	}
	
	/*
	 * Stock Indices Part
	 */
	
	public List<StockIndices> findAllIndices() {
		return sharesDao.findAllIndices();
	}
}
