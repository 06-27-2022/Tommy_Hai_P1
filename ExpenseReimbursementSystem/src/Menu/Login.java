package Menu;
import java.util.ArrayList;

import Account.*;

public class Login extends Menu{
	
	protected ArrayList<Account>acc;
	
	/*
	 * will use the provided arraylist as the acc
	 * arraylist
	 */
	public Login(ArrayList<Account>accounts) {	
		acc = accounts;
		menuOptions=new String[3];
		menuOptions[0]="Exit";
		menuOptions[1]="Login";
		menuOptions[2]="Create Account";
	}
	
	public void traverse() {
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
		System.out.println("Username:");
		String user = input();
		System.out.println("Password:");
		String pass = input();

		//get account
		Account acc = getAccount(user,pass);
		if(acc!=null ) {
			System.out.println("Welcome "+acc.getName()+"\nRole:"+acc.getRole());
			Menu menu;
			if(acc.getRole().equals("Manager"))
				menu=new ManagerMenu(acc,this.acc);
			else
				menu=new EmployeeMenu(acc,this.acc);
			menu.traverse();
		}
		else
			System.out.println("User and Password combination do not exist");
	}

	/*
	 * create account through console
	 */
	public void createAccount() {
		//get credentials
		System.out.println("Username:");
		String user = input();
		System.out.println("Password:");
		String pass = input();
		System.out.println("Role:");
		String role= input();
		
		//create account
		boolean success = createAccount(user,pass,role);
		if(success)
			System.out.println("Account creation successful");
		else
			System.out.println("Account name unavailable");	
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
		for(Account a:acc)
			if(a.getName().equals(username))
				return a;
		return null;
	}

	/*
	 * Creates a new Account
	 */
	protected boolean createAccount(String username, String password,String role) {
		if(searchAccount(username)!=null)
			return false;
		if(role.equalsIgnoreCase("Manager"))
			acc.add(new Account(username,password,"Manager"));
		else
			acc.add(new Account(username,password,"Employee"));
		return true;
	}
	/*
	 * returns the acc arraylist
	 */
	protected ArrayList<Account>getAccounts() {
		return acc;
	}
	
	/*
	 * replaces the acc arraylist with the one provided
	 */
	protected void setAccounts(ArrayList<Account>accounts) {
		acc=accounts;
	}


}
