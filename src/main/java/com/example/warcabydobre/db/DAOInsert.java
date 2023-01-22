package com.example.warcabydobre.db;


/**
 * Interface responsible for inserting objects
 * into data base
 *
 */
public interface DAOInsert {
	/**
	 * Method responsible for inserting
	 * new game into data base.
	 */
	public void insertGame(int gameTypeId);

}
