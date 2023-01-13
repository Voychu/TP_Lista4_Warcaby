package com.example.warcabydobre;

import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.*;


import javafx.scene.Group;
import org.junit.Test;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;



public class PieceTest {

    private Group piecesGroup = new Group();
    final private int x = 4;
    final private int y = 3;


    @Test
    public void TestGraphicPiece() {

        GraphicalPiece gPiece = new GraphicalPiece(PieceColor.BLACK, x, y, piecesGroup);
        assertEquals(gPiece.getColor(), PieceColor.BLACK);
        piecesGroup.getChildren().addAll(gPiece);
        BlackSquare tile = new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
        tile.setGraphicalPiece(gPiece);
        assertTrue(tile.isOccupied());
        int newX=8;
        int newY=0;
        gPiece.move(newX,newY);
        assertEquals(gPiece.getLayoutX(),newX*Config.SQUARE_CLASSIC_WIDTH,0);
        assertEquals(gPiece.getLayoutY(),newY*Config.CLASSICAL_CHECKERS_BOARD_HEIGHT,0);
    }
    @Test
    public void TestObjectPieceNotQueen(){
        PieceObject pObject = new PieceObject(x,y, PieceColor.BLACK);
        assertFalse(pObject.isQueen());
    }


    @Test
    public void TestNumberOfPieces() {
        GraphicalPiece wPiece;
        GraphicalPiece bPiece;
        Square[][] board = new Square[Config.CLASSICAL_CHECKERS_BOARD_WIDTH][Config.CLASSICAL_CHECKERS_BOARD_HEIGHT];

        for (int j = 0; j < Config.CLASSICAL_CHECKERS_BOARD_HEIGHT; j++) {
            for (int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; i++) {

                if ((i + j) % 2 == 0) {
                    WhiteSquare wSquare = new WhiteSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    board[i][j] = wSquare;
                    System.out.println(i + " " + j);
                } else {
                    BlackSquare bSquare = new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    board[i][j] = bSquare;
                    System.out.println(i + " " + j);
                }
            }
        }    
            int OccupiedTiles = 0;
            for (int j = 0; j < Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES; j++) {
                for ( int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; i++) {
                    if ((i + j) % 2 == 1) {
                        bPiece = new GraphicalPiece(PieceColor.BLACK, i, j, piecesGroup);
                        board[i][j].setGraphicalPiece(bPiece);
                        OccupiedTiles++;
                        piecesGroup.getChildren().addAll(bPiece);
                    }
                }
            }
            for (int j = 5; j < 5 + Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES; j++) {
                for (int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; i++) {
                    if ((i + j) % 2 == 1) {
                        wPiece = new GraphicalPiece(PieceColor.WHITE, i, j, piecesGroup);
                        board[i][j].setGraphicalPiece(wPiece);
                        OccupiedTiles++;
                        piecesGroup.getChildren().addAll(wPiece);
                    }
                }
            }
            assertEquals(OccupiedTiles, 24);
        }
}