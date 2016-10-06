package pl.pvkk.profit.user.pocket;

import java.util.LinkedList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import pl.pvkk.profit.gpw.Share;

@Entity
public class Pocket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private double money;
	@OneToMany
	private List<Share> shares;

	public Pocket() {
		money = 5000;
		shares = new LinkedList<Share>();
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

	public List<Share> getShares() {
		return shares;
	}

	public void setShares(List<Share> shares) {
		this.shares = shares;
	}

	@Override
	public String toString() {
		return "Pocket [id=" + id + ", money=" + money + ", shares=" + shares + "]";
	}

}
