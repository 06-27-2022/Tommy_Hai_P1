import Account.*;
import Menu.*;

import java.util.*;

public class Driver {
	
	public static void main(String[] args) {		
		//accounts
		ArrayList<Account>acc = new ArrayList<Account>();
		Account  a = new Account("admin","1234");
		Account e = new Account("Emp", "qwer1234");
		Account  m = new Account("Man","password");
		acc.add(a);
		acc.add(e);
		acc.add(m);
		
		//tickets
		Ticket t = new Ticket(1234.2345,"bqwerqwejkg");
		e.getTickets().add(t);
		t = new Ticket(0.6789,"q");
		acc.get(0).getTickets().add(t);
		
		
		Menu login = new Login(acc);
		login.traverse();

		System.out.println("Close");
	}

}
