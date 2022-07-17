package com.account;

import java.util.List;

import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;
import com.jdbc.TicketList;

public class Account{
	private final int ACCOUNT_ID;
	private String Name;
	private String Password;
	private String Role;
	
	/**
	 * account using this id should already by on account table
	 * @param accountID
	 */
	public Account(int accountID) {
		ACCOUNT_ID=accountID;
	}	
	public Account(String name,String password,String role) {
		//insert into account ("name",pass,"role")values('asdf','asdf','Employee')returning id;
		final String SQL = "insert into account (\"name\",pass,\"role\")values(?,?,?)returning id";
		if(role.equalsIgnoreCase("Manager"))
			role="Manager";
		else
			role="Employee";
		Object[]args= {name,password,role};
		this.ACCOUNT_ID =(int)ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
	}
	private boolean local() {
		if(ACCOUNT_ID==-1) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return "Name:"+getName()+"|Pass:"+getPassword()+"|Role:"+getRole();
	}
	public int getID() {
		return ACCOUNT_ID;
	}
	
	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean setName(String name) {
		if(local()) {
			return false;
		}
		//update account set pass = 'test' where id=1;
		final String SQL="update account set name = ? where id="+ACCOUNT_ID;
		Object[]args= {name};
		return ConnectionUtil.stmtExecute(SQL,args);
	}
	/**
	 * 
	 * @return
	 */
	public String getName(){
		if(local()) {
			return Name;
		}
		//select amount from ticket where id=1;
		final String SQL="select \"name\" from account where id="+ACCOUNT_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 * password
	 * @param password
	 * @return
	 */
	public boolean setPassword(String password) {
		if(local()) {
			return false;
		}
		//update account set pass = 'test' where id=1;
		final String SQL="update account set pass = ? where id="+ACCOUNT_ID;
		Object[]args= {password};
		return ConnectionUtil.stmtExecute(SQL,args);
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		if(local()) {
			return Password;
		}

		//select amount from ticket where id=1;
		final String SQL="select \"pass\" from account where id="+ACCOUNT_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}

	/**
	 * role
	 * @param role
	 * @return
	 */
	public boolean setRole(String role) {
		if(local()) {
			return false;
		}
		//update account set pass = 'test' where id=1;
		final String SQL="update account set role = ? where id="+ACCOUNT_ID;
		Object[]args=new Object[1];
		if(role.equalsIgnoreCase("Manager"))
			args[0]="Manager";
		else
			args[0]="Employee";
		return ConnectionUtil.stmtExecute(SQL,args);
	}	
	public String getRole() {
		if(local()) {
			return "Local Account";
		}
		//select amount from ticket where id=1;
		final String SQL="select \"role\" from account where id="+ACCOUNT_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 * 
	 * @param name
	 * @param address
	 * @param picFilePath
	 * @return
	 */
//	public boolean setProfile(int profileID) {
//		//update account set pass = 'test' where id=1;
//		final String SQL="update account set name = ? where id="+ACCOUNT_ID;
//		Object[]args= {profileID};
//		return ConnectionUtil.stmtExecute(SQL,args);
//	}	
//	public int getProfile() {
//		//select amount from ticket where id=1;
//		final String SQL="select \"pass\" from account where id="+ACCOUNT_ID;
//		return 0;//(String) ConnectionUtil.stmtExecuteQuery(SQL);
//	}	
	
	/**
	 * All tickets submitted by this account
	 * @return an arraylist containing processed and pending tickets
	 */
	public List<Ticket>getTickets(){
		if(local()) {
			return null;
		}
		return new TicketList(getID());
	}
	public List<Ticket>getPendingTickets(){
		if(local()) {
			return null;
		}
		return new TicketList(getID(),"p");
	}
	

	/**
	 * approve or deny the provided ticket
	 * @param t	the ticket being approved or denied
	 * @param approve true=approve, false=deny
	 * @throws Exception the ticket does not exist on this account
	 */
	public void approveTicket(Ticket t,boolean approve){
		if(local()) {
			return;
		}
		t.setStatus(approve);
	}
	
	public static void main(String[]args) {
		//Account a1=new Account("test1","test1","whuh");
		//System.out.println(a1.getID());
		AccountList alist = new AccountList();
		Account a = alist.get(1);
		System.out.println(a);		
		a.setName("a2");
		a.setPassword("a2");
		a.setRole("employee");
		System.out.println(a);		
	}

}
