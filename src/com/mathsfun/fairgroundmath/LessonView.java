package com.mathsfun.fairgroundmath;

import java.util.Random;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Log;

public class LessonView{
	
//	private AudioHandler audioHandler;
	private GameSharedPreferences mPrefs;
	@SuppressWarnings("unused")
	private LessonControl lc;
	@SuppressWarnings("unused")
	private SizeControl sc;
	private LevelSelector ls;
	Matrix m = new Matrix();

	private float targetWidth;
	private float targetHeight;
	private float hTargetWidth;
	private float hTargetHeight;
	private int screenWidth;

	private static float objectScale = 1.1f;
	private static int idToEffect = 0;
	private static boolean lastObject = false;
	private static boolean lessonRunning = false;
	private static boolean runLesson = false;
	private static boolean countDone = false;
	private static boolean questionSet = false;
	private static float questionY = 0f;
	private static float questionObjectCount = 0f;
	private static int choiceOne = 0;
	private static int choiceTwo = 0;
	private static int choiceThree = 0;
	private static int correctPos = 0;
	private static float correctPosX = 0f;
	private static float correctPosY = 0f;
	private static float xPosClicked = 0f;
	private static float yPosClicked = 0f;
	private Context cntx;
	
	private String numOfTries;
	private int score = 0;
	private float tScale = 0f;
	
	private Bitmap targetImg;
	private Bitmap questionTargetImg;
	private Bitmap correctImg;
	private Bitmap wrongImg;
	private Bitmap resetButtonImg;
	private Bitmap questionIcon;

	public LessonView(Context context) {
		cntx = context;
//		audioHandler  = new AudioHandler(context);
		mPrefs = new GameSharedPreferences(context);
		ls = new LevelSelector(context);
		lc = new LessonControl(context);
		sc = new SizeControl(context);
		getImgs();
		getSizes();
	}
	
	private void checkAnimation(){
		if(!LessonControl.animRunning){
			objectScale = 1.1f;
			idToEffect = 0;
			lastObject = false;
			countDone = false;
			questionSet = false;
			lessonRunning = false;
			runLesson = false;
			questionY = 0f;
			questionObjectCount = 0f;
			choiceOne = 0;
			choiceTwo = 0;
			choiceThree = 0;
			correctPos = 0;
			correctPosX = 0f;
			correctPosY = 0f;
			xPosClicked = 0f;
			yPosClicked = 0f;
		}
	}
	
	private void getImgs(){
		targetImg = LessonControl.targetImg;
		resetButtonImg = LessonControl.resetButtonImg;
		questionTargetImg = LessonControl.questionTargetImg;
		correctImg = LessonControl.correctImg;
		wrongImg = LessonControl.wrongImg;
		questionIcon = LessonControl.questionIcon;
	}

	private void getSizes(){
		tScale = SizeControl.tScale;
		targetWidth = SizeControl.targetWidth;
		targetHeight = SizeControl.targetHeight;
		hTargetWidth = SizeControl.hTargetWidth;
		hTargetHeight = SizeControl.hTargetHeight;
		screenWidth = SizeControl.screenWidth;
	}
	
	public void targetPosition(Canvas c){
		checkAnimation();
		LessonControl.animRunning = true;
		
		score = GameControl.lastScore;
		float spacePerRow = screenWidth/targetWidth;
		float rowsNeeded =score/spacePerRow;
		
		int rows = (int) rowsNeeded;
		int objectsPerRow = (int) spacePerRow;
		
		if(rows<rowsNeeded){
			rows+=1;
		}
		float y = (SizeControl.screenHeight*0.6f)-(targetHeight*2);
		float x = 0;
		int id = 0;
		for(int s = 1; s<=rows; s++){
			y+=targetHeight;
			if(s==rows){
				int unused = (rows*objectsPerRow)-score;
				float posDifference = (targetWidth*unused)/2; 
				x=((screenWidth-(objectsPerRow*targetWidth))/2)+posDifference;
			}else{
				x=(screenWidth-(objectsPerRow*targetWidth))/2;
			}
			for(int i = 1; i<=objectsPerRow; i++){
				if(id<score){
					id++;
					targetControl(c,x,y,id);
					x+=targetWidth;
				}
			}
		}
	}

	private void targetControl(Canvas c, float x, float y, int id){
		if(idToEffect == 0){
			idToEffect = id;
			Log.d("runLesson", "= " + runLesson);
			Log.d("LevelSelector.questionCount", " = " + LevelSelector.questionCount);
			Log.d("mPrefs.getLevel()", "= " + mPrefs.getLevel());
			if(LevelSelector.questionCount == 2 && mPrefs.getLevel().equals("1")){
				objectScale = 0.7f;
				ls.checkLevel();
				runLesson = true;
			}else if(!mPrefs.getLevel().equals("1") && mPrefs.getGoldStarCount().equals("5")){
				objectScale = 0.7f;
				ls.checkLevel();
				runLesson = true;
			}else{
				playAudio(id);
				objectScale = AudioHandler.duration/1000;
				runLesson = false;
			}
		}
		
		if(y > questionY){
			questionY=y;
		}

		if(!lastObject){
			if(idToEffect>id){
				questionControl(c,x,y,id,tScale);
			}else if(idToEffect == id){
				
				float scale = tScale;
				scale+=0.5f;
				objectScale-=0.01f;
				scale-=objectScale;
				questionControl(c,x,y,id,scale);
				if(objectScale<=0.5f){
					if(score==idToEffect){
						lastObject = true;
					}else{
						idToEffect=0;
					}		
				}
			}
		}else{
			questionControl(c,x,y,id,tScale);
		}
	}

