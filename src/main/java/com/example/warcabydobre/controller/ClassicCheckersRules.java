package com.example.warcabydobre.controller;

import java.util.LinkedList;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.model.NotDiagonalException;
import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.PieceColor;

/**
 * The class responsible for checking moves according to classic checkers game's
 * rules.
 * 
 */
public class ClassicCheckersRules implements GameRules {

	/** The Constant numCols number of columns in board */
	private static final int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;

	/** The Constant numRows number of rows in board */
	private static final int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;

	/**
	 * Model of the game that stores current pieces' positions and properties.
	 */
	private BoardModel boardModel;

	/**
	 * Constructor of ClassicCheckersRules class
	 *
	 * @param boardModel model of the game
	 */
	public ClassicCheckersRules(BoardModel boardModel) {
		this.boardModel = boardModel;
	}

	/**
	 * Method returning color of pieces of passed player
	 * 
	 * @param player number of player in the game
	 * @return color of player's pieces
	 * @throws InvalidPlayerException the exception thrown when player number is
	 *                                incorrect
	 */
	@Override
	public PieceColor getPieceColor(int player) throws InvalidPlayerException {
		if (player == Config.FIRST) {
			return PieceColor.WHITE;
		}
		if (player == Config.SECOND) {
			return PieceColor.BLACK;
		} else
			throw new InvalidPlayerException("Nieprawidlowy numer gracza");
	}

	/**
	 * Method checking if queen can move to passed field according to classic
	 * checkers' rules.
	 * 
	 * @param oldX x coordinate of initial position of the move
	 * @param oldY y coordinate of initial position of the move
	 * @param newX x coordinate of final position of the move
	 * @param newY y coordinate of final position of the move
	 * @return true, if queen can move to the given field on the board
	 */
	public boolean isQueenDiagonalMove(int oldX, int oldY, int newX, int newY) {
		if (!(Math.abs(newX - oldX) == Math.abs(newY - oldY))) {
			return false;
		}
		try {
			int numberOfWhite = boardModel.countPiecesBetween(oldX, oldY, newX, newY, PieceColor.WHITE);
			int numberOfBlack = boardModel.countPiecesBetween(oldX, oldY, newX, newY, PieceColor.BLACK);
			if (numberOfWhite + numberOfBlack <= 1) {
				return true;
			} else {
				return false;
			}
		} catch (NotDiagonalException e) {
			return false;
		}
	}

