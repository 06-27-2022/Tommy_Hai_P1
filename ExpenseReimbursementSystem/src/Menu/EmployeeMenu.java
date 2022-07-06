package Menu;
import java.util.*;
import Account.*;

public class EmployeeMenu extends Menu{
	
	protected Account Account;
	protected ArrayList<Ticket>tickets;
	ArrayList<Account>Accounts;
	public EmployeeMenu(Account account,ArrayList<Account>accounts){
		this.Account = account;
		this.Accounts=accounts;
		tickets = Account.getTickets();
		menuOptions=new String[4];
		menuOptions[0]="Logout";
		menuOptions[1]="Submit Ticket";
		menuOptions[2]="View Ticket";
		menuOptions[3]="View Profile";
	}
	
	/*
	 * exit
	 * submit ticket
	 * view ticket
	 */
	public void traverse() {
		consoleTraverse();
	}

	public String input() {
		return consoleInput();
	}

	public void submitTicket() {
		
		//mandatory
		double amount = -1;			
		String desc;
		
		//bonus
		String user=Account.getName();
		String type = null;
		String image;
		
		//get amount
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
		desc = input();
		
		//get type Travel, Lodging, Food, Other 
		String[]types = {"Travel", "Lodging", "Food", "Other"};
		boolean repeat=true;
		while(repeat) {
			//prompt
			System.out.print("Type:\n/");
			for(String t:types)
				System.out.print(t+"/");
			System.out.println();
			
			//input
			type=input();
			for(String t:types)	{			
				if(t.equalsIgnoreCase(type)) {
					repeat=false;
					type=t;
				}
			}
		}
		
		//get image
		System.out.println("Image:");
		image=input();
		
		System.out.println("Ticket: "+amount+"|"+desc);
		
		//submit ticket
		Ticket t = new Ticket(amount,desc,user, type, image);
		tickets.add(t);
	}

	public void viewTickets() {
		System.out.println("Tickets:");
		for(Ticket t:tickets) {
			t.print();
			System.out.println();
		}
	}
	public void viewProfile() {
		Menu profile1 = new ProfileMenu(Account,Accounts);
		profile1.traverse();
	}
	
	@Override
	protected void consoleTraverse() {
		boolean exit=false;
		while(!exit) {
			int option = consoleSelect();
			switch(option) {
				case 0:
					exit=true;
				break;
				case 1:
					submitTicket();
				break;
				case 2:
					viewTickets();
				break;
				case 3:
					viewProfile();
				default:
					System.out.println("Error");
			}
		}
	}

}
