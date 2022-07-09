package com.chess.chess;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public interface Piece {
    Set<Square> getAvailableSquareToMove(Piece piece);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);
    default Set<Square> correctSet(Set<Square> setToCorrect) {
        Set<Square> correctedSet = new HashSet<>();
        correctedSet = setToCorrect.stream()
                .filter(n -> n.getSquareAxisX() >= 0)
                .filter(n -> n.getSquareAxisX() <= 7)
                .filter(n -> n.getSquareAxisY() >= 0)
                .filter(n -> n.getSquareAxisY() <= 7)
                .collect(Collectors.toSet());
        return correctedSet;
    }
    default void setPieceAxis(Piece piece) {
        piece.setX(GridPane.getColumnIndex((Button)piece));
        piece.setY(GridPane.getRowIndex((Button) piece));    }
}
