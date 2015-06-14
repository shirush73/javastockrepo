package com.myorg.javacourse.model;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.ExceptionOfInput;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistsException;
import com.myorg.javacourse.exception.StockNotExistException;

/** this Portfolio Class is like a stocks array 
 * each element in this array is an instance (stock1, stock2, stock3..)
 * this class holds the data about the stocks and thus is called - model class
 * the logic size of this array is three - meantime, but the Physical size is five.
 */

public class Portfolio implements PortfolioInterface {
	
//const members
	
	public static final int MAX_PORTFOLIO_SIZE = 5;
		

	// enum
	public enum ALGO_RECOMMENDATION{
		BUY, SELL, REMOVE, HOLD;
	}


//data members
	private String title;
	private StockInterface[] stocks;
	private int portFolioSize; // an index which advances the array = logic size
	private String htmlString;
	private float balance = 0;
	
	
//                 ************ c'tor - initializes the instance ************
	
	public Portfolio(String title){
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.setTitle(title);
		this.portFolioSize = 0;
	}
	
	
// copy c'tor - by value - create new and same instance's details - like in p.f #1
	
	public Portfolio(Portfolio portfolio){
		
		
		this.stocks = new StockInterface[MAX_PORTFOLIO_SIZE];
		this.setTitle("Portfolio #2");
		this.portFolioSize = portfolio.portFolioSize;
		
		for(int i=0; i<portFolioSize; i++){
			Stock coppied =  new Stock(portfolio.stocks[i]);
			this.stocks[i] = coppied; //also copy c'tor
		}		
	}
	
	public Portfolio(Stock[] stockArray) {
		
		this();
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portFolioSize = 0;
		this.title = null;
		this.balance = 0;
		for(int i =0; i<stockArray.length; i++){
			this.stocks[i] = stockArray[i];
			this.portFolioSize++;
		}		
	}
	
	public Portfolio(){
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.portFolioSize = 0;
		this.title = null;
		this.balance = 0;
	}

	
//                  **************** methods *******************

//Getters & Setters
	
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public StockInterface[] getStocks(){
		return stocks;
	}
	
	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) { // not for usage in this stage
		this.balance = balance;
	}
	
	public int getPortFolioSize() {
		return this.portFolioSize;
	}
	

	public void setPortFolioSize(int portFolioSize) {
		this.portFolioSize = portFolioSize;
	}

//                           ************ Other Methods ***************
	
	

/**  ******************* addStock method *********************
 
 * receives Stock instance add needs to add it to portfolio - stocks array
 * checks if this stocks is not null and if there is a space in array
 * there are #3 CASES - if there is place, the stock does not esixt and the stock not null
 * else - return or printing error massage to console - add does not succeed
 */
	
	public void addStock(Stock stock) throws StockAlreadyExistsException,StockNotExistException, PortfolioFullException{
		
		boolean isExist = false;
		
		if(this.getPortFolioSize() == MAX_PORTFOLIO_SIZE){   // #1 - portfolio or the array is full
			
			System.out.println("Can’t add new stock, portfolio can have only "+ MAX_PORTFOLIO_SIZE +" stocks");
			throw new PortfolioFullException();
			//return;
		}
		
		if (stock == null) { // refers as '0' 
			throw new StockNotExistException("Check again input validance!");
		}
		
		for(int i=0 ; i<this.portFolioSize; i++){         // #2 - the stock exists in array already
			
			if(stock.getSymbol().equals(stocks[i].getSymbol())){
	// NOTE: in Assumption that you should insert only an uppercase or lowercase - not both!!!
				isExist = true;
				
				System.out.println("the stock is already exist");
				throw new StockAlreadyExistsException(stock.getSymbol());
				//return;
			}
		}
		if((isExist == false) && (stock != null)){    // case #3 - NOT exists and NOT null - it's case o.k
			
			this.stocks[portFolioSize] = new Stock(stock);
			this.portFolioSize++;
		
		}else{
			
			System.out.println("the stock is null or the stock exists already in array");
		}
	}
		
	
	
	
	
	
	
