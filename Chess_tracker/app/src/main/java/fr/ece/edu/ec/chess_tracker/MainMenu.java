package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import fr.ece.edu.ec.chess_tracker.business.Player;

public class MainMenu extends AppCompatActivity {
    Handler handler = new Handler();
    Player me ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        update();
    }

    private void update() {
        Intent intent = getIntent();
        if (intent != null ) {
            me = (Player) intent.getSerializableExtra("me");
            System.out.println("It's me : " + me);
            TextView name = findViewById(R.id.namePlayerMe);
            TextView surname = findViewById(R.id.surnamePlayerMe);
            TextView elo = findViewById(R.id.eloPlayerMe);
            TextView id = findViewById(R.id.idPlayerMe);

            name.setText(me.getName());
            surname.setText(me.getSurname());
            elo.setText(Integer.toString(me.getElo()));
            id.setText(Integer.toString(me.getIdUser()));

        }
    }

    public void onClickRegisterNewGame(View v) {
        Intent i = new Intent(this, RegisterGame.class);
        i.putExtra("me", me);
        this.startActivity(i);
    }

    public void onClickWatchGame(View v) {

    }
}