	private void playAudio(int id){
		String number = "zero";
		
		switch (id){
		case(1):
			number = "one";
			break;
		case(2):
			number = "two";		
			break;
		case(3):
			number = "three";
			break;
		case(4):
			number = "four";
			break;
		case(5):
			number = "five";
			break;
		case(6):
			number = "six";
			break;
		case(7):
			number = "seven";
			break;
		case(8):
			number = "eight";
			break;
		case(9):
			number = "nine";
			break;
		case(10):
			number = "ten";
			break;
		case(11):
			number = "eleven";
			break;
		case(12):
			number = "twelve";
			break;
		case(13):
			number = "thirteen";
			break;
		case(14):
			number = "fourteen";
			break;
		case(15):
			number = "fifteen";
			break;
		}
		AudioHandler.playAudio(number);
	}
	
	private void questionControl(Canvas c, float x, float y, int id, float s){
		if(runLesson || lessonRunning){
			if(score>=3){
				questionTargetInst(c,x,y,s);
				if(lastObject){
					if(!questionSet){
						questionNumberSetter();
						LevelSelector.questionCount--;
						numOfTries = mPrefs.getTryCount();
						int tries = Integer.valueOf(numOfTries);
						tries++;
						mPrefs.save("TRY_COUNT", String.valueOf(tries));
						questionSet = true;
					}				
					drawQuestionSetter(c,x,y,id,s);
				}
			}else{
				targetInst(c,x,y,s);
				controlInt(c,x,y,id,s);
				drawResetBtn(c);
			}
		}else{
			if(!countDone){
				LevelSelector.questionCount--;
				countDone = true;
			}
			targetInst(c,x,y,s);
			controlInt(c,x,y,id,s);
			drawResetBtn(c);
		}
	}

	private void questionNumberSetter(){
		lessonRunning = true;
		Random r = new Random();
		int correct = r.nextInt(3-1 +1)+1;

		for(int i=0; i<=3; i++){
			if(correct==1){
				choiceOne = score;
				correctPos = 1;
			}else if(correct==2){
				choiceTwo = score;
				correctPos = 2;
			}else if(correct==3){
				choiceThree = score;
				correctPos = 3;
			}
		}

		for(int i=0; i<=2; i++){
			int random = r.nextInt(score-1)+1;
			if(choiceOne==0){
				choiceOne = random;
			}else if(choiceTwo==0){
				choiceTwo = random;
			}else if(choiceThree==0){
				choiceThree = random;
			}
		}
	}
	
	private void drawQuestionSetter(Canvas c, float x, float y, int id, float s){
		float qy = questionY+(targetHeight+(hTargetHeight/2));
		float qx = GameControl.targetStartPosX - (targetWidth*2);
		drawQuestion(c,qx,qy);
		questionObjectSetter(c,qx,qy+(hTargetHeight/2),id,s);
	}
	
	private void questionObjectSetter(Canvas c, float x, float y, int id, float s){
		questionIcon(c,x,y);
		x+=targetWidth;
		if(questionObjectCount<=3){
			x+=(targetWidth*questionObjectCount);
			questionObjectCount++;
			// set correct position y and x
			if(questionObjectCount==correctPos){
				correctPosY = y;
				correctPosX = x;
			}
			// instantiate number choices 
			if(questionObjectCount==1){
				checkResult(c,x,y,choiceOne,s);
			}else if(questionObjectCount==2){
				checkResult(c,x,y,choiceTwo,s);
			}else if(questionObjectCount==3){
				checkResult(c,x,y,choiceThree,s);
				questionObjectCount=0;
			}
		}
	}
	
	private void checkResult(Canvas c, float x, float y, int n, float s){
		if(xPosClicked==0 && yPosClicked==0){
			if(AddView.xPosClicked>0f&&AddView.yPosClicked>0f){
				if(AddView.xPosClicked<x+targetWidth&&AddView.xPosClicked>x&&AddView.yPosClicked<y+targetHeight&&AddView.yPosClicked>y){
					xPosClicked = x;
					yPosClicked = y;
					if(!mPrefs.getLevel().equals("1")){
						mPrefs.save("GOLD_STAR_COUNT", "0");
					}else{
						LevelSelector.questionCount = 6;
					}
				}
			}
		}
		checkClick(c,x,y,n,s);
	}
	
