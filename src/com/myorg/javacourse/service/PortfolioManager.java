package com.myorg.javacourse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.myorg.javacourse.exception.BalanceException;
import com.myorg.javacourse.exception.ExceptionOfInput;
import com.myorg.javacourse.exception.PortfolioFullException;
import com.myorg.javacourse.exception.StockAlreadyExistsException;
import com.myorg.javacourse.exception.StockNotExistException;
import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMENDATION;
import com.myorg.javacourse.model.Stock;






//import com.myorg.javacourse.model.Portfolio.ALGO_RECOMMENDATION;
import org.algo.dto.PortfolioDto;
import org.algo.dto.PortfolioTotalStatus;
import org.algo.dto.StockDto;
import org.algo.exception.PortfolioException;
import org.algo.exception.SymbolNotFoundInNasdaq;
import org.algo.model.PortfolioInterface;
import org.algo.model.StockInterface;
import org.algo.service.DatastoreService;
import org.algo.service.MarketService;
import org.algo.service.PortfolioManagerInterface;
import org.algo.service.ServiceManager;



public class PortfolioManager implements PortfolioManagerInterface{
	
	public enum OPERATION{HOLD,REMOVE ,SELL,BUY };
	
	//Stock stock1, stock2, stock3 ;
	
	private DatastoreService datastoreService = ServiceManager.datastoreService();

	public PortfolioInterface getPortfolio() {
		PortfolioDto portfolioDto = datastoreService.getPortfolilo();
		return fromDto(portfolioDto);
	}
	
