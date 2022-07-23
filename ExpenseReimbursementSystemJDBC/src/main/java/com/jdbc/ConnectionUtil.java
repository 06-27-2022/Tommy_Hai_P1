package com.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.postgresql.util.PSQLException;

import com.account.Account;

public class ConnectionUtil {
	
	public static Connection getConnection() {
		String url=System.getenv("db_url");
		String user=System.getenv("db_username");//she named hers db_username
		String password=System.getenv("db_password");		
		try {
			return DriverManager.getConnection(url,user,password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static boolean closeConnection(Connection conn) {
		boolean success;
		try{
			success=true;
			conn.close();
		}catch(SQLException e) {
			success=false;
			e.printStackTrace();			
		}
		return success;
	}
	public static boolean closeConnection(Connection conn,Statement stmt) {
		boolean success;
		try{
			success=true;
			conn.close();
			stmt.close();
		}catch(SQLException e) {
			success=false;
			e.printStackTrace();			
		}
		return success;
	}
	public static boolean closeConnection(Connection conn,Statement stmt,ResultSet set) {
		boolean success;
		try{
			success=true;
			conn.close();
			stmt.close();
			set.close();
		}catch(SQLException e) {
			success=false;
			e.printStackTrace();			
		}
		return success;
	}
	/**
	 * simply executes the statement given
	 * @param SQL String used in prepareStatement()
	 * @return returns true if the statement executed
	 */
	public static boolean stmtExecute(String SQL) {
		Connection conn=getConnection();
		PreparedStatement stmt=null;
		boolean success = false;
		try{
			stmt=conn.prepareStatement(SQL);			
			stmt.execute();
			success = true;
		}catch(SQLException e){
			e.printStackTrace();
		}finally {
			closeConnection(conn,stmt);			
		}
		return success;
	}
	
	/**
	 * executes a prepared statment
	 * @param SQL string used for the prepareStatement() function
	 * @param args list of objects used for stmt.set____(), see setStatement() for more info
	 * @return returns true if no SQLExceptions occured and the statment executed
	 */
	public static boolean stmtExecute(String SQL,Object[]args) {
		Connection conn = getConnection();
		PreparedStatement stmt=null;
		boolean success=false;
		try {
			stmt = conn.prepareStatement(SQL);	
			for(int i=1;i<=args.length;i++)
				//stmt.setObject(i, args[i-1]);
				setStatement(stmt,i,args[i-1]);
			stmt.execute();
			success=true;
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			closeConnection(conn,stmt);
		}
		return success;
	}
		
		/**
		 * automatically selects the correct datatype for stmt.set___() 
		 * supports int, double, char, string, boolean
		 * chars will be submitted as a string
		 * resultset causes exceptions when closing when trying to manully choose the type of set
		 * only uses stmt.setObject for now
		 * @param stmt The ProtectedStatement object you are trying to set
		 * @param i The parameterindex for stmt
		 * @param o	The object being set into stmt
		 * @throws SQLException
		 */
		private static void setStatement(PreparedStatement stmt,int i,Object o) throws SQLException {		
			if(o.getClass().equals(File.class)) {
				File f = (File)o;
				try{
					stmt.setBinaryStream(i,new FileInputStream(f),f.length());				
				}catch(FileNotFoundException e) {
					e.printStackTrace();
				}
			}
			else
				stmt.setObject(i, o);
		}
//			if(o.getClass()==Integer.class)
//				stmt.setInt(i,(int)o);
//			else if(o.getClass()==Double.class)
//				stmt.setDouble(i,(double)o);
//			else if(o.getClass()==Character.class)
//				stmt.setString(i,(String)o);
//			else if(o.getClass()==String.class)
//				stmt.setString(i,(String)o);					
//			else if(o.getClass()==Boolean.class)
//				stmt.setBoolean(i,(boolean)o);	
//			else //avoid using this case, implement the missing type here instead
//				stmt.setObject(i, o);
//		}
		
	/**
	 * Runs a ResultSet. Only works for querys requesting a single element
	 * @param SQL
	 * @param args
	 * @return
	 */
	public static Object stmtExecuteQuery(String SQL) {
		Object[]args = {};
		return stmtExecuteQuery2D(SQL,args)[0][0];
	}
	public static Object stmtExecuteQuery(String SQL,Object o) {
		Object[]args = {o};
		return stmtExecuteQuery2D(SQL,args)[0][0];
	}
	public static Object stmtExecuteQuery(String SQL, Object[]args) {
		return stmtExecuteQuery2D(SQL,args)[0][0];
	}
	
	public static Object[][] stmtExecuteQuery2D(String SQL) {
		Object[]args = {};
		return stmtExecuteQuery2D(SQL,args);		
	}
	public static Object[][] stmtExecuteQuery2D(String SQL,Object arg) {
		Object[]args = {arg};
		return stmtExecuteQuery2D(SQL,args);		
	}
	/**
	 * Runs a resultSet.
	 * @param SQL used for a preparedstatement
	 * @param args	arguments for the preparedstatement
	 * @return returns a 2d object array obj[rows][columns]
	 */
	public static Object[][] stmtExecuteQuery2D(String SQL,Object[]args) {
		Connection conn=ConnectionUtil.getConnection();
		PreparedStatement stmt=null;
		ResultSet set=null;
		Object[][]obj=null;//=new Object[rows][columns];
		ArrayList<ArrayList<Object>>output=new ArrayList<ArrayList<Object>>();
		try {
			stmt=conn.prepareStatement(SQL);
			for(int i=1;i<=args.length;i++)
				setStatement(stmt,i,args[i-1]);
			set=stmt.executeQuery();
			int rows=0;
			int columns=0;
			while(set.next()) {
				output.add(new ArrayList<Object>());
				int c=0;
				while(columns==0||c<columns) {
					try{
						output.get(rows).add(setGetObject(set, c+1));
					}catch(PSQLException e) {
						columns=c;
						break;
					}
					c++;
				}
				rows++;					
			}
			obj=new Object[rows][columns];
			for(int y=0;y<rows;y++)
				for(int x=0;x<columns;x++)
					obj[y][x]=output.get(y).get(x);
		}catch(SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectionUtil.closeConnection(conn,stmt,set);
		}		
		return obj;
	}
	/**
	 * supports int, double, string
	 * defaults to object as last resort
	 * @param set A non null ResultSet object
	 * @param i columnindex
	 * @return attempts to return double, int, string, then object
	 * @throws SQLException
	 */
	private static Object setGetObject(ResultSet set, int i) throws SQLException {		
		Object obj = set.getObject(i);
		if(obj==null)
			return null;
		else if(obj.getClass()==Long.class){
			return set.getInt(i);
		}
		else if(obj.getClass()==BigDecimal.class)
			return set.getDouble(i);
		else if(obj.getClass().toString().equalsIgnoreCase("class [B"))
			return set.getBinaryStream(i);
		return obj;
	}
	
	/**
	 * insert into tablename values(args[0],args[1],etc)
	 * executes a prepared statement using this sql line
	 * @param tablename the name of the table being inserted into
	 * @param args the supported types are: int, double, char(will be sent as a string), string, boolean
	 * @throws SQLException 
	 */
//	private static void insertInto(String tablename,Object[]args){
//		/*
//		 *construct SQL statement
//		 *
//		 *tablenames keep getting put inside 'tablename' so i have to insert it in manually instead
//		 *the function was changed from public to private so other functions will have to be 
//		 *responsible for inputing a propper tablename 
//		 *
//		 *the default keyword also has a similiar problem of being placed inside 'default'
//		 *forcing me to also insert it manually
//		 */
//		String sql ="insert into "+tablename+" values (";
//		List<Object>params=new ArrayList<Object>();
//		for(Object o:args) {
//			if(o.getClass()==String.class)
//				if(((String) o).equalsIgnoreCase("default"))
//					sql+="default,";
//				else{
//					sql+="?,";
//					params.add(o);
//				}
//			else{
//				sql+="?,";
//				params.add(o);
//			}
//		}		
//		sql=sql.substring(0, sql.length()-1)+")";
//		System.out.println(sql);
//		executeStatement(sql,params.toArray());
//	}
//	
//	//select * from
//	private static List<Object>selectFrom(Object[]args){
//		List<Object>result = new ArrayList<Object>();
//
//		
//		
//		return result;
//	}
	
	
	
	
	/**
	 * insert into the account table
	 * @param accountname
	 * @param accountpassword
	 * @param role
	 * @param profile
	 */
//	public static void insertIntoAccounts(Account a){
//		//insert into accounts values (default,'test1','pass1','role1',1,1);
//		Object[]arr= {"default",a.getName(),a.getPassword(),a.getRole()};
//		insertInto("accounts",arr);
//	}	
//	
//	public static void insertIntoTickets(Ticket t) {
//		Object[]arr= {"default",t.getAmount(),t.getDescription(),t.getUser(),t.getImage()};
//		insertInto("tickets",arr);
//	}
//	
//	public static void insertIntoProfile(Profile p) {
//		Object[]arr= {p.getName(),p.getAddress(),p.getPicture()};
//		insertInto("tickets",arr);
//	}
	
	public static void main(String[] args) {
		AccountList a = new AccountList();
		Account acc=a.get(0);
		System.out.println(acc.getName()+acc.getPassword()+acc.getRole());
	}
}
