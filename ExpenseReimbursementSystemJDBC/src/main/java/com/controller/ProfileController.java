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
import com.account.Profile;
import com.account.ProfileRemote;
import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;
/**
 * all features available to profiles
 * @author tomh0
 *
 */
public class ProfileController extends Controller{

	protected Account account;
	protected List<Account>accounts;

	/**
	 * must have a httpsession from login controller
	 * @param request passed from dispatcher servlet
	 * @param response passed from dispatcher servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public ProfileController(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		super(request, response);
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
	 * displays a table of profiles. 
	 * the profiles displayed are chosen by the name on the profile.
	 */
	public void searchProfile() {
		if(!permission())return;
		//search name
		String name=request.getParameter("name");

		final String SQL="select account from profile where \"name\"=?";
		Object o[][] = ConnectionUtil.stmtExecuteQuery2D(SQL, name);

		if(o==null) {
			writer.write("No Profiles using the name "+name);
			return;
		}
		
		//no users
		if(o.length==0) {
			writer.write("No profiles found using the name "+name);
			return;
		}
		//select user
		writer.write(o.length+" users have the name "+name+"<br>");
		Account[]accounts=new AccountRemote[o.length];
		for(int i=0;i<o.length;i++)
			accounts[i]=new AccountRemote((int)o[i][0]);
		
		//write profiles onto table
		int size=accounts.length;
		String[][]table=new String[size+1][4];
		table[0][0]="Username";
		table[0][1]="Name";
		table[0][2]="Address";
		table[0][3]="Picture";
		for(int i=0;i<size;i++) {
			Profile p=accounts[i].getProfile();
			table[i+1][0]=accounts[i].getName();
			table[i+1][1]=p.getName();
			table[i+1][2]=p.getAddress();
			table[i+1][3]=displayPictureRaw(p.getPicture(), 50, 50);
		}		
		displayTable(table,true);

	}
	
	/**
	 * displays 1 profile based on the username of the account
	 * that owns the profile
	 */
	public void selectProfile() {		
		if(!permission())return;
		//String user = input();
		String user=request.getParameter("user");
		final String SQL="select id from account where name=?";
		int accountID;
		try{
			accountID = (int)ConnectionUtil.stmtExecuteQuery(SQL,user);
		}catch(NullPointerException e) {//account does not exist
			writer.write("No user "+user);
			return;
		}
		Profile profile=new AccountRemote(accountID).getProfile();
		displayProfile(profile);
	
	}
	
	/**
	 * displays the profile of the account currently signed in
	 */
	public void viewProfile() {
		if(!permission())return;
		Profile p = account.getProfile();
		displayProfile(p);
	}
	
	/**
	 * edit the name, address, and picture filepath 
	 * of the signed in user's profile
	 */
	public void editProfile() {
		if(!permission())return;
		String name=request.getParameter("name");
		String address=request.getParameter("address");
//		String pic=;

		Picture picture = new PictureRemote("temp", request.getParameter("filepath"));
		Profile p = new ProfileRemote(account.getID(),name,address,picture);
		account.setProfile(p);
		writer.write("Profile Edited");
	}
	
	/**
	 * writes the contents of the provided profile
	 * @param p profile object. Should already be on the database with an id
	 */
	private void displayProfile(Profile p) {
		displayPicture(p.getPicture(),100,100);
		writer.write("<br>PictureID:"+p.getPicture().getID());		
		writer.write("<br>Name:"+p.getName());
		writer.write("<br>Address:"+p.getAddress());
	}
	
}
