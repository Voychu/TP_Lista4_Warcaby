package com.example.warcabydobre.model;

import com.example.warcabydobre.utils.AbstractPiece;


/**
 * Class representing move on the model.
 * 
 * @author Wojciech Bajurny
 */
public class ModelMove {

	/** The type of the move */
	private MovementTypes type;

	/**
	 * x coordinate of the initial pieceObject's position
	 */
	private int oldX;

	/**
	 * y coordinate of the initial pieceObject's position
	 */
	private int oldY;

	/**
	 * x coordinate of the field where pieceObject is moving
	 */
	private int newX;

	/**
	 * y coordinate of the field where pieceObject is moving
	 */
	private int newY;

	/** The pieceObject on which move is performed */
	private AbstractPiece pieceObject;

	/**
	 * Gets the movement type.
	 *
	 * @return the movement type
	 */
	public MovementTypes getMovementType() {
		return type;
	}

	/**
	 * Gets the piece object.
	 *
	 * @return the piece object
	 */
	public AbstractPiece getPieceObject() {
		return pieceObject;
	}
	
	
	/**
	 * Gets the old X.
	 *
	 * @return the oldX
	 */
	public int getOldX() {
		return oldX;
	}

	/**
	 * Gets the old Y.
	 *
	 * @return the oldY
	 */
	public int getOldY() {
		return oldY;
	}

	/**
	 * Gets the new X.
	 *
	 * @return the newX
	 */
	public int getNewX() {
		return newX;
	}

	/**
	 * Gets the new Y.
	 *
	 * @return the newY
	 */
	public int getNewY() {
		return newY;
	}

	/**
	 * Constructor of ModelMove
	 *
	 * @param type type of the move
	 */
	public ModelMove(MovementTypes type) {
		this.type = type;
	}

	/**
	 * Constructor of ModelMove
	 *
	 * @param type        the type of the move
	 * @param pieceObject the pieceObject on which move is performed
	 * @param oldX        x coordinate of the initial pieceObject's position
	 * @param oldY        y coordinate of the initial pieceObject's position
	 * @param newX        x coordinate of the field where pieceObject is moving
	 * @param newY        y coordinate of the field where pieceObject is moving
	 */
	public ModelMove(MovementTypes type, AbstractPiece pieceObject, int oldX, int oldY, int newX, int newY) {
		this.type = type;
		this.pieceObject = pieceObject;
		this.oldX = oldX;
		this.oldY = oldY;
		this.newX = newX;
		this.newY = newY;
	}

	/**
	 * Constructor of ModelMove
	 *
	 * @param type        the type of the move
	 * @param oldX        x coordinate of the initial pieceObject's position
	 * @param oldY        y coordinate of the initial pieceObject's position
	 * @param newX        x coordinate of the field where pieceObject is moving
	 * @param newY        y coordinate of the field where pieceObject is moving
	 */
	public ModelMove(MovementTypes type, int oldX, int oldY, int newX, int newY) {
		this(type, null, oldX, oldY, newX, newY);
	}

	/**
	 * Constructor of ModelMove
	 *
	 * @param oldX        x coordinate of the initial pieceObject's position
	 * @param oldY        y coordinate of the initial pieceObject's position
	 * @param newX        x coordinate of the field where pieceObject is moving
	 * @param newY        y coordinate of the field where pieceObject is moving
	 */
	public ModelMove(int oldX, int oldY, int newX, int newY) {
		this(null, oldX, oldY, newX, newY);
	}

	

	/**
	 * Method checking if move is equal to other move
	 *
	 * @param obj the obj
	 * @return true, if obj is move with the same
	 * coordinates
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null || obj.getClass() != this.getClass())
			return false;
		return (this.oldX == ((ModelMove) obj).getOldX()) && (this.oldY == ((ModelMove) obj).getOldY())
				&& (this.newX == ((ModelMove) obj).getNewX()) && (this.newY == ((ModelMove) obj).getNewY());
	}

}
