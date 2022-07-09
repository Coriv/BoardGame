package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Queen extends Button implements Piece {
    private int x;
    private int y;
    public Queen(String text, Node graphic) {
        super(text, graphic);
    }

    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
        Set<Square> sqSet = new HashSet<>();
        piece.setPieceAxis(piece);
        int r = piece.getX();
        int t = piece.getY();

        int d = 100;
        for (int i = 0; i < 8; i++) {
            Square sqXx = new Square(d, d, d, d, r+i, t, 1);
            Square sqX = new Square(d, d, d, d, r-i, t, 1);
            Square sqYy = new Square(d, d, d, d, r, t+i, 1);
            Square sqY = new Square(d, d, d, d, r, t-i, 1);
            sqSet.add(sqXx);
            sqSet.add(sqX);
            sqSet.add(sqYy);
            sqSet.add(sqY);
        }
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
        Set<Square> correctedSet = piece.correctSet(sqSet);
        return correctedSet;
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public void setX(int x) {
        this.x = x;
    }

    @Override
    public void setY(int y) {
        this.y = y;
    }
}
