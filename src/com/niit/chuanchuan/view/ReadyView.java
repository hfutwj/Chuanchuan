package com.niit.chuanchuan.view;

import javax.xml.transform.Templates;

import android.R.integer;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import com.niit.chuanchuan.R;
import com.niit.chuanchuan.constant.ConstantUtil;
import com.niit.chuanchuan.sound.GameSoundPool;

public class ReadyView extends BaseView {
	private float button_x;
	private float button_y1;
	private float button_y2;
	private float button_y3;
	private float string_width;
	private float string_height;
	private float event_x;
	private float event_y;
	private float title_x;
	private float title_y;
	private float enlarge_x;
	private float enlarge_y;
	private float string_x;
	private float string_y1;
	private float string_y2;
	private String button_value1;
	private String button_value2;
	private String button_value3;
	private Bitmap button_bg1;
	private Bitmap button_bg2;
	private Bitmap background;
	private Bitmap title;
	private Bitmap button_bg11;
	private Bitmap button_bg21;
	private Bitmap background1;
	private Bitmap title1;
	private Rect rect;
	private boolean isBtChange1;
	private boolean isBtChange2;
	private boolean isBtChange3;
	private static int type;

	public ReadyView(Context context, GameSoundPool sounds) {
		super(context, sounds);
		paint.setTextSize(20);
		rect = new Rect();
		thread = new Thread(this);
	}

