import Account.*;
import Menu.*;

import java.util.*;

public class Driver {
	
	public static Account generateAccount(int x) {

		//account parameters
		String name = ""+x;
		String pass = ""+x;
		String role;
		if(Math.random()>0.5)
			role = "Manager";
		else
			role="Employee";
		
		//create and return
		Account a = new Account(name, pass, role);
		return a;
	}
	public static void generateTicket(Account a) {
		double value = Math.random()*(100);
		String desc = "d"+Math.random();
		//Ticket t = new Ticket(value,desc,a.getName(),"Other","N/A");
		a.addTicket(value,desc,"Other","N/A");
	}
	
	
	public static void main(String[] args) {		
		//accounts
		ArrayList<Account>acc = new ArrayList<Account>();
		Account  m = new Account("m","m","Manager");
		Account e = new Account("e", "e","Employee");
		acc.add(e);
		acc.add(m);
		
		int numofaccounts=5;
		int numoftickets=2;
		for(int i =0;i<numofaccounts;i++){
			Account a = generateAccount(i);
			for(int j =0;j<numoftickets;j++){
				generateTicket(a);
			}	
			acc.add(a);
		}
		
		
		Menu login = new Login(acc);
		login.traverse();

		System.out.println("Close");
		System.exit(0);
	}

}
