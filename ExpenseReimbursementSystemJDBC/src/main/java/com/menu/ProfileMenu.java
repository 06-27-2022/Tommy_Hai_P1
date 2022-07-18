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
		System.out.println("Enter Name");
		String name = input();
		
		//select id from profile where "name"='qwer';
		final String SQL="select account from profile where \"name\"=?";
		Object o[][] = ConnectionUtil.stmtExecuteQuery2D(SQL, name);

		//no users
		if(o.length==0) {
			System.out.println("No profiles found using the name "+name);
			return;
		}

		//select user
		System.out.print(o.length+" users have the name "+name+"\n/");
		Account[]accounts=new AccountRemote[o.length];
		for(int i=0;i<o.length;i++) {
			accounts[i]=new AccountRemote((int)o[i][0]);
			System.out.print(""+accounts[i].getName()+"/");
		}		
		String user = input();
		
		//display profile of selected account
		for(Account a:accounts) {
			if(user.equalsIgnoreCase(a.getName())) {
				System.out.println("Name:"+a.getProfile().getName());
				System.out.println("Address:"+a.getProfile().getAddress());
				a.getProfile().getPicture().displayPicture(200, 200);
				return;
			}
		}
		System.out.println("No selection made");
	}
	public void viewProfile() {
		Account.getProfile().print();
	}
	
	public void editProfile() {
		System.out.println("Enter Name");
		String name = input();
		System.out.println("Enter Address");
		String address = input();
		System.out.println("Enter Picture");
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
					System.out.println("Error");
			}
		}		
	}
	public static void main(String[]args) {
		Menu m = new ProfileMenu(new AccountRemote(1),new AccountList());
		m.traverse();
	}

}
