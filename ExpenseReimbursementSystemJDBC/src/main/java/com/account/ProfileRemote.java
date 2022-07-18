package com.account;

import java.io.IOException;

import com.jdbc.ConnectionUtil;

/**
 * -track additional user information
	-name, address, etc
	-users can edit their account
	-users can add profile picture
 */
public class ProfileRemote implements Profile {
	
	private final int PROFILE_ID;
	private int AccountID;
	private String Name;
	private String Address;
	private Picture Image;	
	
	public ProfileRemote(int profileID) {
		PROFILE_ID=profileID;
	}
	
	/**
	 * 
	 * @param accountID
	 * @param name
	 * @param address
	 * @param pictureFilePath
	 */
	public ProfileRemote(int accountID, String name, String address,Picture picture) {
		PROFILE_ID=-1;
		AccountID=accountID;
		Name=name;
		Address=address;
		Image = picture;//new PictureRemote(pictureFilePath, pictureFilePath);
	}
	private boolean local() {
		if(PROFILE_ID==-1) {
			return true;
		}
		return false;
	}
	@Override
	public int getID() {
		if(local())return -1;
		return PROFILE_ID;
	}
	@Override
	public int getAccountID() {
		if(local())
			return AccountID;
		final String SQL = "select account from profile where id="+PROFILE_ID;
		return (int)ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	//name
	@Override
	public String getName() {
		if(local())return Name;
		final String SQL ="select name from profile where id="+PROFILE_ID;
		return (String)ConnectionUtil.stmtExecuteQuery(SQL);
	}
	@Override
	public boolean setName(String name) {
		if(local())return false;
		final String SQL ="update profile set name=? where id="+PROFILE_ID;
		String[]args= {name};
		return ConnectionUtil.stmtExecute(SQL,args);
	}
	
	//address
	@Override
	public String getAddress() {
		if(local())return Address;
		final String SQL ="select address from profile where id="+PROFILE_ID;
		return (String)ConnectionUtil.stmtExecuteQuery(SQL);
	}
	@Override
	public boolean setAddress(String address) {
		if(local())return false;
		final String SQL ="update profile set address=? where id="+PROFILE_ID;
		String[]args= {address};
		return ConnectionUtil.stmtExecute(SQL,args);
	}
	
	@Override
	public String toString() {
		return "ID:"+getID()+"|Name:"+getName()+"|Address:"+getAddress()+"|Image:"+getImage();
	}
	/**
	 * prints name, address, and picture filepath into the console
	 * will also display the picture in a new window.
	 */
	@Override
	public void print() {
		System.out.println(toString());
		Picture p = new PictureRemote(getID());
		p.displayPicture(100, 100);
	}

	/**
	 * used for the profile picture
	 * @return The File object containing the saved profile picture
	 */
	@Override
	public int getImage() {
		if(local()) {
			return -1;//Image.getID();
		}
		//select amount from ticket where id=1;
		final String SQL="select picture from profile where id="+PROFILE_ID;
		try{
			return (int) ConnectionUtil.stmtExecuteQuery(SQL);
		}catch(NullPointerException e) {
			return -1;//image id is null
		}		
	}
	
	/**
	 * used for the profile picture
	 * @param filePath The file path of the profile picture
	 */
	@Override
	public boolean setImage(String filePath) {
		if(local())return false;
		return Image.setPictureFile(filePath);
	}
	
	/**
	 * displays the profile picture in a new window
	 */
//	public void displayPicture() {
//		profilePic.displayPicture();
//	}
	
	public static void main(String[]args) throws IOException {
		Profile p = new ProfileRemote(1);
		System.out.println(p.getAccountID());
		System.out.println(p.getName());
		System.out.println(p.getAddress());
		System.out.println(p.getImage());
		p.print();
	}

	@Override
	public Picture getPicture() {
		if(local()) {return Image;}
		return new PictureRemote(getImage());
	}
	
}
