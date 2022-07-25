package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.account.Account;
import com.account.AccountRemote;
import com.account.Ticket;
import com.account.TicketRemote;
import com.jdbc.TicketList;

/**
 * contains features available to managers. Includes employee features.
 * @author tomh0
 *
 */
public class ManagerController extends EmployeeController {

	/**
	 * must have an httpsession from the login controller
	 * @param request passed from dispatcher servlet
	 * @param response passed from dispatcher servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public ManagerController(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		super(request, response);
	}

	@Override
	protected boolean permission() {
		if(!super.permission())
			return false;
		
		if(!account.getRole().equalsIgnoreCase("Manager")) {
			writer.write("<h2>Status 403</h2><br>You do not have permission to view this page");
			response.setStatus(403);//forbidden
			return false;
		}
		return true;
	}
	
	/**
	 * Used by the Request Helper.
	 * views all tickets with the pending status "p"
	 */
	public void viewPendingTickets() {		
		if(!permission()) {return;}
		
		List<Ticket>tList=new TicketList("p");
		int size=tList.size();
		String[][]table=new String[size+1][5];
		table[0][0]="Amount";
		table[0][1]="Description";
		table[0][2]="Status";
		table[0][3]="Type";
		table[0][4]="Picture";
		for(int i=0;i<size;i++) {
			Ticket t = tList.get(i);			
			table[i+1][0]="$"+t.getAmount();
			table[i+1][1]=t.getDescription();
			table[i+1][2]=t.getStatus();
			table[i+1][3]=t.getType();
			table[i+1][4]=displayPictureRaw(t.getPicture(), 50, 50);
		}		
		displayTable(table,true);
	}
	
	/**
	 * Used by the request helper.
	 * Sets the status of a ticket to
	 * approved "a" or denied "d"
	 */
	public void processPendingTickets() {		
		if(!permission()) {return;}
		
		int ticketID=Integer.parseInt(request.getParameter("ticketID"));
		Ticket ticket=new TicketRemote(ticketID);
		String status=request.getParameter("status");
		
		if(ticket.getAccountID()==account.getID()) {
			writer.write("You cannot approve a ticket you submitted yourself");
		}
		else if(!ticket.getStatus().equalsIgnoreCase("p")) {
			writer.write("This ticket has already been processed");
		}
		//approve ticket
		else if(status.equalsIgnoreCase("Approved")) {
			ticket.setStatus(true);
			writer.write("Ticket "+ticket.getID()+" Approved");
		}
		//deny ticket
		else if(status.equalsIgnoreCase("Denied")) {
			ticket.setStatus(false);			
			writer.write("Ticket "+ticket.getID()+" Denied");
		}
		//inproper input
		else
			writer.write(status+" is not a valid status");
	}
	
	/**
	 * used by requesthelper.
	 * displays all accounts
	 */
	public void viewAccounts() {		
		if(!permission()) {return;}
				
		int size=accounts.size();
		String[][]aList=new String[size+1][3];
		aList[0][0]="ID";
		aList[0][1]="Name";
		aList[0][2]="Role";
		for(int i=0;i<size;i++) {
			Account account=accounts.get(i);
			aList[i+1][0]=""+account.getID();
			aList[i+1][1]=account.getName();
			aList[i+1][2]=account.getRole();
		}
		displayTable(aList, true);
	}

	/**
	 * used by requesthelper.
	 * change user role
	 */
	public void editAccounts() {
		if(!permission()) {return;}

		int accountID=Integer.parseInt(request.getParameter("accountID"));
		Account account=new AccountRemote(accountID);
		String role=request.getParameter("role");
		try {			
			if(role.equalsIgnoreCase("Manager")){
				account.setRole("Manager");
				writer.write("Account "+account.getName()+" has been set to Manager");
			}
			else{
				account.setRole("Employee");
				writer.write("Account "+account.getName()+" has been set to Employee");
			}
		}catch(NullPointerException e) {
			writer.write("There is no account with the id "+accountID);
		}
	}
}
