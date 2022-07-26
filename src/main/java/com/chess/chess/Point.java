package com.chess.chess;

import javafx.scene.Node;

import java.util.HashSet;
import java.util.Set;

public class Point extends Piece {

    public Point(String text, Node graphic, boolean isWhite, int x, int y) {
        super(text, graphic, isWhite, x, y);
    }

    private Set<Square> excludedSetBlackPiece;
    private Set<Square> excludedSetWhitePiece;

    @Override
    public void setExcludedSets(Set<Square> wex, Set<Square> bex) {
        this.excludedSetWhitePiece = wex;
        this.excludedSetBlackPiece = bex;
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
            if(!excludedSetWhitePiece.contains(s1)) {
                sqSet.add(s1);
            }
            Square kill1 = new Square(d,d,d,d,r-1, t+1,1);
            Square kill2 = new Square(d,d,d,d, r+1, t+1, 1);
            if(excludedSetWhitePiece.contains(kill1)) {
                sqSet.add(kill1);
            }
            if(excludedSetWhitePiece.contains(kill2)) {
                sqSet.add(kill2);
            }

            if (piece.getY() == 1) {
                Square s2 = new Square(d, d, d, d, r, t + 2, 1);
                if (!excludedSetWhitePiece.contains(s2)) {
                    sqSet.add(s2);
                }
            }
        } else {
            Square s1 = new Square(d, d, d, d, r, t - 1, 1);
            if(!excludedSetBlackPiece.contains(s1)) {
                sqSet.add(s1);
            }
            Square kill1 = new Square(d,d,d,d,r-1, t-1,1);
            Square kill2 = new Square(d,d,d,d, r+1, t-1, 1);
            if(excludedSetBlackPiece.contains(kill1)) {
                sqSet.add(kill1);
            }
            if(excludedSetBlackPiece.contains(kill2)) {
                sqSet.add(kill2);
            }
            if (piece.getY() == 6) {
                Square s2 = new Square(d, d, d, d, r, t - 2, 1);
                if (!excludedSetBlackPiece.contains(s2)) {
                    sqSet.add(s2);
                }
            }
        }
        return correctSet(sqSet);
    }
}
