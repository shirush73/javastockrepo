package com.myorg.javacourse;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyNewServlet extends HttpServlet{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		resp.setContentType("text/html");
		
		//Working assumptions - when an integer was given -i left it as is but for angles and area data i declared them as double
		
		int radius;
		double area;
		radius = 50;
		area = radius * radius *Math.PI;
		
		String line1 = new String("calculation 1: Area of circle with radius "+radius+ " cm is "+area+" cm^2");
		
		int hypotenuse; // assuming the hypotenuse is fixed length
		double angleB, opposite;
		
		hypotenuse = 50;
		angleB = 30;
		opposite = hypotenuse * Math.sin(Math.toRadians(angleB));
		String line2 = new String("calculation 2: Length of opposite where angle B is "+angleB+" degrees is "+opposite+" cm");
		
		int base, exp;
		long result; //assuming the result is a long number
		
		base = 20;
		exp = 13;
		result = (long)(Math.pow(base, exp));
		
		
		String line3 = new String("calculation 3: Power of "+base+" with exp of "+exp+" is "+result);
		
		String resultStr = line1 + "<br>" + line2 + "<br>" + line3;
		resp.getWriter().println(resultStr);
		//.
	}
		
	

}


