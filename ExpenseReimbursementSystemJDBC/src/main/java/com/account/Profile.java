package com.account;

public interface Profile {

	int getID();
	int getAccountID();
	String getName();
	boolean setName(String name);
	String getAddress();
	boolean setAddress(String address);
	Picture getPicture();
	boolean setPicture(Picture picture);
	String toString();
	void print();

}