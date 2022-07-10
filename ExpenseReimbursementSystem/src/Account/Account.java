package Account;

import java.util.ArrayList;

public class Account{
	private String Name;
	private String Password;
	private String Role;
	private ArrayList<Ticket>Tickets;
	private ArrayList<Ticket>PendingTickets;
	private Profile AccountProfile;
	
	public Account(String name, String password, String role) {
		setName(name);
		setPassword(password);
		setRole(role);
		Tickets=new ArrayList<Ticket>();
		PendingTickets=new ArrayList<Ticket>();
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
	
	/**
	 * All tickets submitted by this account
	 * @return an arraylist containing processed and pending tickets
	 */
	public ArrayList<Ticket>getAllTickets(){
		ArrayList<Ticket>allTickets=new ArrayList<Ticket>();
		for(Ticket t:Tickets)
			allTickets.add(t);
		for(Ticket t:PendingTickets)
			allTickets.add(t);
		return allTickets;
	}

	/**
	 * Only contains tickets that are pending
	 * @return an arraylist of tickets with pending status
	 */
	public ArrayList<Ticket>getPendingTickets(){
		return PendingTickets;
	}

	/**
	 * will create a new ticket and add it to the account's list of pending tickets
	 * @param amount displayed on ticket
	 * @param description displayed on ticket
	 * @param type The types are Travel, Lodging, Food, Other 
	 * @param filepath the filepath of image displayed on ticket
	 */
	public void addTicket(double amount, String description,String type, String filepath) {
		Ticket t=new Ticket(amount, description, Name, type, filepath);
		PendingTickets.add(t);
	}
	
	/**
	 * will create a copy of the ticket to add to the list of pending tickets
	 */
	public void addTicket(Ticket ticket) {
		double amount=ticket.getAmount();
		String description=ticket.getDescription();
		String type=ticket.getType();
		String filepath=ticket.getImage();
		Ticket t=new Ticket(amount, description, Name, type, filepath);
		PendingTickets.add(t);
	}

	/**
	 * approve or deny the provided ticket
	 * @param t	the ticket being approved or denied
	 * @param approve true=approve, false=deny
	 * @throws Exception the ticket does not exist on this account
	 */
	public void approveTicket(Ticket t,boolean approve) throws Exception {
		if(PendingTickets.contains(t)) {
			if(approve)
				t.setStatus(true);
			else if(!approve)
				t.setStatus(false);
			PendingTickets.remove(t);
			Tickets.add(t);
		}
		else
			throw new Exception("Ticket does not exist on this account");
	}
	public static void main(String[]args) {
		Account acc = new Account("name", "pass", "e");
		//submit tickets
		for(int i=0;i<10;i++) {
			double val=Math.random()*100;
			String desc = "d"+Math.random();
			String type = "t"+Math.random();
			String path = "p"+Math.random();
			acc.addTicket(val,desc,type,path);			
		}		
		System.out.println("======================================");
		//view tickets
		for(Ticket t:acc.getAllTickets())
			t.print();
		//approve ticket
		for(Ticket t:acc.getPendingTickets()) {
			if(0.5<Math.random())
				t.setStatus(true);			
			else
				t.setStatus(false);
		}
		System.out.println("======================================");		
		//view tickets
		for(Ticket t:acc.getAllTickets())
			t.print();
		
	}

}
