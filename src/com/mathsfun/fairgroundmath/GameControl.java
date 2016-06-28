package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class GameControl {
	private static GameSharedPreferences mPrefs;
	static float aimPosX;	
	static float aimPosY;
	static float speedX;	
	static float speedY;
	static boolean moveAim = false;
	
	static int lastScore = 0;
	
	static float targetStartPosY;
	static float targetStartPosX;

	static boolean animRunning = false;
	
	static float xPosClicked = 0f;
	static float yPosClicked = 0f;
	
	static boolean shotTaken = false;
	static boolean targetHit = false;
	
	static int numOfRows;
	static float ballSpeed;
	
	static Bitmap aimImg;
	static Bitmap ballImg;
	static Bitmap targetImg;
	static Bitmap cannonImg;

	public GameControl(Context context){
		mPrefs = new GameSharedPreferences(context);
		getImgs(context);
		getAimSpeed();
		getStartPosX();
		getStartPosY();
		getBallSpeeed();
	}
	
	// set speed of the aim
	private void getAimSpeed(){
		String prefSpeed = mPrefs.getAimSpeed();
		int speed = Integer.valueOf(prefSpeed);
		float s = 0;
		if(SizeControl.tScale!=0){
			s = speed * SizeControl.tScale;
		}
		speedX = s;
		speedY = s;
	}
	
	private void getImgs(Context context){
		String prefAimImg = mPrefs.getAimImg();
		String prefTargetImg = mPrefs.getTargetImg();
		String prefBallImg = mPrefs.getBallImg();
		String prefCannonImg = mPrefs.getCannonImg();
		
		Resources res = context.getResources();
		int aImg = res.getIdentifier(prefAimImg, "drawable", context.getPackageName());
		int tImg = res.getIdentifier(prefTargetImg, "drawable", context.getPackageName());
		int bImg = res.getIdentifier(prefBallImg, "drawable", context.getPackageName());
		int cImg = res.getIdentifier(prefCannonImg, "drawable", context.getPackageName());

		aimImg = BitmapFactory.decodeResource(context.getResources(), aImg);
		targetImg = BitmapFactory.decodeResource(context.getResources(), tImg);
		ballImg = BitmapFactory.decodeResource(context.getResources(), bImg);
		cannonImg = BitmapFactory.decodeResource(context.getResources(), cImg);
	}
	
	private void getStartPosX(){
		String prefXpos = mPrefs.getStartPosX();
		float xPos = Float.valueOf(prefXpos);
		float startPosX = 0;
		if(SizeControl.targetHeight!=0){
			startPosX = xPos + SizeControl.hScreenWidth-SizeControl.hTargetWidth;
		}
		targetStartPosX = startPosX;
		
	}

	private void getStartPosY(){
		String prefYpos = mPrefs.getStartPosY();
		float yPos = Float.valueOf(prefYpos);
		float startPosY = 0;
		if(SizeControl.targetHeight!=0){
			startPosY = yPos + ((SizeControl.screenHeight*0.6f)-(SizeControl.targetHeight*numOfRows));
		}
		targetStartPosY = startPosY;
	}
	
	private void getBallSpeeed(){
		String prefSpeed = mPrefs.getBallSpeed();
		int speed = Integer.valueOf(prefSpeed);
		float s = 0;
		if(SizeControl.tScale!=0){
			s = speed * SizeControl.tScale;
		}
		ballSpeed = s;
	}
	
	public void numberOfRows(int r){
		numOfRows = r;
	}

	public static void gameTargetPosX(float x){
		
	}
	
	// do these after (add/deduct seperate number to targetStartPosY)
	public static void aimPositionY(float y){
		aimPosY = y;
	}
	
	public static void aimPositionX(float x){
		aimPosX = x;
	}
}
