package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.account.Picture;

public abstract class Controller {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected PrintWriter writer;

	public Controller(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			this.request=request;
			this.response=response;
			writer = response.getWriter();
	}
	
	/**
	 * The picture needs to be stored in a separate servlet because response.reset
	 * removes all the text written by writer.write
	 * the picture cannot be obtained by directly referencing local files either
	 * @throws IOException 
	 */
	protected void displayPicture(Picture p, int width, int height){
		int pictureID=p.getID();
		writer.write("<img src=http://localhost:8080/check/picture?pictureID="+pictureID+" width=\""+width+"\" height=\""+height+"\">");
	}
}