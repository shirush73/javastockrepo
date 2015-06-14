package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockNotExistException extends PortfolioException{

	
	// kinds of c'tors //
	
	public StockNotExistException(){
		super("Stock Was not found in portfolio");
	}
	
	public StockNotExistException(String stock){
		super("Error, Stock " + stock +  " Was not found in portfolio or Check again your input");
	}
}
