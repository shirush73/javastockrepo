package com.myorg.javacourse;
import java.io.IOException;
import javax.servlet.http.*;

@SuppressWarnings("serial")
public class Java_Shira_IzonServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	}
}
