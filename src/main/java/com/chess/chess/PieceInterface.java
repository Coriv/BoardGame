package com.chess.chess;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.Set;
import java.util.stream.Collectors;

public interface PieceInterface {
    Set<Square> getAvailableSquareToMove(Piece piece);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);


}
