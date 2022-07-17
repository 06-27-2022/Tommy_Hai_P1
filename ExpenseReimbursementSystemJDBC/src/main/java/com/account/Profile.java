package com.account;

import java.io.File;
import java.io.IOException;

/**
 * -track additional user information
	-name, address, etc
	-users can edit their account
	-users can add profile picture
 */
public class Profile {
	
	//first name sent when initialized
	//used for naming local copy of picture
	private final String ACCOUNTNAME;
	
	//dimensions of picture
	private static int PicX, PicY;
	
	private String Name;
	private String Address;
	private Picture profilePic;
	
	/**
	 * Profile attached to accounts. 1 profile assigned to 1 account
	 * @param name displayed in the profile. Name used to initialize should be the name of the Account
	 * @param address displayed in the profile.
	 * @param pictureFilePath file path of profile picture
	 */
	public Profile(String name, String address, String pictureFilePath) {
		ACCOUNTNAME=name;
		PicX=200;
		PicY=150;
		setName(name);
		setAddress(address);
		setPicture(pictureFilePath);
	}
	
	//name
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name=name;
	}
	
	//address
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		this.Address=address;
	}
	

	/**
	 * prints name, address, and picture filepath into the console
	 * will also display the picture in a new window.
	 */
	public void print() {
		System.out.println("Name:"+getName());
		System.out.println("Address:"+getAddress());
		System.out.println("Picture:"+getPicture().getName());
		displayPicture();
	}

	/**
	 * used for the profile picture
	 * @return The File object containing the saved profile picture
	 */
	public File getPicture() {
		return profilePic.getPictureFile();
	}	
	/**
	 * used for the profile picture
	 * @param filePath The file path of the profile picture
	 */
	public void setPicture(String filePath) {
		profilePic = new Picture(PicX, PicY, ACCOUNTNAME, filePath);
	}
	
	/**
	 * displays the profile picture in a new window
	 */
	public void displayPicture() {
		profilePic.displayPicture();
	}
	
	public static void main(String[]args) throws IOException {
		String name = "ProfileTest";
		String address = "address";
		//String filepath = "Screenshot (1).png";
		String filepath = "ProfileTet.png";
		Profile test = new Profile(name,address,filepath);
		
		test.print();
	}
	
}
