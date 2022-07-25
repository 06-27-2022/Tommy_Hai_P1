package com.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.account.Picture;

/**
 * Abstract class for all controller classes to extend.
 * Contains protected methods for
 * writing more complicated html tags such as img and table. 
 * @author tomh0
 *
 */
public abstract class Controller {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected PrintWriter writer;

	/**
	 * alongside the protected request and response variables, it will also create a printwriter name writer
	 * @param request passed from the dispatcher servlet
	 * @param response passed from the dispatcher servlet
	 * @throws ServletException
	 * @throws IOException
	 */
	public Controller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.request=request;
			this.response=response;
			writer = response.getWriter();
	}
	
	/**
	 * The picture needs to be stored in a separate servlet because response.reset
	 * removes all the text written by writer.write so the work done by the dispatcher
	 * servlet would disapear. The picture cannot be obtained by directly referencing 
	 * local files either. This function uses the printwriter and an img tag to display
	 * the picture
	 * @param p picture class. Should be from the database with an id
	 * @param width width of the picture
	 * @param height height of the picture
	 */
	protected void displayPicture(Picture p, int width, int height){
		writer.write(displayPictureRaw(p,width,height));
	}
	/**
	 * returns the img tag but as a string instead of directly writing it into the printwriter
	 * @param p picture class. Should be from the database with an id
	 * @param width width of the picture
	 * @param height height of the picture
	 * @return the complete img tag as a string
	 */
	protected String displayPictureRaw(Picture p, int width, int height){
		int pictureID=p.getID();
		return "<img src=\"http://localhost:8080/check/picture?pictureID="+pictureID+"&r="+Math.random()+"\" width=\""+width+"\" height=\""+height+"\">";
	}

	/**
	 * displays an html table
	 * @param args the data of the table
	 * @param header if true, the first row will be changed from data to header
	 */
	protected void displayTable(String[][]args, boolean header) {
		//https://www.w3schools.com/html/html_tables.asp
		writer.write("<table>");
		for(int i =0;i<args.length;i++) {
			//open table row
			writer.write("<tr>");			
			for(int j =0;j<args[0].length;j++)
			{
				//table header/data
				if(header&&i==0) 
					writer.write("<th>");
				else
					writer.write("<td>");
				//contents
				writer.write(args[i][j]);			
				//close header/data
				if(header&&i==0) 
					writer.write("</th>");
				else
					writer.write("</td>");
			}
			//close table row
			writer.write("</tr>");
		}
		//close table
		writer.write("</table>");
	}
}