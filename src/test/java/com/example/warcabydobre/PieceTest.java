package com.example.warcabydobre;

import com.example.warcabydobre.model.PieceObject;
import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.*;


import javafx.scene.Group;
import org.junit.Test;



import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
        //test delete'a
    }
    @Test
    public void TestObjectPiece(){
        PieceObject pObject = new PieceObject(PieceColor.BLACK,x,y);
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
                } else {
                    BlackSquare bSquare = new BlackSquare(Config.SQUARE_CLASSIC_WIDTH, Config.SQUARE_CLASSIC_HEIGHT);
                    board[i][j] = bSquare;
                }
            }
            int OccupiedTiles = 0;
            for ( j = 0; j < Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES; j++) {
                for ( int i = 0; i < Config.CLASSICAL_CHECKERS_BOARD_WIDTH; i++) {
                    if ((i + j) % 2 == 1) {
                        bPiece = new GraphicalPiece(PieceColor.BLACK, i, j, piecesGroup);
                        board[i][j].setGraphicalPiece(bPiece);
                        OccupiedTiles++;
                        piecesGroup.getChildren().addAll(bPiece);
                    }
                }
            }
            for (j = 5; j < 5 + Config.CLASSICAL_CHECKERS_ROWS_WITH_PIECES; j++) {
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
}