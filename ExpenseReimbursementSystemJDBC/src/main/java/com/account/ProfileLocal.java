package com.account;

public class ProfileLocal implements Profile{

	private String name;
	private String address;
	private Picture picture;
	
	public ProfileLocal() {}
	
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
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setName(String name) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setAddress(String address) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Picture getPicture() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPicture(Picture picture) {
		// TODO Auto-generated method stub
		return false;
	}

}
