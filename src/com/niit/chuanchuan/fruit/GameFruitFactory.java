package com.niit.chuanchuan.fruit;

import java.util.ArrayList;
import java.util.List;

import android.app.PendingIntent.CanceledException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.niit.chuanchuan.R;
import com.niit.chuanchuan.view.MainView;

public class GameFruitFactory {
	private MainView mainView;
	private List<Bitmap> fruits;
	private List<Bitmap> changefruits;
	public GameFruitFactory(MainView mainView) {
		this.mainView = mainView;
		fruits = new ArrayList<Bitmap>();
		changefruits = new ArrayList<Bitmap>();
		CreateFruit();
	}
	private void CreateFruit(){
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_1_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_2_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_3_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_4_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_5_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_6_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_7_1));
		fruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_8_1));
		
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_1_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_2_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_3_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_4_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_5_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_6_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_7_2));
		changefruits.add(BitmapFactory.decodeResource(mainView.getResources(), R.drawable.fruit_8_2));

	}
	
	public List<Bitmap> getFruits() {
		return fruits;
	}
	
	public List<Bitmap> getChangeFruits() {
		return changefruits;
	}
	
}
