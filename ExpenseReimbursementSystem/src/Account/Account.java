package Account;

import java.util.ArrayList;

public interface Account {

	//user name
	void setName(String name);
	String getName();
	
	//password
	void setPassword(String password);
	String getPassword();

	//role
	String getRole();
	
	//ticket
	void addTickets(Ticket ticket);
	ArrayList<Ticket>getTickets();
}
