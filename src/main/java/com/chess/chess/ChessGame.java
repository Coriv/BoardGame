package com.chess.chess;

import javafx.application.Application;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


public class ChessGame extends Application {
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
    private final Image playerVsPlayer = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/playerVsPlayer.png");
    private final Image playerVsComp = new Image("File:/Users/sebastianboron/IdeaProjects/Chess/src/main/resources/playerVsComp.png");

    private Piece pieceToMove;
    private Piece pieceToMoveRandom;
    private boolean withComputer;
    private Set<Square> availableSquares;
    private Set<Square> setOfSquares = new HashSet<>();
    private Set<Piece> pieces  = new HashSet<>();
    private static Set<Piece> whitePiece;
    private static Set<Piece> blackPiece;
    private Set<Piece> setOfPointsW = new HashSet<>();
    private Set<Piece> setOfPointsB = new HashSet<>();
    private Set<Square> allPossibleMoves = new HashSet<>();

    private GridPane grid = new GridPane();
    private GridPane gridToEdit = new GridPane();
    private Set<Square> excludedSet;
    private Set<Square> excludedSetBlackPieces;
    private Set<Square> excludedSetWhitePieces;
    private int d = 93;
    private Set<Piece> piecesOnBoard;
    private King checkKing;

    private boolean isCheck;

    private boolean whiteTurn = true;
    int counter;

    void preparePieceOnBoardSet() {
        piecesOnBoard = new HashSet<>();
        ObservableList<Node> children = grid.getChildren();
        piecesOnBoard = children.stream()
                .filter(n -> n instanceof Piece)
                .map(n -> (Piece) n)
                .collect(Collectors.toSet());
    }

