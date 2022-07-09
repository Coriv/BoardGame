package com.chess.chess;

import javafx.scene.shape.Rectangle;

import java.util.Objects;

public class Square extends Rectangle {
    private boolean isTakenByWhite = false;
    private boolean isTakenByBlack = false;

   private final int squareAxisX;
   private final int squareAxisY;
   private final int uniqueNumber;

    public Square(double x, double y, double width, double height, final int squareAxisX, final int squareAxisY, final int uniqueNumber) {
        super(x, y, width, height);
        this.squareAxisX = squareAxisX;
        this.squareAxisY = squareAxisY;
        this.uniqueNumber = uniqueNumber;
    }

    public boolean isTakenByWhite() {
        return isTakenByWhite;
    }

    public void setTakenByWhite(boolean takenByWhite) {
        isTakenByWhite = takenByWhite;
    }

    public boolean isTakenByBlack() {
        return isTakenByBlack;
    }

    public void setTakenByBlack(boolean takenByBlack) {
        isTakenByBlack = takenByBlack;
    }

    public int getSquareAxisX() {
        return squareAxisX;
    }

    public int getSquareAxisY() {
        return squareAxisY;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Square)) return false;
        Square square = (Square) o;
        return squareAxisX == square.squareAxisX && squareAxisY == square.squareAxisY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(squareAxisX, squareAxisY);
    }

    public int getUniqueNumber() {
        return uniqueNumber;
    }
}
