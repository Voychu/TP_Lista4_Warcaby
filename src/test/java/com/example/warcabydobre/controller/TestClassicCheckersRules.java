package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.*;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.PieceColor;
import com.example.warcabydobre.view.Square;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.Test;
import com.example.warcabydobre.controller.ClassicCheckersRules;

import java.io.IOException;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class TestClassicCheckersRules {

    @Test
    public void TryMoveNoneTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        ModelMove modelMove = new ModelMove(MovementTypes.NONE,0,0,1,1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        ModelMove modelMove2 = classicCheckersRules.tryMove(0,0,1,1);
        assertEquals(modelMove.getMovementType(),modelMove2.getMovementType());
        ModelMove modelMove3 = classicCheckersRules.tryMove(1,0,0,1);
        assertEquals(modelMove3.getMovementType(),modelMove.getMovementType());
    }
    @Test
    public void TryMoveForwardTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        ModelMove modelMove = new ModelMove(MovementTypes.FORWARD,1,2,0,3);
        ModelMove modelMove2 = classicCheckersRules.tryMove(1,2,0,3);
        assertEquals(modelMove.getMovementType(),modelMove2.getMovementType());
    }
    
    
    @Test
    public void TryCaptureTest() throws InvalidMoveException{
         BoardModel boardModel = new BoardModel(1);
         ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
         ModelMove expectedMove = new ModelMove(MovementTypes.SINGLE_CAPTURE,1,4,3,2);
         boardModel.movePieceObject(0, 5, 1, 4);
         boardModel.movePieceObject(3, 2, 2, 3);
         ModelMove resultMove = classicCheckersRules.tryMove(1,4,3,2);
         MovementTypes expectedType = expectedMove.getMovementType();
         MovementTypes resultType = resultMove.getMovementType();
         assertEquals(expectedType, resultType);
     }
     @Test
     public void TryQueenDiagonalTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[1][2].setQueen(true);
        ModelMove modelMove = new ModelMove(MovementTypes.QUEEN_DIAGONAL,1,2,3,4);
        ModelMove modelMove2 = classicCheckersRules.tryMove(1,2,3,4);
        assertEquals(modelMove,modelMove2);
     }

     @Test
     public void TryQueenCaptureTest() throws InvalidMoveException{
         BoardModel boardModel = new BoardModel(1);
         ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
         PieceObject[][] pieceObjects = boardModel.getPiecesArray();
         pieceObjects[1][2].setQueen(true);
         boardModel.movePieceObject(4,5,3,4);
         ModelMove modelMove = new ModelMove(MovementTypes.QUEEN_CAPTURE,1,2,4,5);
         ModelMove resultMove = classicCheckersRules.tryMove(1,2,4,5);
         MovementTypes expectedType = modelMove.getMovementType();
         MovementTypes resultType = resultMove.getMovementType();
         assertEquals(expectedType, resultType);
     }
     @Test
    public void QueenCantJumpOverOwnPieceToCaptureTest() throws InvalidMoveException{
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[0][1].setQueen(true);
        boardModel.movePieceObject(4,5,3,4);
        ModelMove modelMove = new ModelMove(MovementTypes.NONE,0,1,4,5);
        ModelMove resultMove = classicCheckersRules.tryMove(0,1,4,5);
        MovementTypes expectedType = modelMove.getMovementType();
        MovementTypes resultType = resultMove.getMovementType();
        assertEquals(expectedType, resultType);
    }

     @Test
    public void CanPlayerMoveAtGameStartTest(){
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        assertTrue(classicCheckersRules.canPlayerMove(PieceColor.WHITE));
        assertTrue(classicCheckersRules.canPlayerMove(PieceColor.BLACK));
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
         ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
         
         
         assertTrue(!classicCheckersRules.canPlayerMove(PieceColor.BLACK));
         assertTrue(classicCheckersRules.canPlayerMove(PieceColor.WHITE));
     }
     @Test
    public void IsQueenDiagonalMoveTest(){
         BoardModel boardModel = new BoardModel(1);
         ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
         assertFalse(classicCheckersRules.isQueenDiagonalMove(3,0,1,1));
         assertTrue(classicCheckersRules.isQueenDiagonalMove(2,1,3,2));
         assertFalse(classicCheckersRules.isQueenDiagonalMove(2,1,6,7));
    }

    @Test
    public void isCaptureMoveTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        assertFalse(classicCheckersRules.isCaptureMove(1,2,3,4));
        assertFalse(classicCheckersRules.isCaptureMove(1,2,-1,4));

        boardModel.movePieceObject(4,5,2,3);
        assertTrue(classicCheckersRules.isCaptureMove(1,2,3,4));
        boardModel.deletePieceObject(1,2);
        assertFalse(classicCheckersRules.isCaptureMove(1,2,3,4));

        assertFalse(classicCheckersRules.isCaptureMove(7,2,6,4));

        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        pieceObjects[7][2].setQueen(true);
        assertFalse(classicCheckersRules.isCaptureMove(7,2,2,7));
        boardModel.deletePieceObject(2,7);
        assertTrue(classicCheckersRules.isCaptureMove(7,2,2,7));

        pieceObjects[6][1].setQueen(true);
        boardModel.deletePieceObject(1,6);
        assertFalse(classicCheckersRules.isCaptureMove(6,1,1,6));
    }

    @Test
    public void PossibleMovesTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        PieceObject[][] pieceObjects = boardModel.getPiecesArray();
        LinkedList<ModelMove> modelMoves = classicCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),2);
        assertEquals(modelMoves.get(0).getMovementType(),MovementTypes.FORWARD);

        boardModel.movePieceObject(4,5,2,3);
        modelMoves = classicCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),1);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.SINGLE_CAPTURE);

        boardModel.movePieceObject(2,3,4,5);
        pieceObjects[1][2].setQueen(true);
        modelMoves = classicCheckersRules.possibleMoves(pieceObjects[1][2]);
        System.out.println(modelMoves);
        assertEquals(modelMoves.size(),3);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.QUEEN_DIAGONAL);

        boardModel.movePieceObject(4,5,3,4);
        modelMoves = classicCheckersRules.possibleMoves(pieceObjects[1][2]);
        assertEquals(modelMoves.size(),1);
        assertEquals(modelMoves.getFirst().getMovementType(),MovementTypes.QUEEN_CAPTURE);
    }

}
