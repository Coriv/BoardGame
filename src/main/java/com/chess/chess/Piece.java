package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class Piece extends Button {

    private final boolean isWhite;
    private int x;
    private int y;

    public Piece(String text, Node graphic, final boolean isWhite, int x, int y) {
        super(text, graphic);
        this.x = x;
        this.y = y;
        this.isWhite = isWhite;
    }

    abstract public Set<Square> getAvailableSquareToMove(Piece piece);
    public Set<Square> correctSet(Set<Square> setToCorrect) {
        Set<Square> correctedSet = setToCorrect.stream()
                .filter(n -> n.getSquareAxisX() >= 0)
                .filter(n -> n.getSquareAxisX() <= 7)
                .filter(n -> n.getSquareAxisY() >= 0)
                .filter(n -> n.getSquareAxisY() <= 7)
                .collect(Collectors.toSet());
        return correctedSet;
    }

    public void setPieceAxis(Piece piece) {
        piece.setX(GridPane.getColumnIndex((Button)piece));
        piece.setY(GridPane.getRowIndex((Button) piece));
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