	/**
	 * Update portfolio with stocks
	 */
	@Override
	public void update() {
		
		StockInterface[] stocks = getPortfolio().getStocks();
		List<String> symbols = new ArrayList<>(Portfolio.getMaxSize());

		for (StockInterface si : stocks) {
			symbols.add(si.getSymbol());
		}

		List<Stock> update = new ArrayList<>(Portfolio.getMaxSize());

		List<Stock> currentStocksList = new ArrayList<Stock>();
		try {
			List<StockDto> stocksList = MarketService.getInstance().getStocks(symbols);
			for (StockDto stockDto : stocksList) {
				Stock stock = fromDto(stockDto);
				currentStocksList.add(stock);
			}

			for (Stock stock : currentStocksList) {
				update.add(new Stock(stock));
			}

			datastoreService.saveToDataStore(toDtoList(update));

		} catch (SymbolNotFoundInNasdaq e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * Set portfolio title
	 */
	
	public void setTitle(String title) {
		Portfolio portfolio = (Portfolio)getPortfolio();
		((Portfolio) portfolio).setTitle(title);
		
		flush(portfolio);
	}
	
	/**
	 * update portfolio balance 
	 */
	public void updateBalance(float amount) throws PortfolioException{

		Portfolio portfolio = (Portfolio) getPortfolio();
		
		try{
			portfolio.updateBalance(amount);
			
			}catch(BalanceException be){
				be.getMessage();
				be.printStackTrace();
				throw be;
			}

		flush(portfolio);
	
		}
	
	
	
	/**
	 * get portfolio totals
	 */
	@Override
	public PortfolioTotalStatus[] getPortfolioTotalStatus () {

		Portfolio portfolio = (Portfolio) getPortfolio();
		Map<Date, Float> map = new HashMap<>();

		//get stock status from db.
		StockInterface[] stocks = portfolio.getStocks();
		for (int i = 0; i < stocks.length ; i++) {
			
			StockInterface stock = stocks[i];

			if(stock != null) {
				List<StockDto> stockHistory = null;
				try {
					stockHistory = datastoreService.getStockHistory(stock.getSymbol(),30);
				} catch (Exception e) {
					return null;
				}
				for (StockDto stockDto : stockHistory) {
					Stock stockStatus = fromDto(stockDto);
					float value = stockStatus.getBid()*stockStatus.getStockQuantity();

					Date date = stockStatus.getDate();
					Float total = map.get(date);
					if(total == null) {
						total = value;
					}else {
						total += value;
					}

					map.put(date, value);
				}
			}
		}

		PortfolioTotalStatus[] ret = new PortfolioTotalStatus[map.size()];

		int index = 0;
		//create dto objects
		for (Date date : map.keySet()) {
			ret[index] = new PortfolioTotalStatus(date, map.get(date));
			index++;
		}

		//sort by date ascending.
		Arrays.sort(ret);

		return ret;
	}
	

	
	/**
	 * Add stock to portfolio 
	 */
	@Override
	public void addStock(String symbol) throws PortfolioException{
		
		Portfolio portfolio = (Portfolio) getPortfolio();

		try{
				StockDto stockDto = ServiceManager.marketService().getStock(symbol);
				
				Stock stock = fromDto(stockDto);  //get current symbol values from nasdaq.
				
				try{
					portfolio.addStock(stock);   //first thing, add it to portfolio.
				
				}catch(StockAlreadyExistsException saee){
					saee.getMessage();
					saee.printStackTrace();
					throw saee;
				}catch(PortfolioFullException pfe){
					pfe.getMessage();
					pfe.printStackTrace();
					throw pfe;
				}catch(StockNotExistException snee){
					snee.getMessage();
					snee.printStackTrace();
					throw snee;
				}

			 //second thing: save the new stock to the database.
				
				try{
					datastoreService.saveStock(toDto(portfolio.findStock(symbol)));
					
				}catch(StockNotExistException snee) {
					snee.getMessage();
					snee.printStackTrace();
					throw snee;
				}
				
				flush(portfolio);
			
			}catch (SymbolNotFoundInNasdaq snfin) {
				snfin.getMessage();
				snfin.printStackTrace();
			}
		
	}

	
		/**
		 * Buy stock
		 */
		@Override
		public void buyStock(String symbol, int quantity) throws PortfolioException{
			try {
					Portfolio portfolio = (Portfolio) getPortfolio();
					
					Stock stock = (Stock) portfolio.findStock(symbol);
					if(stock == null) {
						stock = fromDto(ServiceManager.marketService().getStock(symbol));
					}
					
					portfolio.buyStock(stock, quantity);
					flush(portfolio);
			
			}catch(BalanceException be){
				be.getMessage();
				be.printStackTrace();
				throw be;
			}catch(PortfolioFullException pfe){
				pfe.getMessage();
				pfe.printStackTrace();
				throw pfe;
			}catch(StockNotExistException snee){
				snee.getMessage();
				snee.printStackTrace();
				throw snee;
			}catch(ExceptionOfInput eoi){
				eoi.getStackTrace();
				eoi.printStackTrace();
				throw eoi;
			}catch (Exception e) {
				System.out.println("Exception: "+e);
			}
		
		}
		
		/**
		 * update database with new portfolio's data
		 * @param portfolio
		 */
		private void flush(Portfolio portfolio) {
			datastoreService.updatePortfolio(toDto(portfolio));
		}

	
		/**
		 * fromDto - get stock from Data Transfer Object
		 * @param stockDto
		 * @return Stock
		 */
		
		private Stock fromDto(StockDto stockDto) {
			Stock newStock = new Stock();
	
			newStock.setSymbol(stockDto.getSymbol());
			newStock.setAsk(stockDto.getAsk());
			newStock.setBid(stockDto.getBid());
			newStock.setDate(stockDto.getDate().getTime());
			newStock.setStockQuantity(stockDto.getQuantity());
			if(stockDto.getRecommendation() != null) newStock.setRecommendation(ALGO_RECOMMENDATION.valueOf(stockDto.getRecommendation()));

			return newStock;
			
		}

	/**
	 * toDto - covert Stock to Stock DTO
	 * @param inStock
	 * @return
	 */
	private StockDto toDto(StockInterface inStock) {
		if (inStock == null) {
			return null;
		}
		
		Stock stock = (Stock) inStock;
		
		String recom;
		if(stock.getRecommendation() == null) {
			recom = ALGO_RECOMMENDATION.HOLD.name();
		}else {
			recom = stock.getRecommendation().name();
		}
		
		
		return new StockDto(stock.getSymbol(), stock.getAsk(), stock.getBid(), 
				stock.getDate(), stock.getStockQuantity(), recom);
	}

	/**
	 * toDto - converts Portfolio to Portfolio DTO
	 * @param portfolio
	 * @return
	 */
	private PortfolioDto toDto(Portfolio portfolio) {
		
		StockDto[] array = null;
		StockInterface[] stocks = portfolio.getStocks();
		if(stocks != null) {
			array = new StockDto[stocks.length];
			for (int i = 0; i < stocks.length; i++) {
				array[i] = toDto(stocks[i]);
			}
		}
		return new PortfolioDto(portfolio.getTitle(), portfolio.getBalance(), array);
	}

	/**
	 * fromDto - converts portfolioDto to Portfolio
	 * @param dto
	 * @return portfolio
	 */
	private Portfolio fromDto(PortfolioDto dto) {
		
		StockDto[] stocks = dto.getStocks();
		Portfolio ret;
		if(stocks == null) {
			ret = new Portfolio();			
		}else {
			List<Stock> stockList = new ArrayList<Stock>();
			for (StockDto stockDto : stocks) {
				stockList.add(fromDto(stockDto));
			}

			Stock[] stockArray = stockList.toArray(new Stock[stockList.size()]);
			ret = new Portfolio(stockArray);
		}

		ret.setTitle(dto.getTitle());
		try {
			ret.updateBalance(dto.getBalance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}	

	/**
	 * toDtoList - convert List of Stocks to list of Stock DTO
	 * @param stocks
	 * @return stockDto
	 */
	private List<StockDto> toDtoList(List<Stock> stocks) {

		List<StockDto> ret = new ArrayList<StockDto>();

		for (Stock stockStatus : stocks) {
			ret.add(toDto(stockStatus));
		}

		return ret;
	}	
	
	
	
	/**
	 * A method that returns a new instance of Portfolio copied from another instance.
	 * @param portfolio		Portfolio to copy.
	 * @return a new Portfolio object with the same values as the one given.
	 */
	
	public Portfolio duplicatePortfolio(Portfolio portfolio) {
		Portfolio copyPortfolio = new Portfolio(portfolio);
		return copyPortfolio;
	}
	
	
	
	
	/**
	 * Sell stock
	 */
	@Override
	
	public void sellStock(String symbol, int quantity) throws PortfolioException {
		
		Portfolio portfolio = (Portfolio) getPortfolio();
		try{
			portfolio.sellStock(symbol, quantity);
			
			
		}catch(StockNotExistException snee){
			snee.getMessage();
			snee.printStackTrace();
			throw snee;
		}catch(ExceptionOfInput eoi){
			eoi.getMessage();
			eoi.printStackTrace();
			throw eoi;
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
		flush(portfolio);
	}
	
	
	
	/**
	 * Remove stock
	 * @throws BalanceException 
	 */
	@Override
			
		
	public void removeStock(String symbol)throws PortfolioException {
		
		Portfolio portfolio = (Portfolio) getPortfolio();
		
		try{
			portfolio.removeStock(symbol); 
			
		}catch(StockNotExistException snee){
			snee.getMessage();
			snee.printStackTrace();
			throw snee;
		}catch(BalanceException be){
			be.getMessage();
			be.printStackTrace();
			throw be;
		}catch(Exception e){
			e.getMessage();
			e.printStackTrace();
		}
			
		flush(portfolio);
	}
			



// NOT RELEVANTS IN THIS EXERSIZE //
	
	/*public PortfolioInterface getPortfolio(){
		
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
	} */

	/*public Portfolio getPortfolio(Portfolio portfolio){
		Portfolio portfolio2 = new Portfolio(portfolio);
		
		return portfolio2;
	}*/
}
