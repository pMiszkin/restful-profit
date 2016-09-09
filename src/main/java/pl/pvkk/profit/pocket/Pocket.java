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
			shares.remove(name);
		else
			shares.put(name, number);
	}

	@Override
	public String toString() {
		return "Pocket [money=" + money + ", shares=" + shares + "]";
	}
	
}
