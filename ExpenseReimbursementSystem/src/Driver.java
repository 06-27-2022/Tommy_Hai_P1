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
	public static Ticket generateTicket(Account a) {
		double value = Math.random()*(100);
		String desc = "test"+Math.random();
		Ticket t = new Ticket(value,desc,a.getName(),"Other","N/A");
		return t;
	}
	
	
	public static void main(String[] args) {		
		//accounts
		ArrayList<Account>acc = new ArrayList<Account>();
		Account  m = new Account("m","m","Manager");
		Account e = new Account("e", "e","Employee");
		acc.add(e);
		acc.add(m);
		
		int numofaccounts=3;
		int numoftickets=1;
		for(int i =0;i<numofaccounts;i++){
			Account a = generateAccount(i);
			for(int j =0;j<numoftickets;j++){
				Ticket t = generateTicket(a);
				a.addTickets(t);
			}	
			acc.add(a);
		}
		
		
		Menu login = new Login(acc);
		login.traverse();

		System.out.println("Close");
	}

}
