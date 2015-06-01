package com.myorg.javacourse.model;

import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;

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
	
	public void addStock(Stock stock){
		
		boolean isExist = false;
		
		if(this.getPortFolioSize() == MAX_PORTFOLIO_SIZE){   // #1 - portfolio or the array is full
			
			System.out.println("Can’t add new stock, portfolio can have only "+ MAX_PORTFOLIO_SIZE +" stocks");
			return;
		}
		
		for(int i=0 ; i<this.portFolioSize; i++){         // #2 - the stock exists in array already
			
			if(stock.getSymbol().equals(stocks[i].getSymbol())){
				
				isExist = true;
				System.out.println("the stock is already exist");
				return;
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
 */
	
	public boolean removeStock(String symbul){
		
		boolean isSuccessRemove = false;
	
		for(int i=0; i< this.portFolioSize; i++){
			
			if(this.stocks[i].getSymbol().equals(symbul)){
				
				sellStock(symbul, -1);  // means that all units are have to be sold
				
				this.stocks[i] = this.stocks[this.portFolioSize-1];  //replaces the last element in array with who is removed
				this.stocks[this.portFolioSize -1] = null;    // in the last element we put null to reduce size
				isSuccessRemove = true;
				this.portFolioSize--;
				
			}else{ // didn't success to remove the stock from array
				
				isSuccessRemove = false;
				System.out.println("The stocks does not exist in array");
			}
		}
		return isSuccessRemove;
	}
				
	
	
	/**     *********************  sellStock method *************************
	 
	 * @param symbulSell string and number of units to sell from this stock - if exists
	 * @return a boolean member to say if the sell action was succeed or not
	 * in first stage we check if this stock exists in array. then,
	 * we check if we have all the amount of units that the quantity parameter asked. then,
	 * the case of selling stock and updating sum of balance in positive amount and reducing the units
	 */
	
	
	public boolean sellStock(String symbulSell, int quantity){
		
		boolean isSuccess = false;
		boolean isExist = false;  // to say if we found the stock for sell in array, if is found - we get out
		
		for(int i= 0; i < this.portFolioSize  && isExist == false; i++){
			
			if(symbulSell.equals(stocks[i].getSymbol())){
				
				isExist = true;
				
				 if(quantity == -1){  // sells all of the units
					 
					 quantity = ((Stock) this.stocks[i]).getStockQuantity();  // in order to update the amount of units to sell
					 
					 updateBalance((float)(quantity) * stocks[i].getBid());
					 ((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() - quantity);
					 isSuccess = true;
					 
				 }else if( quantity > ((Stock) stocks[i]).getStockQuantity()){
					 
					 System.out.println("Sorry, But not enough stocks to sell");
					 return isSuccess;
					 
				 }else if( quantity < 0){ // in case of quantity is negative number - we cannot sell
					 
					 System.out.println("Error input - check again");
					 return isSuccess;
					 
				 }else{   // case o.k - the stock exists and there are enough stocks to sell
					 
					 updateBalance((float)(quantity) * stocks[i].getBid());
					 ((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() - quantity);
					 isSuccess = true;
				 }
			}
			else{
				isExist = false;
			}
		}
		return isSuccess;
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
 * we should care only to positive balance
 * @param amount of portfolio's balance 
 */
	
	public void updateBalance(float amount){
		
		if((this.balance + amount) < 0){
			
			this.balance = 0;
			System.out.println("Sorry, But there is not enough money");
		}
		else{
			this.balance += amount;
		}
	}
	
	
	/**       ***************** buyStock method *********************
	 
	 * @param stock and number of units of this stock to buy
	 * we checks number of stages: #1. if this stock already exists in array, if yes we add units
	 * #2 if does not exist and we have enough money (balance) to buy these units of stock and then we can buy
	 * the next stage is to add this stock (after the buy action) to our array and increase the logic size
	 * @return a boolean member to say if this buying success or not
	 */
	
	
		public boolean buyStock(Stock stock, int quantity){ 
			
			boolean isExist = false;
			boolean isSucceed = false;
			
			for(int i=0; i< portFolioSize; i++){
				
				if(stocks[i].getSymbol().equals(stock.getSymbol())){  // CASE#1 want to buy in addition - exists in array
					
					isExist = true;
					
					if(quantity == -1){  // buy until we're out of money
					
						while(balance > stock.getAsk()){
							
							quantity --;
							((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() +1);
							this.balance = this.getBalance() - stock.getAsk();
						}
						isSucceed = true;
						
					}else if( (stock.getAsk() * quantity) > this.balance){
						
						System.out.print("Sorry, Not enough money to complete buy"); 
						
					}else{
						
						((Stock) stocks[i]).setStockQuantity(((Stock) stocks[i]).getStockQuantity() + quantity);
						this.balance = this.getBalance() - (stock.getAsk() * quantity);
						isSucceed = true;
					}
				}
			}
			
			if(isExist == false){  // CASE#2 - NOT exists in array and want to buy it
				
				if(portFolioSize == MAX_PORTFOLIO_SIZE){     // no space in array
					
					System.out.print(" Sorry, But the portfolio is full"); 
					
				}else if(this.balance > (stock.getAsk() * quantity)){   // it's o.k to buy
					
					stock.setStockQuantity(quantity);
					this.balance = this.getBalance() - (stock.getAsk() * quantity);
					addStock(stock);
					isSucceed = true;
					
				}else{   // not enough money in balance
					
					System.out.print("Sorry, But there is NOT enough money to complete buy"); 
					isSucceed = false;
				}
			}		
		return isSucceed;
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


	public StockInterface findStock(String symbol) {
		
		for(int i=0; i<this.portFolioSize; i++){
			if(this.stocks[i].getSymbol().equals(symbol)){
				return stocks[i];
			}
		}
		return null;
	}


	public static int getMaxSize() {
		return MAX_PORTFOLIO_SIZE;
	}

	
	


	
	


	





}
