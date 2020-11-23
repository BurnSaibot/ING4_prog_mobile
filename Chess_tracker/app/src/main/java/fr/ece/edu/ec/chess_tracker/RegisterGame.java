package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Side;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.business.ChessMove;
import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.ChessGameDAO;
import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class RegisterGame extends AppCompatActivity {
    private Player me;
    private int idFirstTileHit = -1;
    private int idSecondTileHit = -1;
    private ImageView selectedPiece;
    private Board boardGame;
    private HashMap<Integer, Square> idToSquare;
    private String array_spinner[] = new String[2];
    private List<ChessMove> gameMoves;
    private int moveCounter;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_game);
        boardGame = new Board();
        idToSquare = new HashMap<>();
        Intent intent = getIntent();
        if (intent != null ) {
            me = (Player) intent.getSerializableExtra("me");
        }
        gameMoves = new ArrayList<>();
        initMap();
        initSpinner();
    }

    public void backgroundClick (View v) {
        if (selectedPiece == null) {
            return;
        }
        idSecondTileHit = ((View) v.getParent()).getId();
        Square s1 = idToSquare.get(idFirstTileHit);
        Square s2 = idToSquare.get(idSecondTileHit);
        Move tmp = new Move(s1, s2);
        checkGameStatus();
        if (boardGame.legalMoves().contains(tmp) && boardGame.isMoveLegal(tmp, true)) {
            boardGame.doMove(tmp);
            RelativeLayout parent = (RelativeLayout) selectedPiece.getParent();
            parent.removeView(selectedPiece);
            RelativeLayout destination = findViewById(idSecondTileHit);
            destination.addView(selectedPiece);
            gameMoves.add(new ChessMove(tmp, moveCounter));
            moveCounter++;
            clear();
            checkGameStatus();
        } else {
            displayShortToast("Move:" + tmp + " is not legal !!!!");
            clear();
        }
    }

    public void pieceClick (View v) {
        int tileId = ((View) v.getParent()).getId();
        if (tileId == idFirstTileHit) {
            clear();
        } else if (selectedPiece != null){
            idSecondTileHit = ((View) v.getParent()).getId();
            Square s1 = idToSquare.get(idFirstTileHit);
            Square s2 = idToSquare.get(tileId);
            Move tmp = new Move(s1, s2);
            if (boardGame.legalMoves().contains(tmp) && boardGame.isMoveLegal(tmp, true)) {
                boardGame.doMove(tmp);
                RelativeLayout parent = (RelativeLayout) v.getParent();
                parent.removeView(v);
                parent = (RelativeLayout) selectedPiece.getParent();
                parent.removeView(selectedPiece);
                RelativeLayout destination = findViewById(idSecondTileHit);
                destination.addView(selectedPiece);
                gameMoves.add(new ChessMove(tmp, moveCounter));
                moveCounter++;
                clear();
                checkGameStatus();
            } else {
                displayShortToast("Move:" + tmp + " is not legal !!!!");
                clear();
            }
        } else {
            selectedPiece = (ImageView) v;
            idFirstTileHit = tileId;
        }
    }

    public void registerGame(View v) {
        EditText inputNamePlayer = (EditText) findViewById(R.id.inputOponentId);
        Spinner colorSpinner = (Spinner) findViewById(R.id.inputPlayerColor);

        int oponentId = Integer.parseInt(inputNamePlayer.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Player oponent = PlayerDAO.getPlayerFromId(oponentId);
                Side s = boardGame.getSideToMove();

                if (colorSpinner.getSelectedItem().toString().equals(getString(R.string.the_white))) {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_white));
                    Player winner = (s.value().equals("WHITE")) ? oponent : me;
                    ChessGameDAO.insertChessGame(gameMoves, me, oponent, winner);
                } else {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_dark));
                    Player winner = (s.value().equals("BLACK")) ? oponent : me;
                    ChessGameDAO.insertChessGame(gameMoves, oponent, me, winner);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        launchIntent(MainMenu.class, me);
                    }
                });
            }
        }).start();
    }

    public void registerDraw(View v) {
        EditText inputNamePlayer = (EditText) findViewById(R.id.inputOponentId);
        Spinner colorSpinner = (Spinner) findViewById(R.id.inputPlayerColor);

        int oponentId = Integer.parseInt(inputNamePlayer.getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {

                final Player oponent = PlayerDAO.getPlayerFromId(oponentId);
                Side s = boardGame.getSideToMove();

                if (colorSpinner.getSelectedItem().toString().equals(getString(R.string.the_white))) {
                    ChessGameDAO.insertChessGame(gameMoves, me, oponent, null);
                } else {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_dark));
                    ChessGameDAO.insertChessGame(gameMoves, oponent, me, null);
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        launchIntent(MainMenu.class, me);
                    }
                });
            }
        }).start();
    }

    private void initMap() {
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA1), Square.A1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB1), Square.B1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC1), Square.C1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD1), Square.D1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE1), Square.E1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF1), Square.F1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG1), Square.G1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH1), Square.H1);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA2), Square.A2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB2), Square.B2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC2), Square.C2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD2), Square.D2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE2), Square.E2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF2), Square.F2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG2), Square.G2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH2), Square.H2);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA3), Square.A3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB3), Square.B3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC3), Square.C3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD3), Square.D3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE3), Square.E3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF3), Square.F3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG3), Square.G3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH3), Square.H3);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA4), Square.A4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB4), Square.B4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC4), Square.C4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD4), Square.D4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE4), Square.E4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF4), Square.F4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG4), Square.G4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH4), Square.H4);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA5), Square.A5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB5), Square.B5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC5), Square.C5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD5), Square.D5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE5), Square.E5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF5), Square.F5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG5), Square.G5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH5), Square.H5);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA6), Square.A6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB6), Square.B6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC6), Square.C6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD6), Square.D6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE6), Square.E6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF6), Square.F6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG6), Square.G6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH6), Square.H6);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA7), Square.A7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB7), Square.B7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC7), Square.C7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD7), Square.D7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE7), Square.E7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF7), Square.F7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG7), Square.G7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH7), Square.H7);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileA8), Square.A8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileB8), Square.B8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileC8), Square.C8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileD8), Square.D8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileE8), Square.E8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileF8), Square.F8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileG8), Square.G8);
        idToSquare.put(new Integer(R.id.layoutChessBoardTileH8), Square.H8);
    }

    private void initSpinner() {
        Spinner chooseColor = findViewById(R.id.inputPlayerColor);
        array_spinner[0] = getString(R.string.the_white);
        array_spinner[1] = getString(R.string.the_dark);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, array_spinner);
        chooseColor.setAdapter(adapter);
    }

    private void checkGameStatus(){
        if (boardGame.isMated()) {
            displayLongToast("Mated");
            Button btnRegister = findViewById(R.id.btnValidateAndRegisterGame);
            btnRegister.setEnabled(true);
        }
        if (boardGame.isDraw()) {
            displayLongToast("Draw!!");
            Button btnRegister = findViewById(R.id.btnValidateAndRegisterGame);
            btnRegister.setEnabled(true);
        }
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

    private void clear() {
        idFirstTileHit = -1;
        idSecondTileHit = -1;
        selectedPiece = null;
    }

    private void launchIntent(Class c, Player me ) {
        Intent i = new Intent(this, c);
        i.putExtra("me", me);
        this.startActivity(i);
    }
}