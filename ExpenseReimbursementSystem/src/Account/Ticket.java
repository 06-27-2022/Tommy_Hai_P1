package Account;

public class Ticket {
	
	//minimum
	protected final double Amount;
	protected final String Desc;
	protected byte Status;

	//bonus
	private final int ID;
	private static int TicketCounter=0;
	protected final String User;
	protected final String Type;
	//picture parameters
	protected final Picture Pic;
	protected int Width,Height;

	/**
	 * Tickets should be assigned to an Account. Managers cannot approve their own tickets.
	 * @param amount the $amount displayed on the ticket. Will cut off decimals past the hundreds place
	 * @param description description displayed on ticket
	 * @param user username of account who submited the ticket
	 * @param type The types are Travel, Lodging, Food, Other 
	 * @param imageFilePath the file path of the image attached to the ticket
	 */
	public Ticket(double amount, String description, String user, String type, String imageFilePath){
		this.Amount=(double)((int)(amount*100))/100;
		Desc=description;
		Status = 0;
		User = user;
		Type=type;
		ID=TicketCounter;
		TicketCounter++;
		Width=200;
		Height=200;
		Pic = new Picture(Width, Height,user+"("+ID+")",imageFilePath);
	}
	
	/**
	 * prints in console
	 */
	public void print() {
		System.out.println("Amount: "+Amount);
		System.out.println("Description: "+Desc);
		System.out.println("Status: "+getStatus());
		System.out.println("Type: "+getType());
		System.out.println("Image: "+getImage());
		System.out.println("User: "+getUser());
		Pic.displayPicture();
	}
	
	/**
	 * returns amount
	 */
	public double getAmount() {
		return Amount;
	}
	
	/**
	 * returns description
	 */
	public String getDescription() {
		return Desc;
	}
	
	/**
	 * returns status
	 * 1  = Approved
	 * -1 = Denied
	 * 0  = Pending
	 */
	public String getStatus(){
		if(Status==1)
			return "Approved";
		else if(Status==-1)
			return "Denied";
		else
			return "Pending";
	}
	
	/**
	 *will not change approval if it has already been modified
	 *@param approve true will approve the ticket, false will deny the ticket
	 */
	public void setStatus(boolean approve) {
		if(Status!=0)
			return;
		if(approve)
			Status=1;
		else
			Status=-1;
	}
	public int getID() {
		return ID;
	}
	
	/**
	 * returns username of account who submitted this ticket
	 */
	public String getUser() {
		return User;
	}	
	
	/**
	 *Travel, Lodging, Food, Other 
	 */
	public String getType() {
		return Type;
	}
	
	/**
	 * Receipts
	 * upload and store images in 
	 * SQL or cloud storage
	 */
	public String getImage() {
		return Pic.getPictureFile().getPath();
	}
	
	public static void main(String[]args) {
		
		Ticket[]tickets=new Ticket[10];
		
		for(int i=0;i<tickets.length;i++) {
			double amount = Math.random();
			String desc="d-"+Math.random();
			String user="u-"+i;
			String type="t-"+Math.random();
			String image = "i-"+Math.random();
			tickets[i]= new Ticket(amount,desc,user,type,image);
		}		

		for(Ticket t:tickets) {
			t.print();
		}
	}
}
