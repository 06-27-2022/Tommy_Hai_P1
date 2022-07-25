package com.jdbc;
import java.util.List;

import com.account.Account;
import com.account.AccountRemote;


public class AccountList extends jdbcList<Account> {


	public AccountList(){}
	
	@Override
	public int size() {
		final String SQL ="select count(*) from account";
		Object[]args= {};
		return (int) ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
	}
	
	/**
	 * will create a new account using the parameters of the one supplied
	 * @param acc the account being cloned and added into the account table
	 */
	@Override
	public boolean add(Account acc) {
		final String SQL = "insert into account values(default,?,?,?)";
						  //insert into account values(default,'test','test','Employee');
		Object[]o= {acc.getName(),acc.getPassword(),acc.getRole()};
		return ConnectionUtil.stmtExecute(SQL, o);
	}
	@Override
	public Account get(int index) {
		//final String SQL = "SELECT \"name\", \"pass\", \"role\" FROM account order by id asc limit 1 offset ?";
		final String SQL = "SELECT id FROM account order by id asc limit 1 offset ?";
		Object[]args= {index};
		Object[][]a=ConnectionUtil.stmtExecuteQuery2D(SQL,args);
		int id=(int)a[0][0];
		return new AccountRemote(id);		
	}

	@Override
	public boolean isEmpty() {
		if(size()==0)
			return true;
		return false;
	}

	public static void main(String[]args) {
		List<Account> alist= new AccountList();
		//Account a = new Account("c","c","Manager");
		//alist.add(a);
		Account b = alist.get(0);
		//System.out.println("a|"+a.getName()+a.getPassword()+a.getRole());
		System.out.println("b|"+b.getName()+b.getPassword()+b.getRole());
		System.out.println("size:"+alist.size());
		
	}

	
}