	private void checkClick(Canvas c, float x, float y, int n, float s){	
		if(correctPosY==yPosClicked && correctPosX==xPosClicked){
			if(xPosClicked==x && yPosClicked==y){
				String currentScore = mPrefs.getCorrectAnswers();
				int i = Integer.valueOf(currentScore);
				i++;
				mPrefs.save("CORRECT_ANSWERS", String.valueOf(i));
				s*=1.2;
				x-=(0.2*targetWidth);
				targetInst(c,x,y,s);
				drawTick(c,x,y,s);
				controlInt(c,x,y,n,s);
				if(!mPrefs.getLevel().equals("1")){
					startRewardsActivity();
					lessonRunning = false;
					runLesson = false;
				}
			}else{
				s*=0.8;
				targetInst(c,x,y,s);
				drawCross(c,x,y,s);
				controlInt(c,x,y,n,s);
			}
			drawResetBtn(c);
		}else if(xPosClicked!=0 && yPosClicked!=0){
			if(xPosClicked==x && yPosClicked==y){
				String currentScore = mPrefs.getWrongAnswers();
				int i = Integer.valueOf(currentScore);
				i++;
				mPrefs.save("WRONG_ANSWERS", String.valueOf(i));
				s*=1.2;
				x-=(0.2*targetWidth);
				targetInst(c,x,y,s);
				drawCross(c,x,y,s);
				controlInt(c,x,y,n,s);
			}else if (correctPosY==y && correctPosX==x){
				s*=0.8;
				targetInst(c,x,y,s);
				drawTick(c,x,y,s);
				controlInt(c,x,y,n,s);
			}else{
				s*=0.8;
				targetInst(c,x,y,s);
				drawCross(c,x,y,s);
				controlInt(c,x,y,n,s);
			}
			drawResetBtn(c);
		}else{
			targetInst(c,x,y,s);
			controlInt(c,x,y,n,s);
		}
	}
	
	private void drawTick(Canvas c, float x, float y, float s){
		m = new Matrix();
		m.reset();
		float width = correctImg.getWidth();
		float height = correctImg.getHeight();
		float sWidth = s*((targetWidth/width)*0.8f);
		float sHeight = s*((targetHeight/height)*0.8f);
		m.postScale(sWidth, sHeight);
		m.postTranslate(x, y);
		c.drawBitmap(correctImg, m, null);
	}
	
	private void drawCross(Canvas c, float x, float y, float s){
		m = new Matrix();
		m.reset();
		float width = wrongImg.getWidth();
		float height = wrongImg.getHeight();
		float sWidth = s*((targetWidth/width)*0.8f);
		float sHeight = s*((targetHeight/height)*0.8f);
		m.postScale(sWidth, sHeight);
		m.postTranslate(x, y);
		c.drawBitmap(wrongImg, m, null);
	}
	
	private void drawQuestion(Canvas c, float x, float y){
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		//s+=0.5f;
		float scale = hTargetWidth;
		//scale*=tScale;
		y+=(scale/3);
		p.setTextSize(scale/1.5f);
		c.drawText("Can you guess how many?", x, y, p);	
	}
	
	private void questionIcon(Canvas c, float x, float y){
		m = new Matrix();
		m.reset();
		m.postScale(tScale, tScale);
		x-=questionIcon.getWidth();
		m.postTranslate(x, y);
		c.drawBitmap(questionIcon, m, null);
	}
	
	private void targetInst(Canvas c, float x, float y, float s){
		m = new Matrix();
		m.reset();
		m.postScale(s, s);
		m.postTranslate(x, y);
		c.drawBitmap(questionTargetImg, m, null);
	}
	
	private void questionTargetInst(Canvas c, float x, float y, float s){
		m = new Matrix();
		m.reset();
		m.postScale(s, s);
		m.postTranslate(x, y);
		c.drawBitmap(targetImg, m, null);
	}
	
	private void controlInt(Canvas c, float x, float y, int id, float s){	
		if(id>9){
			x+=hTargetWidth-(hTargetWidth);
		}else{
			x+=hTargetWidth-(hTargetWidth/2);
		}		
		y+=hTargetHeight+(hTargetHeight/2);
		String idString = String.valueOf(id);
		drawInt(c,x,y,idString, s);
	}
	
	private void drawInt(Canvas c, float x, float y, String id, float s){
		Paint p = new Paint();
		p.setColor(Color.BLACK);
		//s+=0.5f;
		float scale = hTargetWidth;
		//scale*=s;
		p.setTextSize(scale);
		c.drawText(id, x, y, p);
	}
	
	private void drawResetBtn(Canvas c){
		m = new Matrix();
		m.reset();
		m.postScale(tScale, tScale);
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
				LessonControl.animRunning = false;
				PageControl.gamePage = 1;
			}else if (dx > r){
		    }else if (dy > r){
		    }else if ((dx^2 + dy^2) <= (dr^2)){ 
		    	LessonControl.animRunning = false;
				PageControl.gamePage = 1;
		    }else{}
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
}