package fr.ece.edu.ec.chess_tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.github.bhlangonijr.chesslib.Board;
import com.github.bhlangonijr.chesslib.Square;
import com.github.bhlangonijr.chesslib.move.Move;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        System.out.println("Dans le handler, id =" + v.getId());
        Intent i = null;
        switch (v.getId()) {
            case R.id.btnSignIn :
                i = new Intent(MainActivity.this, RegisterGame.class);
                this.startActivity(i);
                break;
            case R.id.btnSignUp :
                i = new Intent(MainActivity.this, RegisterPlayerActivity.class);
                this.startActivity(i);
                break;

        }
    }
}