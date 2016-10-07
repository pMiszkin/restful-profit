package pl.pvkk.profit.user.pocket;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PocketService {

	@Autowired
	private PocketDao pocketDao;

	public Pocket getPocketById(long id) {
		return pocketDao.getPocketById(id);
	}

	public boolean isShareExistsInPocket(Pocket pocket, String shareShortcut, int shareNumber) {
		Map<String, Integer> shares = pocket.getShares();

		return shares.containsKey(shareShortcut.toUpperCase());
	}

	public void setShares(Pocket pocket, String shareShortcut, int shareNumber, double sharePrice) {
		Map<String, Integer> shares = pocket.getShares();

		if (!shares.containsKey(shareShortcut))
			shares.put(shareShortcut, shareNumber);
		else {
			int shareFromPocketNumber = shares.get(shareShortcut);

			if (shareNumber + shareFromPocketNumber == 0)
				shares.remove(shareShortcut);
			else
				shares.replace(shareShortcut, shareNumber + shareFromPocketNumber);
		}
		
		pocket.setShares(shares);
		pocket.setMoney(pocket.getMoney()-sharePrice*shareNumber);
		pocketDao.updateSharesAndMoneyInPocket(pocket);
	}

}
