package com.account;

import com.jdbc.ConnectionUtil;

public class TicketRemote implements Ticket{

	private final int TICKET_ID;
	private int AccountID;
	private double Amount;
	private String Description;
	private String Type;
	private Picture Image;
	
	/**
	 * The ticket using this id should already be on the ticket table
	 * changes to this ticket will affect the ticket table
	 * @param ticketID id of the ticket in the ticket table
	 */
	public TicketRemote(int ticketID){
		TICKET_ID=ticketID;
	}
	/**
	 * Creates a locally stored ticket. None of the information stored here will effect the ticket table
	 * @param accountID id of the account that submitted this ticket
	 * @param amount decimal amount listed on the ticket
	 * @param description description of the ticket
	 */
	public TicketRemote(int accountID, double amount, String description) {
		this.TICKET_ID = -1;
		//this.AccountID=accountID;
		this.Amount=amount;
		this.Description=description;
	}
	
	public TicketRemote(int accountID, double amount, String description,String type, Picture image) {
		this.TICKET_ID = -1;
		AccountID=accountID;
		Amount=(double)((int)(amount*100))/100;
		Description=description;
		if(type.equals("Travel"))
			Type="Travel";
		else if(type.equals("Lodging"))
			Type="Lodging";
		else if(type.equals("Food"))
			Type="Food";
		else 
			Type="Other";
		if(image!=null)
			Image=new PictureRemote(image.getPictureFile().getName(),image.getPictureFile().getAbsolutePath());
		else
			image=new PictureRemote("temp", "");
	}
	
		//insert into ticket (account,amount,description,status)values(1,22.34,'asdfasdf','p')returning id;
//		final String SQL = "insert into ticket (account,amount,description,status)values(?,?,?,'p')returning id";
//		Object[]args= {accountID,amount,description};
//		this.TICKET_ID =(int)ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
//	}
	@Override
	public String toString() {
		return "Amount:"+getAmount()+"|Desc:"+getDescription()+"|Status:"+getStatus()+"|Type:"+getType()+"|Picture:"+getPictureID();
	}
	/**
	 * prints in console
	 */
	public void print() {
		System.out.println(toString());
		getPicture().displayPicture(200,200);
	}
	private boolean local() {
		if(TICKET_ID==-1)
			return true;
		return false;
	}
	public int getID() {
		return TICKET_ID;
	}
	public int getAccountID() {
		if(local()) {
			return AccountID;
		}
		final String SQL="select account from ticket where id="+TICKET_ID;
		return (int) ConnectionUtil.stmtExecuteQuery(SQL);		
	}
	/**
	 * returns amount
	 */
	public double getAmount() {
		if(local()) {
			return Amount;
		}
		//select amount from ticket where id=1;
		final String SQL="select amount from ticket where id="+TICKET_ID;
		return (double) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	/**
	 * returns description
	 */
	public String getDescription() {
		if(local()) {
			return Description;
		}
		//select amount from ticket where id=1;
		final String SQL="select description from ticket where id="+TICKET_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 * returns status
	 * 1  = Approved
	 * -1 = Denied
	 * 0  = Pending
	 */
	public String getStatus(){
		if(local()) {
			return "Local Ticket";
		}
		//select amount from ticket where id=1;
		final String SQL="select status from ticket where id="+TICKET_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 *will not change approval if it has already been modified or it is a local ticket
	 *@param approve true will approve the ticket, false will deny the ticket
	 */
	public boolean setStatus(boolean approve) {
		if(local()||!getStatus().equalsIgnoreCase("p"))
			return false;
		//update ticket set amount=2.25 where id=4;
		final String SQL="update ticket set status=? where id="+TICKET_ID;
		Object[]args=new Object[1];
		if(approve) {
			args[0]='a';
		}else
			args[0]='d';
		return ConnectionUtil.stmtExecute(SQL, args);
	}
	/**
	 *Travel, Lodging, Food, Other 
	 */
	public String getType() {
		if(local()) {
			return Type;
		}
		//select amount from ticket where id=1;
		final String SQL="select type from ticket where id="+TICKET_ID;
		String t=(String) ConnectionUtil.stmtExecuteQuery(SQL);
		if(t==null) {
			setType("Other");
			return "Other";
		}
		else if(t.equals("Travel"))
			return t;
		else if(t.equals("Lodging"))
			return t;
		else if(t.equals("Food"))
			return t;
		else if(t.equals("Other"))
			return t;
		else {
			setType("Other");
			return "Other";			
		}
	}
	private boolean setType(String t) {
		if(local())
			return false;		
		final String SQL="update ticket set type=? where id="+TICKET_ID;
		String[]args= {t};
		return ConnectionUtil.stmtExecute(SQL, args);
	}
	
	public Picture getPicture() {
		if(local()) {			
			if(Image==null)
				return createPicture();
			return Image;
		}
		return new PictureRemote(getPictureID());
	}
	/**
	 * Receipts
	 * upload and store images in 
	 * SQL or cloud storage
	 */
	private int getPictureID() {
		if(local()) {
			if(Image==null)
				return -1;
			return Image.getID();
		}
		//select amount from ticket where id=1;
		final String SQL="select picture from ticket where id="+TICKET_ID;
		try{
			return (int) ConnectionUtil.stmtExecuteQuery(SQL);
		}catch(NullPointerException e) {
			return -1;//image id is null
		}		
	}
	/**
	 * for use when there is no picture
	 * @return local PictureRemote
	 */
	private Picture createPicture() {
		Picture p=new PictureRemote("AccountID"+getAccountID(),null);
		return p;
	}

	
	public static void main(String[]args) {
		TicketRemote t = new TicketRemote(2);
//		System.out.println("Account:"+t.getAccountID());
//		System.out.println("Amount:	"+t.getAmount());
//		System.out.println("Desc:	"+t.getDescription());
//		System.out.println("Status:	"+t.getStatus());
//		t.setStatus(true);
//		System.out.println("Status:	"+t.getStatus());
//		System.out.println("Type:	"+t.getType());
//		System.out.println("Image:	"+t.getImage());	
//		t.getPicture().displayPicture(100, 100);
//		//Picture p = new PictureRemote(t.getID());
		t.getPicture().displayPicture(200,200);
		System.out.println(t);
		
		//Ticket t1= new Ticket(2,123.34,"jdsdadsfa");
		//System.out.println("t1:"+t1.getID());
	}
}
