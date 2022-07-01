package Menu;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Account.*;

public class Login implements Menu{
	
	protected ArrayList<Account>acc;
	
	/*
	 * will use the provided arraylist as the acc
	 * arraylist
	 */
	public Login() {	
		acc = new ArrayList<Account>();
	}
	public Login(ArrayList<Account>accounts) {	
		acc = accounts;
	}
	
	/*
	 * returns Account if username and password match an account in acc arraylist
	 * returns null if the user does not exist or password does not match
	 */
	public Account signIn(String username,String password) {
		//checks if user exists
		Account user=searchUser(username);
		if(user==null)
			return null;
		
		//checks password
		if(user.getPassword().equals(password))
			return user;
		return null;
	}
	
	/*
	 * if the username isn't already taken
	 * creates an account in the acc arraylist
	 * returns true if an account is created
	 * returns false if an account is not created
	 */
	public boolean createAccount(String username, String password) {
		return createAccount(username,password,"Employee");
	}
	public boolean createAccount(String username, String password,String role) {
		if(searchUser(username)!=null)
			return false;
			
		if(role.equalsIgnoreCase("Manager"))
			acc.add(new Manager(username,password));
		else
			acc.add(new Employee(username,password));
		return true;
	}
	
	/*
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	private Account searchUser(String username) {
		for(Account a:acc)
			if(a.getName().equals(username))
				return a;
		return null;
	}
	
	/*
	 * returns the acc arraylist
	 */
	public ArrayList<Account>getAccounts() {
		return acc;
	}
	
	/*
	 * replaces the acc arraylist with the one provided
	 */
	public void setAccounts(ArrayList<Account>accounts) {
		acc=accounts;
	}

	public void traverse() {
		String option="";
		
		while(!option.equalsIgnoreCase("exit")) {	
			System.out.println("Login\nOptions: Exit, Login, Create Account");
			option = input();
			//Exit
			//login
			if(option.equalsIgnoreCase("login"))
				login();
			//create account
			else if(option.equalsIgnoreCase("Create Account"))
				createAccount();
		}
	}
	/*
	 * login through console
	 */
	private void login() {
		System.out.println("Username:");
		String user = input();
		System.out.println("Password:");
		String pass = input();
		Account acc = signIn(user,pass);
		if(acc!=null ) {
			System.out.println("Welcome "+acc.getName()+"\nRole:"+acc.getRole());
			Menu menu;
			if(acc.getRole().equals("Manager"))
				menu=new ManagerMenu(acc,this.acc);
			else
				menu=new EmployeeMenu(acc);
			menu.traverse();
		}
		else
			System.out.println("User and Password combination do not exist");
	}
	/*
	 * create account through console
	 */
	private void createAccount() {
		System.out.println("Username:");
		String user = input();
		System.out.println("Password:");
		String pass = input();
		System.out.println("Role:");
		String role= input();
		boolean success = createAccount(user,pass,role);
		if(success)
			System.out.println("Account creation successful");
		else
			System.out.println("Account name unavailable");	
	}
	
	/*
	 * input string in console
	 */
	public String input() {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = in.readLine();
		} catch (IOException e) {
			str=null;
		}
		return str;			
	}
}
