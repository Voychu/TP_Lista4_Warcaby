package com.example.warcabydobre.controller;

public class GameRulesFactory extends AbstractFactory {

	@Override
	GameRules getGameRules(String type) {
		if(type == null){
            return null;
        }

        /*if(type.equalsIgnoreCase("classic")){
            return new ClassicCheckersRules();
        }*/   

        return null;
	}
        
}    


