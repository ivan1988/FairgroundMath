package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class AimControl {
	Matrix m = new Matrix();
	@SuppressWarnings("unused")
	private SizeControl sizeC;
	private BallControl bc;
	@SuppressWarnings("unused")
	private GameControl gc;
	private boolean movingRight = true;
	private boolean movingUp = true;
	private float gameTargetPosY;
	private float gameTargetPosX;
	private float targetHeight;
	
	private float tScale = 0f;
	
	private float aimXpos = 0.0f;
	private float aimYpos = 0.0f;
	
	private int numOfRows = 0;
	
	public float x;
	public float y;
	
	public Bitmap aimImg;
	public float aimHeight;
	public float aimWidth;
	
	public AimControl(Context context){
		sizeC = new SizeControl(context);
		bc = new BallControl(context);
		gc = new GameControl(context);
		assignValues();
	}
	
	private void assignValues(){
		tScale = SizeControl.tScale;
		numOfRows = GameControl.numOfRows;
		numOfRows = GameControl.numOfRows;
		gameTargetPosY = GameControl.targetStartPosY;
		gameTargetPosX = GameControl.targetStartPosX;
		targetHeight = SizeControl.targetHeight;
		aimWidth = SizeControl.aimWidth;
		aimHeight = SizeControl.aimHeight;
		aimImg = GameControl.aimImg;
	}

	public void aimMovementX(Canvas c, float speedX, float speedY){
		aimXpos = GameControl.aimPosX;
		aimYpos = GameControl.aimPosY;
		
		if(aimXpos+SizeControl.hScreenWidth >= (gameTargetPosX + ((SizeControl.hTargetWidth*numOfRows)+(aimWidth/2)))){
			movingRight = false;
		}else if(aimXpos+SizeControl.hScreenWidth <= (gameTargetPosX - ((SizeControl.hTargetWidth*numOfRows)+(aimWidth/2)))){
			movingRight = true;
		}
		// move aim left to right
		if(this.movingRight){
			aimXpos += speedX;
		}else{
			aimXpos -= speedX;
		}
		// move the aim up and down
		boolean moveAim = GameControl.moveAim;

		if(aimYpos+SizeControl.hScreenHeight <= gameTargetPosY){
			movingUp=false;
		}else if(aimYpos+SizeControl.hScreenHeight >= gameTargetPosY + ((targetHeight*numOfRows)-aimHeight)){
			movingUp=true;
		}
		
		if(moveAim){
			if(movingUp){
				aimYpos -= speedY;
			}else{
				aimYpos += speedY;
			}
		}

			GameControl.aimPositionY(aimYpos);
			GameControl.aimPositionX(aimXpos); 
			
			if(speedX==0){
				if(!GameControl.targetHit){
					bc.addBall(c, aimXpos+SizeControl.hScreenWidth, aimYpos+SizeControl.hScreenHeight);
				}
			}else{
				addAim(c, aimXpos+SizeControl.hScreenWidth, aimYpos+SizeControl.hScreenHeight);
			}
	}
	
	private void addAim(Canvas c, float x, float y){	
		m = new Matrix();
		m.reset();	
		m.postScale(tScale, tScale);
		m.postTranslate(x, y);
		c.drawBitmap(aimImg, m, null);
	}
}
