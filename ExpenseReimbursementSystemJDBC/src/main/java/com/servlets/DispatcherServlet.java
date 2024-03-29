package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DispatcherServlet
 */
@WebServlet("/DispatcherServlet")
public class DispatcherServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DispatcherServlet() {
        super();
        
        //load postgres, you'll get status code 500 without this
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter w = response.getWriter();

		//https://www.w3schools.com/html/html_tables.asp
		w.write("<style>table, th, td {border: 1px solid;}</style>");

		
		w.write("<h1>Employee Reimbursement System</h1><br>");

		try {
			//send to request helper
			String httpVerb = request.getMethod(); //isolating the verb
			if(httpVerb.equals("GET"))
				RequestHelper.processGet(request, response);
			else if(httpVerb.equals("POST")) 
				RequestHelper.processPost(request, response);		
		}catch(Exception e) {
			//catch any unhandled exceptions 
			//to allow the method o finish
			response.setStatus(500);
			e.printStackTrace();
		}
		w.write("<br><p>Made by Tommy Hai</p>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}

}
