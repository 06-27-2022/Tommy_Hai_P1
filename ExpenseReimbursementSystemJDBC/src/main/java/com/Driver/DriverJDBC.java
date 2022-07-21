package com.Driver;
import java.util.List;

import com.account.Account;
import com.jdbc.AccountList;
import com.menu.LoginMenu;
import com.menu.Menu;
import com.util.OutputUtil;

public class DriverJDBC {
		
//	private static final Logger logger = LoggerFactory.getLogger(DriverJDBC.class);
	
	public static void main(String[] args) {	
		
		OutputUtil.logger.trace("Start Program");
		
		List<Account>alist= new AccountList();							
		Menu login = new LoginMenu(alist);
		
		OutputUtil.logger.trace("Entering Login Menu");
		login.traverse();

		OutputUtil.logger.trace("End Program");
		return;
	}

}
/**
C:\Users\tomh0\Documents\Revature\Project 1\Tommy_Hai_P1\ExpenseReimbursementSystem\Images\1.png
*/