package com.loderunner.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ListView;

public class ScoreActivity extends Activity {

    Button menu;
    Intent intent;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        list = (ListView)findViewById(R.id.list);
        menu = (Button)findViewById(R.id.mainMenu);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);




    }

}
