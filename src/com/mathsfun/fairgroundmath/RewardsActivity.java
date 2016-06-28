package com.mathsfun.fairgroundmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class RewardsActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rewards_activity);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new RewardsFragment()).commit();
		}
	}
	
	@Override
	public void onBackPressed() {
		Intent i = new Intent (RewardsActivity.this, GameActivity.class);
		startActivity(i);
	return;
	}
}
