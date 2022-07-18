package com.jdbc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.account.Picture;
import com.account.PictureRemote;
import com.account.Profile;
import com.account.ProfileRemote;
import com.account.Ticket;
import com.account.TicketRemote;

public class ProfileList implements List<Profile>{

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
		String filepath ="C:\\Users\\tomh0\\Documents\\Revature\\Project 1\\Tommy_Hai_P1\\ExpenseReimbursementSystem\\Images\\1.png";
		Picture pic = new PictureRemote("testfile", filepath);
		Profile profile = new ProfileRemote(1, "nametest", "addresstest", pic);
		//plist.add(profile);
		
		System.out.println("Printing Tickets");
		for(int i=0;i<plist.size();i++){
			Profile p =plist.get(i);
			System.out.println(p.toString());
			p.getPicture().displayPicture(100, 100);
		}
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Profile> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(Collection<? extends Profile> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Profile> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Profile set(int index, Profile element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Profile element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Profile remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int indexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int lastIndexOf(Object o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ListIterator<Profile> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Profile> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profile> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}


}
