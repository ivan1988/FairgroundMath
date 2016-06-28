package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class BallControl {
	@SuppressWarnings("unused")
	private SizeControl sizeC;
	@SuppressWarnings("unused")
	private GameControl gameC;
	 
	
	  
	static float ballLocationX;
	static float locationX = 0;
	static float ballLocationY;
	private static float startScreenWidth;
	private static float startScreenHeight;
	
	Matrix m = new Matrix();
	private Bitmap ballImg;
	
	public BallControl(Context context) {
		sizeC = new SizeControl(context);
		gameC = new GameControl(context);
		checkAnimation();
		ballImg = GameControl.ballImg;
	}
	
	private void checkAnimation(){
		if(!GameControl.shotTaken || GameControl.targetHit){
			ballLocationY = SizeControl.screenHeight-(SizeControl.screenHeight/5);
			ballLocationX = GameControl.targetStartPosX+(SizeControl.ballWidth/2);
			startScreenWidth = SizeControl.screenWidth;
			startScreenHeight = SizeControl.screenHeight;
		}
	}
	
	public void addBall(Canvas c, float aimXpos, float aimYpos){
		m = new Matrix();
		m.reset();
		float xPos = 0f;
		float xAdjustment = 0;
		float yAdjustment = 0;
		float scale = 0f;
		
		
		if(aimXpos>GameControl.targetStartPosX){
			xPos = (aimXpos+SizeControl.aimWidth)-GameControl.targetStartPosX;
		}else if(aimXpos<GameControl.targetStartPosX){
			xPos = GameControl.targetStartPosX-(aimXpos+(SizeControl.aimWidth/2));
		}

		float totalSpeed = 1.00f / (SizeControl.screenHeight/SizeControl.targetHeight);
		float speeY = (SizeControl.screenHeight-aimYpos) / SizeControl.targetHeight;
		float speeX = xPos / SizeControl.targetHeight;
		
		if(aimXpos > GameControl.targetStartPosX){
			ballLocationX+=(GameControl.ballSpeed*(speeX*totalSpeed));
		}else if(aimXpos < GameControl.targetStartPosX){
			ballLocationX-=((GameControl.ballSpeed*(speeX*totalSpeed)*2.5));
		}else if(aimXpos + (SizeControl.aimWidth/2) < GameControl.targetStartPosX + (SizeControl.aimWidth/2) && 
				aimXpos + (SizeControl.aimWidth/2) > GameControl.targetStartPosX - (SizeControl.aimWidth/2)){
			ballLocationX = 0;
		}

		if(startScreenWidth>SizeControl.screenWidth){
			locationX = ballLocationX-((startScreenWidth-SizeControl.screenWidth)/2);
			
			yAdjustment = ballLocationY + ((SizeControl.screenHeight - startScreenHeight)/2);
			
			
			scale = SizeControl.tScale / ((SizeControl.screenHeight-aimYpos)/(yAdjustment- aimYpos));
			
		}else if(startScreenWidth<SizeControl.screenWidth){
			locationX = ballLocationX + ((SizeControl.screenWidth-startScreenWidth)/2);
			
			yAdjustment = ballLocationY + (SizeControl.screenHeight-startScreenHeight);
			
			scale = SizeControl.tScale / ((startScreenHeight-aimYpos)/(yAdjustment- aimYpos));
		}else{
			locationX = ballLocationX;
			yAdjustment = ballLocationY;
			scale = SizeControl.tScale / ((SizeControl.screenHeight-aimYpos)/(yAdjustment- aimYpos));
		}
		
		if(aimYpos>=yAdjustment){
			GameControl.targetHit = true;
			checkAnimation();
		}else{
			ballLocationY-=(GameControl.ballSpeed*(speeY*totalSpeed));
		}

		scale+=0.4f;
		
		if(scale<0.6f){
			scale = 0.6f;	
		}

		xAdjustment+=locationX;
		
		if(aimXpos>SizeControl.hScreenWidth){
			xAdjustment-=SizeControl.ballWidth/3;
		}else{
			xAdjustment-=SizeControl.ballWidth/4;
		}

		m.postScale(scale, scale);
		m.postTranslate(xAdjustment, yAdjustment);
		c.drawBitmap(ballImg, m, null);
	}
}
