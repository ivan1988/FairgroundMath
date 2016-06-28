package com.mathsfun.fairgroundmath;

import android.content.Context;
import android.media.MediaPlayer;

public class AudioHandler {
	private static GameSharedPreferences mPrefs;
	static boolean isPlaying = false;
	private static Context cntx;
	private static int volume;
	static float duration;

	public AudioHandler(Context context) {
		cntx = context;
		mPrefs = new GameSharedPreferences(context);
		volume = Integer.valueOf(mPrefs.getVolume());	
	}
	
	public static void playAudio(String audio){
		MediaPlayer mp = null;
		float v = volume/10f;
		int resID = cntx.getResources().getIdentifier(audio , "raw", cntx.getPackageName());
		mp = MediaPlayer.create(cntx, resID);
		mp.setVolume(v, v);
		mp.start();
		duration = mp.getDuration();
	}
}
