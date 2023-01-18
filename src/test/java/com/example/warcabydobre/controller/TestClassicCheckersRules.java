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
        ModelMove modelMove = new ModelMove(MovementTypes.NONE);
        ClassicCheckersRules classicCheckersRules = new ClassicCheckersRules(boardModel);
        assertEquals(classicCheckersRules.tryMove(0,0,1,1),modelMove);
        assertEquals(classicCheckersRules.tryMove(0,0,1,1).getMovementType(),MovementTypes.NONE);
    }


}
