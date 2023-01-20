package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;


/**
 * Interface of factory, it is
 * responsible for creating GameRules object
 */
public interface RulesFactory {
	
	/**
	 * Method using for getting object of type GameRules
	 * @param gameType String string representing
	 * type of the checkers' game
	 * @param boardModel model of the game
	 * @return rules of game object
	 */
	GameRules getGameRules(String gameType, BoardModel boardModel);

}
