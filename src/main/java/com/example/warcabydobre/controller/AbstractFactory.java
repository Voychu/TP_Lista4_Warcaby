package com.example.warcabydobre.controller;

public abstract class AbstractFactory {
	abstract GameRules getGameRules(String type);

}
