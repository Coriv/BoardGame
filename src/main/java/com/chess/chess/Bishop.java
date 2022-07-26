package com.chess.chess;

import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

public class Bishop extends Piece {

    public Bishop(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }
    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
        Set<Square> sqSet = new HashSet<>();
        piece.setPieceAxis(piece);
        int r = piece.getX();
        int t = piece.getY();

        int d = 100;
        for(int i =0; i<8; i++) {
            Square s1 = new Square(d, d, d, d, r+i , t+i,1);
            Square s2 = new Square(d, d, d, d,r+i, t-i, 1);
            Square s3 = new Square(d, d, d, d, r-i, t+i, 1);
            Square s4 = new Square(d, d, d, d, r-i, t-i, 1);

            sqSet.add(s1);
            sqSet.add(s2);
            sqSet.add(s3);
            sqSet.add(s4);

        }
        Set<Square> correctedSet = correctSet(sqSet);
        return correctedSet;
    }
}
