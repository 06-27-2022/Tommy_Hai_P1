package Menu;
import Account.*;
import java.util.ArrayList;

public class ManagerMenu extends EmployeeMenu{

	//protected ArrayList<Account>accounts;
	protected ArrayList<Ticket>tickets;
	
	
	public ManagerMenu(Account account, ArrayList<Account>accounts) {
		super(account, accounts);
		tickets=getPendingTickets();
		String[]temp=menuOptions;
		int length = menuOptions.length;
		menuOptions=new String[length+2];
		for(int i=0;i<length;i++) 
			menuOptions[i]=temp[i];
		
		menuOptions[length]="Process Ticket";
		menuOptions[length+1]="Edit Roles";
	}
	
	/*
	 * traverse using input() to select options
	 */
	public void traverse() {
		consoleTraverse();
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
		if(!checkPendingTickets())
			return;
		
		//processed tickets
		ArrayList<Ticket>processed=new ArrayList<Ticket>();
		
		//process pending tickets
		for(Ticket t:tickets) {
	
			//check to make sure current user did not submit ticket t
			if(t.getUser().equals(Account.getName())) {
				System.out.println("Skipping ticket "+t.getDescription());
				continue;
			}
			
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
					t.setStatus(true);
					processed.add(t);
				}
				//deny ticket
				else if(str.equalsIgnoreCase("Denied")){
					t.setStatus(false);
					processed.add(t);
				}
				//return to menu
				else if(str.equalsIgnoreCase("Exit")) {
					update(processed);
					return;							
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
		
		//change role
		while(true) {
			System.out.println("Role: "+a.getRole()+"\nEmployee/Manager");
			String str = input();
			if(str.equalsIgnoreCase("Employee")) {
				a.setRole("Employee");
				return;
			}
			else if(str.equalsIgnoreCase("Manager")) {
				a.setRole("Manager");
				return;
			}
			else
				System.out.println("Invalid input");
		}
	}
	
	/*
	 * gets all pending tickets from the accounts arraylist
	 */
	protected ArrayList<Ticket> getPendingTickets() {
		ArrayList<Ticket>tickets=new ArrayList<Ticket>();
		for(Account a:Accounts) {
			ArrayList<Ticket> pending = a.getPendingTickets();
			for(Ticket t:pending) 
				tickets.add(t);
		}
		return tickets;
	}

	/*
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	protected Account searchUser(String username) {
		for(Account a:Accounts)
			if(a.getName().equals(username))
				return a;
		return null;
	}
	
	/*
	 * checks if there are any pending tickets
	 * returns true if there are pending tickets
	 * returns false if there are no pending tickets
	 */
	protected boolean checkPendingTickets() {
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
	
	/*
	 * traverse menu via console
	 */
	protected void consoleTraverse() {
		boolean exit=false;
		while(!exit) {
			int option = consoleSelect();
			exit = optionSelect(option);
		}
	}
	
	protected boolean optionSelect(int option) {
		boolean b = super.optionSelect(option);
		if(b)
			return true;

		option=option-menuOptions.length+2;
		System.out.println(option);
		switch(option) {
		case 0:
			processTicket();
			break;
		case 1:
			editRoles();
			break;
		}
		return false;
	}
	
}
