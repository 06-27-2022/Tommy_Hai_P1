package Account;
/*
 * -track additional user information
	-name, address, etc
	-users can edit their account
	-users can add profile picture
 */
public class Profile {
	
	protected String name;
	protected String address;
	protected String picture;
	
	public Profile(String name, String address, String picture) {
		this.name=name;
		this.address=address;
		this.picture=picture;
	}
	
	//name
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name=name;
	}
	
	//address
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address=address;
	}
	
	//picture
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture=picture;
	}
	
	//print in console
	public void print() {
		System.out.println("Name:"+name);
		System.out.println("Address:"+address);
		System.out.println("Picture:"+picture);
	}
}
