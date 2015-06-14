package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class StockAlreadyExistsException extends PortfolioException{


	// kinds of c'tors //
	
	public StockAlreadyExistsException(String symbol) {
		super("Stock "+symbol+" already exists in your portfolio.");
	}
	
	
	public StockAlreadyExistsException() {
		super(" You have already entered this stock's name! ");
	}
	
}
