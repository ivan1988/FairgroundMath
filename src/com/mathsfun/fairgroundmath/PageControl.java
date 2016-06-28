package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.graphics.Canvas;

public class PageControl {
	@SuppressWarnings("unused")
	private AudioHandler audioHandler;
	private LevelSelector levelSelector;
	private LessonView lessonView;
	private TargetControl targetCon;
	private GameScreenButtons gameScreenButtons;
	private RewardsIndicator rewardsInd;
	
	static int gamePage = 1;
	
	public PageControl(Context context) {
		audioHandler  = new AudioHandler(context);
		rewardsInd = new RewardsIndicator(context);
		levelSelector = new LevelSelector(context);
		levelSelector.checkLevel();
	}
	
	public void pageSorter(Canvas canvas, Context context){
		// home (game screen)
		switch(gamePage){
		case(1):	
			homePage(canvas, context);
			break;
		// lesson
		case(2):
			lessonPage(canvas, context);
			break;
		}
		rewardsInd.starControl(canvas);
	}
	
	private void homePage(Canvas canvas, Context context){
		if(targetCon!=null || gameScreenButtons!=null){
			targetCon.addTargets(canvas);
			gameScreenButtons.addButtons(canvas);
		}else{
			targetCon = new TargetControl(context);
			gameScreenButtons = new GameScreenButtons(context);
		}
		
	}
	
	private void lessonPage(Canvas canvas, Context context){
		if(lessonView!=null){
			lessonView.targetPosition(canvas);
		}else{
			lessonView = new LessonView(context);
		}
		
	}
}
