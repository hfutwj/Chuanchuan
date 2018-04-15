package com.niit.chuanchuan.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.style.BackgroundColorSpan;
import android.view.MotionEvent;
import android.view.SurfaceHolder;

import com.niit.chuanchuan.R;
import com.niit.chuanchuan.constant.ConstantUtil;
import com.niit.chuanchuan.sound.GameSoundPool;

public class EndView extends BaseView{
	private float button_x;
	private float button_y1;
	private float button_y2;
	private float string_width;
	private float string_height;
	private float event_x;
	private float event_y;
	private float title_width;
	private float title_height;
	private float score_width;
	private float score_height;
	private float enlarge_x;
	private float enlarge_y;
	private String button_value1;
	private String button_value2;
	private String title;
	private String last_score;
	private Bitmap button_bg1;
	private Bitmap button_bg2;
	private Bitmap background;
	private Bitmap button_bg11;
	private Bitmap button_bg21;
	private Rect rect;
	private Rect rect1;
	private Rect rect2;
	private boolean isBtChange1;
	private boolean isBtChange2;
	public EndView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		rect = new Rect();
		rect1 = new Rect();
		rect2 = new Rect();
		thread = new Thread(this);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		initBitmap(); 
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		if(thread.isAlive()){
			thread.start();
		}
		else{
			thread = new Thread(this);
			thread.start();
		}
		
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}
	
	@Override
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(), R.drawable.bg_01);
		button_bg1 = BitmapFactory.decodeResource(getResources(), R.drawable.button);
		button_bg2 = BitmapFactory.decodeResource(getResources(), R.drawable.button2);
		
		//计算屏幕适配缩放比
		enlarge_x = screen_width/320;
		enlarge_y = screen_height/480;
		
		button_bg11 = Bitmap.createScaledBitmap(button_bg1, (int)(button_bg1.getWidth()*enlarge_x), (int)(button_bg1.getHeight()*enlarge_y), true);
		button_bg21 = Bitmap.createScaledBitmap(button_bg2, (int)(button_bg1.getWidth()*enlarge_x), (int)(button_bg1.getHeight()*enlarge_y), true);
		button_value1 = "返回菜单";
		button_value2 = "结束游戏";
		title = "游戏结束！";
		last_score ="最后得分:"+String.valueOf(MainView.getScore());
		paint.getTextBounds(button_value1, 0, 3, rect);
		paint.getTextBounds(title, 0, 4, rect1);
		paint.getTextBounds(last_score, 0, last_score.length(), rect2);
		string_width = rect.width();
		string_height = rect.height();
		title_width = rect1.width();		
		title_height = rect1.height();
		score_width = rect2.width();
		score_height = rect2.height();
		//计算按钮位置
		button_x = (screen_width - button_bg11.getWidth()) / 2;
		button_y1 = (screen_height - button_bg11.getHeight()) / 2;
		button_y2 = button_y1 + button_bg11.getHeight() + 20*enlarge_y;
		
		
		
		//确定背景图片缩放比
		scalex = screen_width/background.getWidth();
		scaley = screen_height/background.getHeight();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		event_x = event.getX();
		event_y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event_x>=button_x&&(event_x<=button_x+button_bg11.getWidth())&&(event_y>=button_y1)&&(event_y<=button_y1+button_bg11.getHeight())) {
				isBtChange1 = true;
			}else{
				isBtChange1 = false;
			}
			
			if(event_x>=button_x&&(event_x<=button_x+button_bg11.getWidth())&&(event_y>=button_y2)&&(event_y<=button_y2+button_bg11.getHeight())){
				isBtChange2 = true;
			}else{
				isBtChange2 = false;
			}
			break;

		case MotionEvent.ACTION_UP:
			if (event_x>=button_x&&(event_x<=button_x+button_bg11.getWidth())&&(event_y>=button_y1)&&(event_y<=button_y1+button_bg11.getHeight())) {
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_READY_VIEW);
			}
			if (event_x>=button_x&&(event_x<=button_x+button_bg11.getWidth())&&(event_y>=button_y2)&&(event_y<=button_y2+button_bg11.getHeight())){
				new AlertDialog.Builder(mainActivity)   
				.setTitle("确认")  
				.setMessage("确定要退出游戏吗？")  
				.setPositiveButton("确定", (new DialogInterface.OnClickListener() {						
					@Override
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				}))
				.setNegativeButton("取消", null)  
				.show();  
			}
			break;
		}
		return true;
	}
	
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.WHITE); // 绘制背景色
			canvas.save();
			canvas.scale(scalex, scaley, 0, 0); // 计算背景图片与屏幕的比例
			// 绘制背景图
			canvas.drawBitmap(background, 0, 0, paint); 
			canvas.restore();
			//绘制按钮
			if(isBtChange1){
				canvas.drawBitmap(button_bg21, button_x, button_y1, paint);
			}else{
				canvas.drawBitmap(button_bg11, button_x, button_y1, paint);
			}
			if(isBtChange2){
				canvas.drawBitmap(button_bg21, button_x, button_y2, paint);
			}else{
				canvas.drawBitmap(button_bg11, button_x, button_y2, paint);
			}
			
			//绘制文字
			paint.setColor(Color.WHITE);
			paint.setTextSize(25*enlarge_x);
			canvas.drawText(button_value1, button_x +33*enlarge_x, button_y1+30*enlarge_y, paint);
			canvas.drawText(button_value2, button_x +33*enlarge_x, button_y2+30*enlarge_y, paint);
			canvas.drawText(title, button_x +32*enlarge_x, (screen_height - title_height)/4, paint);
			canvas.drawText(last_score, button_x +30*enlarge_x, (screen_height - title_height)/4+50*enlarge_y, paint);
			canvas.restore();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	@Override
	public void run() {
		while (threadFlag) {
			long startTime = System.currentTimeMillis();
			drawSelf();
			long endTime = System.currentTimeMillis();
			try {
				if (endTime - startTime < 100)
					Thread.sleep(400 - (endTime - startTime));
			} catch (InterruptedException err) {
				err.printStackTrace();
			}
		}
	}

}
