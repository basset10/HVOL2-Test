package com.medievalmadness;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.hvlDrawQuadc;

import org.newdawn.slick.Color;

import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{
	
	public Main() {
		super(144, 1280, 720, "Medieval Madness", new HvlDisplayModeDefault());
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args){
		new Main();
	}

	@Override
	public void initialize() {
		GameBoard.restart();
		
	}
	
	//Display play grid using coordinate-based system
	//Display drawn players manually chosen and positioned based on level_selected
	//Allow players to move, based on player type, a given number of spaces

	@Override
	public void update(float delta) {
		GameBoard.update(delta);	
	}

	
	
}
