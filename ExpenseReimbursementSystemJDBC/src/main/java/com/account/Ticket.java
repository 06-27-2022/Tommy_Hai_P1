package com.account;

import com.jdbc.ConnectionUtil;

public class Ticket {

	private final int TICKET_ID;

	//for 
	private int accountID;
	private double amount;
	private String description;
	private String type;
	private int pictureID;
	/**
	 * The ticket using this id should already be on the ticket table
	 * changes to this ticket will affect the ticket table
	 * @param ticketID id of the ticket in the ticket table
	 */
	public Ticket(int ticketID){
		TICKET_ID=ticketID;
	}
	/**
	 * Creates a locally stored ticket. None of the information stored here will effect the ticket table
	 * @param accountID id of the account that submitted this ticket
	 * @param amount decimal amount listed on the ticket
	 * @param description description of the ticket
	 */
	public Ticket(int accountID, double amount, String description) {
		this.TICKET_ID = -1;
		this.accountID=accountID;
		this.amount=amount;
		this.description=description;
	}

		//insert into ticket (account,amount,description,status)values(1,22.34,'asdfasdf','p')returning id;
//		final String SQL = "insert into ticket (account,amount,description,status)values(?,?,?,'p')returning id";
//		Object[]args= {accountID,amount,description};
//		this.TICKET_ID =(int)ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
//	}
	@Override
	public String toString() {
		return "Amount:"+getAmount()+"|Desc:"+getDescription()+"|Status:"+getStatus()+"|Type:"+getType();
	}
	/**
	 * prints in console
	 */
	public void print() {
		System.out.println(toString());
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
			return accountID;
		}
		final String SQL="select account from ticket where id="+TICKET_ID;
		return (int) ConnectionUtil.stmtExecuteQuery(SQL);		
	}
	/**
	 * returns amount
	 */
	public double getAmount() {
		if(local()) {
			return amount;
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
			return description;
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
	 *will not change approval if it has already been modified
	 *@param approve true will approve the ticket, false will deny the ticket
	 */
	public boolean setStatus(boolean approve) {
		if(local()) {
			return false;
		}
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
			return "Local Ticket";
		}
		//select amount from ticket where id=1;
		final String SQL="select type from ticket where id="+TICKET_ID;
		return (String) ConnectionUtil.stmtExecuteQuery(SQL);
	}
	
	/**
	 * Receipts
	 * upload and store images in 
	 * SQL or cloud storage
	 */
	public int getImage() {
		if(local()) {
			return -1;
		}
		//select amount from ticket where id=1;
		final String SQL="select picture from ticket where id="+TICKET_ID;
		try{
			return (int) ConnectionUtil.stmtExecuteQuery(SQL);
		}catch(NullPointerException e) {
			return 0;//image id is null
		}		
	}
	
	public static void main(String[]args) {
		Ticket t = new Ticket(1);
		System.out.println("Account:"+t.getAccountID());
		System.out.println("Amount:	"+t.getAmount());
		System.out.println("Desc:	"+t.getDescription());
		System.out.println("Status:	"+t.getStatus());
		t.setStatus(true);
		System.out.println("Status:	"+t.getStatus());
		System.out.println("Type:	"+t.getType());
		System.out.println("Image:	"+t.getImage());	
		
		//Ticket t1= new Ticket(2,123.34,"jdsdadsfa");
		//System.out.println("t1:"+t1.getID());
	}
}
