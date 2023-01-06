package com.example.warcabydobre;

import com.example.warcabydobre.view.GraphicalPiece;
import com.example.warcabydobre.view.PieceColor;



import org.junit.Test;



import static org.junit.Assert.assertEquals;

public class PieceTest {

    @Test
    public void TestPiece() {
        GraphicalPiece piece = new GraphicalPiece(PieceColor.WHITE,3,5);
        assertEquals(piece.getColor(), PieceColor.WHITE);
    }
}
