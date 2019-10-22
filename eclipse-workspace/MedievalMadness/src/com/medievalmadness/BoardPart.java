package com.medievalmadness;

public class BoardPart {
	
	public float xPos, yPos;
	public int textureIndex, gridPos;
	public boolean canCollide;
	public static float boardPartSize = 100f;

	public BoardPart(float xPosArg, float yPosArg, int gridPos, float textureIndexArg, boolean canCollideArg) {
		
	}

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

	public int getTextureIndex() {
		return textureIndex;
	}

	public void setTextureIndex(int textureIndex) {
		this.textureIndex = textureIndex;
	}

	public int getGridPos() {
		return gridPos;
	}

	public void setGridPos(int gridPos) {
		this.gridPos = gridPos;
	}

	public boolean getCanCollide() {
		return canCollide;
	}

	public void setCanCollide(boolean canCollide) {
		this.canCollide = canCollide;
	}
	
}
