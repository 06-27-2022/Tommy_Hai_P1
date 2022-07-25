package com.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.controller.EmployeeController;
import com.controller.LoginController;
import com.controller.ManagerController;
import com.controller.ProfileController;

public class RequestHelper {
	
	private static String url = "/check/api";   
	
	public static Object processGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String resource = request.getRequestURI().replace(url, "");			
		switch (resource) {
		case "/employee/ticket":
			//view tickets of signed in account
			new EmployeeController(request,response).viewTickets();
			break;
		case "/manager/ticket":
			//view tickets of signed in account
			new ManagerController(request,response).viewTickets();
			break;
		case "/manager/pending-tickets":
			//process pending tickets
			new ManagerController(request,response).viewPendingTickets();
			break;
		case "/manager/edit-accounts":
			new ManagerController(request,response).viewAccounts();
			break;
		case "/profile/search":
			new ProfileController(request,response).searchProfile();
			break;
		case "/profile/select":
			new ProfileController(request,response).selectProfile();;
			break;
		case "/profile":
			new ProfileController(request,response).viewProfile();;
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
			//sign into an existing account
			new LoginController(request,response).signIn();
			break;
		case "/create-account":
			//create a new account and add it to the database
			new LoginController(request, response).createAccount();
			break;
		case "/logout":
			//logout of the account login-ed to
			new LoginController(request,response).logout();
			break;
		case "/employee/ticket":
			//create and add a ticket to the database
			new EmployeeController(request,response).submitTicket();
			break;
		case "/manager/ticket":
			//create and add a ticket to the database
			new ManagerController(request,response).submitTicket();
			break;
		case "/manager/pending-tickets":
			//process pending tickets
			new ManagerController(request,response).processPendingTickets();
			break;
		case "/manager/edit-accounts":
			new ManagerController(request,response).editAccounts();
			break;
		case "/profile":
			new ProfileController(request,response).editProfile();
			break;
		default:
			//resource does not exist
			response.setStatus(404);
			break;
		}
	}
}
