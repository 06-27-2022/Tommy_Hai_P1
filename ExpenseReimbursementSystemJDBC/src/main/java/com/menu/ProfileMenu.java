package com.menu;
import java.util.List;

import com.account.Account;
import com.account.Picture;
import com.account.PictureRemote;
import com.account.Profile;
import com.account.ProfileRemote;

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
		//TODO searchUer
		System.out.println("Enter Name");
		String user = input();
		System.out.println("Not implemented");
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

}
