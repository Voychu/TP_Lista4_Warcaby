package com.example.warcabydobre.db;


/**
* Interface of business logic,
* its methods according to instructions from application
* call methods performing
* operations on data base.
*/
public interface BusinessLogicIF {
	
	/**
	 * Method responsible for inserting
	 * new game into data base.
	 */
	public void insertGame(int gameTypeId);

}
