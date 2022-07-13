package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.*;
import java.util.stream.Collectors;

public class Knight extends Piece {
    public Knight(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }
    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
        Set<Square> sqSet = new HashSet<>();
        piece.setPieceAxis(piece);
        int r = piece.getX();
        int t = piece.getY();
        int d = 100;

        Square sq1 = new Square(d,d,d,d, r-1, t+2,1);
        Square sq2 = new Square(d,d,d,d, r+1, t+2, 1);
        Square sq3 = new Square(d,d,d,d, r+2, t+1, 1);
        Square sq4 = new Square(d,d,d,d, r+2, t-1, 1);
        Square sq5 = new Square(d,d,d,d, r-1, t-2, 1);
        Square sq6 = new Square(d,d,d,d, r+1, t-2, 1);
        Square sq7 = new Square(d,d,d,d, r-2, t+1, 1);
        Square sq8 = new Square(d,d,d,d, r-2, t-1, 1);

        List<Square> sqList = Arrays.asList(sq1, sq2, sq3, sq4, sq5, sq6, sq7, sq8);
        sqSet = sqList.stream()
                .collect(Collectors.toSet());
        Set<Square> correctedSet = correctSet(sqSet);
        return correctedSet;
    }
}
