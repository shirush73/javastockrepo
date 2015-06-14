package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class PortfolioFullException extends PortfolioException{
	

	//private static String MAX_PORTFOLIO_SIZE ; // number of the portfolio size

	// kinds of c'tors // 
	
	/*public PortfolioFullException(int maxPortfolioSize) {
		super("Can’t add new stock, portfolio can have maximum only "+ MAX_PORTFOLIO_SIZE +" stocks");

	}
	public PortfolioFullException(String msg) {
		super(" You had reached maximum portfolio size! ");

	}
	*/
	
	public PortfolioFullException(){
		super(" Portfolio is Full! ");
	}
}
