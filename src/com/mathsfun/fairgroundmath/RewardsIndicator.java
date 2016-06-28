package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;

public class RewardsIndicator {
	
	private static GameSharedPreferences mPrefs;
	Matrix m = new Matrix();
	static Bitmap onStarImg;
	static Bitmap offStarImg;
	static Bitmap settingsImg;
	static Bitmap rewardImg;
	static Bitmap prize_instructions;
	private float starPosX;
	private float goldStarPosX;
	private Context cntx;
	
	public RewardsIndicator(Context context) {
		mPrefs = new GameSharedPreferences(context);	
		getImgs(context);
		cntx = context;
	}
	
	private void getImgs(Context context){
		String prefOnImg = mPrefs.getRewardOnImg();
		String prefOffImg = mPrefs.getRewardOffImg();
		String prefSettingsImg = mPrefs.getSettingsIconImg();
		String prefRewardsImg = mPrefs.getRewardIconImg();

		Resources res = context.getResources();
		int rOn = res.getIdentifier(prefOnImg, "drawable", context.getPackageName());
		int rOff = res.getIdentifier(prefOffImg, "drawable", context.getPackageName());
		int rSettings = res.getIdentifier(prefSettingsImg, "drawable", context.getPackageName());
		int rRewards = res.getIdentifier(prefRewardsImg, "drawable", context.getPackageName());
		
		onStarImg = BitmapFactory.decodeResource(context.getResources(), rOn);
		offStarImg = BitmapFactory.decodeResource(context.getResources(), rOff);
		settingsImg = BitmapFactory.decodeResource(context.getResources(), rSettings);
		rewardImg = BitmapFactory.decodeResource(context.getResources(), rRewards);
		prize_instructions = BitmapFactory.decodeResource(context.getResources(), R.drawable.prize_instructions);
	}
	
	public void starControl(Canvas canvas){
		drawBackGround(canvas);
	}
	
	private void drawBackGround(Canvas c){
		Paint p = new Paint();
		float left = 0;
		float top = 0;
		float right = SizeControl.screenWidth;
		float bottom = SizeControl.hScreenHeight/6;
		
		p.setColor(Color.RED);
		p.setAlpha(0);
		p.setShadowLayer(bottom/2, 0f, 0f, Color.GRAY);

		c.drawRect(left, top, right, bottom, p);
		
		positionButtons(c, bottom);
	}
	
	private void positionButtons(Canvas c, float parentHeight){
		float margin = parentHeight/10;
		float bottom = parentHeight - margin;
		@SuppressWarnings("unused")
		float left = 0;
		
		left = SizeControl.screenWidth-(parentHeight-margin);
		drawSettingsButton(c, margin, bottom);
		
		left = SizeControl.screenWidth-((parentHeight*2)-margin);
		drawRewardsButton(c, margin, bottom);
		positionStars(c, parentHeight, margin);
	}

	private void drawSettingsButton(Canvas c, float y, float bottom){
		m = new Matrix();
		m.reset();
		float imgWidth = settingsImg.getWidth();
		float s = ((bottom-(imgWidth/3))/imgWidth);
		float x = SizeControl.screenWidth - ((imgWidth*s) + y);
		m.postScale(s, s);
		m.postTranslate(x, y);
		c.drawBitmap(settingsImg, m, null);
		drawSettingsTouchArea(c, x, y, imgWidth, s);
	}
	
	private void drawSettingsTouchArea(Canvas c, float x, float y, float r, float scale){
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setAlpha(0);
		r*=scale;
		r/=2;
		x+=r;
		y+=r;
		c.drawCircle(x, y, r, p);
		checkClick(x, y, r, 2);
	}
	
	private void drawRewardsButton(Canvas c, float y, float bottom){
		m = new Matrix();
		m.reset();
		float imgWidth = rewardImg.getWidth();
		float s = ((bottom-(imgWidth/3))/imgWidth);
		float x = SizeControl.screenWidth - (((imgWidth*2)*s) + (y*2));
		m.postScale(s, s);
		m.postTranslate(x, y);
		c.drawBitmap(rewardImg, m, null);
		drawRewardsTouchArea(c, x, y, imgWidth, s);
	}
	
	private void drawRewardsTouchArea(Canvas c, float x, float y, float r, float scale){
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setAlpha(0);
		r*=scale;
		r/=2;
		x+=r;
		y+=r;
		c.drawCircle(x, y, r, p);
		checkClick(x, y, r, 1);
	}
	
	private void drawPrizeInstructions(Canvas c, float x, float y, float scale){
		m = new Matrix();
		m.reset();
		m.postScale(scale, scale);
		m.postTranslate(x, y);
		c.drawBitmap(prize_instructions, m, null);
	}
	
	private void positionStars(Canvas canvas, float parentHeight, float margin){
		float starWidth = onStarImg.getWidth();
		float prizeHeight = prize_instructions.getHeight();
		float prizeWidth = prize_instructions.getWidth();
		float scale = 0;

		float spaceAvailable = SizeControl.screenWidth - (parentHeight*2);
		
		
		float scaleX = (spaceAvailable/(((starWidth*5)+margin*8)+(prizeWidth)));
		float scaleY = (parentHeight/(prizeHeight+(margin*2)));

		if(scaleY<scaleX){
			scale = scaleY;
		}else{
			scale = scaleX;
		}
		
		float spaceNeeded = (starWidth*scale);
		starPosX=(prizeWidth*scale);
		starPosX-=(spaceNeeded/2);
		
		for(int i=1; i<=5; i++){
			starPosX+=spaceNeeded;
			drawOffStar(canvas, starPosX, margin, scale);
		}
		drawPrizeInstructions(canvas, margin, margin, scale);
		
		goldStarControl(canvas, spaceNeeded, margin, scale, prizeWidth);
	}

	private void drawOffStar(Canvas c, float x, float y, float scale){
		m = new Matrix();
		m.reset();
		m.postScale(scale, scale);
		m.postTranslate(x, y);
		c.drawBitmap(offStarImg, m, null);
	}
	
	private void goldStarControl(Canvas c, float spaceNeeded, float margin, float scale, float prizeWidth){
		String starCount = mPrefs.getGoldStarCount();
		int count = Integer.valueOf(starCount);
		goldStarPosX=prizeWidth*scale;
		goldStarPosX-=(spaceNeeded/2);
		for(int i=1; i<=count; i++){
			goldStarPosX+=spaceNeeded;
			drawOnStar(c, goldStarPosX, margin, scale);
		}
	}

	private void drawOnStar(Canvas c, float x, float y, float scale){
		m = new Matrix();
		m.reset();
		m.postScale(scale, scale);
		m.postTranslate(x, y);
		c.drawBitmap(onStarImg, m, null);
	}
	
	private void checkClick(float x, float y, float r, int action){		
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
		}
	}
	
	private void btnAction(int action){
		switch(action){
		case(1):
			Intent r = new Intent();
			r.setClass(cntx, RewardsActivity.class);
			r.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			cntx.startActivity(r);
			RewardsFragment.addItems = false;
			AddView.closeThread = true;
			break;
		case(2):
			Intent s = new Intent();
			s.setClass(cntx, SettingsActivity.class);
			s.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			cntx.startActivity(s);
			AddView.closeThread = true;
			break;
		}
	}
}
