package com.niit.chuanchuan.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.R.string;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.niit.chuanchuan.R;
import com.niit.chuanchuan.constant.ConstantUtil;
import com.niit.chuanchuan.fruit.GameFruitFactory;
import com.niit.chuanchuan.object.GameObject;
import com.niit.chuanchuan.sound.GameSoundPool;

public class MainView extends BaseView{
	private Bitmap border;
	private Bitmap board;
	private Bitmap background;
	private Bitmap stick_tail;
	private Bitmap stick_head;
	private Bitmap border1;
	private Bitmap board1;
	private Bitmap background1;
	private Bitmap stick_tail1;
	private Bitmap stick_head1;
	private Bitmap temp_fruit;
	private List<GameObject> fruits;
	private int bitmap_width;
	private GameFruitFactory factory;
	private float event_x;
	private float event_y;
	private float stick_x;
	private float stick_y;
	//屏幕适配放大比例
	protected float enlarge_x;
	protected float enlarge_y;
	private String stage;
	private int score;
	private int cnum;
	private static int trans_score;
	private long time_text;
	CountDownTimer timer;
	private final int passScore = 200;
	
	private List<GameObject> cFruits;
	private boolean isTouch = false;	
	
 	//准备地图
	private int[][] nums = new int[4][5];
	
