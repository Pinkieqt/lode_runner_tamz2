package com.loderunner.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;

public class GameOverActivity extends Activity {
    EditText name;
    TextView score;
    Button butMain;
    Button butSend;
    Intent intent;
    String value;
    String string;
    boolean write = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gameover);


        intent = getIntent();
        value = intent.getStringExtra("score");



    }

}
