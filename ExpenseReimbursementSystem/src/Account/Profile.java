package Account;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * -track additional user information
	-name, address, etc
	-users can edit their account
	-users can add profile picture
 */
public class Profile {
	
	//first name sent when initialized
	//used for naming local copy of picture
	final protected String ACCOUNTNAME;
	
	//dimensions of picture
	protected static int PicX, PicY;
	
	protected String Name;
	protected String Address;
	protected File Picture;
	
	public Profile(String name, String address, String pictureFilePath) {
		ACCOUNTNAME=name;
		PicX=200;
		PicY=150;
		setName(name);
		setAddress(address);
		setPicture(pictureFilePath);
	}
	
	//name
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		this.Name=name;
	}
	
	//address
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		this.Address=address;
	}
	
	//picture need to upgrade to class file
	public File getPicture() {
		return Picture;
	}
	
	public void setPicture(String pictureFilePath) {
		//file with path from pictureFilePath
		File inFile = new File(pictureFilePath);
		
		//copy of inFile saved in project as png
		File outFile = new File(ACCOUNTNAME+".png");
		
		//attempt to read filepath in inFile
		BufferedImage bufferedImage;
		try {
			bufferedImage= ImageIO.read(inFile);
		} catch (IOException e) {
			bufferedImage = generateBufferedImage();
		}

		//write picture into outFile
		try {
			ImageIO.write(bufferedImage, "png", outFile);
			Picture=outFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//print in console
	public void print() {
		System.out.println("Name:"+Name);
		System.out.println("Address:"+Address);
		System.out.println("Picture:"+Picture);
		displayPicture();
	}
	
	/*
	 * generates a bufferedimage object using graphics2d from java.awt
	 * only prints name for now
	 */
	protected BufferedImage generateBufferedImage(){
		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed into integer pixels
		BufferedImage bi = new BufferedImage(PicX, PicY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();
		
		//example from http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawanImageandsavetopng.htm
		Font font = new Font("TimesRoman", Font.BOLD, 20);
		ig2.setFont(font);
		String message = Name;
		FontMetrics fontMetrics = ig2.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(message);
		int stringHeight = fontMetrics.getAscent();
		ig2.setPaint(Color.black);
		ig2.drawString(message, (PicX - stringWidth) / 2, PicY/ 2 + stringHeight / 4);
		
		return bi;
	}
	
	protected void displayPicture(){
		
		//loads the picture from the file
		BufferedImage bufferedImage;		
		try {
			bufferedImage = ImageIO.read(Picture);
		} catch (Exception e) {
			bufferedImage = generateBufferedImage();
		}
		
        ImageIcon imageIcon = new ImageIcon(bufferedImage);
		
		JFrame frame = new JFrame();
		frame.setLayout(new FlowLayout());

		//sets size of window
		double scale = 1.5;
		frame.setSize((int)(PicX*scale), (int)(PicY*scale));
		
		JLabel jLabel = new JLabel();
		jLabel.setIcon(imageIcon);
		frame.add(jLabel);
		frame.setVisible(true);
		
		/**
		 *	JFrame.EXIT_ON_CLOSE — Exit the application.
			JFrame.HIDE_ON_CLOSE — Hide the frame, but keep the application running.
			JFrame.DISPOSE_ON_CLOSE — Dispose of the frame object, but keep the application running.
			JFrame.DO_NOTHING_ON_CLOSE — Ignore the click.
		 */
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
	}
	
	public static void main(String[]args) throws IOException {
		String name = "name";
		String address = "address";
		//String filepath = "Screenshot (1).png";
		String filepath = "No";
		Profile test = new Profile(name,address,filepath);
		
		test.print();
	}
	
}
