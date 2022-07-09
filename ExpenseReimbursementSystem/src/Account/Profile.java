package Account;

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
	final protected String ACCOUNTNAME;
	
	//dimensions of picture
	protected static int PicX, PicY;
	
	protected String Name;
	protected String Address;
	protected Picture profilePic;
	
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
	
	//print in console
	public void print() {
		System.out.println("Name:"+getName());
		System.out.println("Address:"+getAddress());
		System.out.println("Picture:"+getPicture());
		displayPicture();
	}

	//picture need to upgrade to class file
	public File getPicture() {
		return profilePic.getPicture();
	}	
	public void setPicture(String filePath) {
		profilePic = new Picture(PicX, PicY, ACCOUNTNAME, filePath);
	}
	public void displayPicture() {
		profilePic.displayPicture();
	}
	
	public static void main(String[]args) throws IOException {
		String name = "ProfileTest";
		String address = "address";
		//String filepath = "Screenshot (1).png";
		String filepath = "No";
		Profile test = new Profile(name,address,filepath);
		
		test.print();
	}
	
}
