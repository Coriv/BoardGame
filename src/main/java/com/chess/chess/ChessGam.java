package com.chess.chess;

import javafx.application.Application;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import kotlin.collections.IntIterator;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ChessGam extends Application {

    private final Image imageback = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/szmaragdowe.jpg");

    private final Image kingWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/kingWhite.png");
    private final Image queenWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/queenWhite.png");
    private final Image bishopWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/bishopWhite.png");
    private final Image knightWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/knightWhite.png");
    private final Image rockWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/rockWhite.png");
    private final Image pointWhiteImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/pointWhite.png");

    private final Image kingBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/kingBlack.png");
    private final Image queenBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/queenBlack.png");
    private final Image bishopBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/bishopBlack.png");
    private final Image knightBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/knightBlack.png");
    private final Image rockBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/rockBlack.png");
    private final Image pointBlackImage = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/pointBlack.png");

    private Piece pieceToMove;
    private Piece pieceToBeat;
    private Set<Square> availableSquares;
    private Set<Square> setOfSquares = new HashSet<>();
    private Set<Piece> pieces  = new HashSet<>();
    private static Set<Piece> whitePiece;
    private static Set<Piece> blackPiece;
    private Set<Piece> setOfPointsW = new HashSet<>();
    private Set<Piece> setOfPointsB = new HashSet<>();
    private Set<Square> allPossibleMoves;
    private Set<Square> getAllPossibleMovesBlack;

    private GridPane grid = new GridPane();
    private GridPane gridToEdit = new GridPane();
    private Set<Square> excludedSet;
    private King checkKing;

    private boolean isCheck;

    private boolean whiteTurn = true;
    int counter;

    void prepareExcludedSet() {
        excludedSet = new HashSet<>();
        ObservableList<Node> children = grid.getChildren();

        Set<Piece> piecesOnBoard = children.stream()
                        .filter(n -> n instanceof Piece)
                        .map(n -> (Piece) n)
                        .collect(Collectors.toSet());

        excludedSet = piecesOnBoard.stream()
                .map(n -> new Square(1, 1, 1, 1, n.getX(), n.getY(), 1))
                .collect(Collectors.toSet());
    }

    void whosTurn(Piece piece) {

    }

    Set<Square> preparePossibleMovments(Piece piece) {
        Set<Square> avaiableSET = new HashSet<>();
        prepareExcludedSet();
        int r = piece.getX();
        int t = piece.getY();
        int d = 100;

        for (int i = 1; i < 8; i++) {
            Square sqXx = new Square(d, d, d, d, r + i, t, 1);
            avaiableSET.add(sqXx);
            if (excludedSet.contains(sqXx)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqX = new Square(d, d, d, d, r - i, t, 1);
            avaiableSET.add(sqX);
            if (excludedSet.contains(sqX)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqYy = new Square(d, d, d, d, r, t + i, 1);
            avaiableSET.add(sqYy);
            if (excludedSet.contains(sqYy)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqY = new Square(d, d, d, d, r, t - i, 1);
            avaiableSET.add(sqY);
            if (excludedSet.contains(sqY)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s1 = new Square(d, d, d, d, r+i , t+i,1);
            avaiableSET.add(s1);
            if(excludedSet.contains(s1)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s2 = new Square(d, d, d, d,r+i, t-i, 1);
            avaiableSET.add(s2);
            if(excludedSet.contains(s2)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s3 = new Square(d, d, d, d, r-i, t+i, 1);
            avaiableSET.add(s3);
            if(excludedSet.contains(s3)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s4 = new Square(d, d, d, d, r-i, t-i, 1);
            avaiableSET.add(s4);
            if(excludedSet.contains(s4)) { break;}
        }

        return avaiableSET.stream()
                .filter(n -> n.getSquareAxisX() >= 0)
                .filter(n -> n.getSquareAxisX() <= 7)
                .filter(n -> n.getSquareAxisY() >= 0)
                .filter(n -> n.getSquareAxisY() <= 7)
                .collect(Collectors.toSet());
    }

   /*void preparePossibleMovesToCheckWhite() {
        for (Piece p : whitePiece) {
            p.getAvailableSquareToMove(p);
            makeSquaresBlackWhite();
            allPossibleMovesWhite.addAll(availableSquares);
        }
   } */

    boolean isCheck(King king) {
        Square sq = new Square(1,1,1,1, king.getX(), king.getY(), 1);
        allPossibleMoves = new HashSet<Square>();
        if(king.isWhite()) {
            for (Piece p : whitePiece) {
            p.getAvailableSquareToMove(p);
            makeSquaresBlackWhite();
            allPossibleMoves.addAll(availableSquares);
        }} else {
            for (Piece p : blackPiece) {
                p.getAvailableSquareToMove(p);
                makeSquaresBlackWhite();
                allPossibleMoves.addAll(availableSquares);
            }
        }
        return allPossibleMoves.contains(sq);
    }
    void makeSquaresBlackWhite() {
        for(Square se : getSetOfSquares()) {
            se.setFill(Color.BLACK);
            if(se.getUniqueNumber() %2 == 0) {
                se.setFill(Color.WHITE);
            }}
    }
    void getAvailableSquare(Piece move) {
        makeSquaresBlackWhite();
        availableSquares = new HashSet<>();
        Set<Square> squareToCompare = move.getAvailableSquareToMove(move);
        Set<Square> intersection = new HashSet<>(setOfSquares);
        intersection.retainAll(squareToCompare);
        if(move instanceof Queen || move instanceof Rock || move instanceof Bishop || move instanceof Point) {
            intersection.retainAll(preparePossibleMovments(move));
        }
        availableSquares.addAll(intersection);
        for (Square sq:availableSquares) {
            sq.setFill(Color.LIGHTBLUE);
        }
    }

     void addToSet(Square square) {
        setOfSquares.add(square);
    }
     Set<Square> getSetOfSquares() {
        return this.setOfSquares;
    }
    @Override
    public void start(Stage primaryStage) throws IOException {
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, false);
        BackgroundImage backgroundImage = new BackgroundImage(imageback, BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);


        BorderPane mainPane = new BorderPane();
        mainPane.setBackground(background);

        //GridPane grid = new GridPane();
        grid.setPadding(new Insets(0,0,0,0));
        grid.setHgap(0);
        grid.setVgap(0);
        grid.setAlignment(Pos.CENTER);

        Button previousMove = new Button("Previous move");
        Button draw = new Button("Offer Draw");
        ToolBar toolBarDown = new ToolBar(previousMove, draw);


        //Squares adding
        int count =0;
        int d = 93;
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

        //White pieces
        Piece king = new King("", new ImageView(kingWhiteImage), true, 4, 7);
        Piece queen = new Queen("", new ImageView(queenWhiteImage), true,3, 7);
        Piece bishop = new Bishop("", new ImageView(bishopWhiteImage), true, 2, 7);
        Piece bishop2 = new Bishop("", new ImageView(bishopWhiteImage), true, 5, 7);
        Piece knight = new Knight("", new ImageView(knightWhiteImage), true, 1, 7);
        Piece knight2 = new Knight("", new ImageView(knightWhiteImage), true, 6, 7);
        Piece rock = new Rock("", new ImageView(rockWhiteImage), true, 0, 7);
        Piece rock2 = new Rock("", new ImageView(rockWhiteImage), true, 7, 7);
        for(int i=0; i<8; i++) {
            Piece point = new Point("", new ImageView(pointWhiteImage), true, i, 6);
            setOfPointsW.add(point);
        }

        List<Piece> listOfWhitePiece = Arrays.asList(king, queen, bishop, bishop2, knight, knight2, rock,rock2);
        whitePiece = listOfWhitePiece.stream()
                        .collect(Collectors.toSet());
        whitePiece.addAll(setOfPointsW);

        //Adding black pieces
        Piece kingB = new King("", new ImageView(kingBlackImage), false, 4,0);
        Piece queenB = new Queen("", new ImageView(queenBlackImage), false, 3, 0);
        Piece bishopB = new Bishop("", new ImageView(bishopBlackImage), false, 2, 0);
        Piece bishop2B = new Bishop("", new ImageView(bishopBlackImage), false, 5, 0);
        Piece knightB = new Knight("", new ImageView(knightBlackImage), false, 1, 0);
        Piece knight2B = new Knight("", new ImageView(knightBlackImage), false, 6, 0);
        Piece rockB = new Rock("", new ImageView(rockBlackImage), false, 0, 0);
        Piece rock2B = new Rock("", new ImageView(rockBlackImage), false, 7, 0);
        for(int i=0; i<8; i++) {
            Piece pointB = new Point("", new ImageView(pointBlackImage),false, i, 1);
            setOfPointsB.add(pointB);
        }
        List<Piece> listOfBlackPiece = Arrays.asList(kingB, queenB, bishopB, bishop2B, knightB, knight2B, rockB,rock2B);
        blackPiece = listOfBlackPiece.stream()
                .collect(Collectors.toSet());
        blackPiece.addAll(setOfPointsB);

        pieces.addAll(whitePiece);
        pieces.addAll(blackPiece);


        for(Piece p : pieces) {
            grid.add(p, p.getX(), p.getY());
        }

        for (Piece p : whitePiece) {
            p.setOnAction(event -> {
                checkKing = (King) kingB;
                if(pieceToMove != null && p.isWhite() != pieceToMove.isWhite()) {
                    int z = GridPane.getColumnIndex(p);
                    int t = GridPane.getRowIndex(p);
                    Square sq = new Square(d,d,d,d, z, t, 1);
                    if(availableSquares.contains(sq)) {
                        grid.getChildren().remove(p);
                        whitePiece.remove(p);
                        GridPane.setConstraints(pieceToMove, z, t);
                        getAvailableSquare(pieceToMove);
                        pieceToMove = null;
                        makeSquaresBlackWhite();


                    } else {
                        pieceToMove = p;
                        getAvailableSquare(p);
                    }
                } else {
                    pieceToMove = p;
                    getAvailableSquare(p);
                }});
        }
        for (Piece p : blackPiece) {
            p.setOnAction(event -> {
                checkKing = (King) king;
                if(pieceToMove != null && p.isWhite() != pieceToMove.isWhite()) {
                    int z = GridPane.getColumnIndex(p);
                    int t = GridPane.getRowIndex(p);
                    Square sq = new Square(d,d,d,d, z, t, 1);
                    if(availableSquares.contains(sq)) {
                        grid.getChildren().remove(p);
                        blackPiece.remove(p);
                        GridPane.setConstraints(pieceToMove, z, t);
                        getAvailableSquare(pieceToMove);
                        pieceToMove = null;
                        makeSquaresBlackWhite();

                    } else {
                        pieceToMove = p;
                        getAvailableSquare(p);
                    }
                } else {
                    pieceToMove = p;
                    getAvailableSquare(p);
                }});
        }


        for (Square r : setOfSquares) {
            r.setOnMouseClicked(event -> {
                makeSquaresBlackWhite();

                if(!availableSquares.contains(r)) {pieceToMove = null;}

                int z = GridPane.getColumnIndex(r);
                int t = GridPane.getRowIndex(r);
                GridPane.setConstraints(pieceToMove, z, t);
                getAvailableSquare(pieceToMove);
                makeSquaresBlackWhite();
                //SZACH BOOLEAN
                //isCheck = isCheck(checkKing);
                System.out.println("CZY JEST SZach:  " +isCheck(checkKing));
                pieceToMove = null;
                });
        }

        mainPane.setCenter(grid);
        //Adding ToolBar down
        mainPane.setBottom(toolBarDown);
        Scene scene = new Scene(mainPane, 900, 900, Color.BLACK);


        primaryStage.setTitle("Chess");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
    public static void main(String[] args) {
        System.out.println("Poczatek gry");
        launch();
        System.out.println("Koniec gry");
    }
}