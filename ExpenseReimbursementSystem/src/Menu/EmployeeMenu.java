package Menu;
import java.util.*;
import Account.*;

public class EmployeeMenu extends Menu{
	
	protected Account acc;
	protected ArrayList<Ticket>tickets;
	
	public EmployeeMenu(Account account){
		this.acc = account;
		tickets = acc.getTickets();
		menuOptions=new String[3];
		menuOptions[0]="Exit";
		menuOptions[1]="Submit Ticket";
		menuOptions[2]="View Ticket";
	}
	
	/*
	 * 0)exit
	 * 1)submit ticket
	 */
	public void traverse() {
		String option = "";
		while(!option.equalsIgnoreCase("logout")) {
			System.out.println("Employee Menu\nOptions: Logout, Ticket, View");
			option = input();
			//logout
			//submit ticket
			if(option.equalsIgnoreCase("ticket"))
				ticket();
			else if(option.equalsIgnoreCase("view"))
				viewTickets();
		}
	}

	public String input() {
		return consoleInput();
	}

	public void ticket() {
		//get amount
		double amount = -1;			
		while(amount<0) {
			System.out.println("Amount:");
			String str = input();
			try{
				amount=(double)((int)(Double.parseDouble(str)*100))/100;
			}
			catch(Exception e){
				System.out.println("Error: "+e);
			}
		}
		//get description
		System.out.println("Description:");
		String desc = input();
		
		System.out.println("Ticket: "+amount+"|"+desc);
	
		//submit ticket
		submitTicket(amount,desc);		
	}

	public void viewTickets() {
		System.out.println("Tickets:");
		for(Ticket t:tickets) {
			t.print();
			System.out.println();
		}
	}

	/*
	 * adds a ticket to the tickets arraylist
	 */
	protected void submitTicket(double amount, String description){
		tickets.add(new Ticket(amount,description));
	}
	
	protected void submitTicket(Ticket ticket){
		tickets.add(ticket);
	}
}
