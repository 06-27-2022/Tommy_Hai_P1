package com.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.account.Account;
import com.account.AccountLocal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.AccountList;
import com.menu.LoginMenu;
import com.menu.Menu;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.setStatus(200);
//		response.setContentType("text/html");
//		response.addHeader("Custom Header", "custom value");
//		PrintWriter writer = response.getWriter();
//		writer.write("Hello world!");//		ObjectMapper om = new ObjectMapper();
		
		String requestBodyText = new String(request.getInputStream().readAllBytes());		
		ObjectMapper objectMapper = new ObjectMapper();
		Account acc = objectMapper.readValue(requestBodyText, AccountLocal.class);
		PrintWriter writer = response.getWriter();
		writer.write("Account\n============");
		writer.write(requestBodyText);
		writer.write("Name:"+acc.getName());
		writer.write("Password:"+acc.getPassword());
		writer.write("Role:"+acc.getRole());
		
		Menu login = new LoginMenu(new AccountList());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
