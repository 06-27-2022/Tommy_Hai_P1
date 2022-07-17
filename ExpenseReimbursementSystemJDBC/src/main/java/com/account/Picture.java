package com.account;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Picture {
	//first name sent when initialized
	//used for naming local copy of picture
	private final String FILENAME;
	private final String EXTENSION;
	private  final String FOLDER;
	//dimensions of picture
	private  int PicX, PicY;
	//the Picture
	private  File PictureFile;

	/**
	 * the character : seems to cause issues
	 * will have to look into input sanitization
	 */
	public Picture(int width, int height, String nameOfCopy, String filePath) {
		FILENAME = nameOfCopy;
		EXTENSION="png";
		FOLDER="Images//";
		setSize(width,height);
		//setPictureFile(filePath);
	}

	/**
	 * returns the File class the picture is stored on
	 */
	public File getPictureFile() {
		return PictureFile;
	}
	
	/**
	 * will save the provided filepath into a File class
	 * @param pictureFilePath the filepath of the image you want to copy
	 */
	public void setPictureFile(String pictureFilePath) {
		//copy of inFile saved in project as png
		File outFile = new File(FOLDER+FILENAME+"."+EXTENSION);
		
		//attempt to read filepath in inFile
		BufferedImage bufferedImage=readFile(pictureFilePath);
	
		//write picture into outFile
		try {
			ImageIO.write(bufferedImage, EXTENSION, outFile);
			PictureFile=outFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	protected BufferedImage readFile(String pictureFilePath) {
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

	public int getWidth() {
		return PicX;
	}
	public int getHeight() {
		return PicY;
	}
	public void setWidth(int x) {
		PicX=x;
	}
	public void setHeight(int y) {
		PicY=y;
	}
	public void setSize(int x, int y) {
		setWidth(x);
		setHeight(y);
	}
	/**
	 * will display the picture in a new window
	 */
	public void displayPicture(){
		
		//loads the picture from the file
		BufferedImage bufferedImage=readFile(PictureFile.getAbsolutePath());		
		
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
		
		JFrame jFrame = new JFrame();
		jFrame.setLayout(new FlowLayout());

		//sets size of window and its contents
		double scale = 1.1;
		jFrame.getContentPane().setSize(new Dimension(PicX,PicY));
		jFrame.setSize((int)(PicX*scale), (int)(PicY*scale+50));
	
		
		
		//jlabel
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		jFrame.add(jLabel);

		//opens the jframe in new window
		jFrame.setVisible(true);
		
		/*
		 *	JFrame.EXIT_ON_CLOSE — Exit the application.
			JFrame.HIDE_ON_CLOSE — Hide the frame, but keep the application running.
			JFrame.DISPOSE_ON_CLOSE — Dispose of the frame object, but keep the application running.
			JFrame.DO_NOTHING_ON_CLOSE — Ignore the click.
		 */
		jFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	/**
	 * Generates a buffered image using Graphics2D from java.awt
	 * used in the instance where the provided filepath does not point
	 * to a valid file
	 */
	protected BufferedImage generateBufferedImage(){
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
		String message = FILENAME;
		FontMetrics fontMetrics = ig2.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(message);
		int stringHeight = fontMetrics.getAscent();
		ig2.setPaint(Color.white);
		ig2.drawString(message, (PicX - stringWidth) / 2, PicY/ 2 + stringHeight / 4);
		
		return bi;
	}

	public static void main(String[] args) {
		int x= 200, y=100;
		String name = "PictureClassTest";
		String filepath = "ProfileTest.png";
		Picture p = new Picture(x,y,name,filepath);
		p.displayPicture();
		System.out.println(p.getPictureFile().getAbsolutePath());
	}

}
