package pl.pvkk.profit.user;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import pl.pvkk.profit.user.pocket.Pocket;
import pl.pvkk.profit.user.trades.Transaction;

@Entity
public class UserProfile {

	@Id
	private String username;
	@OneToOne
	private Pocket pocket;
	@OneToMany(mappedBy = "seller")
	private List<Transaction> sales;
	@OneToMany(mappedBy = "buyer")
	private List<Transaction> purchase;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Pocket getPocket() {
		return pocket;
	}

	public void setPocket(Pocket pocket) {
		this.pocket = pocket;
	}

	public List<Transaction> getSales() {
		return sales;
	}

	public void setSales(List<Transaction> sales) {
		this.sales = sales;
	}

	public List<Transaction> getPurchase() {
		return purchase;
	}

	public void setPurchase(List<Transaction> purchase) {
		this.purchase = purchase;
	}

}
