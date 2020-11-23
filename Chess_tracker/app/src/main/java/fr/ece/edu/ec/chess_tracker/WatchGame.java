package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Piece;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.HashMap;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.business.ChessGame;
import fr.ece.edu.ec.chess_tracker.business.ChessMove;
import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.ChessGameDAO;
import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class WatchGame extends AppCompatActivity {
    private int gameId;
    private int moveCounter;
    private ChessGame chessGame;
    private Board gameBoard;
    private Player player1;
    private Player player2;
    private HashMap<Square, Integer> squareToViewIds;
    private HashMap<Piece, Integer> chessPieceToViewPiece;

    private Handler handler = new Handler();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_game);
        Intent intent = getIntent();
        if (intent != null) {
            gameId = intent.getIntExtra("gameId", -1);
        }
        gameBoard = new Board();
        moveCounter = 0;
        initMapIds();
        initMapPieces();
        initDisplay();
    }

    private void initDisplay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                chessGame = ChessGameDAO.getGameById(gameId);
                if (chessGame == null) {
                    System.err.println("Game Not Found, gameId:" + gameId);
                    return;
                }
                player1 = PlayerDAO.getPlayerFromId(chessGame.getPlayer1());
                player2 = PlayerDAO.getPlayerFromId(chessGame.getPlayer2());

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView p1Name = findViewById(R.id.namePlayer1);
                        TextView p1Surname = findViewById(R.id.surnamePlayer1);
                        TextView p1Elo = findViewById(R.id.eloPlayer1);
                        p1Name.setText(player1.getName());
                        p1Surname.setText(player1.getSurname());
                        p1Elo.setText(String.valueOf(player1.getElo()));

                        TextView p2Name = findViewById(R.id.namePlayer2);
                        TextView p2Surname = findViewById(R.id.surnamePlayer2);
                        TextView p2Elo = findViewById(R.id.eloPlayer2);
                        p2Name.setText(player2.getName());
                        p2Surname.setText(player2.getSurname());
                        p2Elo.setText(String.valueOf(player2.getElo()));

                        if (chessGame.getWinner() != -1 ) {
                            TextView winner = findViewById(R.id.textViewResult);
                            winner.setText(chessGame.getWinner() == player1.getIdUser() ? player1.getName() : player2.getName());
                        }

                        TextView numberOfMoves = findViewById(R.id.textViewNbPlayedMove);
                        numberOfMoves.setText(String.valueOf(chessGame.getChessMoves().size()));

                    }
                });
            }
        }).start();
    }

    private void initMapIds() {
        squareToViewIds = new HashMap<>();
        squareToViewIds.put(Square.A1, R.id.layoutChessBoardTileA1);
        squareToViewIds.put(Square.B1, R.id.layoutChessBoardTileB1);
        squareToViewIds.put(Square.C1, R.id.layoutChessBoardTileC1);
        squareToViewIds.put(Square.D1, R.id.layoutChessBoardTileD1);
        squareToViewIds.put(Square.E1, R.id.layoutChessBoardTileE1);
        squareToViewIds.put(Square.F1, R.id.layoutChessBoardTileF1);
        squareToViewIds.put(Square.G1, R.id.layoutChessBoardTileG1);
        squareToViewIds.put(Square.H1, R.id.layoutChessBoardTileH1);
        squareToViewIds.put(Square.A2, R.id.layoutChessBoardTileA2);
        squareToViewIds.put(Square.B2, R.id.layoutChessBoardTileB2);
        squareToViewIds.put(Square.C2, R.id.layoutChessBoardTileC2);
        squareToViewIds.put(Square.D2, R.id.layoutChessBoardTileD2);
        squareToViewIds.put(Square.E2, R.id.layoutChessBoardTileE2);
        squareToViewIds.put(Square.F2, R.id.layoutChessBoardTileF2);
        squareToViewIds.put(Square.G2, R.id.layoutChessBoardTileG2);
        squareToViewIds.put(Square.H2, R.id.layoutChessBoardTileH2);
        squareToViewIds.put(Square.A3, R.id.layoutChessBoardTileA3);
        squareToViewIds.put(Square.B3, R.id.layoutChessBoardTileB3);
        squareToViewIds.put(Square.C3, R.id.layoutChessBoardTileC3);
        squareToViewIds.put(Square.D3, R.id.layoutChessBoardTileD3);
        squareToViewIds.put(Square.E3, R.id.layoutChessBoardTileE3);
        squareToViewIds.put(Square.F3, R.id.layoutChessBoardTileF3);
        squareToViewIds.put(Square.G3, R.id.layoutChessBoardTileG3);
        squareToViewIds.put(Square.H3, R.id.layoutChessBoardTileH3);
        squareToViewIds.put(Square.A4, R.id.layoutChessBoardTileA4);
        squareToViewIds.put(Square.B4, R.id.layoutChessBoardTileB4);
        squareToViewIds.put(Square.C4, R.id.layoutChessBoardTileC4);
        squareToViewIds.put(Square.D4, R.id.layoutChessBoardTileD4);
        squareToViewIds.put(Square.E4, R.id.layoutChessBoardTileE4);
        squareToViewIds.put(Square.F4, R.id.layoutChessBoardTileF4);
        squareToViewIds.put(Square.G4, R.id.layoutChessBoardTileG4);
        squareToViewIds.put(Square.H4, R.id.layoutChessBoardTileH4);
        squareToViewIds.put(Square.A5, R.id.layoutChessBoardTileA5);
        squareToViewIds.put(Square.B5, R.id.layoutChessBoardTileB5);
        squareToViewIds.put(Square.C5, R.id.layoutChessBoardTileC5);
        squareToViewIds.put(Square.D5, R.id.layoutChessBoardTileD5);
        squareToViewIds.put(Square.E5, R.id.layoutChessBoardTileE5);
        squareToViewIds.put(Square.F5, R.id.layoutChessBoardTileF5);
        squareToViewIds.put(Square.G5, R.id.layoutChessBoardTileG5);
        squareToViewIds.put(Square.H5, R.id.layoutChessBoardTileH5);
        squareToViewIds.put(Square.A6, R.id.layoutChessBoardTileA6);
        squareToViewIds.put(Square.B6, R.id.layoutChessBoardTileB6);
        squareToViewIds.put(Square.C6, R.id.layoutChessBoardTileC6);
        squareToViewIds.put(Square.D6, R.id.layoutChessBoardTileD6);
        squareToViewIds.put(Square.E6, R.id.layoutChessBoardTileE6);
        squareToViewIds.put(Square.F6, R.id.layoutChessBoardTileF6);
        squareToViewIds.put(Square.G6, R.id.layoutChessBoardTileG6);
        squareToViewIds.put(Square.H6, R.id.layoutChessBoardTileH6);
        squareToViewIds.put(Square.A7, R.id.layoutChessBoardTileA7);
        squareToViewIds.put(Square.B7, R.id.layoutChessBoardTileB7);
        squareToViewIds.put(Square.C7, R.id.layoutChessBoardTileC7);
        squareToViewIds.put(Square.D7, R.id.layoutChessBoardTileD7);
        squareToViewIds.put(Square.E7, R.id.layoutChessBoardTileE7);
        squareToViewIds.put(Square.F7, R.id.layoutChessBoardTileF7);
        squareToViewIds.put(Square.G7, R.id.layoutChessBoardTileG7);
        squareToViewIds.put(Square.H7, R.id.layoutChessBoardTileH7);
        squareToViewIds.put(Square.A8, R.id.layoutChessBoardTileA8);
        squareToViewIds.put(Square.B8, R.id.layoutChessBoardTileB8);
        squareToViewIds.put(Square.C8, R.id.layoutChessBoardTileC8);
        squareToViewIds.put(Square.D8, R.id.layoutChessBoardTileD8);
        squareToViewIds.put(Square.E8, R.id.layoutChessBoardTileE8);
        squareToViewIds.put(Square.F8, R.id.layoutChessBoardTileF8);
        squareToViewIds.put(Square.G8, R.id.layoutChessBoardTileG8);
        squareToViewIds.put(Square.H8, R.id.layoutChessBoardTileH8);
    }

    private void initMapPieces(){
        chessPieceToViewPiece = new HashMap<>();

        chessPieceToViewPiece.put(gameBoard.getPiece(Square.H8), R.id.rook1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.G8), R.id.knight1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.F8), R.id.bishop1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.E8), R.id.king1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.D8), R.id.queen1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.C8), R.id.bishop2dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.B8), R.id.knight2dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.A8), R.id.rook2dark);

        chessPieceToViewPiece.put(gameBoard.getPiece(Square.H7), R.id.pawn1dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.G7), R.id.pawn2dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.F7), R.id.pawn3dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.E7), R.id.pawn4dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.D7), R.id.pawn5dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.C7), R.id.pawn6dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.B7), R.id.pawn7dark);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.A7), R.id.pawn8dark);

        chessPieceToViewPiece.put(gameBoard.getPiece(Square.H1), R.id.rook1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.G1), R.id.knight1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.F1), R.id.bishop1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.E1), R.id.king1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.D1), R.id.queen1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.C1), R.id.bishop2white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.B1), R.id.knight2white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.A1), R.id.rook2white);

        chessPieceToViewPiece.put(gameBoard.getPiece(Square.H2), R.id.pawn1white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.G2), R.id.pawn2white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.F2), R.id.pawn3white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.E2), R.id.pawn4white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.D2), R.id.pawn5white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.C2), R.id.pawn6white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.B2), R.id.pawn7white);
        chessPieceToViewPiece.put(gameBoard.getPiece(Square.A2), R.id.pawn8white);

    }

    public void moveForward(View v) {
        if (chessGame.getChessMoves().size() == moveCounter) {
            displayLongToast("You reached final move, can't go forward !!");
            return;
        }
        ChessMove move = chessGame.getChessMoves().get(moveCounter);
        Square from = Square.valueOf(move.getFrom());
        Square to = Square.valueOf(move.getTo());
        Move tmp = new Move(from, to);
        Piece movedPiece = gameBoard.getPiece(from);
        RelativeLayout viewFrom = findViewById(squareToViewIds.get(from));
        ImageView pieceView = (ImageView) viewFrom.getChildAt(viewFrom.getChildCount()-1);
        ((RelativeLayout) pieceView.getParent()).removeView(pieceView);
        RelativeLayout destination = findViewById(squareToViewIds.get(to));
        if (destination.getChildCount()>1) {
            System.out.println("View count:" + destination.getChildCount());
            ImageView killedPiece = (ImageView) destination.getChildAt(viewFrom.getChildCount());
            killedPiece.setVisibility(View.INVISIBLE);
        }
        destination.addView(pieceView);
        moveCounter++;
        updateMoveNumberDisplayed();
        gameBoard.doMove(tmp);

    }

    public void moveBackward(View v) {
        if(moveCounter == 0) {
            displayLongToast("You are at the start of the game, can't go backward !!");
            return;
        }
        ChessMove move = chessGame.getChessMoves().get(moveCounter-1);
        Square to = Square.valueOf(move.getFrom());
        Square from = Square.valueOf(move.getTo());
        Piece movedPiece = gameBoard.getPiece(from);
        RelativeLayout viewFrom = findViewById(squareToViewIds.get(from));
        ImageView pieceView = (ImageView) viewFrom.getChildAt(viewFrom.getChildCount()-1);
        if (viewFrom.getChildCount()>2) {
            ImageView killedPiece = (ImageView) viewFrom.getChildAt(viewFrom.getChildCount()-2);
            killedPiece.setVisibility(View.VISIBLE);
        }
        ((RelativeLayout) pieceView.getParent()).removeView(pieceView);
        RelativeLayout destination = findViewById(squareToViewIds.get(to));
        destination.addView(pieceView);
        moveCounter--;
        updateMoveNumberDisplayed();
        gameBoard.undoMove();
    }

    private void updateMoveNumberDisplayed () {
        TextView numberOfMoves = findViewById(R.id.currentMoveNumber);
        numberOfMoves.setText(String.valueOf(moveCounter));
    }
    
    public void play2moves (View v) {
        moveForward(v);
        moveForward(v);
    }

    private void displayShortToast(String msg) {
        Toast errorMessage = new Toast(getApplicationContext());
        errorMessage.setText(msg);
        errorMessage.setDuration(Toast.LENGTH_SHORT);
        errorMessage.show();
        return;
    }

    private void displayLongToast(String msg) {
        Toast errorMessage = new Toast(getApplicationContext());
        errorMessage.setText(msg);
        errorMessage.setDuration(Toast.LENGTH_LONG);
        errorMessage.show();
        return;
    }
}