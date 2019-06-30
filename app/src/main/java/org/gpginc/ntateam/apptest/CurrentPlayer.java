package org.gpginc.ntateam.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.List;

public class CurrentPlayer extends RuntimeActivity {


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_player);
        setTitle(R.string.wkff_label);
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        if(this.currentPlayer().attacked)this.currentPlayer().getClazz().runAttackTrigger(this);
        /*
        Set players name in window
         */
        TextView playerName = findViewById(R.id.current_player_name_view);
        playerName.setText(this.CURRENT_PLAYER);
        ((ImageView)findViewById(R.id.kingdom_emblem)).setImageResource(Util.getKindomFor(this.currentPlayer()));

        /**
         * SetP player skills into the list
         */
        ListView listinha = findViewById(R.id.skill_list);
       // this.currentPlayer().getClazz().setCurrentPlayer(this.currentPlayer());
        final List<ClazzSkill> skills = this.getAplicableSkillsFor();

        ArrayAdapter<ClazzSkill> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, skills);
        listinha.setAdapter(adapter);
        final CurrentPlayer cpA = this;
        listinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Snackbar.make(view, skills.get(position).getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                skills.get(position).setLastAct(cpA);
               if(skills.get(position).hasLayout()) {
                   Intent skill = new Intent(cpA, SkillRun.class);
                   skill.putExtra("cskill", skills.get(position).getName());
                   skill.putExtra("PLAYER_EXECUTE", cpA.currentPlayer());
                   startActivity(skill);
               } else{
                   skills.get(position).runSkill(currentPlayer());
               }

            }
        });
        /**
         * Seting clazz and kingdom
         */
        ((TextView)findViewById(R.id.kingdom_inspect)).setText(/*this.OUT_KINGDOMS.get(this.PLAYER_NAMES.indexOf(this.CURRENT_PLAYER))*/this.currentPlayer().getKingdom());
        ((TextView)findViewById(R.id.clazz)).setText(/*this.OUT_CLAZZS.get(this.PLAYER_NAMES.indexOf(this.CURRENT_PLAYER))*/this.currentPlayer().getClazz().getName());
        this.currentPlayer().getClazz().runPassive(this);

    }
}