    void prepareExcludedSet() {
        excludedSet = new HashSet<>();

        preparePieceOnBoardSet();

        excludedSet = piecesOnBoard.stream()
                .map(n -> new Square(1, 1, 1, 1, n.getX(), n.getY(), 1))
                .collect(Collectors.toSet());
    }
    void setExcludedSetBlackPieces() {
        excludedSetBlackPieces = new HashSet<>();

        preparePieceOnBoardSet();

        excludedSetBlackPieces = piecesOnBoard.stream()
                .filter(n -> !n.isWhite())
                .map(n -> new Square(1, 1, 1, 1, n.getX(), n.getY(), 1))
                .collect(Collectors.toSet());

    }
    void setExcludedSetWhitePieces() {
        excludedSetWhitePieces = new HashSet<>();

        preparePieceOnBoardSet();

        excludedSetWhitePieces = piecesOnBoard.stream()
                .filter(Piece::isWhite)
                .map(n -> new Square(1, 1, 1, 1, n.getX(), n.getY(), 1))
                .collect(Collectors.toSet());

    }
    Set<Square> preparePossibleMovments(Piece piece) {
        Set<Square> avaiablePieceSet = new HashSet<>();
        prepareExcludedSet();
        int r = piece.getX();
        int t = piece.getY();

        for (int i = 1; i < 8; i++) {
            Square sqXx = new Square(d, d, d, d, r + i, t, 1);
            avaiablePieceSet.add(sqXx);
            if (excludedSet.contains(sqXx)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqX = new Square(d, d, d, d, r - i, t, 1);
            avaiablePieceSet.add(sqX);
            if (excludedSet.contains(sqX)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqYy = new Square(d, d, d, d, r, t + i, 1);
            avaiablePieceSet.add(sqYy);
            if (excludedSet.contains(sqYy)) {break;}
        }
        for (int i = 1; i < 8; i++) {
            Square sqY = new Square(d, d, d, d, r, t - i, 1);
            avaiablePieceSet.add(sqY);
            if (excludedSet.contains(sqY)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s1 = new Square(d, d, d, d, r+i , t+i,1);
            avaiablePieceSet.add(s1);
            if(excludedSet.contains(s1)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s2 = new Square(d, d, d, d,r+i, t-i, 1);
            avaiablePieceSet.add(s2);
            if(excludedSet.contains(s2)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s3 = new Square(d, d, d, d, r-i, t+i, 1);
            avaiablePieceSet.add(s3);
            if(excludedSet.contains(s3)) { break;}
        }
        for (int i =1; i<8; i++) {
            Square s4 = new Square(d, d, d, d, r-i, t-i, 1);
            avaiablePieceSet.add(s4);
            if(excludedSet.contains(s4)) { break;}
        }

        return avaiablePieceSet.stream()
                .filter(n -> n.getSquareAxisX() >= 0)
                .filter(n -> n.getSquareAxisX() <= 7)
                .filter(n -> n.getSquareAxisY() >= 0)
                .filter(n -> n.getSquareAxisY() <= 7)
                .collect(Collectors.toSet());
    }

    boolean isCheck(King king) {
        Square sq = new Square(1,1,1,1, king.getX(), king.getY(), 1);
        System.out.println("polozenie szachowanego krola" + king.getX() + " "  + king.getY());
        allPossibleMoves = new HashSet<>();
        if(!king.isWhite()) {
            for (Piece p : whitePiece) {



                setExcludedSetBlackPieces();
                setExcludedSetWhitePieces();
                p.setExcludedSets(excludedSetWhitePieces, excludedSetBlackPieces);

            getAvailableSquare(p);
            makeSquaresBlackWhite();
            allPossibleMoves.addAll(availableSquares);
        }} else {
            for (Piece p : blackPiece) {


                setExcludedSetBlackPieces();
                setExcludedSetWhitePieces();
                p.setExcludedSets(excludedSetWhitePieces, excludedSetBlackPieces);

                getAvailableSquare(p);
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

        setExcludedSetBlackPieces();
        setExcludedSetWhitePieces();

        move.setExcludedSets(excludedSetWhitePieces, excludedSetBlackPieces);
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

    Piece choseRandomPiece() {
        List<Piece> blackPieceList =  blackPiece.stream().toList();

        int randomPieceIndex = (int) (Math.random() *blackPieceList.size());
        return blackPieceList.get(randomPieceIndex);
    }

  // @@@ SKAD WYJATEK SKORO KAZE MU SZUKAC AZ ZNAJDZIE NIE NULL ?
    void makeRandomMove() {
      Square randomSquare = null;
      Piece randomPiece;
      Piece pieceToRemove = null;
      do {
          setExcludedSetBlackPieces();
          randomPiece = choseRandomPiece();
        getAvailableSquare(randomPiece);

        List<Square>  availableSquaresList = availableSquares.stream().toList();
            if(availableSquares.size() != 0) {
           int randomSquareIndex = (int) (Math.random() * availableSquares.size());
           randomSquare = availableSquaresList.get(randomSquareIndex);
            }
       } while(randomSquare != null && excludedSetBlackPieces.contains(randomSquare));


        try {
            GridPane.setConstraints(randomPiece, randomSquare.getSquareAxisX(), randomSquare.getSquareAxisY());
        } catch (NullPointerException e) {
        makeRandomMove();
        }

        getAvailableSquare(randomPiece);
        makeSquaresBlackWhite();

        if(randomPiece instanceof Point && randomPiece.getY() == 7) {
            Queen p = new Queen("", new ImageView(queenBlackImage), false, randomPiece.getX(), randomPiece.getY());
            transformationAction(p, randomPiece);
        }

        setExcludedSetWhitePieces();

        if(excludedSetWhitePieces.contains(randomSquare)) {
            Set<Piece> whitePieceCopy = new HashSet<>(whitePiece);
            for(Piece p : whitePieceCopy) {
                if(p.getX() == randomSquare.getSquareAxisX() && p.getY() == randomSquare.getSquareAxisY()) {
                    pieceToRemove = p;
                }
                grid.getChildren().remove(pieceToRemove);
                whitePiece.remove(pieceToRemove);
            }
        }
        if (whitePiece.size() == 0 || blackPiece.size() == 0) {
            checkIsItEnd();
        }
    }
     Set<Square> getSetOfSquares() {
        return this.setOfSquares;
    }

    void showTransformationSceneWhite(Piece piece) {
            Piece pawnT = new Queen("", new ImageView(queenWhiteImage), true, piece.getX(), piece.getY());
            Piece rockT = new Rock("", new ImageView(rockWhiteImage), true, piece.getX(), piece.getY());
            Piece bishopT = new Bishop("", new ImageView(bishopWhiteImage), true, piece.getX(), piece.getY());
            Piece knightT = new Knight("", new ImageView(knightWhiteImage), true, piece.getX(), piece.getY());

        Stage transformation = new Stage();
        HBox pane = new HBox();
        pane.getChildren().addAll(pawnT, rockT, bishopT, knightT);
        pane.setPadding(new Insets(5,5,5,5));
        Scene transformationScene = new Scene(pane, Color.GRAY);

        transformation.setScene(transformationScene);
        transformation.setTitle("Chose your new piece");
        transformation.show();


            pawnT.setOnAction( event -> {
                Queen p = new Queen("", new ImageView(queenWhiteImage), true, piece.getX(), piece.getY());
                transformationAction(p, piece);
                transformation.hide();
            });

            rockT.setOnAction(event -> {
                Rock p = new Rock("", new ImageView(rockWhiteImage), true, piece.getX(), piece.getY());
                transformationAction(p, piece);
                transformation.hide();
            });

            bishopT.setOnAction(event -> {
            Bishop p = new Bishop("", new ImageView(bishopWhiteImage), true, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformation.hide();
            });

            knightT.setOnAction(event -> {
            Knight p = new Knight("", new ImageView(knightWhiteImage), true, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformation.hide();
            });
    }
    void showTransformationSceneBlack(Piece piece) {
        Piece pawnT = new Queen("", new ImageView(queenBlackImage), false, piece.getX(), piece.getY());
        Piece rockT = new Rock("", new ImageView(rockBlackImage), false, piece.getX(), piece.getY());
        Piece bishopT = new Bishop("", new ImageView(bishopBlackImage), false, piece.getX(), piece.getY());
        Piece knightT = new Knight("", new ImageView(knightBlackImage), false, piece.getX(), piece.getY());

        Stage transformationB = new Stage();
        HBox pane = new HBox();
        pane.getChildren().addAll(pawnT, rockT, bishopT, knightT);
        pane.setPadding(new Insets(5,5,5,5));
        Scene transformationScene = new Scene(pane, Color.GRAY);

        transformationB.setScene(transformationScene);
        transformationB.setTitle("Chose your new piece");
        transformationB.show();

        pawnT.setOnAction( event -> {
            Queen p = new Queen("", new ImageView(queenBlackImage), false, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformationB.hide();
        });

        rockT.setOnAction(event -> {
            Rock p = new Rock("", new ImageView(rockBlackImage), false, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformationB.hide();
        });

        bishopT.setOnAction(event -> {
            Bishop p = new Bishop("", new ImageView(bishopBlackImage), false, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformationB.hide();
        });

        knightT.setOnAction(event -> {
            Knight p = new Knight("", new ImageView(knightBlackImage), false, piece.getX(), piece.getY());
            transformationAction(p, piece);
            transformationB.hide();
        });
    }

    void transformationAction(Piece p, Piece pieceToRemove) {
        grid.getChildren().add(p);
        GridPane.setConstraints(p, pieceToRemove.getX(), pieceToRemove.getY());
        grid.getChildren().remove(pieceToRemove);
        if(pieceToRemove.isWhite()) {
            whitePiece.add(p);
            whitePiece.remove(pieceToRemove);
        } else {
            blackPiece.add(p);
            blackPiece.remove(pieceToRemove);
        }
        p.setOnAction(event1 -> actionPiece(p));
    }
    void actionPiece(Piece p) {
        if (pieceToMove != null && p.isWhite() != pieceToMove.isWhite()) {
            int z = GridPane.getColumnIndex(p);
            int t = GridPane.getRowIndex(p);
            Square sq = new Square(d, d, d, d, z, t, 1);

            if (availableSquares.contains(sq)) {
                grid.getChildren().remove(p);
                if (p.isWhite()) {
                    whitePiece.remove(p);
                    if (pieceToMove instanceof Point && p.getY() == 7) {
                        showTransformationSceneBlack(pieceToMove);
                    }
                } else {
                    blackPiece.remove(p);
                    if (pieceToMove instanceof Point && p.getY() == 0) {
                        showTransformationSceneWhite(pieceToMove);
                    }
                }
                GridPane.setConstraints(pieceToMove, z, t);
                getAvailableSquare(pieceToMove);
                pieceToMove = null;
                makeSquaresBlackWhite();
            } else if(!withComputer || (withComputer && p.isWhite())){
                pieceToMove = p;
                pieceToMoveRandom = p;
                getAvailableSquare(p);
            }
        } else if(!withComputer || (withComputer && p.isWhite())){
            pieceToMove = p;
            pieceToMoveRandom = p;
            getAvailableSquare(p);
        }
        if (whitePiece.size() == 0 || blackPiece.size() == 0) {
           checkIsItEnd();
        }
    }

    void checkIsItEnd() {
        Stage endOfGame = new Stage();
        String whoWon  ="";
        if (whitePiece.size() == 0) {
            whoWon = "Black pieces won";
        }
        if (blackPiece.size() == 0) {
                whoWon = "White pieces won";
        }

        Label endOfGameLabel = new Label(whoWon);
             VBox vbox= new VBox(endOfGameLabel);
            Scene endOfGameScene = new Scene(vbox, 100, 50);
            endOfGame.setScene(endOfGameScene);
            endOfGame.setTitle("The winner is:");
             endOfGame.show();
            }



    void withWhoYouWantToPlay() {
        GridPane startGrid = new GridPane();
        grid.setAlignment(Pos.BOTTOM_RIGHT);
        Button computer = new Button("", new ImageView(playerVsComp));
        Button secondPlayer = new Button("", new ImageView(playerVsPlayer));
        Stage startStage = new Stage();

        computer.setOnAction(event -> {
            withComputer = true;
            startStage.hide();

        });
        secondPlayer.setOnAction(event -> {
            withComputer = false;
            startStage.hide();

        });

        startGrid.add(computer, 0, 0);
        startGrid.add(secondPlayer, 1,0);

        Scene startingScene= new Scene(startGrid, 230, 105, Color.GRAY);
        startStage.setScene(startingScene);
        startStage.setTitle("Play with: ");
        startStage.show();
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
        whitePiece = new HashSet<>(listOfWhitePiece);
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
        blackPiece = new HashSet<>(listOfBlackPiece);
        blackPiece.addAll(setOfPointsB);

        pieces.addAll(whitePiece);
        pieces.addAll(blackPiece);


        for(Piece p : pieces) {
            grid.add(p, p.getX(), p.getY());
        }

        for (Piece p : whitePiece) {
            p.setOnAction(event -> {
                checkKing = (King) kingB;
                actionPiece(p);
            });
        }

        for (Piece p : blackPiece) {
            p.setOnAction(event -> {
                checkKing = (King) king;
                actionPiece(p);
                if(withComputer && pieceToMoveRandom != null && pieceToMoveRandom.isWhite() && blackPiece.size() != 0) {
                    makeRandomMove();
                    pieceToMoveRandom = null;
                }
        });}


        for (Square r : setOfSquares) {
            r.setOnMouseClicked(event -> {
                makeSquaresBlackWhite();

                /*Optional<Piece> optionalPiece = Optional.ofNullable(pieceToMove);
                Piece optionalPieceToMove = optionalPiece.orElse(new Queen("", null, true, 0,0));
                Optional<Set<Square>> optionalSquare = Optional.ofNullable(availableSquares);
                Set<Square> optionalAvailableSquare = optionalSquare.orElse(new HashSet<>()); */

                if(!availableSquares.contains(r)) {pieceToMove = null;}

                if (withComputer && !pieceToMove.isWhite()){
                        pieceToMove = null;
                    }

                int z = GridPane.getColumnIndex(r);
                int t = GridPane.getRowIndex(r);
                GridPane.setConstraints(pieceToMove, z, t);

                getAvailableSquare(pieceToMove);
                makeSquaresBlackWhite();
                isCheck = isCheck(checkKing);
                System.out.println(isCheck);
                if(withComputer && blackPiece.size() != 0) {
                    makeRandomMove();
                }
                if(pieceToMove instanceof Point && pieceToMove.getY() == 0 && pieceToMove.isWhite()) {
                    showTransformationSceneWhite(pieceToMove);
                }
                if(pieceToMove instanceof Point && pieceToMove.getY() == 7 && !pieceToMove.isWhite()) {
                    showTransformationSceneBlack(pieceToMove);
                }
                pieceToMove = null;
                pieceToMoveRandom = null;
                }) ;
        }

        mainPane.setCenter(grid);
        //Adding ToolBar down
        mainPane.setBottom(toolBarDown);
        Scene scene = new Scene(mainPane, 780, 900, Color.BLACK);

        primaryStage.setTitle("Some game, but not chess yet");
        primaryStage.setScene(scene);
        primaryStage.show();

        withWhoYouWantToPlay();
    }
    public static void main(String[] args) {
        launch();
    }
}