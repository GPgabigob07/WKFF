package org.gpginc.ntateam.apptest;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.DmgStp_Util.DmgLineAdapter;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;

import java.util.ArrayList;
import java.util.List;

public class DamageStep extends RuntimeActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_damage_step);
        setTitle(R.string.title_activity_damage_step);
        if(Main.damageStep(this.ON_PLAYERS))
        {
            findViewById(R.id.maybe_info).setVisibility(View.INVISIBLE);
            List<Player> deads = new ArrayList<>();
            for(Player p : this.ON_PLAYERS)
            {
                if(p.isDead)
                {
                    checkEvents(EventHandler.ON_DEATH);
                    deads.add(p);
                }
            }
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);

            RecyclerView mR = this.findViewById(R.id.dead_players_list);
            mR.setLayoutManager(layoutManager);

            DmgLineAdapter lAdp = new DmgLineAdapter(deads);
            mR.setAdapter(lAdp);
        }
    }

    @Override
    public void goNext(View view) {
        super.goNext(view);
    }
}
