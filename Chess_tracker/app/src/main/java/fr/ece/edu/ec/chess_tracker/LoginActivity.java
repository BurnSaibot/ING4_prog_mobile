package fr.ece.edu.ec.chess_tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class LoginActivity extends AppCompatActivity {
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void signInOnClick(View v) {
        if (v.getId() != R.id.validSignInPlayer) {
            return;
        }
        EditText inputPassword = (EditText) findViewById(R.id.inputPasswordPlayerLogin);
        EditText inputEmail = (EditText) findViewById(R.id.inputEmailPlayerLogin);
        final String pwd = inputPassword.getText().toString();
        final String email = inputEmail.getText().toString();


        new Thread(new Runnable() {
            @Override
            public void run() {
                final Player me = PlayerDAO.connectPlayer(email.toUpperCase(), pwd);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (me == null) {
                            Toast errorMessage = new Toast(getApplicationContext());
                            errorMessage.setText("User not found !!!");
                            errorMessage.setDuration(Toast.LENGTH_LONG);
                            errorMessage.show();
                            return;
                        }
                        launchInstance(MainMenu.class, me);
                    }
                });
            }
        }).start();

    }

    private void launchInstance(Class c, Player me ) {
        Intent i = new Intent(this, c);
        i.putExtra("me", me);
        this.startActivity(i);
    }
}