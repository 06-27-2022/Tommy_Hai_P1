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
import com.account.Profile;
import com.account.ProfileRemote;
import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;

public class ProfileController extends Controller{

	Account account;
	List<Account>accounts;

	public ProfileController(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		super(request, response);
		HttpSession session = request.getSession(false);
		int accountID = (int) session.getAttribute("accountID");
		this.account=new AccountRemote(accountID);
		this.accounts=new AccountList();	

	}
	public void searchProfile() {
		//search name
//		OutputUtil.println("Enter Name");
//		String name = input();
		String name=request.getParameter("name");

		//select id from profile where "name"='qwer';
		final String SQL="select account from profile where \"name\"=?";
		Object o[][] = ConnectionUtil.stmtExecuteQuery2D(SQL, name);

		//no users
		if(o.length==0) {
//			OutputUtil.println("No profiles found using the name "+name);
			writer.write("No profiles found using the name "+name);
			return;
		}

		//select user
//		OutputUtil.print(o.length+" users have the name "+name+"\n/");
		writer.write(o.length+" users have the name "+name+"<br>/");
		Account[]accounts=new AccountRemote[o.length];
		for(int i=0;i<o.length;i++) {
			accounts[i]=new AccountRemote((int)o[i][0]);
//			OutputUtil.print(""+accounts[i].getName()+"/");
			writer.write(""+accounts[i].getName()+"/");
		}		
	}
	
	public void selectProfile() {		
		//String user = input();
		String user=request.getParameter("user");
		final String SQL="select id from account where name=?";
		int accountID;
		try{
			accountID = (int)ConnectionUtil.stmtExecuteQuery(SQL,user);
		}catch(ArrayIndexOutOfBoundsException e) {//account does not exist
			return;
		}
		Profile profile=new AccountRemote(accountID).getProfile();
//		writer.write("Name:"+profile.getName());
//		writer.write("|Address:"+profile.getAddress());
//		writer.write("|PictureId:"+profile.getPicture().getID());
		displayProfile(profile);
	
	}
	
	public void viewProfile() {
		Profile p = account.getProfile();
//		OutputUtil.println("Name:"+p.getName());
//		OutputUtil.println("Address:"+p.getAddress());
//		OutputUtil.println("PictureID:"+p.getPicture().getID());
//		displayPicture(p.getPicture());
//		writer.write("Name:"+p.getName());
//		writer.write("Address:"+p.getAddress());
//		writer.write("PictureID:"+p.getPicture().getID());
		displayProfile(p);
//		if(OutputUtil.display)
//			p.getPicture().displayPicture(100, 100);
	}
	
	public void editProfile() {
//		OutputUtil.println("Enter Name");
//		String name = input();
//		OutputUtil.println("Enter Address");
//		String address = input();
//		OutputUtil.println("Enter Picture");
//		String pic= input();
		String name=request.getParameter("name");
		String address=request.getParameter("address");
		String pic=request.getParameter("filepath");

		Picture picture = new PictureRemote(account.getName(), pic);
		Profile p = new ProfileRemote(account.getID(),name,address,picture);
		account.setProfile(p);
	}
	
	private void displayProfile(Profile p) {
		displayPicture(p.getPicture(),100,100);
		writer.write("Name:"+p.getName());
		writer.write("Address:"+p.getAddress());
		writer.write("PictureID:"+p.getPicture().getID());		
	}
	
}
