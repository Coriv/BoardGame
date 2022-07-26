package com.chess.chess;

import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

public class Rock extends Piece {
    public Rock(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }
    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {

        Set<Square> sqSet = new HashSet<>();
        piece.setPieceAxis(piece);
        int r = piece.getX();
        int t = piece.getY();

        int d = 100;
        for (int i = 0; i < 8; i++) {
            Square sqXx = new Square(d, d, d, d, r + i, t, 1);
            Square sqX = new Square(d, d, d, d, r - i, t, 1);
            Square sqYy = new Square(d, d, d, d, r, t + i, 1);
            Square sqY = new Square(d, d, d, d, r, t - i, 1);
            sqSet.add(sqXx);
            sqSet.add(sqX);
            sqSet.add(sqYy);
            sqSet.add(sqY);
        }
        Set<Square> correctedSet = correctSet(sqSet);
        return correctedSet;
    }
}