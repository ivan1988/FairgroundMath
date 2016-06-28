package com.mathsfun.fairgroundmath;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class AddView extends SurfaceView implements SurfaceHolder.Callback{

	public MainThread thread;
	private PageControl pc;
	static float xPosClicked = 0f;
	static float yPosClicked = 0f;
	static boolean actionUp = false;
	static boolean closeThread = false;
	
	Matrix m = new Matrix();
	
	Bitmap backGround;
	
	private Context cntx;
	
	public AddView(Context context) {
		super(context);
		cntx = context;
		closeThread = false;
		backGround = BitmapFactory.decodeResource(context.getResources(), R.drawable.background);
		pc = new PageControl(context);
		getHolder().addCallback(this);
		this.thread = new MainThread(getHolder(), this);
	}

	public void onDraw(Canvas canvas){
		super.onDraw(canvas);	
		addBg(canvas);
		pc.pageSorter(canvas, cntx);
		if(closeThread){
			closeThread();
		}
	}
	
	private void addBg(Canvas canvas){
		m = new Matrix();
		m.reset();
		
		if(backGround==null) return;
		
		if(SizeControl.screenWidth!=0 && SizeControl.screenHeight!=0){
			if(SizeControl.screenWidth>SizeControl.screenHeight){
				float scaleX = (0.00f + SizeControl.screenWidth)/(0.00f + backGround.getWidth())*1.00f;
				float scaleY = (0.00f + SizeControl.screenHeight)/(0.00f + backGround.getHeight())*1.00f;
				m.postTranslate(0, 0);
				m.postScale(scaleX, scaleY);
			}else{
				float s = (0.00f + SizeControl.screenHeight)/(0.00f + backGround.getHeight())*1.00f;
				float x = ((backGround.getWidth()*s)-SizeControl.screenWidth)/2;
				m.postScale(s, s);
				m.postTranslate(-x, 0);
			}
			canvas.drawBitmap(backGround, m, null);
		}
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
    public boolean onTouchEvent (MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN : 
            	xPosClicked = event.getX();
            	yPosClicked = event.getY();
            	actionUp = false;
            	break;
            case MotionEvent.ACTION_UP:
            	xPosClicked = 0;
            	yPosClicked = 0;
            	actionUp = true;
            	break;
               
        }
        return true;
    }

	public void startThread(){
		if(thread.getState() == Thread.State.NEW){
			thread.start();	
		}
	}
	
	public void closeThread(){
		thread.close();
	}
	
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		startThread();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub	
		thread.close();
	}
	
}
