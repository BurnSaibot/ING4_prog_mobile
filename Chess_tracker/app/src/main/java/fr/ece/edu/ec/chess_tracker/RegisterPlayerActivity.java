
package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

        Runnable r = new Runnable() {
            @Override
            public void run() {
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
                }

                String name = inputNamePlayer.getText().toString();
                String surname = inputSurnamePlayer.getText().toString();
                String pwd = inputPassword.getText().toString();
                String email = inputEmail.getText().toString();

                final Player me = PlayerDAO.registerPlayer(email, pwd, name, surname, new Integer(1000));

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent i = new Intent(getApplicationContext(), WatchParty.class);
                        i.putExtra
                    }
                });
            }
        };
        new Thread(r).start();



    }
}