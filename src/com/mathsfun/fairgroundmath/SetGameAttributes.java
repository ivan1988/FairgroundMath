package com.mathsfun.fairgroundmath;

import android.content.Context;

public class SetGameAttributes {
	
	private GameSharedPreferences mPrefs;

	public SetGameAttributes(Context context) {
		mPrefs = new GameSharedPreferences(context);
		addAtributes();
		setAttributes();
	}
	
	private void addAtributes(){
		addTargetImgs();
		addAimImgs();
		addBallImgs();
		addCannonImgs();
		addWrongAnswerImg();
		addCorrectAnswerImg();
		addRewardsImgs();
		addHeaderImgs();
	}
	
	private void setAttributes(){
		setStartPosition();
		setScale();
		setLevelCount();
		setMax();
		setMin();
		setDefault();
	}
	
	private void addHeaderImgs(){
		if(mPrefs.getRewardOnImg().contains("0"))
		mPrefs.save("REWARD_ON_IMG", "gold_star");
		if(mPrefs.getRewardOffImg().contains("0"))
		mPrefs.save("REWARD_OFF_IMG", "silver_star");
		if(mPrefs.getRewardIconImg().contains("0"))
		mPrefs.save("REWARD_ICON", "rewards_button");
		if(mPrefs.getSettingsIconImg().contains("0"))
		mPrefs.save("SETTINGS_ICON", "settings_button");
		
	}
	
	private void addRewardsImgs(){
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_one");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_two");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_three");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_four");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_five");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_six");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_seven");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_eight");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_nine");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_ten");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_eleven");
		mPrefs.saveStringSet("REWARDS_IMGS", "teddy_twelve");
	}
	
	private void addWrongAnswerImg(){
		if(mPrefs.getWrongAnswerImg().contains("0"))
		mPrefs.save("WRONG_ANSWER_IMG", "wrong_answer");
	}
	
	private void addCorrectAnswerImg(){
		if(mPrefs.getCorrectAnswerImg().contains("0"))
		mPrefs.save("CORRECT_ANSWER_IMG", "correct_answer");
	}
	
	private void addCannonImgs(){
		if(mPrefs.getCannonImg().contains("0"))
		mPrefs.save("CANNON_IMG", "cannon");
	}
	
	private void addTargetImgs(){
		mPrefs.saveStringSet("TARGET_IMGS", "soda_can");
		mPrefs.saveStringSet("TARGET_IMGS", "bricks");
		mPrefs.saveStringSet("TARGET_IMGS", "milk_bottle");
		mPrefs.saveStringSet("TARGET_IMGS", "poison_bottle");
		mPrefs.saveStringSet("TARGET_IMGS", "wine_glass");
	}
	
	private void addAimImgs(){
		mPrefs.saveStringSet("AIM_IMGS", "aim");
		mPrefs.saveStringSet("AIM_IMGS", "aim_one");
		mPrefs.saveStringSet("AIM_IMGS", "aim_two");
		mPrefs.saveStringSet("AIM_IMGS", "aim_three");
		mPrefs.saveStringSet("AIM_IMGS", "crosshair");
	}
	
	private void addBallImgs(){
		mPrefs.saveStringSet("BALL_IMGS", "basketball");
		mPrefs.saveStringSet("BALL_IMGS", "beach_ball");
		mPrefs.saveStringSet("BALL_IMGS", "football");
		mPrefs.saveStringSet("BALL_IMGS", "tennis_ball");
		mPrefs.saveStringSet("BALL_IMGS", "white_ball");
	}

	private void setDefault(){
		// SettingsFragment.java
		int vol = 9;
		int ball = 5;
		int aim = 1;
		
		//if(mPrefs.getVolume().contains("0"))
		mPrefs.save("VOLUME", String.valueOf(vol));
		if(mPrefs.getBallSpeed().contains("0"))
		mPrefs.save("BALL_SPEED", String.valueOf(ball));
		if(mPrefs.getAimSpeed().contains("0"))
		mPrefs.save("AIM_SPEED", String.valueOf(aim));
		
		// Images
		if(mPrefs.getBallImg().contains("0"))
		mPrefs.save("BALL_IMG", "white_ball");
		if(mPrefs.getAimImg().contains("0"))
		mPrefs.save("AIM_IMG", "aim");
		if(mPrefs.getTargetImg().contains("0"))
		mPrefs.save("TARGET_IMG", "milk_bottle");
	}
	
	private void setStartPosition(){
		// GameControl.java
		float startPosX = 0;
		float startPosY = 0;
		if(mPrefs.getStartPosX().contains("0"))
		mPrefs.save("START_POS_X", String.valueOf(startPosX));
		if(mPrefs.getStartPosY().contains("0"))
		mPrefs.save("START_POS_Y", String.valueOf(startPosY));
	}

	private void setLevelCount(){
		// LevelControl.java
		int l = 3;
		if(mPrefs.getMaxLevels().contains("0"))
		mPrefs.save("MAX_LEVELS", String.valueOf(l));
	}
	
	private void setMax(){
		// SettingsFragment.java
		int vol = 10;
		int ball = 10;
		int aim = 10;
		if(mPrefs.getMaxVolume().contains("0"))
		mPrefs.save("MAX_VOLUME", String.valueOf(vol));
		if(mPrefs.getMaxBallSpeed().contains("0"))
		mPrefs.save("MAX_BALL_SPEED", String.valueOf(ball));
		if(mPrefs.getMaxAimSpeed().contains("0"))
		mPrefs.save("MAX_AIM_SPEED", String.valueOf(aim));
	}
	
	private void setMin(){
		// SettingsFragment.java
		int ball = 3;
		int aim = 1;
		if(mPrefs.getMinBallSpeed().contains("0"))
		mPrefs.save("MIN_BALL_SPEED", String.valueOf(ball));
		if(mPrefs.getMinAimSpeed().contains("0"))
		mPrefs.save("MIN_AIM_SPEED", String.valueOf(aim));
	}
	
	private void setScale(){
		// SizeControl.java
		float s = 1.0f;
		if(mPrefs.getScale().contains("0"))
		mPrefs.save("SCALE", String.valueOf(s));
	}
}
