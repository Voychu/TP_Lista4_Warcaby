package com.example.warcabydobre.db;


/**
 * Class responsible for inserting objects
 * into data base. It uses Hibernate Framework
 *
 */
public class HibernateInsert implements DAOInsert{

	/**
	 * Constructor of HibernateInsert class
	 */
	public HibernateInsert() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Method responsible for inserting
	 * new game into data base.
	 */
	@Override
	public void insertGame(int gameTypeId) {
		System.out.println("Inserting game into Data Base...");
		System.out.println("gameType: " + Integer.toString(gameTypeId));
		
	}

}
