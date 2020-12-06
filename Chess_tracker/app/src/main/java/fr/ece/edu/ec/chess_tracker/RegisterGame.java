package fr.ece.edu.ec.chess_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.business.ChessGame;
import fr.ece.edu.ec.chess_tracker.business.ChessGameImage;
import fr.ece.edu.ec.chess_tracker.business.ChessMove;
import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.ChessGameDAO;
import fr.ece.edu.ec.chess_tracker.dataAcces.ChessGameImageDAO;
import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class RegisterGame extends AppCompatActivity {
    public static final int REQUEST_CODE_CAMERA = 100;
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

    private Button takePictureButton;
    private Bitmap image;
    private Uri imageUri;
    private String imagePath;
    private boolean hasTakenAPicture;

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
        hasTakenAPicture = false;
        gameMoves = new ArrayList<>();
        initMap();
        initSpinner();

        takePictureButton = (Button) findViewById(R.id.btnRegisterPicture);

        // Checking for permission (Camera
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE }, 0);
        }
    }

    // if needing permission and permission are given, we enable the button to take pictures
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 0) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED
                    && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                takePictureButton.setEnabled(true);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (resultCode == RESULT_OK) {
                image = BitmapFactory.decodeFile(imagePath);
                System.out.println("Image:" + image);
                displayShortToast("Image has been added");
                hasTakenAPicture = true;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.general_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mapItem :
                Intent openingHistory = new Intent(this, MapsActivity.class);
                this.startActivity(openingHistory);
                break;
            case R.id.logOutItem :
                Intent openMainActivity = new Intent(this, StartUpMenu.class);
                this.startActivity(openMainActivity);
                break;
        }
        return super.onOptionsItemSelected(item);
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
                ChessGame insertedGame = null;
                if (colorSpinner.getSelectedItem().toString().equals(getString(R.string.the_white))) {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_white));
                    Player winner = (s.value().equals("WHITE")) ? oponent : me;
                    insertedGame = ChessGameDAO.insertChessGame(gameMoves, me, oponent, winner);
                } else {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_dark));
                    Player winner = (s.value().equals("BLACK")) ? oponent : me;
                    insertedGame = ChessGameDAO.insertChessGame(gameMoves, oponent, me, winner);
                }
                if (hasTakenAPicture && insertedGame != null) {
                    /*File f = new File(imageUri.getPath());
                    try {
                        ChessGameImage imageToInput = new ChessGameImage(insertedGame.getIdPartie(), Files.readAllBytes(Paths.get(imageUri.getPath())));
                        ChessGameImageDAO.insertImage(imageToInput);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }*/
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte [] imageInByteArray = stream.toByteArray();
                    ChessGameImage imageToInput = new ChessGameImage(insertedGame.getIdPartie(), imageInByteArray);
                    ChessGameImageDAO.insertImage(imageToInput);
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
                ChessGame insertedGame;

                final Player oponent = PlayerDAO.getPlayerFromId(oponentId);
                Side s = boardGame.getSideToMove();

                if (colorSpinner.getSelectedItem().toString().equals(getString(R.string.the_white))) {
                    insertedGame = ChessGameDAO.insertChessGame(gameMoves, me, oponent, null);
                } else {
                    System.out.println("Side: " + s + " playing: " + getString(R.string.the_dark));
                    insertedGame = ChessGameDAO.insertChessGame(gameMoves, oponent, me, null);
                }
                if (hasTakenAPicture && insertedGame != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    image.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte [] imageInByteArray = stream.toByteArray();
                    ChessGameImage imageToInput = new ChessGameImage(insertedGame.getIdPartie(), imageInByteArray);
                    ChessGameImageDAO.insertImage(imageToInput);

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

    public void takePicture(View v) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageUri = FileProvider.getUriForFile(RegisterGame.this, RegisterGame.this.getApplicationContext().getPackageName() + ".provider", getOutputMediaFile());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, REQUEST_CODE_CAMERA);
    }

    private File getOutputMediaFile(){
        //saving pictures into camera file out of the application, we will also save it into the database in regiserGame(View V) or registerDraw(View V)

        //here we get the directory where we'll put our image
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Chess_game");

        //if there is no local place to store pictures, we just do nothing
        if (!mediaStorageDir.exists()){
            if (!mediaStorageDir.mkdirs()){
                return null;
            }
        }

        //saving pictures : developer.android.com/guide/topics/media/camera.html#saving-media
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //file containing the image
        File photoFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
        imagePath = photoFile.getPath();
        return photoFile;
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