package com.example.warcabydobre.model;


public class ModelMove {
    private MovementTypes type;
    private int oldX;
    private int oldY;
    private int newX;
    private int newY;

    public MovementTypes getMovementType(){
        return type;
    }

    private PieceObject pieceObject;

    public PieceObject getPieceObject(){
        return pieceObject;
    }
    
//    public ModelMove(MovementTypes type, PieceObject pieceObject){
//        this.type = type;
//        this.pieceObject = pieceObject;
//    }
    public ModelMove(MovementTypes type){
        //this(type,null);
    	this.type=type;
    }
    
    public ModelMove(MovementTypes type, PieceObject pieceObject, int oldX, int oldY, int newX, int newY){
        this.type = type;
        this.pieceObject = pieceObject;
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }
    
    public ModelMove(MovementTypes type, int oldX, int oldY, int newX, int newY){
        this(type,null,oldX,oldY,newX,newY);
    }
    
    public ModelMove(int oldX, int oldY, int newX, int newY){
        this(null,oldX,oldY,newX,newY);
    }
	/**
	 * @return the oldX
	 */
	public int getOldX() {
		return oldX;
	}
	/**
	 * @return the oldY
	 */
	public int getOldY() {
		return oldY;
	}
	/**
	 * @return the newX
	 */
	public int getNewX() {
		return newX;
	}
	/**
	 * @return the newY
	 */
	public int getNewY() {
		return newY;
	}
	
}
