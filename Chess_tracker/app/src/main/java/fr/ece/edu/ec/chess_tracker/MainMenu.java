package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.bhlangonijr.chesslib.Square;

import java.util.HashMap;
import java.util.List;

import fr.ece.edu.ec.chess_tracker.business.ChessGame;
import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.ChessGameDAO;

public class MainMenu extends AppCompatActivity {
    Handler handler = new Handler();
    Player me;
    Context myContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        myContext = this;
        update();
    }

    private void update() {
        Intent intent = getIntent();
        if (intent != null ) {
            me = (Player) intent.getSerializableExtra("me");
            TextView name = findViewById(R.id.namePlayerMe);
            TextView surname = findViewById(R.id.surnamePlayerMe);
            TextView elo = findViewById(R.id.eloPlayerMe);
            TextView id = findViewById(R.id.idPlayerMe);
            LinearLayout gameLayout = findViewById(R.id.myGameDisplayer);

            name.setText(me.getName());
            surname.setText(me.getSurname());
            elo.setText(Integer.toString(me.getElo()));
            id.setText(Integer.toString(me.getIdUser()));

            new Thread(new Runnable() {
                @Override
                public void run() {
                    List<ChessGame> myGames = ChessGameDAO.getAllMyGames(me);

                    for(ChessGame aGame : myGames) {

                        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                        Button newButton = new Button(myContext);
                        newButton.setText(aGame.getTitle());
                        newButton.setTag(aGame.getIdPartie());
                        newButton.setLayoutParams(lp);
                        newButton.setBackground(getDrawable(R.drawable.border));
                        newButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                onClickWatchGame(v);
                            }
                        });
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                gameLayout.addView(newButton);
                            }
                        });
                    }

                }
            }).start();
        }
    }

    public void onClickRegisterNewGame(View v) {
        Intent i = new Intent(this, RegisterGame.class);
        i.putExtra("me", me);
        this.startActivity(i);
    }

    public void onClickWatchGame(View v) {
        int id = (int) v.getTag();
        Intent i = new Intent(this, WatchGame.class);
        i.putExtra("gameId", id);
        this.startActivity(i);
    }
}