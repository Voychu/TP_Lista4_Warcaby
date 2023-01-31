package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

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
}
