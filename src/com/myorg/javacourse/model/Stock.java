package com.myorg.javacourse.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** Class Stock - Javadocs
 * this class includes three instances about stocks data
 * Each stock is a new instance and includes some members (symbul, bid, ask etc..)
 * It has a c'tor which initialized by data as following and the second c'tor is got object - copy c'tor 
 * @author shirush
 */

public class Stock {
//static members
	public static final int BUY = 0;
	public static final int SELL = 1;
	public static final int REMOVE = 2;
	public static final int HOLD = 3;
	
	/*public enum Status{  // next assignment
		BID, SELL, REMOVE, HOLD
	}
	*/
	
//data members (primitive and objects)
	private String symbol;
	private float ask, bid;
	private Date date; 
	private String htmlDescription;
	private int recommendation, stockQuantity;
	
	
//c'tor  - initializes the new instance 
	public Stock(String symobl, float ask, float bid, Date date){
		this.setSymbol(symobl);
		this.setAsk(ask);
		this.setBid(bid);
		this.setDate(date);
	}
	
//copy c'tor - coping by value  

	public Stock(Stock stock){
		
		this(new String (stock.getSymbol()),stock.getAsk(),stock.getBid(),new Date(stock.getDate().getTime()));
	}
	
	
//methods
	
	/**getHtmlDescription method in each stock (instance) prints the details to screen/console  
	 * like a toString function
	 * @return a string with the stock's details
	 */
	
	public String getHtmlDescription(){
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = df.format(getDate());
		
		htmlDescription = new String("<b>Stock symbol</b>: "+this.getSymbol()+", <b>Ask</b>: "+this.getAsk()+", <b>Bid</b>: "+this.getBid()+", <b>Date</b>: "+dateStr);
		return htmlDescription;
	}
	
//Getters and Setters
	
	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public float getAsk() {
		return ask;
	}

	public void setAsk(float ask) {
		this.ask = ask;
	}

	public float getBid() {
		return bid;
	}

	public void setBid(float bid) {
		this.bid = bid;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}