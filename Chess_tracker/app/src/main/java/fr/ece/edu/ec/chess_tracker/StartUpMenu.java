package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import fr.ece.edu.ec.chess_tracker.business.Player;

public class StartUpMenu extends AppCompatActivity implements View.OnClickListener{
    Player me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_up_menu);
    }

    @Override
    public void onClick(View v) {
        System.out.println("Dans le handler, id =" + v.getId());
        Intent i = null;
        switch (v.getId()) {
            case R.id.btnSignIn:
                i = new Intent(this, LoginActivity.class);
                this.startActivity(i);
                break;
            case R.id.btnSignUp:
                i = new Intent(this, RegisterPlayerActivity.class);
                this.startActivity(i);
                break;
        }
    }
}