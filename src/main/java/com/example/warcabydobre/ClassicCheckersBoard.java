package com.example.warcabydobre;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.RowConstraints;

public class ClassicCheckersBoard {

    //GridPane, wymiary, zapełnić je WhiteSquare i BlackSquare


    public static GridPane gPane = new GridPane();
    public static Scene CCscene = new Scene(gPane, 640,640);
    final int numCols = 8 ;
    final int numRows = 8 ;


    ClassicCheckersBoard() {

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            gPane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            gPane.getRowConstraints().add(rowConst);
        }

        boolean help = true;
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                if(help) {
                    WhiteSquare wSquare = new WhiteSquare();
                    gPane.add(wSquare.rectangle,y,x);
                }
                else{
                    BlackSquare bSquare = new BlackSquare();
                    gPane.add(bSquare.rectangle,y,x);
                }
            help=!help;
            }
            help=!help;
        }


    }
}
