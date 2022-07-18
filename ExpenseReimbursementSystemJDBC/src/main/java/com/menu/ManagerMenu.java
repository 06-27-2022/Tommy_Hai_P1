package com.menu;
import java.util.List;

import com.account.Account;
import com.account.Ticket;
import com.account.TicketRemote;

public class ManagerMenu extends EmployeeMenu{
	
	public ManagerMenu(Account account, List<Account>accounts) {
		super(account, accounts);
		//tickets=getPendingTickets();
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

	/**
	 * assign Approved/Denied status
	 * to tickets arraylist
	 */
	public void processTicket() {		
		for(int i=0;i<Accounts.size();i++) {
			Account acc=Accounts.get(i);
			List<Ticket>pending=acc.getTickets("p");
			
//			for(Ticket t:acc.getPendingTickets())
//				pending.add(t);
			
			//skips tickets if the account belongs to the one signed in
			//skips if the account has no pending tickets
			if(Account.getID()==acc.getID()||pending.isEmpty())
				continue;
			//proccessing pending tickets
			//for(Ticket t:pending) {
			for(int j=0;j<pending.size();j++) {
				Ticket t = pending.get(j);
				boolean wait=true;	
				while(wait) {
					//prompt
					System.out.println("----------------------------------------");
					t.print();
					System.out.println("Approved/Denied/Exit");
					//input
					String str = input();
					
					//approve ticket
					if(str.equalsIgnoreCase("Approved")) {
						try {
							acc.approveTicket(t, true);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
							wait=false;
						}
					}
					//deny ticket
					else if(str.equalsIgnoreCase("Denied")) {
						try {
							acc.approveTicket(t, false);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
							wait=false;
						}
					}
					//return to menu
					else if(str.equalsIgnoreCase("Exit")) 
						return;
					//invalid input
					else 
						System.out.println("Invalid Input");
				}//finished reading inputs
			}//finished iterating through tickets from account
		}//finished iterating through all accounts
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
	 * Searches acc arraylist for an account using
	 * the username provided
	 * returns the account if a match is found
	 */
	protected Account searchUser(String username) {
		//for(Account a:Accounts)
		for(int i=0;i<Accounts.size();i++) {
			Account a=Accounts.get(i);
			if(a.getName().equals(username))
				return a;
		}
		return null;	
	}
	
	/**
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
		//System.out.println(option);
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
