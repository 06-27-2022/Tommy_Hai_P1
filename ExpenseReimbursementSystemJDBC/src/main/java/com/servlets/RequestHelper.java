package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import controller.EmployeeController;
import controller.LoginController;
import controller.ManagerController;

public class RequestHelper {
	
	private static String url = "/check/api";   
	
	public static Object processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resource = request.getRequestURI().replace(url, "");			
		switch (resource) {
		case "/login":
			//sign into an existing account
			new LoginController(request,response).signIn();
			break;
		case "/ticket":
			//view tickets of signed in account
			new EmployeeController(request,response).viewTickets();
			break;
		case "/pending-tickets":
			//process pending tickets
			new ManagerController(request,response).viewPendingTickets();
			break;
		case "/edit-accounts":
			new ManagerController(request,response).viewAccounts();
			break;
		default:
			//resource does not exist
			response.setStatus(404);
			break;
		}
		return null;
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public static void processPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resource = request.getRequestURI().replace(url, "");

		switch (resource) {
		case "/login":
			//create a new account and add it to the database
			new LoginController(request, response).createAccount();
			break;
		case "/logout":
			//logout of the account login-ed to
			new LoginController(request,response).logout();
			break;
		case "/ticket":
			//create and add a ticket to the database
			new EmployeeController(request,response).submitTicket();
			break;
		case "/pending-tickets":
			//process pending tickets
			new ManagerController(request,response).processPendingTickets();
			break;
		case "/edit-accounts":
			new ManagerController(request,response).editAccounts();
			break;
		default:
			//resource does not exist
			response.setStatus(404);
			break;
		}
	}
}
