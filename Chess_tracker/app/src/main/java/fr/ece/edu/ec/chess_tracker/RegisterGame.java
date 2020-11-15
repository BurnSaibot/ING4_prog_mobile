package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

import java.util.HashMap;

public class RegisterGame extends AppCompatActivity {
    private int idFirstTileHit = -1;
    private int idSecondTileHit = -1;
    private ImageView selectedPiece;
    private Board boardGame;
    private HashMap<Integer, Square> idToSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_game);
        boardGame = new Board();
        idToSquare = new HashMap<>();
        initMap();
    }

    public void backgroundClick (View v) {
        System.out.println("Clicked a Tile");
        if (selectedPiece == null) {
            return;
        }
        idSecondTileHit = ((View) v.getParent()).getId();
        Square s1 = idToSquare.get(idFirstTileHit);
        Square s2 = idToSquare.get(idSecondTileHit);
        Move tmp = new Move(s1, s2);
        if (boardGame.isMoveLegal(tmp, true)) {
            boardGame.doMove(tmp);
            RelativeLayout parent = (RelativeLayout) selectedPiece.getParent();
            parent.removeView(selectedPiece);
            RelativeLayout destination = findViewById(idSecondTileHit);
            destination.addView(selectedPiece);
            clear();
        } else {
            idSecondTileHit = -1;
            System.out.println("Move:" + tmp + ", is not legal !!!!");
            clear();
        }
    }

    public void pieceClick (View v) {
        int tileId = ((View) v.getParent()).getId();
        if (tileId == idFirstTileHit) {
            idFirstTileHit = -1;
            selectedPiece = null;
        } else if (selectedPiece != null){
            idSecondTileHit = ((View) v.getParent()).getId();
            Square s1 = idToSquare.get(idFirstTileHit);
            Square s2 = idToSquare.get(tileId);
            Move tmp = new Move(s1, s2);
            if (boardGame.isMoveLegal(tmp, true)) {
                boardGame.doMove(tmp);
                RelativeLayout parent = (RelativeLayout) v.getParent();
                parent.removeView(v);
                parent = (RelativeLayout) selectedPiece.getParent();
                parent.removeView(selectedPiece);
                RelativeLayout destination = findViewById(idSecondTileHit);
                destination.addView(selectedPiece);
                System.out.println("Doing move:" + tmp);
                clear();
            } else {
                idSecondTileHit = -1;
                System.out.println("Move:" + tmp + ", is not legal !!!!");
                clear();
            }
        } else {
            selectedPiece = (ImageView) v;
            idFirstTileHit = tileId;
            System.out.println("Selected a piece on tile " + idToSquare.get(tileId));
        }
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

    private void clear() {
        idFirstTileHit = -1;
        idSecondTileHit = -1;
        selectedPiece = null;
    }
}