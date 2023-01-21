package com.example.warcabydobre.model;

import com.example.warcabydobre.utils.Config;
import com.example.warcabydobre.view.PieceColor;
import org.junit.Test;


import static org.junit.Assert.*;



public class TestBoardModel {

    @Test
    public void TestConstructingBoardModelAndMoving() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        PieceObject pieceObjects[][] = boardModel.getPiecesArray();
        PieceObject pieceObject = pieceObjects[1][2];
        boardModel.movePieceObject(1,2,2,3);
        assertEquals(pieceObjects[1][2],null);
        assertEquals(pieceObjects[2][3],pieceObject);
    }
    @Test(expected = InvalidMoveException.class)
    public void TestInvalidMoving() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        boardModel.movePieceObject(1,0,1,1);
    }
    @Test
    public void TestQueenTransform(){
        BoardModel boardModel = new BoardModel(1);
        PieceObject pieceObjects[][] = boardModel.getPiecesArray();
        pieceObjects[1][0].setQueen(true);
        boardModel.transformToQueen(3,0);
        assertEquals(pieceObjects[1][0].isQueen,pieceObjects[3][0].isQueen);
    }
    @Test
    public void TestDeleting() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        PieceObject pieceObjects[][] = boardModel.getPiecesArray();
        boardModel.deletePieceObject(1,2);
        assertEquals(pieceObjects[1][2],null);
    }

    @Test(expected = InvalidMoveException.class)
    public void TestDeletingException() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        boardModel.deletePieceObject(0,0);
    }

    @Test
    public void TestToString() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        String msg = boardModel.toString();
        assertEquals(boardModel.toString(),"  b   b   b   b \nb   b   b   b   \n  b   b   b   b \n                \n                \nw   w   w   w   \n  w   w   w   w \nw   w   w   w   \n");
        boardModel.movePieceObject(1,2,2,3);
        assertEquals(boardModel.toString(),"  b   b   b   b \nb   b   b   b   \n      b   b   b \n    b           \n                \nw   w   w   w   \n  w   w   w   w \nw   w   w   w   \n");
    }

    @Test(expected = NotDiagonalException.class)
    public void TestPieceCounterException() throws NotDiagonalException {
        BoardModel boardModel = new BoardModel(1);
        //boardModel.countPiecesBetween(0,0,0,7);
    }
    @Test
    public void TestPieceCounter() throws NotDiagonalException {
        BoardModel boardModel = new BoardModel(1);
        //assertEquals(boardModel.countPiecesBetween(7,0,0,7),4);
    }

}
