package com.teamti.timc.util.chunkanimator;


import com.teamti.timc.util.chunkanimator.handler.AnimationHandler;

public class ChunkAnimator
{
	public static ChunkAnimator INSTANCE;
	
	public AnimationHandler animationHandler;
	
	public int mode = 0;
    public int animationDuration = 2000;
    public int easingFunction = 1;

    public boolean disableAroundPlayer = true;
	
	public ChunkAnimator() {
		INSTANCE = this;
	}
	
	public void onStart() {
		animationHandler = new AnimationHandler();
	}
}
