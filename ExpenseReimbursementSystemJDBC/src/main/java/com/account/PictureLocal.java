package com.account;

import java.io.File;

public class PictureLocal implements Picture{

	private File pictureFile;
	
	public PictureLocal() {}
	
	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public File getPictureFile() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean setPictureFile(String pictureFilePath) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean displayPicture(int width, int height) {
		// TODO Auto-generated method stub
		return false;
	}

}
