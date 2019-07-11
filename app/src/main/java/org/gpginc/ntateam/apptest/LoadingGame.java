package org.gpginc.ntateam.apptest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;

@Deprecated
public class LoadingGame extends AppCompatActivity {


    public SharedPreferences prefer;
    public ProgressBar bar1, bar2;
    public TextView par1, par2;
    public Dialog d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_loading);
        Main.p("Load start");
        prefer = getSharedPreferences(Main.SETTINGS,0);
        bar1 = findViewById(R.id.loading_bar);
        bar2 = findViewById(R.id.spec_loading_bar);
        par1 = findViewById(R.id.loading_what);
        par2 = findViewById(R.id.spec_loading_what);


        Intent i = new Intent(this, MainPlusSettings.class);
        //startActivity(i);
        //finish();

    }
}