	public MainView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		fruits = new ArrayList<GameObject>();
		cFruits = new ArrayList<GameObject>();
		thread = new Thread(this);
		//stage = 1;
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		factory =  new GameFruitFactory(this);
		initBitmap(); 
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
		timer = new CountDownTimer(60000,1000) {			
			@Override
			public void onTick(long arg0) {
				// TODO Auto-generated method stub
				time_text = arg0/1000;				
			}			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				trans_score = score;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_END_VIEW);
			}
		};	
		timer.start();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		super.surfaceDestroyed(arg0);
	}
	
	@Override
	public void initBitmap() {
		border = BitmapFactory.decodeResource(getResources(), R.drawable.border);
		board = BitmapFactory.decodeResource(getResources(), R.drawable.board);
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_3);
		stick_tail = BitmapFactory.decodeResource(getResources(), R.drawable.stick_tail);
		stick_head = BitmapFactory.decodeResource(getResources(), R.drawable.stick_head);
		
		//计算屏幕适配缩放比
		enlarge_x = (screen_width/320);
		enlarge_y = (screen_height/480);
		
		//按照比例对图片进行放大		
		border1 = Bitmap.createScaledBitmap(border, (int)(screen_width), (int)(screen_height), true);
		board1 = Bitmap.createScaledBitmap(board, (int)(screen_width), (int)(board.getHeight()*enlarge_y), true);
		background1 = Bitmap.createScaledBitmap(background, (int)(screen_width), (int)(screen_height), true);
		stick_head1 = Bitmap.createScaledBitmap(stick_head, (int)(stick_head.getWidth()*enlarge_x), (int)(stick_head.getHeight()*enlarge_y), true);
		stick_tail1 = Bitmap.createScaledBitmap(stick_tail, (int)(stick_tail.getWidth()*enlarge_x), (int)(stick_tail.getHeight()*enlarge_y), true);
		
		//计算显示内容的位置
		bitmap_width = (int) ((screen_width - 20*enlarge_x)/5);
		event_x = screen_width/2;
		
		Random random = new Random();
		for(int i = 0 ;i < nums.length ; i++){
			for(int j = 0 ; j < nums[i].length ; j++){
				if(ReadyView.getType() == 1){
					nums[i][j] = random.nextInt(5);
				}
				if(ReadyView.getType() == 2){
					nums[i][j] = random.nextInt(8);
				}
				
				GameObject obj = new GameObject(factory.getFruits().get(nums[i][j])
						, factory.getChangeFruits().get(nums[i][j])
						, (10 + (bitmap_width - factory.getFruits().get(nums[i][j]).getWidth()*enlarge_x)/2 + bitmap_width * j)
						, (int) (enlarge_y*10 + factory.getFruits().get(nums[i][j]).getHeight()*enlarge_y * i)
						, (int) (factory.getFruits().get(nums[i][j]).getWidth())
						, (int) (factory.getFruits().get(nums[i][j]).getHeight()));
				fruits.add(obj);
			}
		}			
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		event_x = event.getX();
		event_y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			isTouch = true;
			for(int i = fruits.size()-1 ; i>=0 ; i--){
				if(fruits.get(i).getState()==0){
					if(fruits.get(i).actionConform(event_x, event_y, bitmap_width, enlarge_x, enlarge_y)){
						fruits.get(i).setState(1);		//将水果状态变为被选中状态
						cFruits.add(fruits.get(i));		//将当前水果加入到被选中的水果列表中去
						fruits.get(i).setState(2);
					}
				}
			}
			drawSelf();
			break;

		case MotionEvent.ACTION_UP:
			isTouch = false;
			for(GameObject obj : fruits){
				if(obj.getState()==1){		//如果水果状态为被选中状态则将水果状态置为入列表状态
					obj.setState(2);
				}
			}
			addFruits();
			checkFruit();
			checkScore();
			drawSelf();
			break;
		}
		return true;
	}
	
	private void checkScore() {
		if(cFruits.size()>=7){
			trans_score = score;
			timer.cancel();
			mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_END_VIEW);
			sounds.playSound(3, 0);
		}else {
			int count = 0;
			for(GameObject obj : fruits){
				if(obj.getState()==0);
					count++;
			}		
			if (count==0) {
				trans_score = score;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_END_VIEW);
				if (score < passScore) {
					sounds.playSound(3, 0);
				}else {
					sounds.playSound(5, 0);
				}
			}
		}
	}
	
	private void checkFruit(){
		for(int j = cFruits.size()-1 ; j>=2 ; j--){
			int count = 0;
			for(int i = j-1 ; i>=0 ; i--){
				if(cFruits.get(j).getImage()==cFruits.get(i).getImage())
					count++;
				else {
					break;
				}
			}
			if(count==6){
				score += 200;
				cFruits.remove(j);
				cFruits.remove(j-1);
				cFruits.remove(j-2);
				cFruits.remove(j-3);
				cFruits.remove(j-4);
				cFruits.remove(j-5);
				cFruits.remove(j-6);
				sounds.playSound(6, 0);
				j = cFruits.size();
				
			}else if (count==5) {
				score += 150;
				cFruits.remove(j);
				cFruits.remove(j-1);
				cFruits.remove(j-2);
				cFruits.remove(j-3);
				cFruits.remove(j-4);
				cFruits.remove(j-5);
				sounds.playSound(6, 0);
				j = cFruits.size();
				
			}else if (count==4) {
				score += 75;
				cFruits.remove(j);
				cFruits.remove(j-1);
				cFruits.remove(j-2);
				cFruits.remove(j-3);
				cFruits.remove(j-4);
				sounds.playSound(6, 0);
				j = cFruits.size();
				
			}else if (count==3) {
				score += 35;
				cFruits.remove(j);
				cFruits.remove(j-1);
				cFruits.remove(j-2);
				cFruits.remove(j-3);
				sounds.playSound(6, 0);
				j = cFruits.size();
				
			}else if (count==2) {
				score += 10;
				cFruits.remove(j);
				cFruits.remove(j-1);
				cFruits.remove(j-2);
				sounds.playSound(6, 0);
				j = cFruits.size();
				
			}else if (count==1 || count==0) {
				continue;
			}
		}
	}
	
	private void addFruits(){
		int sum =0;
		int j = 0;
		Random rd = new Random();
		for(GameObject obj:fruits){
			if(obj.getState()==0)
				sum++;
			}
		if(sum<=14){  //水果数目过少，则产生新的一排水果
		for(GameObject obj:fruits){  //位置下移
			temp_fruit = Bitmap.createScaledBitmap(obj.getImage(), (int)(obj.getWidth()*enlarge_x), (int)(obj.getHeight()*enlarge_y), true);
			obj.setInity(obj.getInity()+temp_fruit.getHeight());
		}
			
		for(int i=0;i<nums[0].length;i++){  //产生新的水果
			if (ReadyView.getType() == 1) {
				j=rd.nextInt(5);
			}
			if(ReadyView.getType() == 2){
				j=rd.nextInt(8);
			}
			GameObject obj = new GameObject(factory.getFruits().get(j)
					, factory.getChangeFruits().get(j)
					, (10*enlarge_x + (bitmap_width - factory.getFruits().get(j).getWidth()*enlarge_x)/2 + bitmap_width * i)
					, (int) (10*enlarge_y)
					, factory.getFruits().get(j).getWidth()
					, factory.getFruits().get(j).getHeight());
			fruits.add(i, obj);
		}
	}
		for(GameObject obj:fruits){ //判断水果是否多出界面
			if(obj.getState()==0 && obj.getInity()+obj.getHeight()>=screen_height*7/8){
				trans_score = score;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_END_VIEW);
				sounds.playSound(3, 0);
				}
			}
	}
	
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE); // 绘制背景色
			canvas.save();
			//绘制背景
			canvas.drawBitmap(background1, 0, 0, paint);
			//绘制竹签
			if(isTouch){		//当手指触摸到屏幕时
				stick_x = event_x;
				stick_y = event_y;
				canvas.drawBitmap(stick_tail1, stick_x, stick_y + 40, paint);
				canvas.drawBitmap(stick_head1, stick_x, stick_y, paint);
			}else {				//当手指离开屏幕时
				stick_x = event_x;
				stick_y = screen_height-(board1.getHeight()/(3/2))-50*enlarge_y;
				canvas.drawBitmap(stick_tail1, stick_x, stick_y + 40*enlarge_y, paint);
				canvas.drawBitmap(stick_head1, stick_x, stick_y, paint);
			}	
			//绘制水果
			for(GameObject obj : fruits){
				temp_fruit = Bitmap.createScaledBitmap(obj.getImage(), (int)(obj.getWidth()*enlarge_x), (int)(obj.getHeight()*enlarge_y), true);
				if(obj.getState()==0)
					canvas.drawBitmap(temp_fruit, obj.getInitx(), obj.getInity(), paint);
				else if (obj.getState()==1)
					canvas.drawBitmap(temp_fruit, obj.getInitx(), obj.getInity(), paint);
			} 	
			//绘制竹签上的水果
			cnum = cFruits.size();
			for(int k = cnum-1; k >= 0; k--){
				temp_fruit = Bitmap.createScaledBitmap(cFruits.get(k).getImage(), (int)(cFruits.get(k).getWidth()*enlarge_x), (int)(cFruits.get(k).getHeight()*enlarge_y), true);
				canvas.drawBitmap(temp_fruit, stick_x-(temp_fruit.getWidth()/2), (stick_y+((cnum-k)*temp_fruit.getHeight())), paint);					
			}			
			//绘制背景和记分板		
			canvas.drawBitmap(border1, 0, 0, paint);
			canvas.drawBitmap(board1, 0, screen_height-board1.getHeight(), paint);
			//设置文字大小及颜色
			paint.setTextSize(enlarge_x*15);
			paint.setColor(Color.BLACK);
			//绘制文字
			if(ReadyView.getType() == 1){
				stage = "Easy";
			}
			if(ReadyView.getType() == 2){
				stage = "Crazy";
			}
			canvas.drawText(stage, enlarge_x*21, screen_height-13*enlarge_y, paint);
			canvas.drawText(String.valueOf(score), enlarge_x*105, screen_height-13*enlarge_y, paint);
			canvas.drawText("7", enlarge_x*185, screen_height-15*enlarge_y, paint);
			canvas.drawText("剩余时间：", enlarge_x*210, screen_height-20*enlarge_y, paint);
			canvas.drawText(String.valueOf(cnum), enlarge_x*160, screen_height-23*enlarge_y, paint);
			canvas.drawText(String.valueOf(time_text)+"s", enlarge_x*285, screen_height-20*enlarge_y, paint);
			canvas.restore();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	public static int getScore(){
		return trans_score;
	}
	
	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 400)
					Thread.sleep(400 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}

}
