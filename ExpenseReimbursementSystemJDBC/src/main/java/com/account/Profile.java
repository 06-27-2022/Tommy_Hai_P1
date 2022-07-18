package com.account;

public interface Profile {

	int getID();

	int getAccountID();

	//name
	String getName();

	boolean setName(String name);

	//address
	String getAddress();

	boolean setAddress(String address);

	String toString();

	/**
	 * prints name, address, and picture filepath into the console
	 * will also display the picture in a new window.
	 */
	void print();

	/**
	 * used for the profile picture
	 * @return The File object containing the saved profile picture
	 */
	int getImage();

	/**
	 * used for the profile picture
	 * @param filePath The file path of the profile picture
	 */
	boolean setImage(String filePath);

	Picture getPicture();

}