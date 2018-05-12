package pl.pvkk.profit.user;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import pl.pvkk.profit.trades.Transaction;

@Entity
public class Pocket {

	@Id
	private String username;
	@Column(scale = 2)
	private BigDecimal money;
	@ElementCollection
	@MapKeyColumn(name = "share_shortcut")
	private Map<String, Integer> shares;
	@OneToMany(fetch = FetchType.LAZY)
	@JsonIgnoreProperties("buyer")
	private List<Transaction> purchases;

	public Pocket() {
		money = new BigDecimal(5000);
		shares = new HashMap<String, Integer>();
		purchases = new ArrayList<Transaction>();
	}

	public void addShares(String shareIsin, int shareNumber) {
        if (shares.containsKey(shareIsin)) {
            int shareSum = shares.get(shareIsin) + shareNumber;
            shares.replace(shareIsin, shareSum);
            if(shareSum == 0)
                shares.remove(shareIsin);
        }
        else {
            shares.put(shareIsin, shareNumber);
        }
    }

    public void addPurchase(Transaction transaction) {
	    purchases.add(transaction);
    }

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public BigDecimal getMoney() {
		return money;
	}

	public void setMoney(BigDecimal money) {
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
