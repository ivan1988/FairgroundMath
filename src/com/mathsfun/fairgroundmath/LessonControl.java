package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



public class LessonControl {
		
	private static GameSharedPreferences mPrefs;

	static boolean animRunning = false;
	
	static Bitmap targetImg;
	static Bitmap questionTargetImg;
	static Bitmap resetButtonImg;
	static Bitmap correctImg;
	static Bitmap wrongImg;
	static Bitmap questionIcon;

	public LessonControl(Context context) {		
		mPrefs = new GameSharedPreferences(context);	
		getImgs(context);		
	}
	
	private void getImgs(Context context){
		String prefTargetImg = mPrefs.getTargetImg();
		String prefCorrectImg = mPrefs.getCorrectAnswerImg();
		String prefWrongImg = mPrefs.getWrongAnswerImg();
		
		Resources res = context.getResources();
		int tImg = res.getIdentifier(prefTargetImg, "drawable", context.getPackageName());
		int cImg = res.getIdentifier(prefCorrectImg, "drawable", context.getPackageName());
		int wImg = res.getIdentifier(prefWrongImg, "drawable", context.getPackageName());
		
		questionTargetImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.milk_bottle);
		resetButtonImg = BitmapFactory.decodeResource(context.getResources(), R.drawable.reset_button);
		targetImg = BitmapFactory.decodeResource(context.getResources(), tImg);
		correctImg = BitmapFactory.decodeResource(context.getResources(), cImg);
		wrongImg = BitmapFactory.decodeResource(context.getResources(), wImg);
		questionIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.question_instructions);
	}
}
