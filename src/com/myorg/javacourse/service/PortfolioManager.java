package com.myorg.javacourse.service;

import java.util.Calendar;
import java.util.Date;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Stock;

public class PortfolioManager {
	
//const members
	
//data members
	Portfolio portfolio;
	Stock stock1, stock2, stock3 ;
	
//c'tor
	public PortfolioManager(){  // there is a default already  
	}
	
	/**
	 * this package means to service and manages all the information about the instances and do actions on the model package (portfolio)
	 * in this case e.g: to sell stock from the stocks array  
	 * @return
	 */
//methods
	
	public Portfolio getPortfolio(String title){
		Portfolio portfolio1 = new Portfolio(title);
	
	
		/** Java Doc: creating an instance that includes all the data about the stocks array - means as model package 
		 * in each of date instance there are its time details which was defined before by set.cal method
		 */
		
		Calendar cal =  Calendar.getInstance();
		cal.set(2014, 10, 15);
		Date date1 = cal.getTime();
		
		cal.set(2014, 10, 15);
		Date date2 = cal.getTime();
		
		cal.set(2014, 10, 15);
		Date date3 = cal.getTime();
		 
		/** there are three (instances)stocks meantime that include all these details - from exercise 04
		 * in the second phase we move the stocks's information under the portfolio package for saving-maintenance them
		 * then return the pointer of the first stocks array (portfolio)
		 */
		
		stock1 = new Stock("PIH", (float)13.1, (float)12.4, date1);
		stock2 = new Stock("AAL", (float)5.78, (float)5.5,  date2);
		stock3 = new Stock("CAAS",(float)32.2, (float)31.5, date3);
		

		portfolio1.addStock(stock1);
		portfolio1.addStock(stock2);
		portfolio1.addStock(stock3);
		
		return portfolio1;
	}

	public Portfolio getPortfolio(Portfolio portfolio){
		Portfolio portfolio2 = new Portfolio(portfolio);
		
		return portfolio2;
	}
	
}
