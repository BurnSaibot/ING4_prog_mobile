
package fr.ece.edu.ec.chess_tracker;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
            @Override
            public void run() {
                final Player me = PlayerDAO.registerPlayer(email.toUpperCase(), pwd, name.toUpperCase(), surname.toUpperCase(), new Integer(1000));
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        launchIntent(MainMenu.class, me);
                    }
                });
            }
        }).start();


    }

    private void launchIntent(Class c, Player me ) {
        Intent i = new Intent(this, c);
        i.putExtra("me", me);
        this.startActivity(i);
    }
}