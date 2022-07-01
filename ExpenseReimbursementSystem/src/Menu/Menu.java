package Menu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public abstract class Menu {
	
	protected String[]menuOptions;

	/*
	 * traverse menu options
	 */
	public abstract void traverse();
	public abstract String input();
	

	/*
	 * traverse menu through console
	 * returns 0 if menu options are empty
	 */
	public int consoleTraverse() {
		int length = menuOptions.length;
		int option=-1;
		
		//there are 0 or less menu options
		if(length < 1) {
			System.out.println("No menu options");
			return 0;
		}
		
		while(option==-1) {
			System.out.println("Options:");
			
			//list menu options
			System.out.print("/");			
			for(String s:menuOptions)
				System.out.print(s+"/");			
			System.out.println();
			
			//select and return option
			String str=input();
			for(int i=0;i<length;i++) 
				if(str.equalsIgnoreCase(menuOptions[i])) 
					return i;
		}
		return option;
	}
	
	/*
	 * input string in console
	 */	
	public String consoleInput() {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String str;
		try {
			str = in.readLine();
		} catch (IOException e) {
			str=null;
		}
		return str;	
	}
}
