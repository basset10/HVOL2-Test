package com.medievalmadness;

public class Player {
	
	private float xPos;
	private float yPos;
	private boolean playerSelected = false;
	private static int moveCooldown = 0;
	public static final int PLAYER_TYPE_SWORD = 1;
	public static final int PLAYER_TYPE_SPEAR = 2;
	public static final int PLAYER_TYPE_ARCHER = 3;
	
	public float getxPos() {
		return xPos;
	}

	public void setxPos(float xPos) {
		this.xPos = xPos;
	}

	public float getyPos() {
		return yPos;
	}

	public void setyPos(float yPos) {
		this.yPos = yPos;
	}
	
	
	
	
}
