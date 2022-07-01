import Account.*;
import Menu.*;

import java.util.*;

public class Driver {
	
	public static void main(String[] args) {		
		//accounts
		ArrayList<Account>acc = new ArrayList<Account>();
		Account e = new Employee("Emp", "qwer1234");
		Account  m = new Manager("Man","password");
		acc.add(e);
		acc.add(m);
		acc.add(new Employee("qwer","qwer"));
		acc.add(new Manager("1234","1234"));

		//tickets
		Ticket t = new Ticket(1234.2345,"bqwerqwejkg");
		e.getTickets().add(t);
		
		
		Menu login = new Login(acc);
		
		login.traverse();
		System.out.println("Close");
	}

}
