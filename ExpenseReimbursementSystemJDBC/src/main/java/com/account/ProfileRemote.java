package com.account;

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
	private Picture ProfilePicture;	
	
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
		ProfilePicture = picture;
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
		return "ProfileID:"+getID()+"|Name:"+getName()+"|Address:"+getAddress()+"|PictureID:"+getPictureID();
	}
	/**
	 * prints name, address, and picture file path into the console
	 * will also display the picture in a new window.
	 */
	@Override
	public void print() {
		System.out.println(toString());
		//Picture p = new PictureRemote(getID());
		getPicture().displayPicture(200, 200);
	}

	/**
	 * used for the profile picture
	 * @return The File object containing the saved profile picture
	 */
	private int getPictureID() {
		if(local()) {return -1;}
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
	public boolean setPicture(Picture picture) {
		if(local())return false;
		return getPicture().setPictureFile(picture.getPictureFile().getAbsolutePath());
	}
	
	@Override
	public Picture getPicture() {
		if(local()) {
			if(ProfilePicture==null)
					return createPicture();
			return ProfilePicture;
		}
		return new PictureRemote(getPictureID());
	}
	
	/**
	 * for use when there is no picture
	 * @return local PictureRemote
	 */
	private Picture createPicture() {
		Picture p=new PictureRemote(getName(),null);
		return p;
	}
	
	/**
	 * displays the profile picture in a new window
	 */
//	public void displayPicture() {
//		profilePic.displayPicture();
//	}
	
	public static void main(String[]args){
		Profile p = new ProfileRemote(2);
		System.out.println(p.getAccountID());
		System.out.println(p.getName());
		System.out.println(p.getAddress());
		System.out.println(p.getPicture().getID());
		p.print();
	}
	
}
