package com.account;

import java.io.File;

public interface Picture {

	int getID();
	/**
	 * returns the File class the picture is stored on
	 */
	File getPictureFile();
	/**
	 * will save the provided filepath into a File class
	 * @param pictureFilePath the filepath of the image you want to copy
	 */
	boolean setPictureFile(String pictureFilePath);
	/**
	 * will display the picture in a new window
	 */
	boolean displayPicture(int width, int height);
}