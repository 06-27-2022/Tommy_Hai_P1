package com.jdbc;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.account.Account;
import com.account.AccountRemote;


public class AccountList implements List<Account> {


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

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Account> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Account> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Account> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccountRemote set(int index, Account element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Account element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AccountRemote remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Account> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Account> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Account> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
