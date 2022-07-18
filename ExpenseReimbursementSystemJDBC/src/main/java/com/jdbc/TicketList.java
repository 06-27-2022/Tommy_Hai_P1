package com.jdbc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.account.Picture;
import com.account.PictureRemote;
import com.account.Ticket;
import com.account.TicketRemote;

public class TicketList implements List<Ticket>{

	final String TABLE;
	
	public TicketList() {
		TABLE="ticket";
	}
	public TicketList(int accountID) {
		this.TABLE = "ticket where account="+accountID;
	}
	public TicketList(int accountID,String status) {
		String s="";
		if(status.equalsIgnoreCase("p")||status.equalsIgnoreCase("pending"))
			s=" and status='p'";
		else if(status.equalsIgnoreCase("a")||status.equalsIgnoreCase("approved"))
			s=" and status='a'";
		else if(status.equalsIgnoreCase("d")||status.equalsIgnoreCase("denied"))
			s=" and status='d'";
		this.TABLE = "ticket where account="+accountID+s;
	}
	
	public int size() {
		final String SQL ="select count(*) from "+TABLE;
		return (int) ConnectionUtil.stmtExecuteQuery(SQL);
	}

	/**
	 *don't use this yet
	 */
	@Override
	public boolean add(Ticket t) {
		int picID=-1;
		if(t.getPicture()!=null) {
			//insert into picture (id)values(default)returning id;
			final String SQL0="insert into picture (id,image)values(default,?)returning id";
			picID=(int)ConnectionUtil.stmtExecuteQuery(SQL0,t.getPicture().getPictureFile());
		}
		//insert into ticket(account,amount,description,status)values(3,1.1,'qwer','p');
		final String SQL = "insert into ticket (account,amount,description,status,type,picture)values(?,?,?,?,?,?)";
		Object[]obj= {t.getAccountID(),t.getAmount(),t.getDescription(),'p',t.getType(),picID};
		return ConnectionUtil.stmtExecute(SQL, obj);
	}
	@Override
	public TicketRemote get(int index) {
		//select "account","amount","description","type","picture" from ticket order by id asc limit 1 offset 0;
		//String SQL ="select \"account\",\"amount\",\"description\",\"type\",\"picture\" from "+TABLE+" order by id asc limit 1 offset ?";
		final String SQL ="select id from "+TABLE+" order by id asc limit 1 offset ?";
		Object[]args= {index};
		Object[][]t=ConnectionUtil.stmtExecuteQuery2D(SQL, args);
		int ticketID=(int)t[0][0];
		return new TicketRemote(ticketID);
	}
	@Override
	public boolean isEmpty() {
		if(size()==0)
			return true;
		return false;
	}
	public static void main(String[] args) {
		List<Ticket>plist = new TicketList(1);
		String filepath ="C:\\Users\\tomh0\\Documents\\Revature\\Project 1\\Tommy_Hai_P1\\ExpenseReimbursementSystem\\Images\\1.png";
		Picture pic = new PictureRemote("testfile", filepath);
		Ticket ticket = new TicketRemote(1, 1.23, "tryingtofixpictures", "a", pic);
		plist.add(ticket);
		
		System.out.println("Printing Tickets");
		for(int i=0;i<plist.size();i++){
			Ticket t =plist.get(i);
			System.out.println(t.toString());
			t.getPicture().displayPicture(100, 100);
		}
	}
	
	
	
	
	
	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Iterator<Ticket> iterator() {
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
	public boolean addAll(Collection<? extends Ticket> c) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean addAll(int index, Collection<? extends Ticket> c) {
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
	public TicketRemote set(int index, Ticket element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(int index, Ticket element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public TicketRemote remove(int index) {
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
	public ListIterator<Ticket> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ListIterator<Ticket> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Ticket> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}
}
