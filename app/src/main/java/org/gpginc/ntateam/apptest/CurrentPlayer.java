package org.gpginc.ntateam.apptest;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer.CurrentPlayerFragment;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer.CurrentPlayerTabsAdapter;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.List;

public class CurrentPlayer extends RuntimeActivity {


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_player_neo);
        setTitle(R.string.wkff_label);
        if(this.currentPlayer().attacked)this.currentPlayer().getClazz().runAttackTrigger(this);

        CurrentPlayerTabsAdapter adapterTab = new CurrentPlayerTabsAdapter(getSupportFragmentManager());
        adapterTab.add(CurrentPlayerFragment.newInstance(0, this.enableNext()), getResources().getString(R.string.battle_txt));
        adapterTab.add(CurrentPlayerFragment.newInstance(1, this.enableNext()), getResources().getString(R.string.objective_txt));

        ViewPager pager = findViewById(R.id.player_pager);
        pager.setAdapter(adapterTab);

        TabLayout tabs = findViewById(R.id.player_tab);
        tabs.setupWithViewPager(pager);

        this.currentPlayer().getClazz().runPassive(this);
    }

    @Override
    public void goNext(View view) {
        super.goNext(view);
    }
}
