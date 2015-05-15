package com.myorg.stock.servlet;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.myorg.javacourse.model.Portfolio;
import com.myorg.javacourse.model.Stock;
import com.myorg.javacourse.service.PortfolioManager;

public class PortfolioServlet extends HttpServlet{
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
			resp.setContentType("text/html");
						
			/** creating new instance of portfolio manager 
			 * then being called calling to getPortfolio function (all the data about the stocks) and then
			 * the function that prints the details of the instances which defined in getHtmlString 
			 */
			
			PortfolioManager portfolioManager = new PortfolioManager();
			
		//	***** NOT RELEVANTS   *****
			
			//Portfolio portfolio1 = portfolioManager.getPortfolio("portfolio1");
			
		   // Portfolio portfolio2 = portfolioManager.getPortfolio(portfolio1);
		    

			//resp.getWriter().println(portfolio1.getHtmlString()); // print p.f #1 original

			//resp.getWriter().println(portfolio2.getHtmlString()); // print p.f #2 - copy
			
			// *** remove first stock from p.f #1 ***
			
			//portfolio1.removeStock(portfolioManager.getPortfolio(portfolio1).getStocks(), 0); // section g
			
			//resp.getWriter().println(portfolio1.getHtmlString());  // print p.f #1 - stock removed
			
			//resp.getWriter().println(portfolio2.getHtmlString()); //  print p.f #2 - not changed
			
			// *** change bid value from p.f #2 ***
			
			//resp.getWriter().println(portfolio1.getHtmlString()); // print p.f #1 - not changed
			
			//portfolio2.changeValueStock(55.55f, 2); // changing the stock's required bid value
			
			//resp.getWriter().println(portfolio2.getHtmlString()); // print p.f #2 - value changed
			
			
		///************** EX 07 *************
			
			Portfolio myPortfolio = portfolioManager.getPortfolio("");
			
			resp.getWriter().println(myPortfolio.getHtmlString());
			
			
			}
}
