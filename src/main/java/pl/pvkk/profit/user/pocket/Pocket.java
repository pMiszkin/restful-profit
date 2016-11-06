package pl.pvkk.profit.user.pocket;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import pl.pvkk.profit.user.trades.Transaction;

@Entity
public class Pocket {

	@Id
	private String username;
	@Column(scale = 2)
	private double money;
	@ElementCollection
	@MapKeyColumn(name = "share_shortcut")
	private Map<String, Integer> shares;
	@OneToMany
	private List<Transaction> purchases;

	public Pocket() {
		money = 5000;
		shares = new HashMap<String, Integer>();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<Transaction> getPurchases() {
		return purchases;
	}

	public void setPurchases(List<Transaction> purchases) {
		this.purchases = purchases;
	}

}
