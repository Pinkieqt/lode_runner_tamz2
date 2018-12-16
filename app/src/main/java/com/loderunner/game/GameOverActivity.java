package com.loderunner.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GameOverActivity extends Activity {

    TextView currentScore;
    EditText userName;

    Button buttonMenu;
    Button buttonScore;

    DatabaseHelper databaseHelper;

    Intent intent;


    int myScoreValue = 0;
    String scoreBeforeint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);

        userName = findViewById(R.id.editText);
        currentScore = findViewById(R.id.textView2);
        buttonMenu = findViewById(R.id.button);
        buttonScore = findViewById(R.id.button2);

        databaseHelper = new DatabaseHelper(this);

        intent = getIntent();
        scoreBeforeint = intent.getStringExtra("score");

        currentScore.setText(scoreBeforeint);



    }

    public void toMenu(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

    public void saveScore(View view){
        if (userName.getText().length() > 0) {
            myScoreValue = Integer.parseInt(scoreBeforeint);
            boolean insertData = databaseHelper.addData(userName.getText().toString(), myScoreValue);

            if(insertData){
                Toast.makeText(this, "Score added", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ScoreActivity.class);
                startActivity(intent);
            }
            else Toast.makeText(this, "Score not added", Toast.LENGTH_SHORT).show();
        }
        else
            Toast.makeText(this, "Must specify name", Toast.LENGTH_SHORT).show();
    }

}
