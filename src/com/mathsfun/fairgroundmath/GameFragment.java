package com.mathsfun.fairgroundmath;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class GameFragment extends Fragment{
	
	boolean paused = false;
	@SuppressWarnings("unused")
	private AddView av;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.game_fragment, container, false);
		av = new AddView(getActivity().getApplicationContext());
		
		return rootView;
	}
	
	@Override
	public void onResume(){
		super.onResume();
		if(paused){
			Intent i = getActivity().getIntent();
			getActivity().finish();
			startActivity(i);
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		AddView.closeThread = true;
		paused = true;
	}
	
	@Override
	public void onStop(){
		super.onStop();
	}
	
	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}
