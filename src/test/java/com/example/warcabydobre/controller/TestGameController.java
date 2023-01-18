package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
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
}
