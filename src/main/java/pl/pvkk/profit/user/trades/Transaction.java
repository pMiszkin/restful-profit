package pl.pvkk.profit.user.trades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import pl.pvkk.profit.gpw.Share;
import pl.pvkk.profit.user.UserProfile;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne
	private UserProfile seller;
	@ManyToOne
	private UserProfile buyer;
	@OneToOne
	private Share share;
	private int share_number;
	private double share_price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public UserProfile getSeller() {
		return seller;
	}

	public void setSeller(UserProfile seller) {
		this.seller = seller;
	}

	public UserProfile getBuyer() {
		return buyer;
	}

	public void setBuyer(UserProfile buyer) {
		this.buyer = buyer;
	}

	public Share getShare() {
		return share;
	}

	public void setShare(Share share) {
		this.share = share;
	}

	public int getShare_number() {
		return share_number;
	}

	public void setShare_number(int share_number) {
		this.share_number = share_number;
	}

	public double getShare_price() {
		return share_price;
	}

	public void setShare_price(double share_price) {
		this.share_price = share_price;
	}

}
