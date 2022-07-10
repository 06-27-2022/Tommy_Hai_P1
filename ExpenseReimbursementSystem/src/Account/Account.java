package Account;

import java.util.ArrayList;

public class Account{
	protected String Name;
	protected String Password;
	protected String Role;
	protected ArrayList<Ticket>Tickets;
	protected ArrayList<Ticket>PendingTickets;
	protected Profile AccountProfile;
	
	public Account(String name, String password, String role) {
		setName(name);
		setPassword(password);
		setRole(role);
		Tickets=new ArrayList<Ticket>();
		AccountProfile = new Profile(name,"N/A","N/A");
	}	
	//user name
	public void setName(String name){
		Name=name;
	}
	public String getName(){
		return Name;
	}
	
	//password
	public void setPassword(String password) {
		Password=password;
	}
	public String getPassword() {
		return Password;
	}

	//role
	public void setRole(String role) {
		Role=role;
	}	
	public String getRole() {
		return Role;
	}
	
	//profile
	public void setProfile(String name, String address, String picFilePath) {
		AccountProfile.setName(name);
		AccountProfile.setAddress(address);
		AccountProfile.setPicture(picFilePath);
	}	
	public Profile getProfile() {
		return AccountProfile;
	}	
	
	//get tickets
	public ArrayList<Ticket>getTickets(){
		ArrayList<Ticket>allTickets=new ArrayList<Ticket>();
		for(Ticket t:Tickets)
			allTickets.add(t);
		for(Ticket t:PendingTickets)
			allTickets.add(t);
		return allTickets;
	}
	//get pending tickets
	public ArrayList<Ticket>getPendingTickets(){
//		ArrayList<Ticket>pending=new ArrayList<Ticket>();
//		for(Ticket t:Tickets)
//			if(t.getStatus().equalsIgnoreCase("Pending"))
//				pending.add(t);
//		return pending;
		return PendingTickets;
	}
	//add ticket t to tickets arraylist
	public void addTickets(double amount, String description,String type, String filepath) {
		Ticket t=new Ticket(amount, description, Name, type, filepath);
		//Tickets.add(t);
		PendingTickets.add(t);
	}

	public void approveTicket(Ticket t,boolean approve) throws Exception {
		if(Tickets.contains(t)) {
			if(approve) {
				t.setStatus(true);
				PendingTickets.remove(t);
				Tickets.add(t);
			}
			else if(!approve) {
				t.setStatus(false);
				PendingTickets.remove(t);
				Tickets.add(t);
			}
		}
		else
			throw new Exception("Ticket does not exist on this account");
	}
	public static void main(String[]args) {
		Account acc = new Account("name", "pass", "e");
		for(int i=0;i<10;i++) {
			double val=Math.random()*100;
			String desc = "asdf";
			String type = "asdf";
			String path = "adsf";
			acc.addTickets(val,desc,type,path);			
			
			//submit ticket
			
			//view tickets
			
			//approve ticket
		}
		
	}

}
