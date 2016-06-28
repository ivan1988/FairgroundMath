package com.mathsfun.fairgroundmath;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.os.Vibrator;

public class TargetControl {
	@SuppressWarnings("unused")
	private GameControl gc;
	@SuppressWarnings("unused")
	private SizeControl sizeC;
	GameFragment gameFragment;
	Matrix m = new Matrix();
	private AimControl aimControl;
	private CannonControl cc;
	private LevelSelector ls;
	private static GameSharedPreferences mPrefs;
	
	
	
	private Bitmap targetImg;
	
	public static float xPos = 0f;
	public static float yPos = 0f;
	public static float translateX = 0f;
	public static float translateY = 0f;
	public static float scaleX = 0f;
	public static float scaleY = 0f;
	public static int rotateAngle = 0;
	
	public float speedX = 0;
	private float speedY = 0;
	
	private int rowId = 0;
	private float gameTargetPosY;
	private float gameTargetPosX;
	private int numOfRows = 0;
	
	private float targetWidth;
	private float targetHeight;
	private float hTargetWidth;
	private float hTargetHeight;
	
	private int screenHeight;
	
	private float aimWidth;
	private float aimHeight;
	
	private static int rowEffected;
	private static int columnEffected;

	private static int rSpeedLeft;
	private static int rSpeedRight;
	private static int rSpeedCenter;
	
	private float tScale = 0f;

	private static float forceLeft;
	private static float forceLeftY;
	private static float forceCenter;
	private static float forceRight;
	private static float forceRightY;
	
	private static boolean hitLeft;
	private static boolean hitRight;
	private static boolean hitCenter;
	private static boolean audioPlaying;
	
	
	private Vibrator vibrate;
	
	private Context cntx;
	
	private boolean starCountComplete = false;

	private static ArrayList<Integer> columsHit = new ArrayList<Integer>();
	
	private static ArrayList<Float> allEffected = new ArrayList<Float>();
	
	public TargetControl(Context context) {
		mPrefs = new GameSharedPreferences(context);
		gc = new GameControl(context);
		vibrate = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
		sizeC = new SizeControl(context);
		cc = new CannonControl(context);
		ls = new LevelSelector(context);
		ls.checkLevel();
		aimControl = new AimControl(context);
		gameFragment = new GameFragment();
		targetImg = GameControl.targetImg;
		numOfRows = GameControl.numOfRows;
		cntx = context;
		getSizes();
		setPosition();
	}
	
	private void checkAnimation(){
		if(!GameControl.animRunning){
			forceLeft = 0f;
			hitLeft = false;
			hitRight = false;
			hitCenter = false;
			audioPlaying = false;
			forceRightY = 0f;
			forceRight = 0f;
			forceCenter = 0f;
			forceCenter = 0f;
			forceLeftY = 0f;
			forceLeft= 0f;
			rSpeedLeft = 0;
			rSpeedRight = 0;
			rSpeedCenter = 0;
			rowEffected = 0;
			columnEffected = 0;
			columsHit.clear();
			allEffected.clear();
			starCountComplete = false;
		}
	}

	private void setPosition(){
		gameTargetPosY = GameControl.targetStartPosY;
		gameTargetPosX = GameControl.targetStartPosX;
	}
	
	private void getSizes(){
		tScale = SizeControl.tScale;
		aimWidth = SizeControl.aimWidth;
		aimHeight = SizeControl.aimHeight;
		targetWidth = SizeControl.targetWidth;
		targetHeight = SizeControl.targetHeight;
		hTargetWidth = SizeControl.hTargetWidth;
		hTargetHeight = SizeControl.hTargetHeight;
		screenHeight = SizeControl.screenHeight;
		speedX = GameControl.speedX;
		speedY = GameControl.speedY;
	}
	
	public void controlObjects(Canvas c, float x, float y, int rId, int cId){
		checkAnimation();
		GameControl.animRunning = true;
		if(!GameControl.targetHit){
			targetControl(c, x, y, rId, cId, 0);
			if(GameControl.shotTaken){
				collisionDetector(x, y, rId, cId);
				aimControl.aimMovementX(c, 0f, 0f);
			}else{
				aimControl.aimMovementX(c, speedX, speedY);
			}
		}else{
			if(rowEffected==0){
				GameControl.targetHit = false;
				GameControl.shotTaken = false;
				PageControl.gamePage = 1;
			}else{
				affectedAreas(c, x, y, rId, cId);
			}
		}
		cc.addCannon(c, GameControl.aimPosX+SizeControl.hScreenWidth);
	}

