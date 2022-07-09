package com.chess.chess;

import javafx.application.Application;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


public class HelloApplication extends Application {

    Image imageback = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/szmaragdowe.jpg");
    Image kingImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/King.png");
    Image queenImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/Queen.png");
    Image bishopImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/bishopWhite.png");
    Image knightImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/knight.png");
    Image rockImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/towerEdit.png");
    Image pointImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/point.png");
    private Piece move;
    private Set<Square> availableSquares;
    private Set<Square> setOfSquares = new HashSet<>();

    int counter;

    public void getAvailableSquare(Piece move) {
        availableSquares = new HashSet<>();
        Set<Square> squareToCompare = move.getAvailableSquareToMove(move);
        for (Square sq: setOfSquares) {
            for (Square sqToC : squareToCompare) {
                if(sq.getSquareAxisX() == sqToC.getSquareAxisX() &&
                        sq.getSquareAxisY() == sqToC.getSquareAxisY()) {
                    availableSquares.add(sq);
                    sq.setFill(Color.LIGHTBLUE);
                }
            }
        }
    }

    public void addToSet(Square square) {
        setOfSquares.add(square);
    }
    public Set<Square> getSetOfSquares() {
        return this.setOfSquares;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        BorderPane mainPane = new BorderPane();
        mainPane.setBackground(background);

        GridPane grid = new GridPane();
        grid.setPadding(new Insets(0,0,0,0));
        grid.setHgap(0);
        grid.setVgap(0);
        Button play = new Button("Play");
        Button exit = new Button("Exit");
        ToolBar toolBarUp = new ToolBar(play, exit);

        Button previousMove = new Button("Previous move");
        Button draw = new Button("Offer Draw");
        ToolBar toolBarDown = new ToolBar(previousMove, draw);



        //Squares adding
        int count =0;
        int d = 100;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Square square = new Square(d, d, d, d, j, i, count);
                if(count % 2 == 0) {
                    square.setFill(Color.WHITE);
                }
                count++;
                grid.add(square, j, i);
                addToSet(square);            }
            count++;
        }

        //pieces !!
        ImageView kingView = new ImageView(kingImage);
        ImageView queenView = new ImageView(queenImage);
        ImageView bishopView = new ImageView(bishopImage);
        ImageView knightView = new ImageView(knightImage);
        ImageView towerView = new ImageView(rockImage);
        ImageView pointView = new ImageView(pointImage);
        ImageView towerView2 = new ImageView(rockImage);
        ImageView bishopView2 = new ImageView(bishopImage);
        ImageView knightView2 = new ImageView(bishopImage);


        King king = new King("", kingView);
        Queen queen = new Queen("", queenView);
        Bishop bishop = new Bishop("", bishopView);
        Bishop bishop2 = new Bishop("", bishopView2);
        Button knight = new Button("", knightView);
        Button knight2 = new Button("", knightView2);
        Rock rock = new Rock("", towerView);
        Rock rock2 = new Rock("", towerView2);
        Button point = new Button("", pointView);

        grid.add(rock, 0, 7);
        grid.add(knight, 1, 7);
        grid.add(bishop, 2, 7);
        grid.add(queen, 3, 7);
        grid.add(king, 4, 7);
        grid.add(bishop2, 5, 7);
        grid.add(knight2, 6, 7);
        grid.add(rock2, 7, 7);
        grid.add(point, 0, 6);


        king.setOnAction(event -> {
           move = king;
           getAvailableSquare(move);
        });

        queen.setOnAction(event -> {
            move = queen;
            getAvailableSquare(queen);
        });

        rock.setOnAction(event -> {
            move = rock;
            getAvailableSquare(rock);
        });

        rock2.setOnAction(event -> {
            move = rock2;
            getAvailableSquare(rock2);
        });

        bishop.setOnAction(event -> {
            move = bishop;
            getAvailableSquare(bishop);
        });

        bishop2.setOnAction(event -> {
            move = bishop2;
            getAvailableSquare(bishop2);
        });

        for (Square r : setOfSquares) {
            r.setOnMouseClicked(event -> {
                for(Square se : getSetOfSquares()) {
                se.setFill(Color.BLACK);
                if(se.getUniqueNumber() %2 == 0) {
                    se.setFill(Color.WHITE);
                }}
               int z = GridPane.getColumnIndex(r);
               int t = GridPane.getRowIndex(r);

                        GridPane.setConstraints((Button)move, z, t);
                        move = null;
                System.out.println("rozmiar setofSQQQQ: " + setOfSquares.size());
                });
        }



        mainPane.setTop(toolBarUp);
        mainPane.setCenter(grid);
        //Adding ToolBar down
        mainPane.setBottom(toolBarDown);
        Scene scene = new Scene(mainPane, 900, 900, Color.BLACK);


        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}