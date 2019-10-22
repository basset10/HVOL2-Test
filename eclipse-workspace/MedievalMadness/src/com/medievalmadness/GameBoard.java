package com.medievalmadness;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import java.util.ArrayList;

import org.newdawn.slick.Color;

import com.medievalmadness.BoardPart;

public class GameBoard {

	public static ArrayList<BoardPart> boardParts = new ArrayList<>();
	
	public static void restart() {
		
		boardParts.clear();
		if(Game.selected_level == 1) {
			boardParts.add(new BoardPart(250, 250, 0, 0, true));
		}
	}
	
	public static void update(float delta){
		//Cycle through and draw all parts in the BoardPart arraylist
		for(BoardPart i : boardParts){
		hvlDrawQuadc(i.getxPos(), i.getyPos(), BoardPart.boardPartSize, BoardPart.boardPartSize, Color.cyan);
		}
		
	}
	
	
}
