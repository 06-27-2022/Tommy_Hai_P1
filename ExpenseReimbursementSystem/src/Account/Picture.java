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

public class Picture {
	//first name sent when initialized
	//used for naming local copy of picture
	protected final String ACCOUNTNAME;
	protected final String EXTENSION;
	//dimensions of picture
	protected int PicX, PicY;

	//the Picture
	protected File Picture;

	public Picture(int width, int height, String nameOfCopy, String filePath) {
		ACCOUNTNAME = nameOfCopy;
		EXTENSION="png";
		PicX=width;
		PicY=height;
		setPicture(filePath);
	}

	//picture need to upgrade to class file
	public File getPicture() {
		return Picture;
	}
	
	public void setPicture(String pictureFilePath) {
		//file with path from pictureFilePath
		File inFile = new File(pictureFilePath);
		//copy of inFile saved in project as png
		File outFile = new File(ACCOUNTNAME+"."+EXTENSION);
		
		//attempt to read filepath in inFile
		BufferedImage bufferedImage;
		try {
			bufferedImage= ImageIO.read(inFile);
			System.out.println("File found");
		} catch (IOException e) {
			bufferedImage = generateBufferedImage();
			System.out.println("File not found");
		}

		//write picture into outFile
		try {
			ImageIO.write(bufferedImage, EXTENSION, outFile);
			Picture=outFile;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void displayPicture(){
		
		//loads the picture from the file
		BufferedImage bufferedImage;		
		try {
			bufferedImage = ImageIO.read(Picture);
			//System.out.println("File found");
		} catch (Exception e) {
			bufferedImage = generateBufferedImage();
			//System.out.println("File not found");
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
	
	protected BufferedImage generateBufferedImage(){
		// TYPE_INT_ARGB specifies the image format: 8-bit RGBA packed into integer pixels
		BufferedImage bi = new BufferedImage(PicX, PicY, BufferedImage.TYPE_INT_ARGB);
		Graphics2D ig2 = bi.createGraphics();
		
		//example from 
		//http://www.java2s.com/Code/Java/2D-Graphics-GUI/DrawanImageandsavetopng.htm
		Font font = new Font("TimesRoman", Font.BOLD, 20);
		ig2.setFont(font);
		String message = ACCOUNTNAME;
		FontMetrics fontMetrics = ig2.getFontMetrics();
		int stringWidth = fontMetrics.stringWidth(message);
		int stringHeight = fontMetrics.getAscent();
		ig2.setPaint(Color.black);
		ig2.drawString(message, (PicX - stringWidth) / 2, PicY/ 2 + stringHeight / 4);
		
		return bi;
	}

	public static void main(String[] args) {
		int x= 200, y=150;
		String name = "PictureClassTest";
		String filepath = "ProfileTest.png";
		Picture p = new Picture(x,y,name,filepath);
		p.displayPicture();
		System.out.println(p.getPicture().getAbsolutePath());
	}

}