	public void addTargets(Canvas canvas){	
		float rowX = gameTargetPosX;
		float targetY  = gameTargetPosY;
		float columnX;

		for(int r = 1; r <= numOfRows; r++){
			rowId = r;
			int columnId = 1;
			if(r != 1){
				targetY+=targetHeight;
				rowX+= hTargetWidth;
				columnX = rowX;
				for(int c = 1; c < r; c++){
					columnX -= targetWidth;
					controlObjects(canvas, columnX, targetY, rowId, c + 1);
				}
			}
			controlObjects(canvas, rowX, targetY, r, columnId);
		}
	}
	
	private void collisionDetector(float x, float y, int rId, int cId){
		float tx = x + targetWidth;
		float ty = y + targetHeight;
		float ax = (GameControl.aimPosX + SizeControl.hScreenWidth) + (aimWidth*0.6f);
		float ay = (GameControl.aimPosY + SizeControl.hScreenHeight) + (aimHeight*0.6f);	

		if(GameControl.aimPosX+SizeControl.hScreenWidth < tx && GameControl.aimPosY+ SizeControl.hScreenHeight < ty && ay > y && ax > x){
			rowEffected = rId;
			columsHit.add(cId);
			if(GameControl.aimPosX+SizeControl.hScreenWidth<SizeControl.hScreenWidth && ax>SizeControl.hScreenWidth){					
				hitCenter = true;
			}else if(GameControl.aimPosX+SizeControl.hScreenWidth<SizeControl.hScreenWidth && ax<SizeControl.hScreenWidth){
				hitLeft = true;
			}else if(ax>SizeControl.hScreenWidth && GameControl.aimPosX+SizeControl.hScreenWidth>SizeControl.hScreenWidth){
				hitRight = true;
			}
		}
	}
	
	private void affectedAreas(Canvas c, float x, float y, int rId, int cId){
		int pos = 0;
		if(columsHit.contains(cId)){
			columnEffected = cId;
		}

		if(rowEffected != 0 && columnEffected != 0){
			if(rId <= rowEffected){
				// center (all)
				if(hitCenter && numOfRows == rowEffected){
					goldStarCount();
					if(rId == rowEffected && cId == columnEffected){
						pos = 2;
					}else if(x+hTargetWidth > gameTargetPosX){
						pos = 3;
					}else{
						pos = 1;
					}
				// right
				}else if(hitRight && (cId <= columnEffected)){
					if(rId == rowEffected && cId == columnEffected){
						pos = 2;
					}else{
						pos = 3;
					}
				// far left
				}else if(hitLeft && rId == cId  && columnEffected == rowEffected){
					if(rId == rowEffected && cId == columnEffected){
						pos = 2;
					}else{
						pos = 1;
					}
				// left
				}else if(hitLeft && rId - cId <=1 && columsHit.contains(rowEffected-1)){
					if(rId == rowEffected && cId == columnEffected){
						pos = 2;
					}else{
						pos = 1;
					}
				// center if there is more rows below	
				}else if(hitCenter && rowEffected < numOfRows){
					if(cId < rowEffected){
						if(rId == rowEffected && cId == columnEffected){
							pos = 2;
						}else if(rId == cId){
							pos = 1;	
						}else{
							pos = 3;
						}
					}else if(rId == 1){
						pos = 2;
					}
				}
			}
		}
		targetControl(c, x, y, rId, cId, pos);
		
		if(pos!=0){
			if(!audioPlaying){
				AudioHandler.playAudio("target_hit");
				audioPlaying=true;
			}
			Vibrate(true);
			scoreCounter(rId, cId);
		}
	}
	
	private void scoreCounter(int rId, int cId){
		float score = Float.valueOf(rId+ "." + cId);
		if(!allEffected.contains(score)){
			allEffected.add(score);
		}
	}

	private void targetControl(Canvas c, float x, float y, int rId, int cId, int pos){
		if(pos==1){
			setMovementLeft(c, x, y, rId);
		}else if(pos==2){
			setMovementCenter(c, x, y, rId);
		}else if(pos==3){
			setMovementRight(c, x, y, rId);
		}else if(pos==0){
			drawTarget(c, x, y, 0, 0, 0, tScale, tScale);
		}
	}
	
