package com.menu;
import java.util.List;

import com.account.Account;
import com.account.AccountRemote;
import com.account.Picture;
import com.account.PictureRemote;
import com.account.Profile;
import com.account.ProfileRemote;
import com.jdbc.AccountList;
import com.jdbc.ConnectionUtil;
import com.util.OutputUtil;

public class ProfileMenu extends Menu {

	Account Account;
	List<Account>Accounts;
	
	public ProfileMenu(Account account, List<Account>accounts){
		this.Account=account;
		this.Accounts=accounts;	
		menuOptions=new String[4];
		menuOptions[0]="Exit";
		menuOptions[1]="Search User";
		menuOptions[2]="View Profile";
		menuOptions[3]="Edit Profile";
	}
	
	@Override
	public void traverse() {
		consoleTraverse();
	}

	@Override
	public String input() {
		return consoleInput();
	}

	public void searchUser() {
		//search name
		OutputUtil.println("Enter Name");
		String name = input();
		
		//select id from profile where "name"='qwer';
		final String SQL="select account from profile where \"name\"=?";
		Object o[][] = ConnectionUtil.stmtExecuteQuery2D(SQL, name);

		//no users
		if(o.length==0) {
			OutputUtil.println("No profiles found using the name "+name);
			return;
		}

		//select user
		OutputUtil.print(o.length+" users have the name "+name+"\n/");
		Account[]accounts=new AccountRemote[o.length];
		for(int i=0;i<o.length;i++) {
			accounts[i]=new AccountRemote((int)o[i][0]);
			OutputUtil.print(""+accounts[i].getName()+"/");
		}		
		String user = input();
		
		//display profile of selected account
		for(Account a:accounts) {
			if(user.equalsIgnoreCase(a.getName())) {
				OutputUtil.println("Name:"+a.getProfile().getName());
				OutputUtil.println("Address:"+a.getProfile().getAddress());
				a.getProfile().getPicture().displayPicture(200, 200);
				return;
			}
		}
		OutputUtil.println("No selection made");
	}
	public void viewProfile() {
		Profile p = Account.getProfile();
		OutputUtil.println("Name:"+p.getName());
		OutputUtil.println("Address:"+p.getAddress());
		OutputUtil.println("PictureID:"+p.getPicture().getID());
		if(OutputUtil.display)
			p.getPicture().displayPicture(100, 100);
	}
	
	public void editProfile() {
		OutputUtil.println("Enter Name");
		String name = input();
		OutputUtil.println("Enter Address");
		String address = input();
		OutputUtil.println("Enter Picture");
		String pic= input();
		Picture picture = new PictureRemote(Account.getName(), pic);
		Profile p = new ProfileRemote(Account.getID(),name,address,picture);
		Account.setProfile(p);
	}
	protected void consoleTraverse() {
		boolean exit=false;
		while(!exit) {
			int option = consoleSelect();
			switch(option) {
				case 0:
					exit=true;
					break;
				case 1:
					searchUser();
					break;
				case 2:
					viewProfile();
					break;
				case 3:
					editProfile();
					break;
				default:
					OutputUtil.println("Error");
			}
		}		
	}
	public static void main(String[]args) {
		Menu m = new ProfileMenu(new AccountRemote(1),new AccountList());
		m.traverse();
	}

}
