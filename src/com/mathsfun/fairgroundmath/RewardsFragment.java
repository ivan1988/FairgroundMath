package com.mathsfun.fairgroundmath;

import java.util.ArrayList;
import java.util.Set;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RewardsFragment extends Fragment{

	private ArrayList<String> mRewardImgs;
	private ArrayList<String> mAwardedImgs;
	private DisplayMetrics metrics;
	private GameSharedPreferences mPrefs;
	private int screenWidth;
	private int screenHeight;
	private int rowCount;
	private boolean rowCountSet = false;
	private int xPos= 0;
	private int yPos = 0;
	static boolean addItems = false;
	private ImageButton homeBtn;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.rewards_fragment,
				container, false);
		mPrefs = new GameSharedPreferences(getActivity().getApplicationContext());
		getRewardImgs();
		getAwardedImgs();
		metrics = getActivity().getApplicationContext().getResources().getDisplayMetrics();
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		pageControl(rootView);
		addHomeBtn(rootView);
		return rootView;
	}
	
	private void pageControl(View view){
		if(addItems){
			viewControl(view, mRewardImgs);
			updateTitle("Select Your Prize", view);
		}else{
			viewControl(view, mAwardedImgs);
			updateTitle("View Your Prizes", view);
			hideSelectIcon(view);
		}
	}
	
	private void hideSelectIcon(View view){
		ImageView iv = (ImageView) view.findViewById(R.id.icon_instructions);
		iv.setVisibility(View.INVISIBLE);
	}
	
	public void updateTitle(String title, View view) {
	    TextView tv = (TextView) view.findViewById(R.id.rewards_title);
	    tv.setText(title);
	    return;
	}
	
	private void viewControl(View view, ArrayList<String> rImgs){
		final RelativeLayout rewardsLayout = (RelativeLayout) view.findViewById(R.id.rewardsLayout);
		
		
		
		for(String imgs:rImgs){	
			screenManagment(rewardsLayout, imgs);
		}
	}
		
	private void screenManagment(RelativeLayout rl, final String imgs){
		int rowHeight = 0;
		int itemsPerRow = 0;
		float scale = 0;
		int marginX = 0;
		int scaledX = 0;
		int scaledY = 0;
		int bitmapHeight = 0;
		int bitmapWidth = 0;

		int rImg = getActivity().getApplicationContext().getResources().getIdentifier(imgs, "drawable", getActivity().getApplicationContext().getPackageName());
		Bitmap br = BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(), rImg);
		
		bitmapHeight = br.getHeight();
		bitmapWidth = br.getWidth();
		itemsPerRow = 4;
		rowHeight = screenHeight/5;
		scaledY = (int) (rowHeight*0.7f);
		scale = 1.00f / (0.00f + (bitmapHeight) / (0.00f + (bitmapHeight-scaledY)));
		scaledX = bitmapWidth;
		scaledX-=(int)(bitmapWidth*scale);
		marginX = screenWidth/4;
		
		
		if(!rowCountSet){
			rowCount = (itemsPerRow+1);
			xPos-=marginX;
			yPos+=rowHeight;
			rowCountSet = true;
		}
		
		rowCount--;
		xPos+=marginX;
		if(rowCount==0){			
			rowCount = itemsPerRow;
			yPos+=rowHeight;
			xPos=0;
		}
		Bitmap scaledBitmap = Bitmap.createScaledBitmap(br, scaledX, scaledY, true);
		populateView(rl,imgs, xPos, yPos, scaledBitmap);
	}

	private void populateView(RelativeLayout rl, final String imgs, float x, float y, Bitmap b){
		ImageButton rewardImg = new ImageButton(getActivity());
		
		rewardImg.setImageBitmap(b);
		rewardImg.setX(x);
		rewardImg.setY(y);

		if(addItems){
			if(mAwardedImgs.contains(imgs)){
				rewardImg.setAlpha(0.3f);
				rewardImg.setBackgroundColor(Color.TRANSPARENT);
			}else{
				rewardImg.setAlpha(1.0f);
				rewardImg.setBackgroundColor(Color.TRANSPARENT);
			}
			
			rewardImg.setOnClickListener(new OnClickListener(){
				public void onClick(View v){		
					mPrefs.saveStringSet("REWARDS_AWARDED", imgs);
					Intent i = getActivity().getIntent();
					getActivity().finish();
					startActivity(i);
				}
			});
		}else{
			rewardImg.setBackgroundColor(Color.TRANSPARENT);
		}
		rl.addView(rewardImg);
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
	
	private ArrayList<String> getRewardImgs(){
		Set<String> rewardImgs = mPrefs.getRewardsImgs();
		mRewardImgs = new ArrayList<String>();
		if(rewardImgs!=null){
			mRewardImgs.addAll(rewardImgs);
		}
		return mRewardImgs;
    }
	
	private ArrayList<String> getAwardedImgs(){
		Set<String> imgs = mPrefs.getRewardsAwarded();
		mAwardedImgs = new ArrayList<String>();
		if(imgs!=null){
			mAwardedImgs.addAll(imgs);
		}
		return mAwardedImgs;
    }
}
