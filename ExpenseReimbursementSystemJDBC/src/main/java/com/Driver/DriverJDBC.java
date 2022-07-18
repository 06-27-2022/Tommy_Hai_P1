package com.Driver;
import java.util.List;

import com.account.Account;
import com.jdbc.AccountList;
import com.menu.Login;
import com.menu.Menu;

public class DriverJDBC {
		
	public static void main(String[] args) {		
		List<Account>alist= new AccountList();		
					
		Menu login = new Login(alist);
		login.traverse();

		System.out.println("Close");
		System.exit(0);
	}

}
/**
C:\Users\tomh0\Documents\Revature\Project 1\Tommy_Hai_P1\ExpenseReimbursementSystem\Images\1.png
*/