	// TODO: implmentcja metody
	/**
	 * Method checking whether piece can move
	 * 
	 * @param piece passed piece
	 * @return true, if piece can move
	 */
	private boolean canPieceMove(PieceObject piece) {
		int pieceX = piece.getX();
		int pieceY = piece.getY();
		int possibleX;
		int possibleY;
		if (piece.isQueen()) {

		} else {
			int stepY = piece.getMovementDirection();
			possibleY = pieceY + stepY;
			if (!boardModel.isOccupied(pieceX + 1, possibleY)) {
				return true;
			}
			if (!boardModel.isOccupied(pieceX - 1, possibleY)) {
				return true;
			}
			// if((!boardModel.isOccupied(pieceX - 2, pieceY - 2)) )

		}
		return true;
	}

	
	private LinkedList<ModelMove> possibleQueensCaptures(PieceObject piece){
		int oldX = piece.getX();
		int oldY = piece.getY();
		LinkedList<ModelMove> posMoves = new LinkedList<>();
		if (piece.isQueen()) {
			PieceColor playerColor = piece.getColor();
			PieceColor enemysColor = piece.getOppositeColor();
			if (canPlayerCapture(piece.getColor())) {
				int newX = oldX - 2;
				int newY = oldY - 2;
				int playerPieces = 0;
				int enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX >= 0 && newY >= 0) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						ModelMove captureMove = new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
						posMoves.add(captureMove);
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX--;
					newY--;
				}

				newX = oldX + 2;
				newY = oldY - 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX < numCols && newY >= 0) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						ModelMove captureMove = new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
						posMoves.add(captureMove);
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX++;
					newY--;
				}
				newX = oldX - 2;
				newY = oldY + 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX >= 0 && newY < numRows) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						ModelMove captureMove = new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
						posMoves.add(captureMove);
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX--;
					newY++;
				}
				newX = oldX + 2;
				newY = oldY + 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX < numCols && newY < numRows) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						ModelMove captureMove = new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
						posMoves.add(captureMove);
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY)
							&& boardModel.getPiecesArray()[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX++;
					newY++;
				}

			}

		}
		return posMoves;
	}
	
	//nie bicia
	private LinkedList<ModelMove> possibleQueensMoves(PieceObject piece){
		int oldX = piece.getX();
		int oldY = piece.getY();
		LinkedList<ModelMove> posMoves = new LinkedList<>();
		if (piece.isQueen()) {
			if (!canPlayerCapture(piece.getColor())) {
				int newX = oldX - 1;
				int newY = oldY - 1;
				int counter = 0;
				while (counter < 1 && newX >= 0 && newY >= 0) {
					if(!boardModel.isOccupied(newX, newY)) {
						ModelMove move = new ModelMove(MovementTypes.QUEEN_DIAGONAL, 
								oldX, oldY, newX, newY);
						posMoves.add(move);
					}
					else {
						counter++;
					}
					newX--;
					newY--;
				}

				newX = oldX + 1;
				newY = oldY - 1;
				counter = 0;
				while (counter < 1 && newX < numCols && newY >= 0) {
					if(!boardModel.isOccupied(newX, newY)) {
						ModelMove move = new ModelMove(MovementTypes.QUEEN_DIAGONAL, 
								oldX, oldY, newX, newY);
						posMoves.add(move);
					}
					else {
						counter++;
					}
					newX++;
					newY--;
				}
				newX = oldX - 1;
				newY = oldY + 1;
				counter = 0;
				while (counter < 1 && newX >= 0 && newY < numRows) {
					if(!boardModel.isOccupied(newX, newY)) {
						ModelMove move = new ModelMove(MovementTypes.QUEEN_DIAGONAL, 
								oldX, oldY, newX, newY);
						posMoves.add(move);
					}
					else {
						counter++;
					}
					newX++;
					newY--;
				}
				newX = oldX + 1;
				newY = oldY + 1;
				counter = 0;
				while (counter < 1 && newX < numCols && newY < numRows) {
					if(!boardModel.isOccupied(newX, newY)) {
						ModelMove move = new ModelMove(MovementTypes.QUEEN_DIAGONAL, 
								oldX, oldY, newX, newY);
						posMoves.add(move);
					}
					else {
						counter++;
					}
					newX++;
					newY++;
				}

			}

		}
		return posMoves;
	}
	
	
	public LinkedList<ModelMove> possibleMoves(PieceObject piece) {
		int oldX = piece.getX();
		int oldY = piece.getY();
		LinkedList<ModelMove> posMoves = new LinkedList<>();
		if(piece.isQueen()) {
			LinkedList<ModelMove> posQueensCaptures = possibleQueensCaptures(piece);
			posMoves.addAll(posQueensCaptures);
			LinkedList<ModelMove> posQueensMoves = possibleQueensMoves(piece);
			posMoves.addAll(posQueensMoves);
			
		}
		

		else {
			if (canPlayerCapture(piece.getColor())) {
				if (isCaptureMove(oldX, oldY, oldX - 2, oldY - 2)) {
					PieceObject secondPiece = boardModel.getPiecesArray()[oldX - 1][oldY - 1];
					ModelMove posCapture = new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY,
							oldX - 2, oldY - 2);
					posMoves.add(posCapture);
				}
				if (isCaptureMove(oldX, oldY, oldX - 2, oldY + 2)) {
					PieceObject secondPiece = boardModel.getPiecesArray()[oldX - 1][oldY + 1];
					ModelMove posCapture = new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY,
							oldX - 2, oldY + 2);
					posMoves.add(posCapture);
				}
				if (isCaptureMove(oldX, oldY, oldX + 2, oldY - 2)) {
					PieceObject secondPiece = boardModel.getPiecesArray()[oldX + 1][oldY - 1];
					ModelMove posCapture = new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY,
							oldX + 2, oldY - 2);
					posMoves.add(posCapture);
				}
				if (isCaptureMove(oldX, oldY, oldX + 2, oldY + 2)) {
					PieceObject secondPiece = boardModel.getPiecesArray()[oldX + 1][oldY + 1];
					ModelMove posCapture = new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY,
							oldX + 2, oldY + 2);
					posMoves.add(posCapture);
				}

			} else {
				int stepY = piece.getMovementDirection();
				if (oldX - 1 >= 0 && oldY + stepY >= 0 && oldY + stepY < numRows
						&& !boardModel.isOccupied(oldX - 1, oldY + stepY)) {
					ModelMove posForw = new ModelMove(MovementTypes.FORWARD, oldX, oldY, oldX - 1, oldY + stepY);
					posMoves.add(posForw);
				}
				if (oldX + 1 < numCols && oldY + stepY >= 0 && oldY + stepY < numRows
						&& !boardModel.isOccupied(oldX + 1, oldY + stepY)) {
					ModelMove posForw = new ModelMove(MovementTypes.FORWARD, oldX, oldY, oldX + 1, oldY + stepY);
					posMoves.add(posForw);
				}

			}
		}
		return posMoves;

	}

	/**
	 * Method checking whether player playing with pieces of passed color can move
	 * 
	 * @param color color of pieces of checked player
	 * @return true, if player can move
	 */
	@Override
	public boolean canPlayerMove(PieceColor color) {
		int numCols = Config.CLASSICAL_CHECKERS_BOARD_WIDTH;
		int numRows = Config.CLASSICAL_CHECKERS_BOARD_HEIGHT;
		PieceObject[][] piecesArray = boardModel.getPiecesArray();
		for (int y = 0; y < numRows; y++) {
			for (int x = 0; x < numCols; x++) {
				if (boardModel.isOccupied(x, y)) {
					PieceObject currentPiece = piecesArray[x][y];
					if (currentPiece.getColor() == color &&
							possibleMoves(currentPiece) != null) {
						return true;
					}
				}
			}
		}
		return false;

	}

	@Override
	public boolean playerWin(PieceColor color) {
		PieceColor oppositeColor;
		if (color == PieceColor.WHITE) {
			oppositeColor = PieceColor.BLACK;
		} else {
			oppositeColor = PieceColor.WHITE;
		}
		return !canPlayerMove(oppositeColor);
	}

	@Override
	public boolean playerLost(PieceColor color) {
		return !canPlayerMove(color);
	}

	private boolean isCaptureMove(int oldX, int oldY, int newX, int newY) {
		if (newX < 0 || newX >= numCols || newY < 0 || newY >= numRows) {
			return false;
		}
		if (oldX < 0 || oldX >= numCols || oldY < 0 || oldY >= numRows) {
			return false;
		}
		PieceObject pieceObject = boardModel.getPiecesArray()[oldX][oldY];
		if (pieceObject == null) {
			return false;
		}
		if (boardModel.isOccupied(newX, newY) || !boardModel.isBlackSquare(newX, newY)) {
			return false;

		}

		if (pieceObject.isQueen() && isQueenDiagonalMove(oldX, oldY, newX, newY)) {
			PieceColor myColor = pieceObject.getColor();
			PieceColor oppositeColor = pieceObject.getOppositeColor();
			int numberOfMyPieces;
			try {
				numberOfMyPieces = boardModel.countPiecesBetween(oldX, oldY, newX, newY, myColor);
				int numberOfEnemysPieces = boardModel.countPiecesBetween(oldX, oldY, newX, newY, oppositeColor);
				if (numberOfEnemysPieces == 1 && numberOfMyPieces == 0) {
					return true;
				}
			} catch (NotDiagonalException ex) {
				System.out.println(ex.getMessage());
			}

			return false;

		}

		if (Math.abs(newX - oldX) != 2 || Math.abs(newY - oldY) != 2) {
			return false;
		}

		int x1 = oldX + (newX - oldX) / 2;
		int y1 = oldY + (newY - oldY) / 2;
		PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];
		if (boardModel.isOccupied(x1, y1) && secondPiece.getColor() != pieceObject.getColor()) {
			return true;
		}

		return false;
	}

	/**
	 * Method returning optimal capture
	 * 
	 * @param color of the player's pieces
	 * @return optimal capture if exists
	 */
	private ModelMove getOptimalCapture(PieceColor color) {
		LinkedList<PieceObject> piecesList = new LinkedList<>();
		PieceObject[][] piecesArray = boardModel.getPiecesArray();
		for (int y = 0; y < numRows; y++) {
			for (int x = 0; x < numCols; x++) {
				PieceObject currPiece = piecesArray[x][y];
				if (boardModel.isOccupied(x, y) && currPiece.getColor() == color) {
					piecesList.add(currPiece);
				}

			}
		}
		for (PieceObject piece : piecesList) {
			int oldX = piece.getX();
			int oldY = piece.getY();
			int newX;
			int newY;
			int x1, y1;
			if (piece.isQueen()) {
				PieceColor playerColor = piece.getColor();
				PieceColor enemysColor = piece.getOppositeColor();
				newX = oldX - 2;
				newY = oldY - 2;
				int playerPieces = 0;
				int enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX >= 0 && newY >= 0) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX--;
					newY--;
				}
				newX = oldX - 2;
				newY = oldY + 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX >= 0 && newY < numRows) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX--;
					newY++;
				}
				newX = oldX + 2;
				newY = oldY - 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX < numCols && newY >= 0) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX++;
					newY--;
				}
				newX = oldX + 2;
				newY = oldY + 2;
				playerPieces = 0;
				enemysPieces = 0;
				while (playerPieces < 1 && enemysPieces < 2 && newX < numCols && newY < numRows) {
					if (this.isCaptureMove(oldX, oldY, newX, newY)) {
						return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == playerColor) {
						playerPieces++;
					}
					if (boardModel.isOccupied(newX, newY) && piecesArray[newX][newY].getColor() == enemysColor) {
						enemysPieces++;
					}
					newX++;
					newY++;
				}
				continue;
			}
			newX = oldX - 2;
			newY = oldY - 2;
			if (newX >= 0 && newY >= 0 && isCaptureMove(oldX, oldY, newX, newY)) {
				x1 = oldX + (newX - oldX) / 2;
				y1 = oldY + (newY - oldY) / 2;
				PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
			newX = oldX - 2;
			newY = oldY + 2;
			if (newX >= 0 && newY < numRows && isCaptureMove(oldX, oldY, newX, newY)) {
				x1 = oldX + (newX - oldX) / 2;
				y1 = oldY + (newY - oldY) / 2;
				PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
			newX = oldX + 2;
			newY = oldY - 2;
			if (newX < numCols && newY >= 0 && isCaptureMove(oldX, oldY, newX, newY)) {
				x1 = oldX + (newX - oldX) / 2;
				y1 = oldY + (newY - oldY) / 2;
				PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
			newX = oldX + 2;
			newY = oldY + 2;
			if (newX < numCols && newY < numRows && isCaptureMove(oldX, oldY, newX, newY)) {
				x1 = oldX + (newX - oldX) / 2;
				y1 = oldY + (newY - oldY) / 2;
				PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
		}
		return new ModelMove(MovementTypes.NONE);

	}

	private boolean canPlayerCapture(PieceColor color) {
		ModelMove optimalCapture = getOptimalCapture(color);
		if (optimalCapture.getMovementType() == MovementTypes.SINGLE_CAPTURE) {
			return true;
		}
		if (optimalCapture.getMovementType() == MovementTypes.QUEEN_CAPTURE) {
			return true;
		}
		return false;
	}

	/**
	 * Method checking if move is valid and if it is valid it returns type of the
	 * move. It operates according to classic checkers game's rules.
	 *
	 * @param oldX x coordinate of initial position of the move
	 * @param oldY y coordinate of initial position of the move
	 * @param newX x coordinate of final position of the move
	 * @param newY y coordinate of final position of the move
	 * @return type of the move
	 * @throws InvalidMoveException the exception thrown when the move is incorrect
	 */
	@Override
	public ModelMove tryMove(int oldX, int oldY, int newX, int newY) throws InvalidMoveException {
		if (boardModel.isOccupied(newX, newY) || !boardModel.isBlackSquare(newX, newY)) {
			return new ModelMove(MovementTypes.NONE);
		}

		PieceObject pieceObject = boardModel.getPiecesArray()[oldX][oldY];
		if (pieceObject == null) {
			throw new InvalidMoveException("Na tym polu nie ma pionka");
		}
		PieceColor oppositeColor = pieceObject.getOppositeColor();
		PieceColor myColor = pieceObject.getColor();

		ModelMove optimalCapture = getOptimalCapture(myColor);

		if (pieceObject.isQueen() && isQueenDiagonalMove(oldX, oldY, newX, newY)) {
			try {
				int numberOfMyPieces = boardModel.countPiecesBetween(oldX, oldY, newX, newY, myColor);
				int numberOfEnemysPieces = boardModel.countPiecesBetween(oldX, oldY, newX, newY, oppositeColor);
				if (numberOfEnemysPieces == 1 && numberOfMyPieces == 0) {
					return new ModelMove(MovementTypes.QUEEN_CAPTURE, oldX, oldY, newX, newY);
				}
				if (numberOfEnemysPieces == 0 && numberOfMyPieces == 0 && !canPlayerCapture(myColor)) {
					return new ModelMove(MovementTypes.QUEEN_DIAGONAL, oldX, oldY, newX, newY);
				}
			} catch (NotDiagonalException e) {
				System.out.println(0);
			}

		}

		if (Math.abs(newX - oldX) == 1 && newY - oldY == pieceObject.getMovementDirection()
				&& !canPlayerCapture(myColor))
			return new ModelMove(MovementTypes.FORWARD, oldX, oldY, newX, newY);
		else if (Math.abs(newX - oldX) == 2 && Math.abs(newY - oldY) == 2) {
			int x1 = oldX + (newX - oldX) / 2;
			int y1 = oldY + (newY - oldY) / 2;

			PieceObject secondPiece = boardModel.getPiecesArray()[x1][y1];

			if (boardModel.isOccupied(x1, y1) && secondPiece.getColor() != pieceObject.getColor()) {
				return new ModelMove(MovementTypes.SINGLE_CAPTURE, secondPiece, oldX, oldY, newX, newY);
			}
		}
		return new ModelMove(MovementTypes.NONE);
	}

}
