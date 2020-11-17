
package fr.ece.edu.ec.chess_tracker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import fr.ece.edu.ec.chess_tracker.business.Player;
import fr.ece.edu.ec.chess_tracker.dataAcces.PlayerDAO;

public class RegisterPlayerActivity extends AppCompatActivity {

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

    }

    public void registerPlayer(View v) {
        if (v.getId() != R.id.validRegistrationPlayer){
            return;
        }
        EditText inputNamePlayer = (EditText) findViewById(R.id.inputNamePlayer);
        EditText inputSurnamePlayer = (EditText) findViewById(R.id.inputSurnamePlayer);
        EditText inputPassword = (EditText) findViewById(R.id.inputPasswordPlayer);
        EditText inputVerifPassword = (EditText) findViewById(R.id.inputPasswordVerifPlayer);
        EditText inputEmail = (EditText) findViewById(R.id.inputEmailPlayer);

        Toast errorMessage = new Toast(getApplicationContext());
        errorMessage.setText("Passwords don't match !!");
        errorMessage.setDuration(Toast.LENGTH_LONG);
        if ( ! inputPassword.getText().toString().equals(inputVerifPassword.getText().toString())) {
            errorMessage.show();
            return;
        }

        final String name = inputNamePlayer.getText().toString();
        final String surname = inputSurnamePlayer.getText().toString();
        final String pwd = inputPassword.getText().toString();
        final String email = inputEmail.getText().toString();


        new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                final Player me = PlayerDAO.registerPlayer(email.toUpperCase(), pwd, name.toUpperCase(), surname.toUpperCase(), new Integer(1000));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
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