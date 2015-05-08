package com.myorg.javacourse.model;


public class Portfolio {
	
	/** this Portfolio class is like a stocks array 
	 * each element in this array is an instance (stock1, stock2, stock3)
	 * this class holds the data about the stocks and thus is called - model class
	 * the logic size of this array is three - meantime, but the Physical size is five.
	 */
	
//const members
	
	public final int MAX_PORTFOLIO_SIZE = 5;

//data members
	private String title;
	private Stock[] stocks;
	private int portFolioSize = 0; // an index which advances the array = logic size
	private String htmlString;

	
//c'tor - initializes the instance
	public Portfolio(String title){
		stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.setTitle(title);
	}
	
// copy c'tor - by value - create new and same instance's details - like in p.f #1
	
	public Portfolio(Portfolio portfolio){
		this.stocks = new Stock[MAX_PORTFOLIO_SIZE];
		this.setTitle("Portfolio #2");
		this.portFolioSize = portfolio.portFolioSize;


		for(int i=0; i<portFolioSize; i++){
			Stock coppied = new Stock(portfolio.stocks[i]);
			this.stocks[i] = coppied; //also copy c'tor
		}		
	}
	
//methods
	
	public int getPortFolioSize() {
		return portFolioSize;
	}

	public void setPortFolioSize(int portFolioSize) {
		this.portFolioSize = portFolioSize;
	}

	/** addStock method
	 * receives Stock instance add needs to add it to portfolio - stocks array
	 * checks if this stocks is not null and if there is a space in array
	 */
	
	public void addStock(Stock stock){
		if(stock != null && portFolioSize < MAX_PORTFOLIO_SIZE){
		this.stocks[portFolioSize] = stock;
		portFolioSize++;
	}else{
			System.out.println("Sorry, portfolio is full, or stock is null !");
		}
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Stock[] getStocks(){
		return stocks;
	}
	
	
	/**getHtmlString method
	 * includes title of the portfolio (in html code) + details about the stocks array
	 * @return string that includes all the details about the portfolio in order to print it
	 */
	
	public String getHtmlString(){
		htmlString = "<h1>" + this.getTitle()+ "</h1>";
		String htmlString2 = new String("");

		for(int i=0; i< portFolioSize; i++){

			htmlString2 = stocks[i].getHtmlDescription();
			htmlString+= "<br>" + htmlString2;
		}
		return htmlString;
	}
	
	
	/** removeStock method
	 * gets the stocks array and an index that shows which element need to be removed from array
	 * needs to remove the first stock in this array
	 * @param stocks
	 * @param indexToRemove
	 */
	
	public void removeStock(Stock[] stocks, int indexToRemove){
		
		for(int i=indexToRemove; i< portFolioSize; i++){
			
			if(this.stocks[i+1] ==  null){
				stocks[i] = null;
			}else{
				this.stocks[i]= stocks[i+1];
			}
		}
		portFolioSize--;
	}
	
	
	/**
	 * changeValueStock method
	 * needs to change the bid value of the last stock (index NO 2 in array)
	 * @param bid
	 * @param indexToChange
	 */
	
	public void changeValueStock(float bid, int indexToChange){
		this.stocks[indexToChange].setBid(bid);
	}
	
}
