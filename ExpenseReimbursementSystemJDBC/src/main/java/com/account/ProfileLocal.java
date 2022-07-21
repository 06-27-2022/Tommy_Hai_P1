package com.account;

public class ProfileLocal implements Profile{

	private int accountID;
	private String name;
	private String address;
	private Picture picture;
	
	public ProfileLocal() {}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return -1;
	}
	@Override
	public int getAccountID() {
		// TODO Auto-generated method stub
		return accountID;
	}
	@Override
	public String getName() {
		return name;
	}
	@Override
	public boolean setName(String name) {
		this.name=name;
		return true;
	}
	@Override
	public String getAddress() {
		return address;
	}
	@Override
	public boolean setAddress(String address) {
		this.address=address;
		return true;
	}
	@Override
	public Picture getPicture() {
		return picture;
	}
	@Override
	public boolean setPicture(Picture picture) {
		this.picture=picture;
		return true;
	}
}
