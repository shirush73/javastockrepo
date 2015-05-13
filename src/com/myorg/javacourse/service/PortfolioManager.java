package com.myorg.javacourse.service;

import java.util.Calendar;
import java.util.Date;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Stock;

public class PortfolioManager {
	
//consts members
	
//data members
	Stock stock1, stock2, stock3 ;
	
//c'tor
	public PortfolioManager(){  // there is a default already
		
	}
	
//methods
	
	public Portfolio getPortfolio(String title){
		
		Calendar cal =  Calendar.getInstance();
		cal.set(2014, 11, 15);
		Date date1 = cal.getTime();
		
		cal.set(2014, 11, 15);
		Date date2 = cal.getTime();
		
		cal.set(2014, 11, 15);
		Date date3 = cal.getTime();
		
		Portfolio myPortfolio = new Portfolio(" Exercise 7 Portfolio"); // the string's name is called in servlet
		
		myPortfolio.setBalance(10000);
		
		
		stock1 = new Stock("PIH", (float)10.0, (float)8.5,  date1, 0);
		stock2 = new Stock("AAL", (float)30.0, (float)25.5, date2, 0);
		stock3 = new Stock("CAAS",(float)20.0, (float)15.5, date3, 0);
		
		// calling to functions //
		
		myPortfolio.buyStock(stock1, 20);
		myPortfolio.buyStock(stock2, 30);
		myPortfolio.buyStock(stock3, 40);
		
		myPortfolio.sellStock("AAL", 30);
		myPortfolio.removeStock("CAAS");
		
		
		//Portfolio portfolio1 = new Portfolio(title); //the same as myPortfolio
	
		//portfolio1.addStock(stock1);
		//portfolio1.addStock(stock2);
		//portfolio1.addStock(stock3);
		
		return myPortfolio;
	}

	public Portfolio getPortfolio(Portfolio portfolio){
		Portfolio portfolio2 = new Portfolio(portfolio);
		
		return portfolio2;
	}
	
}
