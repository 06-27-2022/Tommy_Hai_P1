package com.menu;
import java.util.List;

import com.account.Account;
import com.account.Ticket;
import com.jdbc.TicketList;

public class EmployeeMenu extends Menu{
	
	protected Account Account;
	private List<Ticket>Tickets;
	List<Account>Accounts;
	
	public EmployeeMenu(Account account,List<Account>accounts){
		this.Account = account;
		this.Accounts=accounts;
		Tickets = new TicketList(account.getID());
		
		menuOptions=new String[4];
		menuOptions[0]="Logout";
		menuOptions[1]="Submit Ticket";
		menuOptions[2]="View Ticket";
		menuOptions[3]="Profile";
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
		String type = null;
		
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
		//String image=input();
		
		System.out.println("Ticket: "+amount+"|"+desc);
		
		//submit ticket
		Ticket t = new Ticket(Account.getID(),amount,desc);
		Tickets.add(t);
		//tickets.add(t);
	}

	public void viewTickets() {
		System.out.println("---Tickets---");
		for(int i=0;i<Tickets.size();i++) {
			Tickets.get(i).print();
			System.out.println("=======");
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
			exit = optionSelect(option);
//			switch(option) {
//				case 0:
//					exit=true;
//				break;
//				case 1:
//					submitTicket();
//				break;
//				case 2:
//					viewTickets();
//				break;
//				case 3:
//					viewProfile();
//				default:
//					System.out.println("Error");
//			}
		}
	}
	protected boolean optionSelect(int option) {
		switch(option) {
		case 0:
			return true;
		case 1:
			submitTicket();
		break;
		case 2:
			viewTickets();
		break;
		case 3:
			viewProfile();		
		}
		return false;
	}
}
