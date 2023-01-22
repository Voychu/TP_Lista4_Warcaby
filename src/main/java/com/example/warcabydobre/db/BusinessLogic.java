package com.example.warcabydobre.db;


/**
 * Class of business logic,
 * according to instructions from application
 * it calls methods performing
 * operations on data base.
 *
 */
public class BusinessLogic implements BusinessLogicIF{

	/**
	 * Object responsible for
	 * inserting into data base
	 */
	DAOInsert daoInsert;
	
	
	/**
	 * Constructor of business logic.
	 */
	public BusinessLogic() {
		daoInsert = new HibernateInsert();
	}
	
	/**
	 * Method responsible for inserting
	 * new game into data base.
	 */
	@Override
	public void insertGame(int gameTypeId) {
		daoInsert.insertGame(gameTypeId);
	}

}
