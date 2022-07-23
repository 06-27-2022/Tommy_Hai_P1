package com.account;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.jdbc.ConnectionUtil;

public class PictureRemote implements Picture {
	
	//has code showing how to make use of bytea for uploading images
	//f is a file object
	//stmt.setBinaryStream(2, new FileInputStream(f), (int)f.length());
	//https://www.enterprisedb.com/docs/jdbc_connector/latest/08_advanced_jdbc_connector_functionality/05_using_bytea_data_with_java/
	private final int PICTURE_ID;

	private  File PictureFile;

	//first name sent when initialized
	//used for naming local copy of picture
	private String FILENAME;
	private static String EXTENSION;
	private static String FOLDER;
	
	public PictureRemote(int pictureID) {
		PICTURE_ID=pictureID;
		EXTENSION="png";
		//FOLDER="Images//";
		FOLDER="";
	}	
	/**
	 * the character : seems to cause issues
	 * will have to look into input sanitization
	 */
	public PictureRemote(String nameOfCopy, String filePath) {
		this.PICTURE_ID = -1;
		FILENAME = nameOfCopy;
		EXTENSION="png";
		//FOLDER="Images//";
		FOLDER="";
		setPictureFile(filePath);
	}
	private boolean local() {
		if(PICTURE_ID==-1)
			return true;
		return false;
	}
	@Override
	public String toString() {
		return "PictureID:"+getID()+"|FileTotalSpace:"+getPictureFile().getTotalSpace();
	}
	@Override
	public int getID() {
		if(local())
			return -1;
		return PICTURE_ID;
	}

	/**
	 * returns the File class the picture is stored on
	 */
	@Override
	public File getPictureFile() {
		if(local())
			return PictureFile;
		//select image from picture where id=1;
		final String SQL="select image from picture where id=?";
		Object[]args= {getID()};
		try {
			File outFile = new File(FOLDER+FILENAME+"."+EXTENSION);
			InputStream in=(ByteArrayInputStream) ConnectionUtil.stmtExecuteQuery2D(SQL, args)[0][0];
			OutputStream out=new FileOutputStream(outFile.getName());
			int data;
			do{
				data=in.read();
				out.write(data);
			}while(data!=-1); 
			return outFile;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		catch(ArrayIndexOutOfBoundsException e){//the cell is null
//			// TODO Auto-generated catch block
//			//e.printStackTrace();			
//		}catch(NullPointerException e) {//inputstream returned nothing
//			//e.printStackTrace();
//		}
		return null;
	}
	
	/**
	 * will save the provided filepath into a File class
	 * @param pictureFilePath the filepath of the image you want to copy
	 */
	@Override
	public boolean setPictureFile(String pictureFilePath) {
		//copy of inFile saved in project as png
		File outFile = new File(FOLDER+FILENAME+"."+EXTENSION);
		//attempt to read filepath in inFile
		BufferedImage bufferedImage=readFile(pictureFilePath);
		//write picture into outFile
		try {
			ImageIO.write(bufferedImage, EXTENSION, outFile);
			PictureFile=outFile;
			if(local())
				return false;
		} catch (IOException e) {//file not found
			PictureFile=generateFile();
		}catch(IllegalArgumentException e) {
			PictureFile=generateFile();			
		}
		if(local()) {return false;}
		//changing in picture table
		final String SQL="insert into picture values(default,?)";
		File[]args= {PictureFile};
		return ConnectionUtil.stmtExecute(SQL, args);		
	}
	private File generateFile() {
		File outFile = new File(FOLDER+FILENAME+"."+EXTENSION);
		try {//generate a file
			ImageIO.write(generateBufferedImage(), EXTENSION, outFile);
			PictureFile=outFile;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		return outFile;
	}
	/**
	 * Generates a buffered image using Graphics2D from java.awt
	 * used in the instance where the provided filepath does not point
	 * to a valid file
	 */
	private BufferedImage generateBufferedImage(){
		int PicX=100, PicY=100;
		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed into integer pixels
		BufferedImage bi = new BufferedImage(PicX, PicY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();		
	
		int max;
		if(PicX<PicY)
			max=PicY;
		else
			max=PicX;
		
		for(int p=0;p<=max;p++) {
			//draw rectangle
			int x=PicX/2*(max-p)/max;
			int y=PicY/2*(max-p)/max;
			int width=PicX*p/max;
			int height=PicY*p/max;
			Shape shape = new Rectangle(x,y,width,height);
			
			//color
			float h=(float)(Math.random());
			float s=(float)(Math.random()*0.5+0.5);
			float b=(float)(Math.random()*0.75);
			ig2.setPaint(Color.getHSBColor(h, s, b));
			ig2.draw(shape);				
		}			
	
		//example from 
		//http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawanImageandsavetopng.htm
		Font font = new Font("TimesRoman", Font.BOLD, 20);
		ig2.setFont(font);
		String message = "<"+FILENAME+">";
		FontMetrics fontMetrics = ig2.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(message);
		int stringHeight = fontMetrics.getAscent();
		ig2.setPaint(Color.white);
		ig2.drawString(message, (PicX - stringWidth) / 2, PicY/ 2 + stringHeight / 4);
		
		return bi;
	}


	private BufferedImage readFile(String pictureFilePath) {
		File inFile;
		BufferedImage bi;
		try {
			inFile = new File(pictureFilePath);
			bi = ImageIO.read(inFile);
		} catch (IOException e) {
			try {
				inFile = new File(FOLDER+pictureFilePath);
				bi = ImageIO.read(inFile);				
			}catch(IOException e1) {
				bi=generateBufferedImage();
				//System.out.println("File not found");
			}
		}catch(NullPointerException e2) {
			bi=generateBufferedImage();
			//System.out.println("File not found");
		}
		return bi;
	}

	/**
	 * will display the picture in a new window
	 */
	@Override
	public boolean displayPicture(int width, int height){
		File f = getPictureFile();
		if(f==null)
			return false;
		//loads the picture from the file
		BufferedImage bufferedImage=readFile(getPictureFile().getAbsolutePath());		
        ImageIcon imageIcon = new ImageIcon(bufferedImage);		
		JFrame frame = new JFrame();
		JLabel label = new JLabel();

		//should automatically resize jlabel to the size of the jframe
		//it does the opposite, i got more pressing things to worry about so this will have to wait
		frame.setLayout(new BorderLayout());
		label.setIcon(imageIcon);
		frame.add(label, BorderLayout.CENTER);
		
		frame.getContentPane().setSize(new Dimension(width,height));
		frame.setSize(width, height);
		frame.setAlwaysOnTop(true);
		//frame.pack();

		//sets size of window
//		frame.setLayout(new FlowLayout());
//		frame.getContentPane().setSize(new Dimension(width,height));
//		frame.setSize((int)(width), (int)(height+50));
//		frame.setAlwaysOnTop(true);
		
		//opens the jframe in new window
		frame.setVisible(true);
		
		/*
		 *	JFrame.EXIT_ON_CLOSE — Exit the application.
			JFrame.HIDE_ON_CLOSE — Hide the frame, but keep the application running.
			JFrame.DISPOSE_ON_CLOSE — Dispose of the frame object, but keep the application running.
			JFrame.DO_NOTHING_ON_CLOSE — Ignore the click.
		 */
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		return true;
	}
	public static void main(String[] args) {
		PictureRemote p0 = new PictureRemote(1);
		PictureRemote p1 = new PictureRemote(p0.getID());
		System.out.println(p0+"\n"+p1);
		p0.displayPicture(100, 100);
		p1.displayPicture(100, 100);
	}
}
