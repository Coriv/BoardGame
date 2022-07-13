package com.chess.chess;

import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

public class Point extends Piece {

    private boolean firstMove = true;
    public Point(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }

    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
        Set<Square> sqSet = new HashSet<>();
        piece.setPieceAxis(piece);
        int r = piece.getX();
        int t = piece.getY();
        int d = 100;
        if (!piece.isWhite()) {
            Square s1 = new Square(d, d, d, d, r, t + 1, 1);
            sqSet.add(s1);
            if (firstMove) {
                Square s2 = new Square(d, d, d, d, r, t + 2, 1);
                sqSet.add(s2);
                firstMove = false;
            }
        } else {
            Square s1 = new Square(d, d, d, d, r, t - 1, 1);
            sqSet.add(s1);
            if (firstMove) {
                Square s2 = new Square(d, d, d, d, r, t - 2, 1);
                sqSet.add(s2);
                firstMove = false;
            }
        }
        return correctSet(sqSet);
    }
}
