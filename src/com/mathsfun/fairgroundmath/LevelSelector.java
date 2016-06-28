package com.mathsfun.fairgroundmath;

import android.content.Context;

public class LevelSelector {
	private GameSharedPreferences mPrefs;
	static int questionCount = 0;
	private String currentLevel;
	private String wrongAnswers;
	private String correctAnswers;
	private String numOfTries;
	private String maxLevels;
	private boolean levelUpdated = false;
	
	GameControl gc;
	
	public LevelSelector(Context context){
		gc = new GameControl(context);
		mPrefs = new GameSharedPreferences(context);
	}

	public void checkLevel(){
		
		currentLevel = mPrefs.getLevel();
		numOfTries = mPrefs.getTryCount();
		correctAnswers = mPrefs.getCorrectAnswers();
		wrongAnswers = mPrefs.getWrongAnswers();
		maxLevels = mPrefs.getMaxLevels();

		int cLevel = Integer.valueOf(currentLevel);
		int tries = Integer.valueOf(numOfTries);
		int correct = Integer.valueOf(correctAnswers);
		int wrong = Integer.valueOf(wrongAnswers);
		int mLevels = Integer.valueOf(maxLevels);
		
		// check the wrongs against the rights to see if the level needs to go up
		if(cLevel<=mLevels){
			if(tries>=10){
				if(correct>wrong && !levelUpdated){
					cLevel++;
					mPrefs.save("CORRECT_ANSWERS", "0");
					mPrefs.save("WRONG_ANSWERS", "0");
					mPrefs.save("TRY_COUNT", "0");
					mPrefs.save("LEVEL", String.valueOf(cLevel));
					selectLevel(cLevel);
					levelUpdated = true;
				}else{
					selectLevel(cLevel);
				}
			}else{
				selectLevel(cLevel);
			}
		}else{
			selectLevel(cLevel);
		}
	}
	
	private void selectLevel(int level){
		switch(level){
		case(1):
			levelOne();
			break;
		case(2):
			levelTwo();
			break;
		case(3):
			levelThree();
			break;
		}
	}
	
	public void levelOne(){
		gc.numberOfRows(3);
		if(questionCount==0){
			questionCount = 6;
		}
	}
	
	public void levelTwo(){
		gc.numberOfRows(4);
		if(questionCount==0){
			questionCount = 5;
		}
	}

	public void levelThree(){
		gc.numberOfRows(5);
		if(questionCount==0){
			questionCount = 4;
		}
	}
}
