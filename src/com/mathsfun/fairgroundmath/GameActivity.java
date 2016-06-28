package com.mathsfun.fairgroundmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class GameActivity extends FragmentActivity {
	SetGameAttributes setGameAttributes;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setGameAttributes = new SetGameAttributes(getApplicationContext());
		setContentView(R.layout.game_activity);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction().add(R.id.container, new GameFragment()).commit();
		}
	}

	@Override
	public void onResume(){
		super.onResume();
	}
	
	@Override
	public void onPause(){
		super.onPause();
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent (GameActivity.this, GameActivity.class);
		startActivity(i);
	return;
	}
}
