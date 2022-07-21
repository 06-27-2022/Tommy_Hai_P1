package com.account;

import java.util.List;

public class AccountLocal implements Account {

	private String accountName;
	private String accountPassword;
	private String accountRole;
	private Profile profile;
//	private List<Ticket>tickets; local account can't submit tickets
	
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
		return profile;
	}

	@Override
	public boolean setProfile(Profile p) {
		profile=p;
		return true;
	}

	@Override
	public List<Ticket> getTickets() {
		return null;
	}
	@Override
	public List<Ticket> getTickets(String string) {
		return null;
	}

	@Override
	public boolean approveTicket(Ticket t, boolean approve) {
		return false;
	}
	@Override
	public String toString() {
		return getName()+"|"+getPassword()+"|"+getRole()+"|"+getProfile();
	}

}
