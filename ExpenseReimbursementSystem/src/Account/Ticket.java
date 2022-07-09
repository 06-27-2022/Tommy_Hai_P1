package Account;

public class Ticket {
	
	//minimum
	protected final double Amount;
	protected final String Desc;
	protected byte Status;

	//bonus
	protected final String User;
	protected final String Type;
	protected final Picture Pic;
	private static int ID=0;
	private int Width,Height;

	public Ticket(double amount, String description, String user, String type, String imageFilePath){
		this.Amount=(double)((int)(amount*100))/100;
		Desc=description;
		Status = 0;
		User = user;
		Type=type;
		ID++;
		Width=200;
		Height=200;
		Pic = new Picture(Width, Height,user+ID,imageFilePath);
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
	 *will not change approval
	 *if it has already been modified
	 *1  = approved
	 *-1 = denied
	 *0  = pending
	 */
	public void setStatus(boolean approve) {
		if(Status!=0)
			return;
		if(approve)
			Status=1;
		else
			Status=-1;
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
		return Pic.Picture.getPath();
	}
	
	public static void main(String[]args) {
		
		Ticket[]tickets=new Ticket[10];
		
		for(int i=0;i<tickets.length;i++) {
			double amount = Math.random();
			String desc="d-"+Math.random();
			String user="u-"+i+" ";
			String type="t-"+Math.random();
			String image = "i-"+Math.random();
			tickets[i]= new Ticket(amount,desc,user,type,image);
		}		

		for(Ticket t:tickets) {
			t.print();
		}
	}
}
