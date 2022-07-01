package Menu;
import Account.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Account.Account;

public class ManagerMenu implements Menu{

	protected Account acc;
	protected ArrayList<Account>accounts;
	
	
	public ManagerMenu(Account account, ArrayList<Account>accounts) {
		acc=account;
		this.accounts=accounts;
	}
	
	public void approveTicket(Ticket ticket, boolean approve) {
		ticket.setStatus(approve);
	}
	
	public ArrayList<Ticket> getPendingTickets() {
		ArrayList<Ticket>tickets=new ArrayList<Ticket>();
		for(Account a:accounts)
			if(a.getRole().equalsIgnoreCase("Employee"))
				for(Ticket t:a.getTickets())
					if(t.getStatus().equalsIgnoreCase("Pending"))
						tickets.add(t);
		return tickets;
	}
	
	public void traverse() {
		String option = "";
		
		while(!option.equalsIgnoreCase("Logout")) {
			System.out.println("Manager Menu\nOptions: Logout, Ticket");
			option=input();
			//Logout
			//approveTicket
			if(option.equalsIgnoreCase("Ticket")) 
				ticket();
		}
	}
	private void ticket() {
		ArrayList<Ticket>tickets=getPendingTickets();
		
		//no pending tickets
		if(tickets.size()==0) {
			System.out.println("No Pending Tickets");
			return;
		}
		
		//pending tickets
		for(Ticket t:tickets) {
			String str="";
			System.out.println("Pending Ticket:");
			t.print();
			System.out.println("Approved/Denied");
			str = input();
			if(str.equalsIgnoreCase("Approved"))
				approveTicket(t, true);
			else if(str.equalsIgnoreCase("Denied"))
				approveTicket(t, false);
			else if(str.equalsIgnoreCase("Exit")) {
				break;				
			}
		}
	}

	public String input() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = in.readLine();
		} catch (IOException e) {
			str=null;
		}
		return str;		
	}
}
