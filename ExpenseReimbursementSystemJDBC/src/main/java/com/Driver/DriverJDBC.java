package com.Driver;
import java.util.*;

import com.account.Account;
import com.jdbc.AccountList;
import com.menu.*;

public class DriverJDBC {
		
	public static void main(String[] args) {		
		List<Account>alist= new AccountList();		
					
		Menu login = new Login(alist);
		login.traverse();

		System.out.println("Close");
		System.exit(0);
	}

}
