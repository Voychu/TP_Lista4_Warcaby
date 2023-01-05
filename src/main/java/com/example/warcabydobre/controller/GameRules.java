package com.example.warcabydobre.controller;

import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.Move;

public interface GameRules {
	
	boolean isMoveValid();
	
	public Move tryMove(int oldX, int oldY, int newX, int newY);
	//TODO: sprawdzic waznosc ruchu. Jesli nie, to zwrocic NONE
	//TODO: Jesli dobry, to zwrocic nowe wspolrzedne intowe oraz fakt, czy jest bicie i czy zmienia sie w damke
	
	

}
