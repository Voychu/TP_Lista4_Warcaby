package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.Square;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

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
  /* @Test
    public void TryCaptureTest() throws InvalidMoveException, IOException {
        BoardModel boardModel = new BoardModel(1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        GraphicalPiece graphicalPiecesArray[][] = new GraphicalPiece[8][8];
        Square[][] board = new Square[8][8];
        String adress = "fdsa";
        int port = 4444;
        ServerHandler serverHandler = new ServerHandler(adress, port);
        Label turnLabel = new Label();
        Group piecesGroup = new Group();
        GameController gameController = new GameController(boardModel,graphicalPiecesArray,board,
               serverHandler, turnLabel , piecesGroup);
        classicCheckersRules.tryMove(1,2,2,3);
        classicCheckersRules.tryMove(4,5,3,4);
        ModelMove modelMove = new ModelMove(MovementTypes.SINGLE_CAPTURE,2,3,4,5);
        ModelMove modelMove2 = classicCheckersRules.tryMove(2,3,4,5);
        assertEquals(modelMove2.getMovementType(),modelMove.getMovementType());
    } */


}
