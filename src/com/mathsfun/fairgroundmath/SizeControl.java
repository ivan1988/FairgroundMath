package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;

public class SizeControl {
	
	private Bitmap aimImg;
	private Bitmap ballImg;
	private Bitmap targetImg;
	private DisplayMetrics metrics;
	@SuppressWarnings("unused")
	private GameControl gc;
	private GameSharedPreferences mPrefs;
	private float prefScale;
	
	static float tScale = 0f;
	
	static float ballWidth;
	static float ballHeight;
	
	static float aimWidth;
	static float aimHeight;
	
	static int screenWidth;
	static int screenHeight;
	static float hScreenWidth;
	static float hScreenHeight;
		
	static float targetWidth;
	static float targetHeight;
	static float hTargetWidth;
	static float hTargetHeight;

	
	public SizeControl(Context context){
		gc = new GameControl(context);
		mPrefs = new GameSharedPreferences(context);
		String pScale = mPrefs.getScale();
		prefScale = Float.valueOf(pScale);
		metrics = context.getResources().getDisplayMetrics();
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		hScreenWidth = screenWidth/2;
		hScreenHeight = screenHeight/2;
		aimImg = GameControl.aimImg;
		targetImg = GameControl.targetImg;
		ballImg = GameControl.ballImg;
		objectSize();
		setScale();
		scaleToSize();
	}
	
	private void scaleToSize(){
		ballWidth*=tScale;
		ballHeight*=tScale;
		aimWidth*=tScale;
		aimHeight*=tScale;
		targetWidth*=tScale;
		targetHeight*=tScale;
		hTargetWidth*=tScale;
		hTargetHeight*=tScale;
	}
	
	private void objectSize(){
		ballWidth = ballImg.getWidth();
		ballHeight = ballImg.getHeight();
		aimWidth = aimImg.getWidth();
		aimHeight = aimImg.getHeight();
		targetWidth = targetImg.getWidth();
		targetHeight = targetImg.getHeight();
		hTargetWidth = targetWidth/2;
		hTargetHeight = targetHeight/2;
	}
	
	public void setScale(){		
		int spacesNeeded = GameControl.numOfRows;		
		float spacesAvailableX = screenWidth / targetWidth;	
		float spacesAvailableY = screenHeight / (targetHeight*GameControl.numOfRows);
		if(spacesAvailableX<spacesNeeded || spacesAvailableY<spacesNeeded){
			if(spacesAvailableX<spacesAvailableY){
				tScale =  (spacesAvailableX/spacesNeeded)*1.00f;
				tScale*=prefScale;
			}else{
				tScale =  ( spacesAvailableY/spacesNeeded)*1.00f;
				tScale*=prefScale;
			}
		}else{
			tScale = 1.0f;
			tScale*=prefScale;
		}
	}
}