	private void goldStarCount(){
		String starCount = mPrefs.getGoldStarCount();
		int count = Integer.valueOf(starCount);
		if(!starCountComplete){
			count++;
			mPrefs.save("GOLD_STAR_COUNT", String.valueOf(count));
			starCountComplete = true;
		}
	}
	
	private void setMovementRight(Canvas c, float x, float y, int rId){
		int speed = 0;
		
		if(rSpeedRight<(rId*20)+220){
			rSpeedRight+=1;
		}
		int s = rSpeedRight - (rId*20)+20;	

		if(s>0){
			speed = s;	
		}
		y+=targetHeight;
		// size
		if(forceRight<hTargetHeight*rId){
			forceRight+=2.5;
		}
		if(forceRightY<screenHeight){
			if(forceRightY>targetHeight*rId){
				forceRightY+=14;
			}else{
				forceRightY+=5;
			}			
		}
		x+=forceRight;
		y+=forceRightY;
		// check if top one is off the screen
		animationFinished(rId, y);

		drawTarget(c, x, y, speed, 0, -targetHeight, tScale, tScale);
	}
	
	private void setMovementLeft(Canvas c, float x, float y, int rId){
		int speed = 0;
		if(rSpeedLeft<((rId*20))+220){
			rSpeedLeft+=1;
		}
		int s = (rSpeedLeft - rId*20)+20;
		if(s>0){
			speed-=s;
		}
		// rotation
		x+=targetWidth;
		y+=targetHeight;
		// size
		if(forceLeft<hTargetHeight*rId){
			forceLeft+=2.5;
		}
		if(forceLeftY<screenHeight){
			if(forceLeftY>targetHeight*rId){
				forceLeftY+=14;
			}else{
				forceLeftY+=5;
			}			
		}		
		y+=forceLeftY;
		x-=forceLeft;
		
		animationFinished(rId, y);
		drawTarget(c, x, y, speed, -targetWidth, -targetHeight, tScale, tScale);
	}
		
	private void setMovementCenter(Canvas c, float x, float y, int rId){
		x+=hTargetWidth;
		y+=hTargetHeight;
		
		// size
		Float s = tScale;
		s -= forceCenter/gameTargetPosX;
		
		if(forceCenter<gameTargetPosX){
			forceCenter+=25;
		}
		y+=forceCenter;

		if((GameControl.aimPosX+hTargetWidth)<gameTargetPosX){
			rSpeedCenter-=50;			
		}else{
			rSpeedCenter+=50;	
		}
		animationFinished(rId, y);
		drawTarget(c, x, y, rSpeedCenter, -hTargetWidth, -hTargetHeight, s, s);
	}
	
	public void drawTarget(Canvas c, float x, float y, int angle, float ptX, float ptY, float scaleX, float scaleY){
		m = new Matrix();
		m.reset();
		m.postTranslate(ptX, ptY);
		m.postRotate(angle);
		m.postScale(scaleX, scaleY);
		m.postTranslate(x, y);
		c.drawBitmap(targetImg, m, null);
	}
	
	private void animationFinished(int rId, float y){

		// check if top one is off the screen
		String starCount = mPrefs.getGoldStarCount();
		String level = mPrefs.getLevel();
		int count = Integer.valueOf(starCount);
		if(rId == 1 && y > ((targetHeight*GameControl.numOfRows)+ SizeControl.hScreenHeight)){
			GameControl.targetHit = false;
			GameControl.shotTaken = false;
			GameControl.animRunning = false;
			int score = 0;
			for(@SuppressWarnings("unused") Float s: allEffected){
				score++;
			}
			GameControl.lastScore = score;
			if(count==5){
				if(level.equals("1")){
					mPrefs.save("GOLD_STAR_COUNT", "0");
					startRewardsActivity();
				}else{
//					LevelSelector.questionCount = 2;
					PageControl.gamePage = 2;
				}
				
			}else{
				PageControl.gamePage = 2;
			}
		}
	}
	
	private void startRewardsActivity(){
		Intent r = new Intent();
		r.setClass(cntx, RewardsActivity.class);
		r.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		AddView.closeThread = true;
		RewardsFragment.addItems = true;
		cntx.startActivity(r);
	}
	
	private void Vibrate(boolean vibrateOn){
    	if(vibrateOn == true){		
			//long pattern[]={150,350};
	        vibrate.vibrate(500);
    	}else{
    		vibrate.cancel();
    	}
    }
}
