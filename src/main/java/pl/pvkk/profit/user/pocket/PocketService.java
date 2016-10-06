package pl.pvkk.profit.user.pocket;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pl.pvkk.profit.gpw.Share;

@Service
public class PocketService {
	
	@Autowired
	private PocketDao pocketDao;
	
	public Pocket getPocketById(long id) {
		return pocketDao.getPocketById(id);
	}
	
	public boolean isShareExistsInPocket(Pocket pocket, long shareId, int shareNumber) {
		List<Share> shares = pocket.getShares();		
		
		return shares.contains(shareId);
	}
	
	public void setShares(Pocket pocket, long shareId, int shareNumber) {
		List<Share> shares = pocket.getShares();
		
		
		pocket.setShares(shares);
	}

}
