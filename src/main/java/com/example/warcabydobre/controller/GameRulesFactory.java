package com.example.warcabydobre.controller;

import com.example.warcabydobre.bot.controller.BotAntiCheckersRules;
import com.example.warcabydobre.bot.controller.BotClassicCheckersRules;
import com.example.warcabydobre.bot.controller.BotSecondCheckersRules;
import com.example.warcabydobre.bot.model.GameData;
import com.example.warcabydobre.model.BoardModel;

/**
 * Class of factory, it is responsible for
 * creating GameRules object
 *
 */
public class GameRulesFactory  {

	/**
	 * Method using for getting object of type GameRules
	 * @param gameType String string representing
	 * type of the checkers' game
	 * @param boardModel model of the game
	 * @return rules of game object
	 */
	public GameRules getGameRules(String gameType, BoardModel boardModel) {
		if(gameType.equalsIgnoreCase("classic")){
            return new ClassicCheckersRules(boardModel);
        }
		if(gameType.equalsIgnoreCase("second")){
            return new SecondCheckersRules(boardModel);
        } 
		if(gameType.equalsIgnoreCase("anti")){
            return new AntiCheckersRules(boardModel);
        }
		else {
			return null;
		}
		
		
	}
	
	
	/**
	 * Method using for getting object of type GameRules
	 * @param gameType String string representing
	 * type of the checkers' game
	 * @param boardModel model of the game
	 * @return rules of game object
	 */
	public GameRules getGameRules(String gameType, GameData gameData) {
		if(gameType.equalsIgnoreCase("classic")){
            return new BotClassicCheckersRules(gameData);
        }
		if(gameType.equalsIgnoreCase("second")){
            return new BotSecondCheckersRules(gameData);
        } 
		if(gameType.equalsIgnoreCase("anti")){
            return new BotAntiCheckersRules(gameData);
        } 

		else return null;
	}
        
}    


