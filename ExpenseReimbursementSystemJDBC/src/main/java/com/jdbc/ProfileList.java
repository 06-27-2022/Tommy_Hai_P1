package com.jdbc;

import java.util.List;

import com.account.Profile;
import com.account.ProfileRemote;

public class ProfileList extends jdbcList<Profile>{

	@Override
	public boolean add(Profile p) {
		int picID=-1;
		if(p.getPicture()!=null) {
			//insert into picture (id)values(default)returning id;
			final String SQL0="insert into picture (id,image)values(default,?)returning id";
			picID=(int)ConnectionUtil.stmtExecuteQuery(SQL0,p.getPicture().getPictureFile());
		}

		//insert into ticket(account,amount,description,status)values(3,1.1,'qwer','p');
		final String SQL = "insert into profile (account,name,address,picture)values(?,?,?,?)";
		Object[]obj= {p.getAccountID(),p.getName(),p.getAddress(),picID};
		return ConnectionUtil.stmtExecute(SQL, obj);
		
	}
	@Override
	public Profile get(int index) {
		//select "account","amount","description","type","picture" from ticket order by id asc limit 1 offset 0;
		//String SQL ="select \"account\",\"amount\",\"description\",\"type\",\"picture\" from "+TABLE+" order by id asc limit 1 offset ?";
		final String SQL ="select id from profile order by id asc limit 1 offset ?";
		int profileID=(int) ConnectionUtil.stmtExecuteQuery(SQL, index);
		return new ProfileRemote(profileID);
	}

	@Override
	public int size() {
		final String SQL="select count(*) from profile;";
		return (int)ConnectionUtil.stmtExecuteQuery(SQL);
	}

	@Override
	public boolean isEmpty() {
		if(size()==0) {return true;}
		return false;
	}
	public static void main(String[]args) {
		List<Profile>plist = new ProfileList();
		//String filepath ="C:\\Users\\tomh0\\Documents\\Revature\\Project 1\\Tommy_Hai_P1\\ExpenseReimbursementSystem\\Images\\1.png";
		//Picture pic = new PictureRemote("testfile", filepath);
		//Profile profile = new ProfileRemote(1, "nametest", "addresstest", pic);
		//plist.add(profile);
		
		System.out.println("Printing profiles");
		for(int i=0;i<plist.size();i++){
			Profile p =plist.get(i);
			System.out.println(p.toString());
			p.getPicture().displayPicture(100, 100);
		}
	}
}