/**    ********************** getHtmlString method ***************************
  
 * includes title of the portfolio (in html code) + details about the stocks array
 * @return string that includes all the details about the portfolio in order to print it
 */
	
	public String getHtmlString(){
		
		htmlString = "<h1>" + this.getTitle()+ "</h1>";
		String htmlString2 = new String("");
		
		String htmlValues = new String(" Total Portfolio Value is: " + this.getTotalValue()+ "$" + "<br>" + " Total Stock Value is: " + this.getStockValue()+ "$" + "<br>" + " Balance is: "+ this.getBalance()+ "$" + "<br");
		
		htmlString += htmlValues + "<br>" + "<br>";

		for(int i=0; i< this.portFolioSize; i++){

			htmlString2 = ((Stock) stocks[i]).getHtmlDescription();
			htmlString+= "<br>" + htmlString2 ;
		}
		return htmlString;
	}
	

/**        ********************** removeStock method ************************
  
 * gets a string to remove it. first stage is to find out the stock's string which we want to remove
 * if the arguments that are sent include -1 sign it means to remove all the units or quantities of this stock
 * thus, we call to sell function, in order first of all sell them and then to remove stock from array
 * return boolean member to say if this action was succeed.
 * @throws Exception 
 * @throws StockNotExistException, BalanceException 
 */
	
	public void removeStock(String symbol) throws StockNotExistException,BalanceException,Exception{
		
		boolean notFound = false;
		
		if (symbol == null) { 
			
			throw new StockNotExistException();
		}
		if (portFolioSize == 1	|| symbol.equals(stocks[portFolioSize - 1].getSymbol())) {
			 
			this.sellStock(stocks[portFolioSize - 1].getSymbol(), -1);
	
			stocks[portFolioSize - 1] = null;
			portFolioSize--;
			notFound = true;
		}
		
		for (int i = 0; i < this.portFolioSize && notFound == false; i++) { 
			
			if (symbol.equals(stocks[i].getSymbol())) {
				
				this.sellStock(stocks[portFolioSize - 1].getSymbol(), -1);
				
				stocks[i] = stocks[portFolioSize - 1];
				stocks[portFolioSize - 1] = null;
				portFolioSize--;
				notFound = true; 
			}
		}
		if (notFound == false){
			throw new StockNotExistException();
		}
		
	}
				
	
	
	/**     *********************  sellStock method *************************
	 
	 * @param symbulSell string and number of units to sell from this stock - if exists
	 * @return a boolean member to say if the sell action was succeed or not
	 * in first stage we check if this stock exists in array. then,
	 * we check if we have all the amount of units that the quantity parameter asked. then,
	 * the case of selling stock and updating sum of balance in positive amount and reducing the units
	 */
	
	
	public void sellStock(String symbolSell, int quantity)throws StockNotExistException,ExceptionOfInput, Exception{
		
		boolean isExist = false;
		
		if (symbolSell == null || quantity < -1) {    // if the input is error
			throw new ExceptionOfInput("Invalid input, please try again!"); 
		}
		for (int i = 0; i < this.portFolioSize && isExist == false; i++) {
			if (symbolSell.equals(stocks[i].getSymbol())) {
				
				isExist = true;
				
				if (((Stock) this.stocks[i]).getStockQuantity() - quantity < 0) {
					throw new ExceptionOfInput("Error of Selling over quantities");
					


				} else if (quantity == -1) {
					this.balance += ((Stock) this.stocks[i]).getStockQuantity()* this.stocks[i].getBid();
					((Stock) this.stocks[i]).setStockQuantity(0);

				} else {   // any other case
					this.balance += quantity * this.stocks[i].getBid();
					((Stock) this.stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() - quantity);
				}
			}
		}
		if (isExist == false) {
			throw new StockNotExistException();
		}
	
	}
			
	
/**
 * changeValueStock method
 * needs to change the bid value of the last stock (index NO 2 in array)
 * @param bid
 * @param indexToChange
 */
	
	public void changeValueStock(float bid, int indexToChange){
		((Stock) this.stocks[indexToChange]).setBid(bid);
	}
	
	  
