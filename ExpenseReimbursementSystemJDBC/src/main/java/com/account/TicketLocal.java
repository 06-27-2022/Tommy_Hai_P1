package com.account;

public class TicketLocal implements Ticket{

	private int accountID;
	private double ammount;
	private String status;
	private String type;
	private Picture picture;
	
	public TicketLocal() {}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getAccountID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getAmount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setStatus(boolean approve) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Picture getPicture() {
		// TODO Auto-generated method stub
		return null;
	}

}
