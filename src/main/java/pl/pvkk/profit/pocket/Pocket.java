package pl.pvkk.profit.pocket;

import java.util.HashMap;
import java.util.Map;

public class Pocket {

	private static final Pocket instance = new Pocket();
	private double money;
	private Map<String, Integer> shares;
	
	private Pocket() {
		money = 5000;
		shares = new HashMap<String, Integer>();
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
	
	public Map<String, Integer> getShares() {
		return shares;
	}

	public void setShares(String name, int number) {
		if(shares.containsKey(name))
			shares.put(name, shares.get(name)+number);
		else
			shares.put(name, number);
		if(shares.get(name) == 0)
			shares.remove(name);
	}
	public boolean isShareExist(String name, int number){
		return (shares.containsKey(name) && shares.get(name) >= number);
	}

	@Override
	public String toString() {
		return "Pocket [money=" + money + ", shares=" + shares + "]";
	}
	
}
