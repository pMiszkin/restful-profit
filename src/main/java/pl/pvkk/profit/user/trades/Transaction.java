package pl.pvkk.profit.user.trades;

import javax.persistence.Entity;


public class Transaction {

	
	private long id;
	private long seller_id;
	private long buyer_id;
	private long share_id;
	private double share_price;
}
