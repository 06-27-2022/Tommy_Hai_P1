package com.jdbc;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.account.Picture;
import com.account.PictureRemote;

public class PictureList implements List<Picture>{

	@Override
	public int size() {
		final String SQL="select count(*) from picture";
		return (int)ConnectionUtil.stmtExecuteQuery(SQL);
	}

	@Override
	public boolean add(Picture p) {
		final String SQL = "insert into picture values(default,?)";
		  //insert into account values(default,'test','test','Employee');
		Object[]o= {p.getPictureFile()};
		return ConnectionUtil.stmtExecute(SQL, o);
	}

	@Override
	public Picture get(int index) {
		//final String SQL = "SELECT \"name\", \"pass\", \"role\" FROM account order by id asc limit 1 offset ?";
		final String SQL = "SELECT id FROM picture order by id asc limit 1 offset ?";
		Object[]args= {index};
		Object[][]a=ConnectionUtil.stmtExecuteQuery2D(SQL,args);
		int id=(int)a[0][0];
		return new PictureRemote(id);
	}

	@Override
	public boolean isEmpty() {
		if(size()==0)
			return true;
		return false;
	}
	public static void main(String[]args) {
		List<Picture>plist=new PictureList();
		//String filepath="C:\\Users\\tomh0\\Pictures\\Screenshots\\Screenshot (5).png";
		for(int i=0;i<plist.size();i++) {
			Picture p = plist.get(i);
			System.out.println(p.getID());
			plist.get(i).displayPicture(400,400);
		}
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<Picture> iterator() {
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
	public boolean addAll(Collection<? extends Picture> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Picture> c) {
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
	public Picture set(int index, Picture element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(int index, Picture element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Picture remove(int index) {
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
	public ListIterator<Picture> listIterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ListIterator<Picture> listIterator(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Picture> subList(int fromIndex, int toIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
