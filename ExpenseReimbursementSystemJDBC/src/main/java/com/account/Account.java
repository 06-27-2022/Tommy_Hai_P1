package com.account;

import java.util.List;

public interface Account{
	public abstract int getID();
	public abstract boolean setName(String name);
	public abstract String getName();
	public abstract String getPassword();
	public abstract boolean setPassword(String password);
	public abstract String getRole();
	public abstract boolean setRole(String role);
	public abstract boolean setProfile(Profile p);
	public abstract Profile getProfile();
	public abstract List<Ticket>getTickets();
	public abstract List<Ticket> getTickets(String string);
	public abstract boolean approveTicket(Ticket t,boolean approve);
}
