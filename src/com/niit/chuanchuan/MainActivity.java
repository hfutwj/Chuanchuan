package com.niit.chuanchuan;

import java.io.IOException;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import com.niit.chuanchuan.constant.ConstantUtil;
import com.niit.chuanchuan.sound.GameSoundPool;
import com.niit.chuanchuan.view.EndView;
import com.niit.chuanchuan.view.MainView;
import com.niit.chuanchuan.view.ReadyView;

public class MainActivity extends Activity {
	private ReadyView readyView;
	private MainView mainView;
	private EndView endView;
	private GameSoundPool sounds;
	private MediaPlayer mediaPlayer;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler(){ 
		@Override
        public void handleMessage(Message msg){
            if(msg.what == ConstantUtil.TO_MAIN_VIEW){
            	toMainView();
            }
            else  if(msg.what == ConstantUtil.TO_END_VIEW){
            	toEndView(msg.arg1);
            }else  if(msg.what == ConstantUtil.TO_READY_VIEW){
            	toReadyView();
            }
            else  if(msg.what == ConstantUtil.END_GAME){
            	endGame();
            }
        }

		
    };
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置没有标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//设置全屏幕
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//获取和加载声音道声音池
		sounds = new GameSoundPool(this);
		sounds.initGameSound();
		//创建初始界面
		readyView = new ReadyView(this,sounds);
		setContentView(readyView);
    }
    
    /**
     * 结束游戏
     */
    private void endGame() {
    	
    }

    /**
     * 进入初始游戏界面
     * @param arg1
     */
	private void toEndView(int arg1) {
		if(endView == null){
			endView = new EndView(this,sounds);
		}
		setContentView(endView);
		try {
			AssetManager am = getAssets();//获得该应用的AssetManager
			AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
			mediaPlayer = new MediaPlayer();
		    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
		    mediaPlayer.prepare();
		    mediaPlayer.setLooping(true);
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		mainView = null;
	}

	/**
	 * 显示游戏主界面
	 */
	private void toMainView() {
		if(mainView == null){
			mainView = new MainView(this,sounds);
		}
		setContentView(mainView);
		try {
			AssetManager am = getAssets();//获得该应用的AssetManager
			AssetFileDescriptor fileDescriptor = am.openFd("bgm.mp3");
			mediaPlayer = new MediaPlayer();
		    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
		    mediaPlayer.prepare();
		    mediaPlayer.setLooping(true);
			mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		readyView = null;
	}
	
	private void toReadyView() {
		if(readyView == null){
			readyView = new ReadyView(this,sounds);
		}
		setContentView(readyView);
		try {
			AssetManager am = getAssets();//获得该应用的AssetManager
			AssetFileDescriptor fileDescriptor = am.openFd("sound_background.mp3");
			mediaPlayer = new MediaPlayer();
		    mediaPlayer.setDataSource(fileDescriptor.getFileDescriptor(),fileDescriptor.getStartOffset(),fileDescriptor.getLength());
		    mediaPlayer.prepare();
		    mediaPlayer.setLooping(true);
			//mediaPlayer.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		endView = null;
	}
	
	// getter和setter方法
	public Handler getHandler() {
		return handler;
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
