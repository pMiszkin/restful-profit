package pl.pvkk.profit.user.trades;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.pvkk.profit.shares.Share;
import pl.pvkk.profit.user.pocket.Pocket;

@Entity
public class Transaction {

	@Id
	@GeneratedValue
	private long id;
	private Date date;
	@ManyToOne
	@JsonIgnoreProperties("purchases")
	private Pocket buyer;
	@ManyToOne(cascade = CascadeType.ALL)
	@JsonIgnoreProperties("transactions")
	private Share share;
	private int share_number;
	private double share_price;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Pocket getBuyer() {
		return buyer;
	}

	public void setBuyer(Pocket buyer) {
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