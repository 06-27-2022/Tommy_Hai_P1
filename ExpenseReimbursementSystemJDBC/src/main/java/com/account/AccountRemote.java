package com.account;

import java.util.List;

import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;
import com.jdbc.ProfileList;
import com.jdbc.TicketList;

public class AccountRemote implements Account{
	private final int ACCOUNT_ID;
	private String Name;
	private String Password;
	private String Role;
	
	/**
	 * account using this id should already by on account table
	 * i'm thinking it might have been better to create an account interface and having a local and
	 * a table variant as its children.
	 * @param accountID
	 */
	public AccountRemote(int accountID) {
		ACCOUNT_ID=accountID;
	}	
	public AccountRemote(String name,String password,String role) {
		//insert into account ("name",pass,"role")values('asdf','asdf','Employee')returning id;
		//final String SQL = "insert into account (\"name\",pass,\"role\")values(?,?,?)returning id";
		if(role.equalsIgnoreCase("Manager"))
			role="Manager";
		else
			role="Employee";
		//Object[]args= {name,password,role};
		//this.ACCOUNT_ID =(int)ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
		ACCOUNT_ID=-1;
		Name=name;
		Password=password;
		Role=role;
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
			return Role;
		}
		//select amount from ticket where id=1;
		final String SQL="select \"role\" from account where id="+ACCOUNT_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 * unimplemented
	 * @param name
	 * @param address
	 * @param picFilePath
	 * @return
	 */
	public boolean setProfile(Profile p) {
		if(local()) {return false;}
		//update profile set name='qwer',address='qewr',picture=1 where id=1;
//		final String SQL="update profile set name=?,address=?,picture=? where account="+getID();	
//		Object[]args= {p.getName(),p.getAddress(),p.getPicture().getID()};
//		return ConnectionUtil.stmtExecute(SQL, args);
		Profile profile=getProfile();
		profile.setName(p.getName());
		profile.setAddress(p.getAddress());
		profile.setPicture(p.getPicture());
		return true;
	}	
	/**
	 * unimplemented
	 * @return
	 */
	public Profile getProfile() {
		//select amount from ticket where id=1;
		final String SQL="select \"id\" from profile where account="+getID();
		try{
			return new ProfileRemote((int) ConnectionUtil.stmtExecuteQuery(SQL));
		}catch(ArrayIndexOutOfBoundsException e) {//no profile
			createProfile();
			return getProfile();
		}
	}	
	private boolean createProfile() {
		List<Profile>plist = new ProfileList();
		Profile p=new ProfileRemote(getID(), getName(), "No Address", null);
		plist.add(p);
		return true;
	}
	
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
	public List<Ticket>getTickets(String status){
		if(local()) {
			return null;
		}
		if(status.equalsIgnoreCase("p")||status.equalsIgnoreCase("pending"))
			status="p";
		else if(status.equalsIgnoreCase("a")||status.equalsIgnoreCase("approved"))
			status="a";
		else if(status.equalsIgnoreCase("ad")||status.equalsIgnoreCase("denied"))
			status="d";
		return new TicketList(getID(),status);
	}
	

	/**
	 * approve or deny the provided ticket
	 * @param t	the ticket being approved or denied
	 * @param approve true=approve, false=deny
	 * @return only returns true if the ticket table was changed
	 */
	public boolean approveTicket(Ticket t,boolean approve){
		if(local()) {
			return false;
		}
		else if(getRole().equalsIgnoreCase("Manager")) {
			t.setStatus(approve);			
			return true;
		}
		else 
			return false;
		
	}
	
	public static void main(String[]args) {
		AccountList alist = new AccountList();
		Account a = alist.get(4);
		System.out.println(a);		
		String str = a.getProfile().toString();
		System.out.println(str);
	}

}
