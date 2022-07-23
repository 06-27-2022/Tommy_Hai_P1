package controller;

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
import com.account.TicketLocal;
import com.account.TicketRemote;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jdbc.AccountList;
import com.jdbc.TicketList;

public class EmployeeController extends Controller {

	protected Account account;
	protected List<Account>accounts;
//	protected HttpServletRequest request;
//	protected HttpServletResponse response;
//	protected PrintWriter writer;
	
	public EmployeeController(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super(request,response);
		//get account id from HttpSession
		//code 500 if not account signed in
		HttpSession session = request.getSession(false);
		int accountID = (int) session.getAttribute("accountID");
		account=new AccountRemote(accountID);		
		accounts=new AccountList();
//		this.request=request;
//		this.response=response;
//		writer = response.getWriter();
	}
	/**
	 * submits a new ticket to the ticket table
	 * @throws ServletException
	 * @throws IOException
	 */
	public void submitTicket() throws ServletException, IOException {
		writer.write("<p>Submitting new Ticket</p><br>");
		String requestBodyText = new String(request.getInputStream().readAllBytes());
		//Ticket ticket = new ObjectMapper().readValue(requestBodyText,TicketLocal.class);

		//transfer the contents of the local ticket to a remote ticket
		int accountID=account.getID();

		//amount on ticket, only 2 decimal places should be allowed
		//double amount=(double)((int)(ticket.getAmount()*100))/100;
		double amount=(double)((int)(Double.parseDouble(request.getParameter("amount"))*100))/100;
		
		//description of ticket
		//String desc=ticket.getDescription();
		String desc=request.getParameter("description");
		if(desc==null)
			desc="No Description";
		
		//get type Travel, Lodging, Food, Other 
		//String type=ticket.getType();
		String type=request.getParameter("type");
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
//			pic = new PictureRemote("Ticket"+account.getID(),ticket.getPicture().getPictureFile().getAbsolutePath());
//		}catch(NullPointerException e) {
//			pic = new PictureRemote("Ticket"+account.getID(),"asdf");
//		}
		
		Picture pic = new PictureRemote("temp",request.getParameter("filepath"));

		
//		//add ticketR to database
		Ticket ticketR = new TicketRemote(accountID,amount,desc,type,pic); 
		List<Ticket>tList = new TicketList();
		tList.add(ticketR);
	}

	public void viewTickets() throws IOException {
		writer.write("<h2>View Tickets</h2><br>");
		//request parameters
		String order=request.getParameter("order");
		writer.write("<h3>Order:"+order+"</h3><br>");
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
			
//			System.out.println("Working Directory = " + System.getProperty("user.dir"));			
			//t.getPicture().displayPicture(200, 200);
//			String filepath=null;
			//my current implementation of images is poorly done
			//next time look into the aws stuff sent to you
//			filepath ="http://localhost:8080/check/"+t.getPicture().getPictureFile().getPath();
//			filepath="C:\\Users\\tomh0\\scoop\\apps\\sts\\current\\"+t.getPicture().getPictureFile().getName();
//			writer.write("<img src=\""+filepath+"\" alt=\""+filepath+"\" width=\"50\" height=\"50\">");
			
			displayPicture(t.getPicture(),50,50);
			writer.write(t.toString()+"<br>");
			String border="";
			for(int j=0;j<50;j++)
				border+="=";
			writer.write(border+"<br>");
		}		
	}
	
}