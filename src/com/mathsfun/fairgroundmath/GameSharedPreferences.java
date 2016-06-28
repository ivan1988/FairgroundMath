package com.mathsfun.fairgroundmath;

import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class GameSharedPreferences {
	public static final String PREFS_NAME = "APP_PREFS";
	
	public static final String SCORE = "SCORE";
	public static final String WRONG_ANSWERS = "WRONG_ANSWERS";
	public static final String CORRECT_ANSWERS = "CORRECT_ANSWERS";
	public static final String LEVEL = "LEVEL";
	public static final String TRY_COUNT = "TRY_COUNT";
	public static final String START_POS_X = "START_POS_X";
	public static final String START_POS_Y = "START_POS_Y";
	public static final String SCALE = "SCALE";
	public static final String MAX_LEVELS = "MAX_LEVELS";
	public static final String MAX_VOLUME = "MAX_VOLUME";
	public static final String MAX_BALL_SPEED = "MAX_BALL_SPEED";
	public static final String MAX_AIM_SPEED = "MAX_AIM_SPEED";
	public static final String MIN_BALL_SPEED = "MIN_BALL_SPEED";
	public static final String MIN_AIM_SPEED = "MIN_AIM_SPEED";
	public static final String VOLUME = "VOLUME";
	public static final String BALL_SPEED = "BALL_SPEED";
	public static final String AIM_SPEED = "AIM_SPEED";
	public static final String CANNON_IMG = "CANNON_IMG";
	public static final String CORRECT_ANSWER_IMG = "CORRECT_ANSWER_IMG";
	public static final String WRONG_ANSWER_IMG = "WRONG_ANSWER_IMG";
	public static final String BALL_IMG = "BALL_IMG";
	public static final String AIM_IMG = "AIM_IMG";
	public static final String TARGET_IMG = "TARGET_IMG";
	public static final String TARGET_IMGS = "TARGET_IMGS";
	public static final String AIM_IMGS = "AIM_IMGS";
	public static final String BALL_IMGS = "BALL_IMGS";
	public static final String REWARD_ON_IMG = "REWARD_ON_IMG";
	public static final String REWARD_OFF_IMG = "REWARD_OFF_IMG";
	public static final String REWARD_ICON = "REWARD_ICON";
	public static final String SETTINGS_ICON = "SETTINGS_ICON";
	public static final String GOLD_STAR_COUNT = "GOLD_STAR_COUNT";
	public static final String REWARDS_IMGS = "REWARDS_IMGS";
	public static final String REWARDS_AWARDED = "REWARDS_AWARDED";
	
	private String mDefaultScore = "0";
	private String mDefaultWrongAnswers = "0";
	private String mDefaultCorrectAnswers = "0";
	private String mDefaultLevel = "1";
	private String mDefaultTryCount = "0";
	private String mDefaultStartPosX = "0";
	private String mDefaultStartPosY = "0";
	private String mDefaultScale = "0";
	private String mDefaultMaxLevels = "0";
	private String mDefaultMaxVolume = "0";
	private String mDefaultMaxBallSpeed = "0";
	private String mDefaultMaxAimSpeed = "0";
	private String mDefaultMinBallSpeed = "0";
	private String mDefaultMinAimSpeed = "0";
	private String mDefaultVolume = "0";
	private String mDefaultBallSpeed = "0";
	private String mDefaultAimSpeed = "0";
	private String mDefaultCorrectAnswerImg = "0";
	private String mDefaultWrongAnswerImg = "0";
	private String mDefaultCannonImg = "0";
	private String mDefaultBallImg = "0";
	private String mDefaultAimImg = "0";
	private String mDefaultTargetImg = "0";
	private String mDefaultRewardOnImg = "0";
	private String mDefaultRewardOffImg = "0";
	private String mDefaultRewardIconImg = "0";
	private String mDefaultSettingsIconImg = "0";
	private String mDefaultGoldStarCount = "0";
	
	public Context mCntx;
	
	public GameSharedPreferences(Context cntx){
		super();
		mCntx = cntx;
	}
	
	public void save( String prefsKey, String string ) {
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		editor.putString(prefsKey, string);
		editor.commit();
	}
	
	public void saveStringSet(String prefsKey, String string ){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		
		Set<String> sharedStr = new HashSet<String>(sharedPref.getStringSet(prefsKey , new HashSet<String>()));
		sharedStr.add(string);
				
		editor.putStringSet(prefsKey, sharedStr);
		editor.commit();
	}
	
	public Set<String> getRewardsAwarded(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Set<String> stringSet = sharedPref.getStringSet(REWARDS_AWARDED, null);
		return stringSet;
	}
	
	public Set<String> getRewardsImgs(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Set<String> stringSet = sharedPref.getStringSet(REWARDS_IMGS, null);
		return stringSet;
	}
	
	public Set<String> getTargetImgs(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Set<String> stringSet = sharedPref.getStringSet(TARGET_IMGS, null);
		return stringSet;
	}
	
	public Set<String> getAimImgs(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Set<String> stringSet = sharedPref.getStringSet(AIM_IMGS, null);
		return stringSet;
	}
	
	public Set<String> getBallImgs(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Set<String> stringSet = sharedPref.getStringSet(BALL_IMGS, null);
		return stringSet;
	}
	
	public String getGoldStarCount(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(GOLD_STAR_COUNT, null) == null ? mDefaultGoldStarCount : sharedPref.getString(GOLD_STAR_COUNT, null);
		return string;
	}
	
	public String getSettingsIconImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(SETTINGS_ICON, null) == null ? mDefaultSettingsIconImg : sharedPref.getString(SETTINGS_ICON, null);
		return string;
	}
	
	public String getRewardIconImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(REWARD_ICON, null) == null ? mDefaultRewardIconImg : sharedPref.getString(REWARD_ICON, null);
		return string;
	}
	
	public String getRewardOnImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(REWARD_ON_IMG, null) == null ? mDefaultRewardOnImg : sharedPref.getString(REWARD_ON_IMG, null);
		return string;
	}
	
	public String getRewardOffImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(REWARD_OFF_IMG, null) == null ? mDefaultRewardOffImg : sharedPref.getString(REWARD_OFF_IMG, null);
		return string;
	}
	
	public String getTargetImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(TARGET_IMG, null) == null ? mDefaultTargetImg : sharedPref.getString(TARGET_IMG, null);
		return string;
	}
	
	public String getAimImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(AIM_IMG, null) == null ? mDefaultAimImg : sharedPref.getString(AIM_IMG, null);
		return string;
	}
	
	public String getBallImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(BALL_IMG, null) == null ? mDefaultBallImg : sharedPref.getString(BALL_IMG, null);
		return string;
	}
	
	public String getCorrectAnswerImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(CORRECT_ANSWER_IMG, null) == null ? mDefaultCorrectAnswerImg : sharedPref.getString(CORRECT_ANSWER_IMG, null);
		return string;
	}
	
	public String getWrongAnswerImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(WRONG_ANSWER_IMG, null) == null ? mDefaultWrongAnswerImg : sharedPref.getString(WRONG_ANSWER_IMG, null);
		return string;
	}
	
	public String getCannonImg(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(CANNON_IMG, null) == null ? mDefaultCannonImg : sharedPref.getString(CANNON_IMG, null);
		return string;
	}
	
	public String getVolume(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(VOLUME, null) == null ? mDefaultVolume : sharedPref.getString(VOLUME, null);
		return string;
	}
	
	public String getBallSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(BALL_SPEED, null) == null ? mDefaultBallSpeed : sharedPref.getString(BALL_SPEED, null);
		return string;
	}
	
	public String getAimSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(AIM_SPEED, null) == null ? mDefaultAimSpeed : sharedPref.getString(AIM_SPEED, null);
		return string;
	}
	
	public String getMinBallSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MIN_AIM_SPEED, null) == null ? mDefaultMinAimSpeed : sharedPref.getString(MIN_AIM_SPEED, null);
		return string;
	}
	
	public String getMinAimSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MIN_BALL_SPEED, null) == null ? mDefaultMinBallSpeed : sharedPref.getString(MIN_BALL_SPEED, null);
		return string;
	}
	
	public String getMaxVolume(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MAX_VOLUME, null) == null ? mDefaultMaxVolume : sharedPref.getString(MAX_VOLUME, null);
		return string;
	}
	
	public String getMaxBallSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MAX_BALL_SPEED, null) == null ? mDefaultMaxBallSpeed : sharedPref.getString(MAX_BALL_SPEED, null);
		return string;
	}
	
	public String getMaxAimSpeed(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MAX_AIM_SPEED, null) == null ? mDefaultMaxAimSpeed : sharedPref.getString(MAX_AIM_SPEED, null);
		return string;
	}
	
	public String getMaxLevels(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(MAX_LEVELS, null) == null ? mDefaultMaxLevels : sharedPref.getString(MAX_LEVELS, null);
		return string;
	}
	
	public String getScale(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(SCALE, null) == null ? mDefaultScale : sharedPref.getString(SCALE, null);
		return string;
	}
	
	public String getStartPosX(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(START_POS_X, null) == null ? mDefaultStartPosX : sharedPref.getString(START_POS_X, null);
		return string;
	}
	
	public String getStartPosY(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(START_POS_Y, null) == null ? mDefaultStartPosY : sharedPref.getString(START_POS_Y, null);
		return string;
	}
	
	public String getScore(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(SCORE, null) == null ? mDefaultScore : sharedPref.getString(SCORE, null);
		return string;
	}
	
	public String getCorrectAnswers(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(CORRECT_ANSWERS, null) == null ? mDefaultCorrectAnswers : sharedPref.getString(CORRECT_ANSWERS, null);
		return string;
	}
	
	public String getWrongAnswers(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(WRONG_ANSWERS, null) == null ? mDefaultWrongAnswers : sharedPref.getString(WRONG_ANSWERS, null);
		return string;
	}
	
	public String getTryCount(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(TRY_COUNT, null) == null ? mDefaultTryCount : sharedPref.getString(TRY_COUNT, null);
		return string;
	}
	
	public String getLevel(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		String string = sharedPref.getString(LEVEL, null) == null ? mDefaultLevel : sharedPref.getString(LEVEL, null);
		return string;
	}
	
	public void clearPrefs(){
		SharedPreferences sharedPref = mCntx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		Editor editor = sharedPref.edit();
		
		editor.clear();
		editor.commit();
	}
}

