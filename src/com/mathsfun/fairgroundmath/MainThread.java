package com.mathsfun.fairgroundmath;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread{
	
	private SurfaceHolder surfaceHolder;
	private AddView av;
	private Canvas canvas;
	
	private boolean stop = false;
	
	public MainThread(SurfaceHolder surfaceHolder, AddView av){
		this.surfaceHolder = surfaceHolder;
		this.av = av;
		canvas= null;
	}
	
	public void run(){
		while(!stop){
			try{
				canvas = surfaceHolder.lockCanvas(null);
				synchronized(surfaceHolder){
					if(stop){
						break;
					}
					av.onDraw(canvas);										
				}
			}finally{
				if(canvas != null){
					surfaceHolder.unlockCanvasAndPost(canvas);
				}
			}
		}
	}
	
	public void close(){
		stop=true;
	}	
}
