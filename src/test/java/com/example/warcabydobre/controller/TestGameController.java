package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import com.example.warcabydobre.srv.Game;
import com.example.warcabydobre.srvhandler.ServerHandler;
import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.Square;
import javafx.scene.Group;
import javafx.scene.control.Label;
import org.junit.Test;

import java.io.IOException;

public class TestGameController {

    @Test
    public void receiveMoveTest() throws IOException {

        BoardModel boardModel = new BoardModel(1);
        GraphicalPiece[][] graphicalPiecesArray = new GraphicalPiece[8][8];
        Square[][] board = new Square[8][8];
        String message = "msg";
        ServerHandler serverHandler = new ServerHandler(message, 4444);
        Label turnLabel = new Label();
        Group piecesGroup = new Group();
        GameController gameController = new GameController(boardModel,graphicalPiecesArray,board,serverHandler,turnLabel,piecesGroup);

    }
    @Test
    public void initGraphicalQueenTest() throws IOException {
        BoardModel boardModel = new BoardModel(1);
        GraphicalPiece[][] graphicalPiecesArray = new GraphicalPiece[8][8];
        Square[][] board = new Square[8][8];
        String message = "localhost";
        ServerHandler serverHandler = new ServerHandler(message, 4444);
        Label turnLabel = new Label();
        Group piecesGroup = new Group();
        GameController gameController = new GameController(boardModel,graphicalPiecesArray,board,serverHandler,turnLabel,piecesGroup);
        gameController.initGraphicalQueen(5,5);
    }
    @Test
    public void ReceiveMoveTest() throws IOException {
        BoardModel boardModel = new BoardModel(1);
        GraphicalPiece[][] graphicalPiecesArray = new GraphicalPiece[8][8];
        Square[][] board = new Square[8][8];
        String message = "move 1 2 2 3";
        ServerHandler serverHandler = new ServerHandler(message, 4444);
        Label turnLabel = new Label();
        Group piecesGroup = new Group();
        GameController gameController = new GameController(boardModel,graphicalPiecesArray,board,serverHandler,turnLabel,piecesGroup);

        gameController.receiveMessage();


    }
}
