package com.account;

public interface Ticket {
	public String toString();
	public void print();
	public int getID();
	public int getAccountID();
	public double getAmount();
	public String getDescription();
	public String getStatus();
	public boolean setStatus(boolean approve);
	public String getType();
	public Picture getPicture();
}
