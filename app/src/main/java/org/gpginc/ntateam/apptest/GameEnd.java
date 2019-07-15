package org.gpginc.ntateam.apptest;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Events;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.Util;

public class GameEnd extends RuntimeActivity {

    private Event endEvt;
    private Player endPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_end);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*load info from Extras*/
        Bundle b = this.getIntent().getExtras();
        this.endEvt = Events.byName(b.getInt("EVT"));
        this.endPlayer = b.getParcelable("Target");
        if(this.endEvt != null) {
            Main.p("EVT TRUE");
            if(this.endPlayer != null)
            {
                Main.p("Player TRUE");
                /*--------------*/

                ((TextView)findViewById(R.id.current_player_name_view)).setText(this.endPlayer.getName());
                ((TextView)findViewById(R.id.current_player_clazz)).setText(this.endPlayer.getClazz().getName());
                if(this.endPlayer.isDead) {
                    ((TextView) findViewById(R.id.dmg_info)).setText(Util.getDeadInfoFor(this.endPlayer) != -1 ? Util.getDeadInfoFor(this.endPlayer) : R.string.bugstr);
                    if (this.endPlayer.life() < 0) ((TextView) findViewById(R.id.dmg_info)).setText(((TextView) findViewById(R.id.dmg_info)).getText() + " " + this.endPlayer.getLastAttacker().getName());
                } findViewById(R.id.dmg_info).setVisibility(View.INVISIBLE);
                ((ImageView)findViewById(R.id.dmg_kingdom)).setImageResource(Util.getKindomFor(this.endPlayer));

                ((TextView)findViewById(R.id.over_event_descr)).setText(this.endEvt.getDescription());
                /*--------------*/
            }
        }
    }

}
