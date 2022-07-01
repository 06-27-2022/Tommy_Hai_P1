package Account;

import java.util.ArrayList;

public class Account{
	protected String name;
	protected String password;
	protected String role;
	protected ArrayList<Ticket>tickets;
	
	public Account(String name, String password) {
		setName(name);
		setPassword(password);
		setRole("Employee");
		tickets=new ArrayList<Ticket>();
	}
	public Account(String name, String password, String role) {
		setName(name);
		setPassword(password);
		setRole(role);
		tickets=new ArrayList<Ticket>();
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
		String[]roles= {"Employee", "Manager"};
		for(String r:roles)
			if(r.equalsIgnoreCase(role)) {
				this.role=role;
				return;
			}
		role=roles[0];
	}	
	public String getRole() {
		return role;
	}
	
	//get tickets
	public ArrayList<Ticket>getTickets(){
		return tickets;
	}
	public ArrayList<Ticket>getPendingTickets(){
		for(Ticket t:)
		return tickets;
	}
	//add ticket t to tickets arraylist
	public void addTickets(Ticket t) {
		tickets.add(t);
	}

}
