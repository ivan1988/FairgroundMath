package com.mathsfun.fairgroundmath;

import java.util.ArrayList;
import java.util.Set;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;

public class SettingsFragment extends Fragment{
	private GameSharedPreferences mPrefs;
	
	private SeekBar volume;
	private SeekBar aimSpeed;
	private SeekBar ballSpeed;
	
	private ArrayList<String> mTargetImgs;
	private ArrayList<String> mBallImgs;
	private ArrayList<String> mAimImgs;
	
	private Button resetBtn;
	private ImageButton homeBtn;
	private String resetAnswer; 
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.settings_fragment,
				container, false);
		mPrefs = new GameSharedPreferences(getActivity().getApplicationContext());
		getSeekBarValue(rootView);
		
		getTargetImgs();
		getAimImgs();
		getBallImgs();
		addResetBtn(rootView);
		addHomeBtn(rootView);
		
		imgView(rootView);	
		return rootView;
	}
	
	private void imgView(View v){
		final LinearLayout targetLayout = (LinearLayout) v.findViewById(R.id.targetImgsLayout);
		final LinearLayout aimLayout = (LinearLayout) v.findViewById(R.id.aimImgsLayout);
		final LinearLayout ballLayout = (LinearLayout) v.findViewById(R.id.ballImgsLayout);

		
		
		for(String ball:mBallImgs){
			LinearLayout ll = new LinearLayout(getActivity());
    		ll.setOrientation(LinearLayout.VERTICAL);
    		ll.setPadding(20, 20, 40, 20);
    		addBallImgs(ll, ball);
    		ballLayout.addView(ll);
		}

		for(String target:mTargetImgs){
			LinearLayout ll = new LinearLayout(getActivity());
    		ll.setOrientation(LinearLayout.VERTICAL);
    		ll.setPadding(20, 20, 40, 20);
    		addTargetImgs(ll, target);
    		targetLayout.addView(ll);
		}
		
		for(String aim:mAimImgs){
			LinearLayout ll = new LinearLayout(getActivity());
    		ll.setOrientation(LinearLayout.VERTICAL);
    		ll.setPadding(20, 20, 40, 20);
    		addAimImgs(ll, aim);
    		aimLayout.addView(ll);
		}
	}

	private void addBallImgs(LinearLayout ll, final String img){
		ImageButton targetImg = new ImageButton(getActivity());
		targetImg.setPadding(2, 2, 2, 2);
		
		int tImg = getActivity().getApplicationContext().getResources().getIdentifier(img, "drawable", getActivity().getApplicationContext().getPackageName());
		targetImg.setImageResource(tImg);
		if(mPrefs.getBallImg().contains(img)){
			targetImg.setBackgroundColor(Color.GREEN);
		}else{
			targetImg.setBackgroundColor(Color.TRANSPARENT);
		}
		targetImg.setOnClickListener(new OnClickListener(){
			public void onClick(View v){		
				mPrefs.save("BALL_IMG", img);
				Intent i = getActivity().getIntent();
				getActivity().finish();
				startActivity(i);
			}
		});
		ll.addView(targetImg);
	}
	
	private void addTargetImgs(LinearLayout ll, final String img){
		ImageButton targetImg = new ImageButton(getActivity());
		targetImg.setPadding(2, 2, 2, 2);
		
		int tImg = getActivity().getApplicationContext().getResources().getIdentifier(img, "drawable", getActivity().getApplicationContext().getPackageName());
		targetImg.setImageResource(tImg);
		
		if(mPrefs.getTargetImg().contains(img)){
			targetImg.setBackgroundColor(Color.GREEN);
		}else{
			targetImg.setBackgroundColor(Color.TRANSPARENT);
		}
		targetImg.setOnClickListener(new OnClickListener(){
			public void onClick(View v){		
				mPrefs.save("TARGET_IMG", img);
				Intent i = getActivity().getIntent();
				getActivity().finish();
				startActivity(i);
			}
		});
		ll.addView(targetImg);
	}
	
	
	
	private void addAimImgs(LinearLayout ll, final String img){
		ImageButton targetImg = new ImageButton(getActivity());
		targetImg.setPadding(2, 2, 2, 2);
		
		int tImg = getActivity().getApplicationContext().getResources().getIdentifier(img, "drawable", getActivity().getApplicationContext().getPackageName());
		targetImg.setImageResource(tImg);
		if(mPrefs.getAimImg().contains(img)){
			targetImg.setBackgroundColor(Color.GREEN);
		}else{
			targetImg.setBackgroundColor(Color.TRANSPARENT);
		}
		targetImg.setOnClickListener(new OnClickListener(){
			public void onClick(View v){		
				mPrefs.save("AIM_IMG", img);
				Intent i = getActivity().getIntent();
				getActivity().finish();
				startActivity(i);
			}
		});
		ll.addView(targetImg);
	}

	private void getSeekBarValue(View v){
		
		int maxAimSpeed = 0;
		int maxVolume = 0;
		int maxBallSpeed = 0;
		int currentAimSpeed = 0;
		int cuurentVolume = 0;
		int currentBallSpeed = 0;
		
		volume = (SeekBar)v.findViewById(R.id.volume_bar);
		maxVolume = Integer.valueOf(mPrefs.getMaxVolume());
		cuurentVolume = Integer.valueOf(mPrefs.getVolume());
		volume.setMax(maxVolume);
		volume.setProgress(cuurentVolume);
		volume.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String p = String.valueOf(progress);
				mPrefs.save("VOLUME", p);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}			
		});
		
		aimSpeed = (SeekBar)v.findViewById(R.id.aim_speed_bar);
		maxAimSpeed = Integer.valueOf(mPrefs.getMaxAimSpeed());
		currentAimSpeed = Integer.valueOf(mPrefs.getAimSpeed());
		aimSpeed.setMax(maxAimSpeed);
		aimSpeed.setProgress(currentAimSpeed);
		aimSpeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String p = String.valueOf(progress);
				mPrefs.save("AIM_SPEED", p);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
		});
		
		ballSpeed = (SeekBar)v.findViewById(R.id.ball_speed_bar);
		currentBallSpeed = Integer.valueOf(mPrefs.getBallSpeed());
		maxBallSpeed = Integer.valueOf(mPrefs.getMaxBallSpeed());
		ballSpeed.setMax(maxBallSpeed);
		ballSpeed.setProgress(currentBallSpeed);
		ballSpeed.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				String p = String.valueOf(progress);
				mPrefs.save("BALL_SPEED", p);
			}
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}			
		});
	}
	
	private ArrayList<String> getTargetImgs(){
		Set<String> targetImgs = mPrefs.getTargetImgs();
		mTargetImgs = new ArrayList<String>();
		if(targetImgs!=null){
			mTargetImgs.addAll(targetImgs);
		}
		return mTargetImgs;
    }
	
	private ArrayList<String> getAimImgs(){
		Set<String> aimImgs = mPrefs.getAimImgs();
		mAimImgs = new ArrayList<String>();
		if(aimImgs!=null){
			mAimImgs.addAll(aimImgs);
		}
		return mAimImgs;
    }
	
	private ArrayList<String> getBallImgs(){
		Set<String> ballImgs = mPrefs.getBallImgs();
		mBallImgs = new ArrayList<String>();
		if(ballImgs!=null){
			mBallImgs.addAll(ballImgs);
		}
		return mBallImgs;
    }
	
	private void addResetBtn(View view){
		resetBtn = (Button) view.findViewById(R.id.resetGame);

		resetBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){		
				resetAlert();
			}
		});
	}
	
	private void addHomeBtn(View view){
		homeBtn = (ImageButton) view.findViewById(R.id.homeBtn);

		homeBtn.setOnClickListener(new OnClickListener(){
			public void onClick(View v){		
				Intent i = new Intent (getActivity(), GameActivity.class);
				startActivity(i);
			}
		});
	}
	
	private void resetAlert(){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("To reset game please input the answer - "
				+"What does 5x5 equal to");

		final EditText input = new EditText(getActivity());
		input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_CLASS_NUMBER);
		builder.setView(input);

		builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { 
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		    	resetAnswer = input.getText().toString();
		    	checkResetAnswer(resetAnswer);
		    }
		});
		builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		    @Override
		    public void onClick(DialogInterface dialog, int which) {
		        dialog.cancel();
		    }
		});
		builder.show();
	}
	
	private void checkResetAnswer(String s){
		if(s.equals("25")){
			mPrefs.clearPrefs();
			Toast.makeText(getActivity().getApplicationContext(), "RESET COMPLETE",Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(getActivity().getApplicationContext(), "WRONG ANSWER",Toast.LENGTH_LONG).show();
		}
	}
}
