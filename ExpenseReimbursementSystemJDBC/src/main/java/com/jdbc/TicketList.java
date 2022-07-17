package com.jdbc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.account.Ticket;

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
		//insert into ticket(account,amount,description,status)values(3,1.1,'qwer','p');
		final String SQL = "insert into ticket (account,amount,description,status)values(?,?,?,?)";
		Object[]obj= {t.getAccountID(),t.getAmount(),t.getDescription(),'p'};
		return ConnectionUtil.stmtExecute(SQL, obj);
	}
	@Override
	public Ticket get(int index) {
		//select "account","amount","description","type","picture" from ticket order by id asc limit 1 offset 0;
		//String SQL ="select \"account\",\"amount\",\"description\",\"type\",\"picture\" from "+TABLE+" order by id asc limit 1 offset ?";
		final String SQL ="select id from "+TABLE+" order by id asc limit 1 offset ?";
		Object[]args= {index};
		Object[][]t=ConnectionUtil.stmtExecuteQuery2D(SQL, args);
		int ticketID=(int)t[0][0];
		return new Ticket(ticketID);
	}
	@Override
	public boolean isEmpty() {
		if(size()==0)
			return true;
		return false;
	}
	public static void main(String[] args) {
		List<Ticket>plist = new TicketList(1);
		//plist.add(new Ticket(4, 1.23, "no", "blah", 0));

		for(int i=0;i<plist.size();i++){
			Ticket t =plist.get(i);
			System.out.println(t.getAmount()+"|"+t.getDescription()+"|"+t.getType()+"|"+t.getStatus());
		}
		System.out.println("size:"+plist.size());
		
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
	public Ticket set(int index, Ticket element) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void add(int index, Ticket element) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Ticket remove(int index) {
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
