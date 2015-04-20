package com.myorg.javacourse;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class StockDetailsServlet extends HttpServlet {
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
				throws ServletException, IOException{
		
		resp.setContentType("text/html");
		
	//Stock stock;
	Stock stock1, stock2, stock3;

	
	Calendar cal =  Calendar.getInstance();
	cal.set(2014, 10, 15);
	java.util.Date date1 = cal.getTime();
	
	cal.set(2014, 10, 15);
	java.util.Date date2 = cal.getTime();
	
	cal.set(2014, 10, 15);
	java.util.Date date3 = cal.getTime();
	
	
// Creating instances and sending the arguments to c'tor
	 

	stock1 = new Stock("PIH", (float)13.1, (float)12.4, date1);
	stock2 = new Stock("AAL", (float)5.78, (float)5.5,  date2);
	stock3 = new Stock("CAAS",(float)32.2, (float)31.5, date3);
	
	
	
	String resultStr = stock1.getHtmlDescription() + "<br>" + stock2.getHtmlDescription() + "<br>" + stock3.getHtmlDescription();
	resp.getWriter().println(resultStr);
	}
}
