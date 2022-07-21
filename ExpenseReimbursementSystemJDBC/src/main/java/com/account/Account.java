package com.account;

import java.util.List;

public interface Account{
	public abstract int getID();
	public abstract String getName();
	public abstract boolean setName(String name);
	public abstract String getPassword();
	public abstract boolean setPassword(String password);
	public abstract String getRole();
	public abstract boolean setRole(String role);
	public abstract Profile getProfile();
	public abstract boolean setProfile(Profile p);
	public abstract List<Ticket>getTickets();
	public abstract List<Ticket> getTickets(String status);
	public abstract boolean approveTicket(Ticket t,boolean approve);
}
