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
import org.gpginc.ntateam.apptest.runtime.util.Skill;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoadingGame extends AppCompatActivity {


    private SharedPreferences prefer;
    private ProgressBar bar1, bar2;
    private TextView par1, par2;
    private Dialog d;

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

        d= new Dialog(this);
        d.setContentView(R.layout.dialog_demo);
        d = Util.dismissed(d);
        Initializer init = new Initializer();
        init.execute();
        while(!init.done);
        Intent i = new Intent(this, MainPlusSettings.class);
        startActivity(i);
        finish();

    }


    private class Initializer extends AsyncTask<Void, Void, Long>
    {
        private long clazzLoaderProgress = 0;
        private boolean done = false;


        @Override
        protected Long doInBackground(Void... voids) {
            Looper.prepare();
            bar1.setMax(Clazzs.CLAZZS.size() + 1);
            for(Clazz c :  Clazzs.CLAZZS)
            {
                ++clazzLoaderProgress;
                bar1.incrementProgressBy(1);
                par1.setText(c.getName());
                Main.p("Loading: " + c.getName()+"\n\n\n\n\n\n\n--------------------------------------------------------------------------------------");
                if(c.hasSkills())
                {
                    for(ClazzSkill sk : c.getSkills()) {
                        SkillLoad loader = new SkillLoad();
                        loader.execute(sk);
                        ((TextView)d.findViewById(R.id.dialog_info)).setText(sk.getName());
                        d.show();
                    }
                }
                /*try {
                    this.get(100, TimeUnit.MILLISECONDS);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }*/
            }
            /*try {
                this.get(100, TimeUnit.MILLISECONDS);
                bar1.incrementProgressBy(1);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }*/
            Looper.loop();
            this.done = true;
            return clazzLoaderProgress;
        }
    }

    private class SkillLoad extends AsyncTask<ClazzSkill, Void, Long>
    {
        protected boolean done = false;
        private int skillLoaderProgress = 0;

        @Override
        protected Long doInBackground(ClazzSkill... clazzSkills) {
            Looper.prepare();
            ClazzSkill skill = clazzSkills[0];
            bar2.setVisibility(View.VISIBLE);
            par2.setVisibility(View.VISIBLE);

            par2.setText(skill.getName());
            bar2.setMax(10);
            bar2.setIndeterminate(false);
            Main.p("Loading :"+skill.getName()+"\n-\n--\n-------------------------------------------------------------------------------");
            while (skillLoaderProgress < 10) {
                skillLoaderProgress += 1;
                bar2.setProgress(skillLoaderProgress);
                /*try {
                    this.get(150, TimeUnit.MILLISECONDS);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }*/
            }
            skill.isLoaded = true;
            par2.setText("");
            bar2.setProgress(0);
            bar2.setVisibility(View.INVISIBLE);
            par2.setVisibility(View.INVISIBLE);
            this.done = true;
            Looper.loop();
            return Long.valueOf(skillLoaderProgress);
        }
    }
}
