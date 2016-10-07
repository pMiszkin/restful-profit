package pl.pvkk.profit.user.pocket;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;

@Entity
public class Pocket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double money;
	@ElementCollection
    @MapKeyColumn(name="share_shortcut")
	private Map<String, Integer> shares;

	public Pocket() {
		money = 5000;
		shares = new HashMap<String, Integer>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
	}

	public Map<String, Integer> getShares() {
		return shares;
	}

	public void setShares(Map<String, Integer> shares) {
		this.shares = shares;
	}

	@Override
	public String toString() {
		return "Pocket [id=" + id + ", money=" + money + ", shares=" + shares + "]";
	}

}
