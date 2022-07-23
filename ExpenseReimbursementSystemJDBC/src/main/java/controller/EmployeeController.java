package controller;

import java.io.IOException;
import java.io.PrintWriter;
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
import com.account.TicketLocal;
import com.account.TicketRemote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.AccountList;
import com.jdbc.TicketList;
import com.menu.Menu;
import com.menu.ProfileMenu;
import com.util.OutputUtil;

public class EmployeeController {

	protected Account account;
	protected List<Account>accounts;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected PrintWriter writer;
	
	public EmployeeController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//get account id from HttpSession
		//code 500 if not account signed in
		HttpSession session = request.getSession(false);
		int accountID = (int) session.getAttribute("accountID");
		account=new AccountRemote(accountID);
		
		accounts=new AccountList();
		this.request=request;
		this.response=response;
		writer = response.getWriter();
	}
	public void write(String str) {
		writer.write("<p>"+str+"</p>");
	}
	public void writeln(String str) {
		writer.write("<p>"+str+"</p><br>");
	}

	/**
	 * submits a new ticket to the ticket table
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitTicket() throws ServletException, IOException {
		writeln("Submitting new Ticket");
		String requestBodyText = new String(request.getInputStream().readAllBytes());
		Ticket ticket = new ObjectMapper().readValue(requestBodyText,TicketLocal.class);

		//transfer the contents of the local ticket to a remote ticket
		int accountID=account.getID();

		//amount on ticket, only 2 decimal places should be allowed
		double amount=(double)((int)(ticket.getAmount()*100))/100;
		
		//description of ticket
		String desc=ticket.getDescription();
		if(desc==null)
			desc="No Description";
		
		//get type Travel, Lodging, Food, Other 
		String type=ticket.getType();
		if(type.equalsIgnoreCase("Travel"))
			type="Travel";
		else if(type.equalsIgnoreCase("Lodging"))
			type="Lodging";
		else if(type.equalsIgnoreCase("Food"))
			type="Food";
		else
			type="Other";

		//picture
//		Picture pic;
//		try{
//			System.out.println("filepath="+ticket.getPicture().getPictureFile().getAbsolutePath());
//			pic = new PictureRemote("Ticket"+account.getID(),ticket.getPicture().getPictureFile().getAbsolutePath());
//		}catch(NullPointerException e) {
//			pic = new PictureRemote("Ticket"+account.getID(),"asdf");
//		}
//		Picture pic = new PictureRemote("Ticket"+account.getID(),"no");

		
//		//add ticketR to database
		Ticket ticketR = new TicketRemote(accountID,amount,desc,type,null); 
		List<Ticket>tList = new TicketList();
		tList.add(ticketR);
	}

	public void viewTickets() {
		writer.write("<h2>View Tickets</h2><br>");
		//request parameters
		String order=request.getParameter("order");
		writeln(order);
		List<Ticket>tlist=null;
		if(order.equalsIgnoreCase("all"))
			tlist=new TicketList();
		else if(order.equalsIgnoreCase("pending"))
			tlist=new TicketList(account.getID(),"p");
		else if(order.equalsIgnoreCase("approved")) 
			tlist=new TicketList(account.getID(),"a");
		else if(order.equalsIgnoreCase("denied")) 
			tlist=new TicketList(account.getID(),"d");

		//print tickets
		int size = tlist.size();
		for(int i=0;i<size;i++) {
			Ticket t = tlist.get(i);
			//t.getPicture().displayPicture(200, 200);
			writeln(t.toString());
			String border="";
			for(int j=0;j<20;j++)
				border+="=";
			writeln(border);
		}			
	}
	public void viewProfile() {

	}

	
}