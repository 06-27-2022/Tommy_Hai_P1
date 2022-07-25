package com.account;

public class TicketLocal implements Ticket{

	private int accountID;
	private double amount;
	private String description;
	private String status;
	private String type;
	private Picture picture;
	
	
	public TicketLocal() {
		status="p";
	}
	
	@Override
	public int getID() {
		return -1;
	}

	@Override
	public int getAccountID() {
		return accountID;
	}
	protected void setAccountID(int accountID) {
		this.accountID=accountID;
	}
	@Override
	public double getAmount() {
		return amount;
	}
	protected void setAmount(int amount) {
		this.amount=(double)((int)(amount*100))/100;
	}

	@Override
	public String getDescription() {
		return description;
	}
	protected void setDescription(String description) {
		this.description=description;
	}

	
	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public boolean setStatus(boolean approve) {
		return false;
	}

	@Override
	public String getType() {
		return type;
	}
	public boolean setType(String type) {
		if(type.equals("Travel"))
			this.type="Travel";
		else if(type.equals("Lodging"))
			this.type="Lodging";
		else if(type.equals("Food"))
			this.type="Food";
		else 
			this.type="Other";
		return true;
	}

	@Override
	public Picture getPicture() {
		return picture;
	}
	public boolean setPicture(Picture p) {
		if(p==null)
			picture=new PictureRemote("AccountID"+getAccountID(),null);
		else
			picture=p;
		return true;
	}

}
