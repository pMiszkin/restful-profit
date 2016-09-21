package pl.pvkk.profit.pocket;

import java.util.HashMap;
import java.util.Map;

public class Pocket {

	private static final Pocket instance = new Pocket();
	private double money;
	private Map<Integer, Integer> shares;
	
	private Pocket() {
		money = 5000;
		shares = new HashMap<Integer, Integer>();
	}
	
	public static Pocket getInstance(){
		return instance;
	}
	
	public void setMoney(double money){
		this.money = money;
	}

	public double getMoney(){
		return money;
	}
	
	public Map<Integer, Integer> getShares() {
		return shares;
	}

	public void setShares(int id, int number) {
		if(shares.containsKey(id))
			shares.put(id, shares.get(id)+number);
		else
			shares.put(id, number);
		if(shares.get(id) == 0)
			shares.remove(id);
	}
	public boolean isShareExist(int id, int number){
		return (shares.containsKey(id) && shares.get(id) >= number);
	}

	@Override
	public String toString() {
		return "Pocket [money=" + money + ", shares=" + shares + "]";
	}
	
}
