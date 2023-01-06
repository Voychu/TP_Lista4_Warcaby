package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;

public interface GameRules {
	
	boolean isMoveValid();
	
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException;
	//TODO: sprawdzic waznosc ruchu. Jesli nie, to zwrocic NONE
	//TODO: Jesli dobry, to zwrocic nowe wspolrzedne intowe oraz fakt, czy jest bicie i czy zmienia sie w damke
	
	

}
