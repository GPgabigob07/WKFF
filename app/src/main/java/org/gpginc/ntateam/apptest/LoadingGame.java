package org.gpginc.ntateam.apptest;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.Main;

public class LoadingGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_loading);
        Main.p("Load start");
        if(Main.preInit(getSharedPreferences(Main.SETTINGS,0),(ProgressBar)findViewById(R.id.loading_bar),(ProgressBar)findViewById(R.id.spec_loading_bar), ((TextView)findViewById(R.id.loading_what)), ((TextView)findViewById(R.id.spec_loading_what)))){
            Intent start = new Intent(LoadingGame.this, MainPlusSettings.class);
            startActivity(start);
            Main.p("Load done");
        }
    }
}
