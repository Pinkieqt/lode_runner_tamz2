package com.loderunner.game;

import android.content.Intent;
import android.view.View;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class MainMenu extends Activity {

    Button buttonplay;
    Button buttonscore;
    Button buttonexit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        buttonplay = findViewById(R.id.butmenu);
        buttonexit = findViewById((R.id.butexit));
        buttonscore = findViewById(R.id.butscore);

    }


    public void startGame(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    public void showScore(View view){
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

    public void exitApp(View view){
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
        System.exit(0);
    }
}
