package com.account;

public class TicketLocal implements Ticket{

	private int accountID;
	private double ammount;
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

	@Override
	public double getAmount() {
		return ammount;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public String getStatus() {
		return status;
	}

	@Override
	public boolean setStatus(boolean approve) {
		if(approve) {
			status="a";
			return true;
		}
		status="d";
		return false;
	}

	@Override
	public String getType() {
		return type;
	}
	public boolean setType() {
		if(type.equals("Travel"))
			type="Travel";
		else if(type.equals("Lodging"))
			type="Lodging";
		else if(type.equals("Food"))
			type="Food";
		else 
			type="Other";
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