	/**
	 * ��ͼ����
	 */
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		super.surfaceCreated(arg0);
		screen_width = this.getWidth();
		screen_height = this.getHeight();
		initBitmap();
		if (thread.isAlive()) {
			thread.start();
		} else {
			thread = new Thread(this);
			thread.start();
		}
	}

	/**
	 * ��ͼ�仯
	 */
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		super.surfaceChanged(arg0, arg1, arg2, arg3);
	}

	/**
	 * ��ͼ����
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		super.surfaceDestroyed(arg0);
	}

	/**
	 * ��ʼ������
	 */
	@Override
	public void initBitmap() {
		background = BitmapFactory.decodeResource(getResources(),R.drawable.background);
		button_bg1 = BitmapFactory.decodeResource(getResources(),R.drawable.button);
		button_bg2 = BitmapFactory.decodeResource(getResources(),R.drawable.button2);
		title = BitmapFactory.decodeResource(getResources(), R.drawable.title);
		button_value1 = "��ģʽ";
		button_value2 = "���ģʽ";
		button_value3 = "�˳���Ϸ";
		paint.getTextBounds(button_value1, 0, 3, rect);
		string_width = rect.width();
		string_height = rect.height();

		//������Ļ�������ű�
		enlarge_x =  (screen_width/320);
		enlarge_y =  (screen_height/480);
		
		//����ͼƬ����
		//background1 = Bitmap.createScaledBitmap(background, (int)(), enlarge_y, true);
		button_bg11 = Bitmap.createScaledBitmap(button_bg1, (int)(button_bg1.getWidth()*enlarge_x), (int)(button_bg1.getHeight()*enlarge_y), true);
		button_bg21 = Bitmap.createScaledBitmap(button_bg2, (int)(button_bg1.getWidth()*enlarge_x), (int)(button_bg1.getHeight()*enlarge_y), true);
		title1 = Bitmap.createScaledBitmap(title, (int)(title.getWidth()*enlarge_x),(int)(title.getHeight()*enlarge_y), true);
		
		// ���㰴ťλ��
		button_x = (screen_width - button_bg11.getWidth()) / 2;
		button_y1 = (screen_height - button_bg11.getHeight()) / 2;
		button_y2 = button_y1 + button_bg11.getHeight() + 20*enlarge_y;
		button_y3 = button_y2 + button_bg11.getHeight() + 20*enlarge_y;

		// �������ͼƬ��λ��
		title_x = (screen_width - title1.getWidth()) / 2;
		title_y = (screen_height - title1.getHeight()) / 4;

		// ȷ������ͼƬ���ű�
		scalex = screen_width / background.getWidth();
		scaley = screen_height / background.getHeight();

		// ���㰴ť���ֵ�����
		string_x = button_x + (button_bg1.getWidth() - string_width) / 2 - 10;
		/*string_y1 = (button_y1 + button_bg11.getHeight() / 2 + (string_height) / 2);
		string_y2 = (button_y2 + button_bg21.getHeight() / 2 + (string_height) / 2);*/
		
		string_y1 = (button_bg11.getHeight()-string_height)/2+string_height/2+button_y1;
		string_y1 = (button_bg21.getHeight()-string_height)/2+string_height/2+button_y2;
		
	}

	// ��Ӧ�����¼��ķ���
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		event_x = event.getX();
		event_y = event.getY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y1) && (event_y <= (button_y1 + button_bg11.getHeight()))) {
				isBtChange1 = true;
			} else {
				isBtChange1 = false;
			}

			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y2) && (event_y <= (button_y2 + button_bg21.getHeight()))) {
				isBtChange2 = true;
			} else {
				isBtChange2 = false;
			}
			
			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y1) && (event_y <= (button_y3 + button_bg11.getHeight()))) {
				isBtChange3 = true;
			} else {
				isBtChange3 = false;
			}
			break;

		case MotionEvent.ACTION_UP:
			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y1) && (event_y <= (button_y1 + button_bg11.getHeight()))) {
				type = 1;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y2) && (event_y <= (button_y2 + button_bg11.getHeight()))) {
				type = 2;
				mainActivity.getHandler().sendEmptyMessage(ConstantUtil.TO_MAIN_VIEW);
			}
			if (event_x >= button_x	&& (event_x <= (button_x + button_bg11.getWidth())) && (event_y >= button_y3)&& (event_y <= (button_y3 + button_bg11.getHeight()))) {
				new AlertDialog.Builder(mainActivity)
				.setTitle("ȷ��")
				.setMessage("ȷ��Ҫ�˳���Ϸ��")
				.setPositiveButton("ȷ��",(new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog,int which) {
						System.exit(0);
					}
				}))
				.setNegativeButton("ȡ��", null).show();
			}
			break;
		}
		return true;
	}

	/**
	 * ���л�ͼ����
	 */
	@Override
	public void drawSelf() {
		try {
			canvas = sfh.lockCanvas();
			canvas.drawColor(Color.BLACK); // ���Ʊ���ɫ
			canvas.save();
			// ȷ������ͼƬ���ű�
			scalex = screen_width / background.getWidth();
			scaley = screen_height / background.getHeight();
			canvas.scale(scalex, scaley, 0, 0); // ���㱳��ͼƬ����Ļ�ı���
			// ���Ʊ���ͼ
			canvas.drawBitmap(background, 0, 0, paint);
			canvas.restore();
			//canvas.save();
			// ȷ������ͼƬ���ű�,���Ʊ���
			//scalex = (float) (screen_width / title.getWidth() - 320.0 / title.getWidth() +1);
			//scaley = (float) (screen_height / title.getHeight() - 480.0 / title.getHeight() +1);
			//canvas.scale(enlarge_x, enlarge_y, title_x + title.getWidth() / 2,	title_y + title.getHeight() / 2);
			//canvas.scale(scalex, scaley, 0, 0);
			canvas.drawBitmap(title1, title_x, title_y, paint);
			//canvas.restore();
			//canvas.save();
			// ȷ����ťͼƬ���ű�,���ư�ť
			//scalex = (float) (screen_width / button_bg1.getWidth() - 320.0/button_bg1.getWidth() +1);
			//scaley = (float) (screen_height / button_bg1.getHeight() - 480.0/button_bg1.getHeight() +1);
			//canvas.scale(enlarge_x, enlarge_y, button_x + button_bg1.getWidth()/2, button_y2-(button_y2 - button_y1 - button_bg1.getHeight())/2);
			//canvas.scale(scalex, scaley, 0, 0);
			if (isBtChange1) {
				canvas.drawBitmap(button_bg21, button_x, button_y1, paint);
			} else {
				canvas.drawBitmap(button_bg11, button_x, button_y1, paint);
			}
			if (isBtChange2) {
				canvas.drawBitmap(button_bg21, button_x, button_y2, paint);
			} else {
				canvas.drawBitmap(button_bg11, button_x, button_y2, paint);
			}	
			if (isBtChange3) {
				canvas.drawBitmap(button_bg21, button_x, button_y3, paint);
			} else {
				canvas.drawBitmap(button_bg11, button_x, button_y3, paint);
			}	
			// ��������
			paint.setTextSize(25*enlarge_x);
			canvas.drawText(button_value1, button_x +33*enlarge_x, button_y1+30*enlarge_y, paint);
			canvas.drawText(button_value2, button_x +33*enlarge_x, button_y2+30*enlarge_y, paint);
			canvas.drawText(button_value3, button_x +33*enlarge_x, button_y3+30*enlarge_y, paint);
			canvas.restore();
		} catch (Exception err) {
			err.printStackTrace();
		} finally {
			if (canvas != null)
				sfh.unlockCanvasAndPost(canvas);
		}
	}
	
	public static int getType(){
		return type;
	}
	
	// �߳����еķ���
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
