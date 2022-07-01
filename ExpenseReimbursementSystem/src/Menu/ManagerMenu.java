package Menu;
import Account.*;
import java.util.ArrayList;

public class ManagerMenu extends Menu{

	protected Account acc;
	protected ArrayList<Account>accounts;
	protected ArrayList<Ticket>tickets;
	
	
	public ManagerMenu(Account account, ArrayList<Account>accounts) {
		acc=account;
		this.accounts=accounts;
		tickets=getPendingTickets();
	}
	
	/*
	 * traverse using input() to select options
	 */
	public void traverse() {
		//String[]options = {"Logout", "Tickets"};
		String option = "";
		
		while(!option.equalsIgnoreCase("Logout")) {
			System.out.println("Manager Menu\nOptions: Logout, Tickets, Roles");
			option=input();
			//Logout
			//approveTicket
			if(option.equalsIgnoreCase("Tickets")) 
				processTicket();
			else if(option.equalsIgnoreCase("Roles"))
				editRoles();
		}
	}

	/*
	 * reads input from console
	 */
	public String input() {
		return consoleInput();	
	}

	/*
	 * assign Approved/Denied status
	 * to tickets arraylist
	 */
	public void processTicket() {	
		
		//no pending tickets
		if(!checkTickets())
			return;
		
		//processed tickets
		ArrayList<Ticket>processed=new ArrayList<Ticket>();
		
		//pending tickets
		for(Ticket t:tickets) {
			String str="";
	
			boolean b=true;
			while(b) {
				System.out.println("Pending Ticket:");
				t.print();			
				System.out.println("Approved/Denied/Exit");
				str = input();
				b=false;
				
				//approve ticket
				if(str.equalsIgnoreCase("Approved")) {
					approveTicket(t, true);
					processed.add(t);
				}
				//deny ticket
				else if(str.equalsIgnoreCase("Denied")){
					approveTicket(t, false);
					processed.add(t);
				}
				//return to menu
				else if(str.equalsIgnoreCase("Exit")) {
					update(processed);
					break;							
				}
				//invalid input
				else {
					b=true;
					System.out.println("Invalid Input");
				}
			}
		}
		update(processed);
	}

	/*
	 * change user's roles
	 */
	public void editRoles() {
	
		Account a=null;
		
		//get user
		while(true){
			System.out.println("User: ");
			String str = input();
			a=searchUser(str);
			if(a==null)
				System.out.println("User does not exist");
			else
				break;
		}
		
		while(true) {
			System.out.println("Role: "+a.getRole()+"\nEmployee/Manager)");
			String str = input();
			if(str.equalsIgnoreCase("Employee")) {
				a.setRole(str);
				return;
			}
			else if(str.equalsIgnoreCase("Manager")) {
				a.setRole(str);
				return;
			}
			else
				System.out.println("Invalid input");
		}
	}

	protected void approveTicket(Ticket ticket, boolean approve) {
		ticket.setStatus(approve);
	}
	
	protected ArrayList<Ticket> getPendingTickets() {
		ArrayList<Ticket>tickets=new ArrayList<Ticket>();
		for(Account a:accounts)
			if(a.getRole().equalsIgnoreCase("Employee"))
				for(Ticket t:a.getTickets())
					if(t.getStatus().equalsIgnoreCase("Pending"))
						tickets.add(t);
		return tickets;
	}

	/*
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	protected Account searchUser(String username) {
		for(Account a:accounts)
			if(a.getName().equals(username))
				return a;
		return null;
	}
	
	/*
	 * checks if there are any pending tickets
	 * returns true if there are pending tickets
	 * returns false if there are no pending tickets
	 */
	protected boolean checkTickets() {
		//no pending tickets
		if(tickets.size()==0) {
			//update tickets
			tickets=getPendingTickets();
			
			//still no pending tickets
			if(tickets.size()==0) {
				System.out.println("No Pending Tickets");
				return false;
			}
		}
		//pending tickets
		System.out.println("Pending Tickets Found");
		return true;
	}
	/*
	 *removes processed tickets from the tickets arraylist
	 *which should leave behind only tickets that still have
	 *pending status
	 */
	protected void update(ArrayList<Ticket>processedTickets) {
		for(Ticket t:processedTickets) 
			tickets.remove(t);
	}
}
