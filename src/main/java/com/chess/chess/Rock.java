package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;

import java.util.HashSet;
import java.util.Set;

public class Rock extends Button implements Piece {

    private int x;
    private int y;

    public Rock(String text, Node graphic) {
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
        Set<Square> correctedSet = correctSet(sqSet);
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
    this.y=y;
    }
}
