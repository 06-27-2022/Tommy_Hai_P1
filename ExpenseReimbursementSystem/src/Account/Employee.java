package Account;

import java.util.ArrayList;

public class Employee implements Account{
	protected String name;
	protected String password;
	protected String role;
	protected ArrayList<Ticket>tickets;
	
	public Employee(String name, String password) {
		this.name=name;
		this.password=password;
		role="Employee";
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
	public String getRole() {
		return role;
	}
	
	//get tickets
	public ArrayList<Ticket>getTickets(){
		return tickets;
	}
	
	//add ticket t to tickets arraylist
	public void addTickets(Ticket t) {
		tickets.add(t);
	}

}
