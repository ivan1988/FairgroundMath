package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class GameScreenButtons {	
	private Matrix m = new Matrix();	
	private Bitmap upDownBtn;
	private Bitmap fireBtn;

	public GameScreenButtons(Context context) {
		upDownBtn = BitmapFactory.decodeResource(context.getResources(), R.drawable.up_down_button);
		fireBtn = BitmapFactory.decodeResource(context.getResources(), R.drawable.fire_button);
	}
	
	public void addButtons(Canvas c){
		setFireBtn(c);
		setMoveAimBtn(c);
	}
	
	private void setMoveAimBtnBg(Canvas c, float r){
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		r*=3.5f;
		p.setAlpha(90);
		c.drawCircle(SizeControl.screenWidth, SizeControl.screenHeight, r, p);
	}
	
	private void setMoveAimBtn(Canvas c){
		m = new Matrix();
		m.reset();	
		float x = SizeControl.screenWidth-(upDownBtn.getWidth()*SizeControl.tScale);
		float r = (upDownBtn.getHeight()*SizeControl.tScale)/2;
		float y = SizeControl.screenHeight - (r*3f);
		m.postScale(SizeControl.tScale, SizeControl.tScale);		
		m.postTranslate(x, y);
		setMoveAimBtnBg(c, r);
		c.drawBitmap(upDownBtn, m, null);
		setMoveAimTouchArea(c, x, y, r);
	}
	
	private void setMoveAimTouchArea(Canvas c, float x, float y, float r){
		Paint p = new Paint();
		p.setColor(Color.TRANSPARENT);
		x+=r;
		y+=r;
		c.drawCircle(x, y, r, p);
		checkResult(x, y, r, 1);
	}
	
	private void setFireBtnBg(Canvas c, float r){
		Paint p = new Paint();
		p.setColor(Color.BLUE);
		r*=3.5f;
		p.setAlpha(90);
		c.drawCircle(0, SizeControl.screenHeight, r, p);
	}
	
	private void setFireBtn(Canvas c){
		m = new Matrix();
		m.reset();	
		float r = (fireBtn.getHeight()*SizeControl.tScale)/2;
		float y = SizeControl.screenHeight - (r*3f);
		m.postScale(SizeControl.tScale, SizeControl.tScale);
		m.postTranslate(0, y);
		setFireBtnBg(c, r);
		c.drawBitmap(fireBtn, m, null);
		setFireTouchArea(c, 0, y, r);
	}
	
	private void setFireTouchArea(Canvas c, float x, float y, float r){
		Paint p = new Paint();
		p.setColor(Color.TRANSPARENT);
		x+=r;
		y+=r;
		c.drawCircle(x, y, r, p);
		checkResult(x, y, r, 2);
	}
	
	private void checkResult(float x, float y, float r, int action){		
		if(AddView.xPosClicked>0f&&AddView.yPosClicked>0f){			
			int dx = (int) Math.abs(AddView.xPosClicked-x);
			int dy = (int) Math.abs(AddView.yPosClicked-y);
			int dr = (int) r;			
			if (dx + dy <= r){ 
				btnAction(action);
			}else if (dx > r){
		    }else if (dy > r){
		    }else if ((dx^2 + dy^2) <= (dr^2)){ 
		    	btnAction(action);
		    }else{}
		}else{
			GameControl.moveAim = false;
		}
	}
	
	private void btnAction(int action){
		switch(action){
		case(1):
			GameControl.moveAim = true;
			break;
		case(2):
			GameControl.shotTaken = true;
			break;
		}
	}
	
}
