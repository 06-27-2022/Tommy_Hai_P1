package com.account;

import java.io.File;

public interface Picture {

	public int getID();
	/**
	 * returns the File class the picture is stored on
	 */
	public File getPictureFile();
	/**
	 * will save the provided filepath into a File class
	 * @param pictureFilePath the filepath of the image you want to copy
	 */
	public boolean setPictureFile(String pictureFilePath);
	/**
	 * will display the picture in a new window
	 */
	public boolean displayPicture(int width, int height);
	
	public String toString();
}