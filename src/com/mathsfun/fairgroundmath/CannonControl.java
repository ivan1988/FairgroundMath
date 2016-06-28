package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class CannonControl {
	private Bitmap cannonImg;
	private Bitmap resetButtonImg;
	@SuppressWarnings("unused")
	private SizeControl sizeC;
	@SuppressWarnings("unused")
	private GameControl gameC;
	Matrix m = new Matrix();
	static float cannonY;
	
	private float gameTargetPosX;
	private float hTargetWidth;
	private float targetWidth;
	private float aimWidth;
	private int numOfRows = 0;
	private float tScale = 0f;
	
	public CannonControl(Context context) {
		sizeC = new SizeControl(context);
		gameC = new GameControl(context);
		assignValues();
		cannonImg = GameControl.cannonImg;
		resetButtonImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.reset_button);
	}
	
	private void assignValues(){
		tScale = SizeControl.tScale;
		targetWidth = SizeControl.targetWidth;
		numOfRows = GameControl.numOfRows;
		gameTargetPosX = GameControl.targetStartPosX;
		aimWidth = SizeControl.aimWidth;
		hTargetWidth = SizeControl.hTargetWidth;
	}

	private void drawResetBtn(Canvas c){
		m = new Matrix();
		m.reset();
		m.postScale(SizeControl.tScale, SizeControl.tScale);
		float r = (resetButtonImg.getWidth()/2)*SizeControl.tScale;
		float x = SizeControl.hScreenWidth-r;
		float y = SizeControl.screenHeight-(r*3);
		m.postTranslate(x, y);
		c.drawBitmap(resetButtonImg, m, null);
		drawResetTouchArea(c, x, y, r);
	}
	
	private void drawResetTouchArea(Canvas c, float x, float y, float r){
		Paint p = new Paint();
		p.setColor(Color.TRANSPARENT);
		x+=r;
		y+=r;
		c.drawCircle(x, y, r, p);
		resetGame(x, y, r);
	}
	
	private void resetGame(float x, float y, float r){
		if(AddView.xPosClicked>0f&&AddView.yPosClicked>0f){			
			int dx = (int) Math.abs(AddView.xPosClicked-x);
			int dy = (int) Math.abs(AddView.yPosClicked-y);
			int dr = (int) r;			
			if (dx + dy <= r){ 
				GameControl.animRunning = false;
				PageControl.gamePage = 1;
			}else if (dx > r){
		    }else if (dy > r){
		    }else if ((dx^2 + dy^2) <= (dr^2)){ 
		    	GameControl.animRunning = false;
				PageControl.gamePage = 1;
		    }else{}
		}		
	}

	public void addCannon(Canvas c, float x){
		drawResetBtn(c);
		m = new Matrix();
		m.reset();	
		float ptX = cannonImg.getWidth()/2;
		float ptY = SizeControl.screenHeight - SizeControl.screenHeight/5;
		float scaleX = 0;
		
		m.postTranslate(-ptX, -cannonImg.getHeight());
		m.postRotate(-(gameTargetPosX-x)/(((targetWidth*2)*numOfRows)/aimWidth*2));
		scaleX = Math.abs((-(gameTargetPosX-x)/(((targetWidth*2)*numOfRows)/aimWidth*2)/hTargetWidth));
		m.postScale(tScale, tScale-(scaleX/2));	
		m.postTranslate((gameTargetPosX+(ptX*2)), (cannonY+((ptY))));
		c.drawBitmap(cannonImg, m, null);
	}
}
