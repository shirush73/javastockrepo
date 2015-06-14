package com.myorg.javacourse.exception;

import org.algo.exception.PortfolioException;

public class BalanceException extends PortfolioException{
	
	
	// kinds of c'tors //
	

	public BalanceException(){
		super("There is not enough BALANCE in Portfolio");
	}
	
	public BalanceException(String errorString){
		super(errorString);
	}
	
	
	


}
