package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.account.Account;
import com.account.AccountRemote;
import com.account.Ticket;
import com.account.TicketRemote;

public class ManagerController extends EmployeeController {

	public ManagerController(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		super(request, response);
	}
	private boolean forbidden() {
		if(!account.getRole().equalsIgnoreCase("Manager")) {
			response.setStatus(403);//forbidden
			writer.write("<h2>Status 403</h2><br>You do not have permission to view this page");
			return true;
		}
		return false;
	}
	
	public void viewPendingTickets() {		
		if(forbidden()) {return;}

//		int length = accounts.size();
//		int start;
//		int end;
//		//determine how many tickets get displayed
//		try {
//			int page=Integer.parseInt(request.getParameter("page"));
//			int perpage=Integer.parseInt(request.getParameter("perpage"));
//			//start index
//			if((page-1)*perpage>0)
//				start=(page-1)*perpage;
//			else
//				start=0;
//			if(start+perpage>length)
//				end=length;
//			else
//				end=start+perpage;			
//		}catch(NumberFormatException e) {
//			start=0;
//			end=length;
//		}
		
		for(int i=0;i<accounts.size();i++) {
			Account acc=accounts.get(i);
			List<Ticket>pending=acc.getTickets("p");
			for(int j=0;j<pending.size();j++) {
				Ticket t = pending.get(j);
				writer.write("TicketID:"+t.getID()
							+"|Status:"+t.getStatus()
							+"|Amount:"+t.getAmount()
							+"|Description:"+t.getDescription()
							+"|Type:"+t.getType()
							+"|PictureID:"+t.getPicture().getID()
							+"<br>");
			}
		}
	}
	
	public void processPendingTickets() {		
		if(forbidden()) {return;}
		
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
	}
	
	public void viewAccounts() {		
		if(forbidden()) {return;}

		for(int i=0;i<accounts.size();i++) {
			Account account=accounts.get(i);
			writer.write("ID:"+account.getID()
					+"|Name:"+account.getName()
					+"|Role:"+account.getRole()
					+"<br>");
		}
	}

	/*
	 * change user's roles
	 */
	public void editAccounts() {
		if(forbidden()) {return;}

		int accountID=Integer.parseInt(request.getParameter("accountID"));
		Account account=new AccountRemote(accountID);
		String role=request.getParameter("role");
		
		if(role.equalsIgnoreCase("Manager")){
			account.setRole("Manager");
			writer.write("Account "+account.getName()+" has been set to Manager");
		}
		else{
			account.setRole("Employee");
			writer.write("Account "+account.getName()+" has been set to Employee");
		}
	}

}
