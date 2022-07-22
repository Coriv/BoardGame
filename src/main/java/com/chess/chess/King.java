package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece {
    public King(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }
    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
       Set<Square> sqList = new HashSet<>();
        piece.setPieceAxis(piece);

        for(int x = piece.getX() -1; x< piece.getX()+2; x++) {
           for(int y = piece.getY() -1; y < piece.getY()+2; y++){
               int d = 100;
               Square square = new Square(d,d,d,d,x,y, 1);
               sqList.add(square);
               Square itsSelf = new Square(d, d, d, d, piece.getX(), piece.getY(), 1);
               sqList.remove(itsSelf);
           }
       }
       Set<Square> correctedSet = piece.correctSet(sqList);

       return correctedSet;
    }
}
