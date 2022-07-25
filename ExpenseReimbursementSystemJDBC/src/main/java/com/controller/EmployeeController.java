package com.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.account.Account;
import com.account.AccountRemote;
import com.account.Picture;
import com.account.PictureRemote;
import com.account.Ticket;
import com.account.TicketRemote;
import com.jdbc.AccountList;
import com.jdbc.TicketList;

/**
 * contains all features available to employees
 * @author tomh0
 *
 */
public class EmployeeController extends Controller {

	protected Account account;
	protected List<Account>accounts;

	/**
	 * must have an httpsession from the login controller
	 * @param request passed from dispatcher servlet
	 * @param response passed from dispatcher servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public EmployeeController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super(request,response);
		//get account id from HttpSession
		//code 500 if not account signed in
		HttpSession session = request.getSession(false);
		try{
			int accountID = (int) session.getAttribute("accountID");
			account=new AccountRemote(accountID);		
			accounts=new AccountList();
		}catch(NullPointerException e) {
			account=null;
			accounts=null;
		}
	}
	/**
	 * determines if you have permission to view this page
	 * @return true means you are allowed to continue
	 */
	protected boolean permission() {
		if(account==null&&accounts==null){
			response.setStatus(401);
			writer.write("Not Logged In");
			return false;
		}
		return true;
	}
	
	/**
	 * submits a new ticket to the ticket table
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitTicket() throws ServletException, IOException {
		if(!permission())return;
		
		writer.write("<p>Submitting new Ticket</p><br>");

		//transfer the contents of the local ticket to a remote ticket
		int accountID=account.getID();

		//amount on ticket, only 2 decimal places should be allowed
		double amount=(double)((int)(Double.parseDouble(request.getParameter("amount"))*100))/100;
		
		//description of ticket
		String desc=request.getParameter("description");
		if(desc==null)
			desc="No Description";
		
		//get type Travel, Lodging, Food, Other 
		String type=request.getParameter("type");
		if(type.equalsIgnoreCase("Travel"))
			type="Travel";
		else if(type.equalsIgnoreCase("Lodging"))
			type="Lodging";
		else if(type.equalsIgnoreCase("Food"))
			type="Food";
		else
			type="Other";

		Picture pic = new PictureRemote("temp",request.getParameter("filepath"));
		
//		//add ticketR to database
		Ticket ticketR = new TicketRemote(accountID,amount,desc,type,pic); 
		List<Ticket>tList = new TicketList();
		tList.add(ticketR);
	}

	/**
	 * view tickets of the signed in employee inside of a table
	 * defaults to all if request parameter order does not have a proper input
	 * @throws IOException
	 */
	public void viewTickets() throws IOException {
		if(!permission())return;

		writer.write("<h2>View Tickets</h2><br>");
		//request parameters
		String order=request.getParameter("order");
		writer.write("<h3>Order:"+order+"</h3><br>");
		List<Ticket>tlist=null;
		if(order.equalsIgnoreCase("all"))
			tlist=new TicketList(account.getID());
		else if(order.equalsIgnoreCase("pending"))
			tlist=new TicketList(account.getID(),"p");
		else if(order.equalsIgnoreCase("approved")) 
			tlist=new TicketList(account.getID(),"a");
		else if(order.equalsIgnoreCase("denied")) 
			tlist=new TicketList(account.getID(),"d");
		else
			tlist=new TicketList(account.getID());
		
		//check if there are tickets to write
		int size = tlist.size();
		if(size==0) {
			writer.write("No Tickets");
			return;			
		}
		
		//write tickets onto table
		String[][]table=new String[size+1][5];
		table[0][0]="Amount";
		table[0][1]="Description";
		table[0][2]="Status";
		table[0][3]="Type";
		table[0][4]="Picture";
		for(int i=0;i<size;i++) {
			Ticket t = tlist.get(i);			
			table[i+1][0]="$"+t.getAmount();
			table[i+1][1]=t.getDescription();
			table[i+1][2]=t.getStatus();
			table[i+1][3]=t.getType();
			table[i+1][4]=displayPictureRaw(t.getPicture(), 50, 50);
		}		
		displayTable(table,true);
	}
	
}