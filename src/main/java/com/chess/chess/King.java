package com.chess.chess;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class King extends Button implements Piece {
    private int x;
    private int y;

    public King(String text, Node graphic) {
        super(text, graphic);
    }

    @Override
    public Set<Square> getAvailableSquareToMove(Piece piece) {
       Set<Square> sqList = new HashSet<>();
        piece.setPieceAxis(piece);

        for(int x = piece.getX() -1; x< piece.getX()+2; x++) {
           for(int y = piece.getY() -1; y < piece.getY()+2; y++){
               int d = 100;
               Square square = new Square(d,d,d,d,x,y, 1);
               sqList.add(square);
               Square itsSelf = new Square(d, d, d, d, piece.getX(), piece.getY(), 1);
               sqList.remove(itsSelf);
           }
           System.out.println(" ROZMIAR LISTY: " + sqList.size());
       }
       Set<Square> correctedSet = piece.correctSet(sqList);

       return correctedSet;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
