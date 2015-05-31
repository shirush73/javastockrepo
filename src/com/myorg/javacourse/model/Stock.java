package com.myorg.javacourse.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.algo.model.StockInterface;
import org.algo.service.PortfolioManagerInterface.OPERATION;

import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMENDATION;



public class Stock implements StockInterface{
		
//data members (primitive and objects)

	private String symbol;
	private float ask, bid;
	private Date date; 
	private String htmlDescription;
	private int stockQuantity ;
	private ALGO_RECOMMENDATION recommendation;  // part of enum
	//private OPERATION recommendation1;  // part of enum


	
//c'tor  - initializes the new instance
	


	public Stock(String symobl, float ask, float bid, Date date, int quantity){
		this.setSymbol(symobl);
		this.setAsk(ask);
		this.setBid(bid);
		this.setDate(date.getTime());
		this.setStockQuantity(quantity);
	}
	
//copy c'tor - coping by value  

	public Stock(StockInterface stock){
		
		this(new String (stock.getSymbol()),stock.getAsk(),stock.getBid(),new Date(stock.getDate().getTime()), ((Stock) stock).getStockQuantity());
	}
	
	
	public Stock() {

	}
	
	
// *******************************   other methods *********************************
	


	/************************* getHtmlDescription method **************************
	 
	 * in each stock (instance) prints the details to screen/console  
	 * like a toString function
	 * @return a string with the stock's details
	 */
	
	public String getHtmlDescription(){
		
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		String dateStr = df.format(getDate());
		
		htmlDescription = new String("<b>Stock symbol</b>: "+this.getSymbol()+", <b>Ask</b>: "+this.getAsk()+", <b>Bid</b>: "+this.getBid()+", <b>Date</b>: "+dateStr + ", <b>Quantity</b>: " + this.getStockQuantity());
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

	public void setDate(long num) {
		Date date = new Date(num * 1000);		
		this.date =date;
	}
	
	public int getStockQuantity() {
		return stockQuantity;
	}

	public void setStockQuantity(int stockQuantity) {
		this.stockQuantity = stockQuantity;
	}

	public ALGO_RECOMMENDATION getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(ALGO_RECOMMENDATION recommendation) {
		this.recommendation = recommendation;
	}
	
	/*public OPERATION getRecommendation1() {
		return recommendation1;
	}

	public void setRecommendation1(OPERATION recommendation1) {
		this.recommendation1 = recommendation1;
	}
	*/



	
}
