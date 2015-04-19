package com.myorg.javacourse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Stock {
//static members
	
//data members
	private String symbol;
	private float ask, bid;
	private Date date; 
	private String htmlDescription;

	
//c'tors
	public Stock(String symobl, float ask, float bid, Date date){
		this.setSymbol(symobl);
		this.setAsk(ask);
		this.setBid(bid);
		this.setDate(date);
	}
	
//methods
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
