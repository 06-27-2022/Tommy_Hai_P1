package Account;

public class Ticket {
	
	//minimum
	final double amount;
	final String desc;
	protected byte status;

	//bonus
	final String user;
	final String type;
	final String image;
	
	/*
	 * minimum
	 * -amount
	 * -description
	 * -status
	 */
//	public Ticket(double amount, String description){
//		this.amount=(double)((int)(amount*100))/100;
//		desc=description;
//		status = 0;
//		this.user = "undefined";
//		type="undefined";
//		image="undefined";
//	}

	/*
	 * bonus
	 * -amount
	 * -description
	 * -status
	 * -type
	 * -image
	 */
	public Ticket(double amount, String description, String user, String type, String image){
		this.amount=(double)((int)(amount*100))/100;
		desc=description;
		status = 0;
		this.user = user;
		this.type=type;
		this.image=image;
	}
	/*
	 * prints in console
	 */
	public void print() {
		System.out.println("Amount: "+amount);
		System.out.println("Description: "+desc);
		System.out.println("Status: "+getStatus());
		System.out.println("Type: "+getType());
		System.out.println("Image: "+getImage());
		System.out.println("User: "+getUser());
	}
	
	/*
	 * returns amount
	 */
	public double getAmount() {
		return amount;
	}
	
	/*
	 * returns description
	 */
	public String getDescription() {
		return desc;
	}
	
	/*
	 *will not change approval
	 *if it has already been modified
	 *1  = approved
	 *-1 = denied
	 *0  = pending
	 */
	public void setStatus(boolean approve) {
		if(status!=0)
			return;
		if(approve)
			status=1;
		else
			status=-1;
	}
	
	/*
	 * returns status
	 * 1  = Approved
	 * -1 = Denied
	 * 0  = Pending
	 */
	public String getStatus(){
		if(status==1)
			return "Approved";
		else if(status==-1)
			return "Denied";
		else
			return "Pending";
	}
	/*
	 * returns username of account who submitted this ticket
	 */
	public String getUser() {
		return user;
	}	
	/*
	 *Travel, Lodging, Food, Other 
	 */
	public String getType() {
		return type;
	}
	
	/*
	 * Receipts
	 * upload and store images in 
	 * SQL or cloud storage
	 */
	public String getImage() {
		return image;
	}
	
}
