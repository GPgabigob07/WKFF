package org.gpginc.ntateam.apptest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.util.Initializer;
import org.gpginc.ntateam.apptest.runtime.util.Skill;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
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
        bar1 = (ProgressBar)findViewById(R.id.loading_bar);
        bar2 = (ProgressBar)findViewById(R.id.spec_loading_bar);
        par1 = ((TextView)findViewById(R.id.loading_what));
        par2 =((TextView)findViewById(R.id.spec_loading_what));

        Initializer init = new Initializer();
        for(Clazz c : Clazzs.CLAZZS) {
            Initializer.ClazzLoader clazzLoader = init.new ClazzLoader(this, c);
            clazzLoader.execute(c);

            while (clazzLoader.getStatus()== AsyncTask.Status.RUNNING)Main.p(clazzLoader.onLoad.getName() + " "+clazzLoader.getStatus());;
        }
        Intent i = new Intent(this, MainPlusSettings.class);
        //startActivity(i);
        //finish();

    }
}
