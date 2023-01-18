package com.example.warcabydobre.controller;

import com.example.warcabydobre.model.BoardModel;
import com.example.warcabydobre.model.InvalidMoveException;
import com.example.warcabydobre.model.ModelMove;
import com.example.warcabydobre.model.MovementTypes;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestClassicCheckersRules {

    @Test
    public void TryMoveNoneTest() throws InvalidMoveException {
        BoardModel boardModel = new BoardModel(1);
        ModelMove modelMove = new ModelMove(MovementTypes.NONE,0,0,1,1);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        ModelMove modelMove2 = classicCheckersRules.tryMove(0,0,1,1);
        assertEquals(modelMove.getMovementType(),modelMove2.getMovementType());
    }


}
