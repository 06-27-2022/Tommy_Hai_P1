package com.jdbc;

import java.util.List;

import com.account.Picture;
import com.account.PictureRemote;

public class PictureList extends jdbcList<Picture>{

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
}