/**        ******************** updateBalance method ************************
 
 * this function updates the amount of balance as a result of sell or buy actions
 * in buy action we reduces and in sell action we increases amount
 * we should care only to positive balance, the error is: balance is negative
 * @param amount of portfolio's balance 
 */
	
	public void updateBalance(float amount) throws BalanceException{
		
		float tempBalance  = this.balance + amount;
		
		if((tempBalance) < 0){
		
			throw new BalanceException("Please note you may not change balance to negative amount!");

		}else{
				this.balance += amount;
				System.out.println("Good! Balance has been updated to "+ this.balance);
		}
	}
	
	
	
	
	
	/**       ***************** buyStock method *********************
	 
	 * @param stock and number of units of this stock to buy
	 * we checks number of stages: #1. if this stock already exists in array, if yes we add units
	 * #2 if does not exist and we have enough money (balance) to buy these units of stock and then we can buy
	 * the next stage is to add this stock (after the buy action) to our array and increase the logic size
	 * @return a boolean member to say if this buying success or not
	 */
		
	public void buyStock(Stock stock, int quantity) throws BalanceException,ExceptionOfInput,PortfolioFullException, StockNotExistException {
	
		boolean isExist = false;
		int i=0;
		
		if (stock == null || quantity < -1) {
			throw new ExceptionOfInput("Error of null stock OR invalid quantity!");
		}
		if (quantity * stock.getAsk() > this.balance) {
			throw new BalanceException();
		}	
		for (i = 0; i < this.portFolioSize; i++) {
			
			if (stock.getSymbol().equals(this.stocks[i].getSymbol())) { //if exists
				
				isExist = true;
				
				if (quantity == -1) { // buy all of stock's quantity till no more money to buy
					
					int numOfStockToBuy = (int) this.balance / (int) this.stocks[i].getAsk();
					
					this.balance -= numOfStockToBuy * this.stocks[i].getAsk();
					
					((Stock) this.stocks[i]).setStockQuantity(((Stock) this.stocks[i]).getStockQuantity() + numOfStockToBuy);
					
					System.out.println("All stocks of " + stock.getSymbol()+ " Were bought successfuly!");
		
				} else { // any other case
					this.balance -= quantity * this.stocks[i].getAsk();
					
					((Stock) this.stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() + quantity);
					
					System.out.println("Amount of " + quantity+ " stocks  of the stock " + stock.getSymbol()+ " Were bought!");
				}
				break; 
			}
		}
		
		
		// if the stock's not in array!
		if (i == MAX_PORTFOLIO_SIZE) { // cannot add another stock
			throw new PortfolioFullException();
		} 
}
	
	
	
		
		/**      ***************** getStockValue *******************
		 
		 * get out through the array and calculates the value of stocks by the number of units and its bid price
		 * @return the value if each stock in array. in our case is only one stock. the rest are sold
		 */
		
		
	public float getStockValue(){
		
		float totalValueStock = 0;
		
		for(int i= 0; i< this.portFolioSize; i++){
			
			totalValueStock += (this.stocks[i].getBid() * ((Stock) this.stocks[i]).getStockQuantity()); 
		}
		
		return totalValueStock;
	}
	
	
	/**     **************** getTotalValue ***************
	 
	
	 * @return the all value of the portfolio by calculating the balance + valueStocks as mentioned above 
	 */

	public float getTotalValue(){
		
		float totalValueOfPortfolio = 0;
		
		totalValueOfPortfolio = getBalance() + getStockValue();
		
		return totalValueOfPortfolio;
	}


	public StockInterface findStock(String symbol) throws StockNotExistException {
		
		for(int i=0; i<this.portFolioSize; i++){
			if(this.stocks[i].getSymbol().equals(symbol)){
				return stocks[i];
			}
		}
		throw new StockNotExistException();
	}


	public static int getMaxSize() {
		return MAX_PORTFOLIO_SIZE;
	}

	
	


	
	


	





}
