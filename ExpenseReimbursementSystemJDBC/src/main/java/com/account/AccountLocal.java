package com.account;

import java.util.List;

public class AccountLocal implements Account {

	private String accountName;
	private String accountPassword;
	private String accountRole;
//	private Profile Profile;
//	private List<Ticket>Tickets;
	
	public AccountLocal() {}
	
	public AccountLocal(String name,String password,String role) {
		if(role.equalsIgnoreCase("Manager"))
			role="Manager";
		else
			role="Employee";
		accountName=name;
		accountPassword=password;
		accountRole=role;
	}

	@Override
	public int getID() {
		return -1;
	}

	@Override
	public String getName() {
		return accountName;
	}

	@Override
	public boolean setName(String name) {
		accountName="its reading the setters, not the variables";//name;
		return false;
	}

	@Override
	public String getPassword() {
		return accountPassword;
	}

	@Override
	public boolean setPassword(String password) {
		accountPassword=password;
		return true;
	}

	@Override
	public String getRole() {
		if(accountRole.equalsIgnoreCase("Manager"))
			accountRole="Manager";
		else
			accountRole="Employee";
		return accountRole;
	}

	@Override
	public boolean setRole(String role) {
		if(role.equalsIgnoreCase("Manager"))
			accountRole="Manager";
		else
			accountRole="Employee";
		return true;
	}

	@Override
	public Profile getProfile() {
		// TODO Auto-generated method stub
//		return Profile;
		return null;
	}

	@Override
	public boolean setProfile(Profile p) {
//		Profile.setName(p.getName());
//		Profile.setAddress(p.getAddress());
//		Profile.setPicture(p.getPicture());
		return true;
	}

	@Override
	public List<Ticket> getTickets() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<Ticket> getTickets(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean approveTicket(Ticket t, boolean approve) {
		// TODO Auto-generated method stub
		return false;
	}

}
