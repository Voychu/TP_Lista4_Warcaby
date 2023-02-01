package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.*;
import com.example.warcabydobre.view.PieceColor;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class TestSecondCheckersRules {

    @Test
    public void CantCaptureBackwardsTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);

        boardModel.movePieceObject(1,2,3,4);
        boardModel.movePieceObject(4,5,2,3);
        boardModel.isOccupied(3,4);
        assertFalse(secondCheckersRules.isCaptureMove(3,4,1,2));
    }
    @Test
    public void TryMoveNoneTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        ModelMove modelMove = new ModelMove(MovementTypes.NONE,0,0,1,1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        ModelMove modelMove2 = secondCheckersRules.tryMove(0,0,1,1);
        assertEquals(modelMove.getMovementType(),modelMove2.getMovementType());
        ModelMove modelMove3 = secondCheckersRules.tryMove(1,0,0,1);
        assertEquals(modelMove3.getMovementType(),modelMove.getMovementType());
    }
    @Test
    public void TryMoveForwardTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        ModelMove modelMove = new ModelMove(MovementTypes.FORWARD,1,2,0,3);
        ModelMove modelMove2 = secondCheckersRules.tryMove(1,2,0,3);
        assertEquals(modelMove.getMovementType(),modelMove2.getMovementType());
    }


    @Test
    public void TryCaptureTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        ModelMove expectedMove = new ModelMove(MovementTypes.SINGLE_CAPTURE,1,4,3,2);
        boardModel.movePieceObject(0, 5, 1, 4);
        boardModel.movePieceObject(3, 2, 2, 3);
        ModelMove resultMove = secondCheckersRules.tryMove(1,4,3,2);
        MovementTypes expectedType = expectedMove.getMovementType();
        MovementTypes resultType = resultMove.getMovementType();
        assertEquals(expectedType, resultType);
    }
    @Test
    public void TryQueenDiagonalTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[1][2].setQueen(true);
        ModelMove modelMove = new ModelMove(MovementTypes.QUEEN_DIAGONAL,1,2,3,4);
        ModelMove modelMove2 = secondCheckersRules.tryMove(1,2,3,4);
        assertEquals(modelMove,modelMove2);
    }

    @Test
    public void TryQueenCaptureTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[1][2].setQueen(true);
        boardModel.movePieceObject(4,5,3,4);
        ModelMove modelMove = new ModelMove(MovementTypes.QUEEN_CAPTURE,1,2,4,5);
        ModelMove resultMove = secondCheckersRules.tryMove(1,2,4,5);
        MovementTypes expectedType = modelMove.getMovementType();
        MovementTypes resultType = resultMove.getMovementType();
        assertEquals(expectedType, resultType);
    }
    @Test
    public void QueenCantJumpOverOwnPieceToCaptureTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[0][1].setQueen(true);
        boardModel.movePieceObject(4,5,3,4);
        ModelMove modelMove = new ModelMove(MovementTypes.NONE,0,1,4,5);
        ModelMove resultMove = secondCheckersRules.tryMove(0,1,4,5);
        MovementTypes expectedType = modelMove.getMovementType();
        MovementTypes resultType = resultMove.getMovementType();
        assertEquals(expectedType, resultType);
    }

    @Test
    public void CanPlayerMoveAtGameStartTest(){
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        assertTrue(secondCheckersRules.canPlayerMove(PieceColor.WHITE));
        assertTrue(secondCheckersRules.canPlayerMove(PieceColor.BLACK));
    }

    @Test
    public void CanPlayerMoveWithNoPiecesTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        for(int y = 0; y<3; y++)
        {
            for(int x=0; x<8; x++)
            {
                if((x+y)%2 == 1)
                    boardModel.deletePieceObject(x,y);
            }
        }
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);


        assertTrue(!secondCheckersRules.canPlayerMove(PieceColor.BLACK));
        assertTrue(secondCheckersRules.canPlayerMove(PieceColor.WHITE));
    }
    @Test
    public void IsQueenDiagonalMoveTest(){
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        assertFalse(secondCheckersRules.isQueenDiagonalMove(3,0,1,1));
        assertTrue(secondCheckersRules.isQueenDiagonalMove(2,1,3,2));
        assertFalse(secondCheckersRules.isQueenDiagonalMove(2,1,6,7));
    }

    @Test
    public void isCaptureMoveTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        assertFalse(secondCheckersRules.isCaptureMove(1,2,3,4));
        assertFalse(secondCheckersRules.isCaptureMove(1,2,-1,4));

        boardModel.movePieceObject(4,5,2,3);
        assertTrue(secondCheckersRules.isCaptureMove(1,2,3,4));
        boardModel.deletePieceObject(1,2);
        assertFalse(secondCheckersRules.isCaptureMove(1,2,3,4));

        assertFalse(secondCheckersRules.isCaptureMove(7,2,6,4));

        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[7][2].setQueen(true);
        assertFalse(secondCheckersRules.isCaptureMove(7,2,2,7));
        boardModel.deletePieceObject(2,7);
        assertTrue(secondCheckersRules.isCaptureMove(7,2,2,7));

        pieceObjects[6][1].setQueen(true);
        boardModel.deletePieceObject(1,6);
        assertFalse(secondCheckersRules.isCaptureMove(6,1,1,6));
    }

    @Test
    public void PossibleMovesTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        SecondCheckersRules secondCheckersRules = new SecondCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        LinkedList<ModelMove> modelMoves = secondCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),2);
        assertEquals(modelMoves.get(0).getMovementType(),MovementTypes.FORWARD);

        boardModel.movePieceObject(4,5,2,3);
        modelMoves = secondCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),1);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.SINGLE_CAPTURE);

        boardModel.movePieceObject(2,3,4,5);
        pieceObjects[1][2].setQueen(true);
        modelMoves = secondCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),3);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.QUEEN_DIAGONAL);

        boardModel.movePieceObject(4,5,3,4);
        modelMoves = secondCheckersRules.possibleMoves(pieceObjects[1][2]);
        assertEquals(modelMoves.size(),1);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.QUEEN_CAPTURE);
    }
}
