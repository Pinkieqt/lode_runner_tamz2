package com.loderunner.game;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ScoreActivity extends Activity {

    //db
    private static final String TAG = "DatabaseHelper";

    private DatabaseHelper databaseHelper;



    private Button menu;
    private Intent intent;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        list = (ListView)findViewById(R.id.list);
        menu = (Button)findViewById(R.id.mainMenu);

        databaseHelper = new DatabaseHelper(this);

        populateListView();




    }

    private void populateListView() {
        Cursor data = databaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            ScoreData tmp = new ScoreData();
            tmp.name = data.getString(1);
            tmp.score = data.getInt(2);
            String tmpString = tmp.name + " -> " + tmp.score;
            listData.add(tmpString);
        }

        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        list.setAdapter(adapter);
    }


    public void toMenu(View view){
        Intent intent = new Intent(this, MainMenu.class);
        startActivity(intent);
    }

}
