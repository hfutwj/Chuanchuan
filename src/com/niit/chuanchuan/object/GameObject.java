package com.niit.chuanchuan.object;

import android.R.integer;
import android.graphics.Bitmap;
import android.graphics.Rect;

public class GameObject {
	private Bitmap image;
	private Bitmap imageChange;
	private int initx;
	private int inity;
	private int width;
	private int height;
	private int state;
	private boolean isConnected;
	
	
	public GameObject(Bitmap image, Bitmap imageChange, float initx, int inity, int width, int height) {
		super();
		this.setImage(image);
		this.setImageChange(imageChange);
		this.setInitx((int) initx);
		this.setInity(inity);
		this.setWidth(width);
		this.setHeight(height);
	}


	public boolean isConnected() {
		return isConnected;
	}


	public void setConnected(boolean isConnected) {
		this.isConnected = isConnected;
	}
	
	public boolean actionConform(float x,float y, int bitmap_width,float enlarge_x,float enlarge_y){
		if (x > initx && x < (initx+bitmap_width) && y < (inity+height*enlarge_y)) {
			return true;
		}else {
			return false;
		}
		
	}

	public Bitmap getImage() {
		// TODO Auto-generated method stub
		return image;
	}


	public int getInitx() {
		return initx;
	}


	public void setInitx(int initx) {
		this.initx = initx;
	}


	public int getInity() {
		return inity;
	}


	public void setInity(int inity) {
		this.inity = inity;
	}


	public int getWidth() {
		return width;
	}


	public void setWidth(int width) {
		this.width = width;
	}


	public int getHeight() {
		return height;
	}


	public void setHeight(int height) {
		this.height = height;
	}


	public Bitmap getImageChange() {
		return imageChange;
	}


	public void setImageChange(Bitmap imageChange) {
		this.imageChange = imageChange;
	}


	public void setImage(Bitmap image) {
		this.image = image;
	}


	public int getState() {
		return state;
	}

	/**
	 * state为水果的状态，0表示水果未被选中，1表示水果被选中，2表示水果被串起
	 */
	public void setState(int state) {
		this.state = state;
	}


	
}
