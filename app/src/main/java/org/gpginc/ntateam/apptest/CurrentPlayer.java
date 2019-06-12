package org.gpginc.ntateam.apptest;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlayer extends RuntimeActivity {


    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_player);

        /*
        Set players name in window
         */
        TextView playerName = findViewById(R.id.current_player_name_view);
        playerName.setText(this.CURRENT_PLAYER);

        /**
         * SetP player skills into the list
         */
        ListView listinha = findViewById(R.id.skill_list);
        this.CP.getClazz().setCurrentPlayer(this.CP);
        final List<ClazzSkill> skills = this.getAplicableSkillsFor(this.CP.getClazz());

        ArrayAdapter<ClazzSkill> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, skills);
        listinha.setAdapter(adapter);

        listinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Snackbar.make(view, skills.get(position).getName(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent skill = new Intent(CurrentPlayer.this, SkillRun.class);
                skill.putExtra("layout", skills.get(position).getLayout());
                startActivity(skill);

                //((ViewFlipper) CurrentPlayer.this.findViewById(R.id.current_showin)).setDisplayedChild(1);

            }
        });
        /**
         * Seting clazz and kingdom
         */
        ((TextView)findViewById(R.id.kingdom)).setText(/*this.OUT_KINGDOMS.get(this.PLAYER_NAMES.indexOf(this.CURRENT_PLAYER))*/this.CP.getKingdom());
        ((TextView)findViewById(R.id.clazz)).setText(/*this.OUT_CLAZZS.get(this.PLAYER_NAMES.indexOf(this.CURRENT_PLAYER))*/this.CP.getClazz().getName());
    }

    public void goNext(View view)
    {
        Intent next = new Intent(this, CurrentPlayer.class);
        next.putExtras(this.getNextPlayer());
        startActivity(next);
        this.finish();
    }
}
