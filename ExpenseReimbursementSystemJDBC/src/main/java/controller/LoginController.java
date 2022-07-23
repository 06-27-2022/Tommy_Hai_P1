package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.account.Account;
import com.account.AccountLocal;
import com.account.AccountRemote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;

public class LoginController extends Controller{

	private List<Account>accounts;
//	private HttpServletRequest request;
//	private HttpServletResponse response;
//	private PrintWriter writer;
	
	public LoginController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super(request,response);
		accounts=new AccountList();
//		this.request=request;
//		this.response=response;
//		writer = response.getWriter();
	}
	
	public void signIn()throws ServletException, IOException {
		response.setStatus(200);
		response.addHeader("Custom Header", "custom value");
		response.setContentType("application/json");
		writeln("<h2>Sign In</h2>");
	
		//converts and stores the request as bytes
		String requestBodyText = new String(request.getInputStream().readAllBytes());

		//map the json to a local account to compare with the remote accounts
		ObjectMapper objectMapper = new ObjectMapper();
		Account requestedAccount = objectMapper.readValue(requestBodyText, AccountLocal.class);		
		
		//gets the account from account list with matching crudentials
		Account acc = getAccount(requestedAccount.getName(),requestedAccount.getPassword());

		//signed in successfully
		if(acc!=null ) {
			writeln("Welcome "+acc.getName()+"\nRole:"+acc.getRole());			
			HttpSession session = request.getSession();
			session.setAttribute("accountID", acc.getID());
		}else {
			//account does not exist
			response.setStatus(401);
		}
	}
	
	public void logout() {
		writer.write("<h2>Logout</h2><br>");
		HttpSession session = request.getSession(false);
		if(session != null) {
			session.invalidate();
			writeln("Successfully Logged Out");
		}
		else
			writeln("No Account Signed In");
	}
	
	public void createAccount()throws ServletException, IOException {
		response.setStatus(200);
		response.addHeader("Custom Header", "custom value");
		response.setContentType("application/json");

		write("sign up\n");
		
		//input from request
		String requestBodyText = new String(request.getInputStream().readAllBytes());
		ObjectMapper objectMapper = new ObjectMapper();
		Account acc = objectMapper.readValue(requestBodyText, AccountLocal.class);
		
		//create account
		boolean success = addAccount(acc.getName(),acc.getPassword(),acc.getRole());
		if(success)
			writeln("Account Creation Successful");
		else
			writeln("Account Creation Unsuccessful");
	}
	
	private void write(String str) {
		writer.write(str);
	}
	public void writeln(String str) {
		writer.write("<p>"+str+"</p><br>");
	}
	
	/*
	 * returns Account if username and password match an account in acc arraylist
	 * returns null if the user does not exist or password does not match
	 */
	private Account getAccount(String username,String password) {
		//select id from account where name='m' and pass='m';
		final String SQL="select id from account where name=? and pass=?";
		String[]args= {username,password};
		int accountID = (int)ConnectionUtil.stmtExecuteQuery(SQL,args);
		if(accountID<0)
			return null;
		return new AccountRemote(accountID);
	}
		
	/*
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	private Account searchAccount(String username) {
		final String SQL="select id from account where name=?";
		int accountID;
		try{
			accountID = (int)ConnectionUtil.stmtExecuteQuery(SQL,username);
		}catch(ArrayIndexOutOfBoundsException e) {//account does not exist
			return null;
		}
		return new AccountRemote(accountID);
	}

	/*
	 * Creates a new Account
	 */
	private boolean addAccount(String username, String password,String role)throws ServletException, IOException  {
		if(searchAccount(username)!=null)
			return false;		
		if(role.equalsIgnoreCase("Manager")) {
			writeln("Creating Manager Account");
			accounts.add(new AccountRemote(username,password,"Manager"));
		}else {
			writeln("Creating Employee Account");
			accounts.add(new AccountRemote(username,password,"Employee"));
		}return true;
	}
}
