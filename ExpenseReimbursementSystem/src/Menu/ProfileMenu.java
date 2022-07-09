package Menu;
import java.util.ArrayList;

import Account.Account;

public class ProfileMenu extends Menu {

	Account account;
	ArrayList<Account>accounts;
	
	public ProfileMenu(Account account, ArrayList<Account>accounts){
		this.account=account;
		this.accounts=accounts;	
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
		System.out.println("Enter Name");
		String user = input();
		for(Account a:accounts) 
			if(a.getName().equals(user))
				a.getProfile().print();
	}
	public void viewProfile() {
		account.getProfile().print();
	}
	public void editProfile() {
		System.out.println("Enter Name");
		String name = input();
		System.out.println("Enter Address");
		String address = input();
		System.out.println("Enter Picture");
		String pic= input();
		account.setProfile(name,address,pic);
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
