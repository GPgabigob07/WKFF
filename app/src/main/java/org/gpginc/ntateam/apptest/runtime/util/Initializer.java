package org.gpginc.ntateam.apptest.runtime.util;

import android.os.AsyncTask;
import android.view.View;

import org.gpginc.ntateam.apptest.LoadingGame;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;

public class Initializer
{
    public class ClazzLoader extends AsyncTask<Clazz, Integer, Boolean>
    {
        private LoadingGame game;
        public Clazz onLoad;
        public boolean loaded = false;

        public ClazzLoader(LoadingGame game, Clazz onLoad) {
            this.game = game;
            this.onLoad = onLoad;
        }

        @Override
        protected void onPreExecute()
        {
            this.game.par1.setText(this.onLoad.getName());
        }

        @Override
        protected Boolean doInBackground(Clazz... clazz) {
            Clazz c = clazz[0];
            this.game.bar1.setMax(c.getSkills().size() + 1);
            for(ClazzSkill cs : c.getSkills())
            {
                Initializer.SkillLoader cLoader = new Initializer.SkillLoader(this.game, cs);
                cLoader.execute(cs);

                while(cLoader.getStatus() == Status.RUNNING)
                {
                    try {
                        Main.p(cLoader.onLoad.getName() + " "+cLoader.getStatus());

                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            c.enabled = this.game.prefer.getBoolean(c.getName(), true);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            this.loaded = true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            this.game.bar1.incrementProgressBy(values[0]);
        }
    }

    public class SkillLoader extends AsyncTask<ClazzSkill,  Integer, Boolean>
    {
        private LoadingGame game;
        private ClazzSkill onLoad;
        public boolean loaded =false;

        public SkillLoader(LoadingGame game, ClazzSkill onLoad) {
            this.game = game;
            this.onLoad = onLoad;
        }

        @Override
        protected void onPreExecute() {
            this.game.bar2.setVisibility(View.VISIBLE);
            this.game.par2.setVisibility(View.VISIBLE);
            this.game.par2.setText(this.onLoad.getName());
            this.game.bar2.setMax(100);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            this.game.bar2.setVisibility(View.INVISIBLE);
            this.game.par2.setVisibility(View.INVISIBLE);
            this.loaded = true;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            this.game.bar2.incrementProgressBy(values[0]);
        }

        @Override
        protected Boolean doInBackground(ClazzSkill... clazzSkills)
        {
            try
            {
                int aux = 0;
                while(aux <100) {
                    ++aux;
                    Thread.sleep(1);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return true;
        }
    }
}
