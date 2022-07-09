package Account;

import java.util.ArrayList;

public class Account{
	protected String name;
	protected String password;
	protected String role;
	protected ArrayList<Ticket>tickets;
	protected Profile profile;
	
	public Account(String name, String password, String role) {
		setName(name);
		setPassword(password);
		setRole(role);
		tickets=new ArrayList<Ticket>();
		profile = new Profile(name,"N/A","N/A");
	}	
	//user name
	public void setName(String name){
		this.name=name;
	}
	public String getName(){
		return name;
	}
	
	//password
	public void setPassword(String password) {
		this.password=password;
	}
	public String getPassword() {
		return password;
	}

	//role
	public void setRole(String role) {
		this.role=role;
	}	
	public String getRole() {
		return role;
	}
	
	//profile
	public void setProfile(String name, String address, String picFilePath) {
		profile.setName(name);
		profile.setAddress(address);
		profile.setPicture(picFilePath);
	}	
	public Profile getProfile() {
		return profile;
	}	
	
	//get tickets
	public ArrayList<Ticket>getTickets(){
		return tickets;
	}
	//get pending tickets
	public ArrayList<Ticket>getPendingTickets(){
		ArrayList<Ticket>pending=new ArrayList<Ticket>();
		for(Ticket t:tickets)
			if(t.getStatus().equalsIgnoreCase("Pending"))
				pending.add(t);
		return pending;
	}
	//add ticket t to tickets arraylist
	public void addTickets(Ticket t) {
		tickets.add(t);
	}

}
