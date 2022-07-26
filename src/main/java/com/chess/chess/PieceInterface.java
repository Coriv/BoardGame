package com.chess.chess;

import java.util.Set;

public interface PieceInterface {
    Set<Square> getAvailableSquareToMove(Piece piece);
    int getX();
    int getY();
    void setX(int x);
    void setY(int y);


}
