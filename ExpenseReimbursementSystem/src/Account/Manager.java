package Account;

import java.util.ArrayList;

public class Manager extends Employee{

	public Manager(String username, String password){
		super(username,password);
		role="Manager";
	}
	
	//get tickets
	public ArrayList<Ticket>getTickets(){
		System.out.println("Error: Managers don't have tickets");
		return null;
	}
	
	//add ticket t to tickets arraylist
	public void addTickets(Ticket t) {
		System.out.println("Error: Managers don't have tickets");
		return;
	}
}
