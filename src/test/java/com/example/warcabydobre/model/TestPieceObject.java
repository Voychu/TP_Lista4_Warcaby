package com.example.warcabydobre.model;

import com.example.warcabydobre.view.PieceColor;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestPieceObject {

    private int x,y;
    private PieceObject pObject;
    @Test
    public void TestObjectPieceNotQueen(){
        pObject = new PieceObject(x,y, PieceColor.BLACK);
        assertFalse(pObject.isQueen());
    }
    @Test
    public void TestQueenChange(){
        pObject = new PieceObject(x,y,PieceColor.WHITE);
        pObject.setQueen(!pObject.isQueen);
        assertEquals(pObject.isQueen(),true);
    }
}
