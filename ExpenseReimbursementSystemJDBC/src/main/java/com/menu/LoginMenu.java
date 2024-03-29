package com.menu;
import java.util.List;

import com.account.*;
import com.util.OutputUtil;

public class LoginMenu extends Menu{
	
	private List<Account>Accounts;

	/*
	 * will use the provided arraylist as the acc
	 * arraylist
	 */
	public LoginMenu(List<Account>accounts) {	
		Accounts = accounts;
		menuOptions=new String[3];
		menuOptions[0]="Exit";
		menuOptions[1]="Login";
		menuOptions[2]="Create Account";
		OutputUtil.logger.trace("Created Instance of Login class");
	}
	
	public void traverse() {
		OutputUtil.logger.trace("Traversing the Login menu");
		consoleTraverse();
	}
	
	/*
	 * using console for input
	 */
	public String input() {
		return consoleInput();
	}
	
	/*
	 * login through console
	 */
	public void login() {
		//get credentials
		OutputUtil.println("Username:");
		String user = input();
		OutputUtil.println("Password:");
		String pass = input();

		//get account
		Account acc = getAccount(user,pass);
		if(acc!=null ) {
			OutputUtil.println("Welcome "+acc.getName()+"\nRole:"+acc.getRole());
			Menu menu;
			if(acc.getRole().equals("Manager"))
				menu=new ManagerMenu(acc,Accounts);
			else
				menu=new EmployeeMenu(acc,Accounts);
			menu.traverse();
		}
		else
			OutputUtil.println("User and Password combination do not exist");
	}

	/*
	 * create account through console
	 */
	public void createAccount() {
		//get credentials
		OutputUtil.println("Username:");
		String user = input();
		OutputUtil.println("Password:");
		String pass = input();
		OutputUtil.println("Role:");
		String role= input();
		
		//create account
		boolean success = createAccount(user,pass,role);
		if(success)
			OutputUtil.println("Account creation successful");
		else
			OutputUtil.println("Account name unavailable");	
	}

	protected void consoleTraverse() {
		boolean exit=false;
		while(!exit) {
			int option = consoleSelect();
			switch(option) {
				case 0:
					exit=true;
				break;
				case 1:
					login();
				break;
				case 2:
					createAccount();
				break;
				default:
					System.out.println("Error");
			}
		}
	}

	/*
	 * returns Account if username and password match an account in acc arraylist
	 * returns null if the user does not exist or password does not match
	 */
	protected Account getAccount(String username,String password) {
		//checks if user exists
		Account user=searchAccount(username);
		if(user==null)
			return null;
		
		//checks password
		if(user.getPassword().equals(password))
			return user;
		return null;
	}

	/*
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	protected Account searchAccount(String username) {
		//for(Account a:acc)
		for(int i=0;i<Accounts.size();i++) {
			Account a=Accounts.get(i);
			if(a.getName().equals(username))
				return a;
		}
		return null;
	}

	/*
	 * Creates a new Account
	 */
	protected boolean createAccount(String username, String password,String role) {
		if(searchAccount(username)!=null)
			return false;
		if(role.equalsIgnoreCase("Manager"))
			Accounts.add(new AccountRemote(username,password,"Manager"));
		else
			Accounts.add(new AccountRemote(username,password,"Employee"));
		return true;
	}
	/*
	 * returns the acc arraylist
	 */
	protected List<Account>getAccounts() {
		return Accounts;
	}
	
	/*
	 * replaces the acc arraylist with the one provided
	 */
	protected void setAccounts(List<Account>accounts) {
		Accounts=accounts;
	}


}
