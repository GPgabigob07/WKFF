package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.CurrentPlayer;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.ArrayList;
import java.util.List;

public class CurrentPlayerFragment extends Fragment
{

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = null;
        Bundle b = this.getArguments();
        int kind = b.getInt("Kind");

        /*Loading from arguments*/
        final ArrayList<Event> EVENTS = new ArrayList<>();
        final int currentPlayerCod = b.getInt("CurrentPlayerCod");
        Player a = null;
        for(Parcelable p : b.getParcelableArrayList("Players"))
        {
            Player pp = (Player)p;
            if(pp.getCod()==currentPlayerCod)a=pp;
        }
        final Player currentPlayer = a;
        final RuntimeActivity cpA = b.getParcelable("CPA");
        /*----------------------*/
        switch(kind)
        {
            case 0:
                v = inflater.inflate(R.layout.content_cp, container, false);
                /*
                 Set players name in window
                */
                TextView playerName = v.findViewById(R.id.current_player_name_view);
                playerName.setText(currentPlayer.getName());
                ((ImageView)v.findViewById(R.id.kingdom_emblem)).setImageResource(Util.getKindomFor(currentPlayer));

                /**
                 * SetP player skills into the list
                 */
                ListView listinha = v.findViewById(R.id.skill_list);
                final List<ClazzSkill> skills = this.getAplicableSkillsFor(currentPlayer);

                ArrayAdapter<ClazzSkill> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, skills);
                listinha.setAdapter(adapter);
                //final CurrentPlayer cpA = this;
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
                            skills.get(position).runSkill(currentPlayer);
                        }

                    }
                });
                /**
                 * Setting clazz and kingdom
                 */
                ((TextView)v.findViewById(R.id.kingdom_inspect)).setText(currentPlayer.getKingdom());
                ((TextView)v.findViewById(R.id.clazz)).setText(currentPlayer.getClazz().getName());
                ((ImageView)v.findViewById(R.id.clazz_emblem)).setImageResource(currentPlayer.getClazz().getIcon());

                break;
            case 1:
                v = inflater.inflate(R.layout.activity_pre_player, container, false);
                break;
        }
        return v;
    }


    public static CurrentPlayerFragment newInstance(int kind, Bundle b) {
        b.putInt("Kind", kind);
        CurrentPlayerFragment fragment = new CurrentPlayerFragment();
        fragment.setArguments(b);
        return fragment;
    }

    protected List<ClazzSkill> getAplicableSkillsFor(Player p)
    {
        final List<ClazzSkill> out_skills = new ArrayList<>();
        //c.runPassive();
        
        Clazz c = p.getClazz();
        int asd = 0;
        for(int i = 0; i < c.getSkills().size(); ++i)
        {
            if(!c.getSkillAt(i).isPassive() && !c.getSkillAt(i).isAttackTriggered())
            {
                if(p.attacked)
                {
                    out_skills.add(c.getSkillAt(i));
                    Main.p("ATTACKED");
                }else if(!p.attacked && !c.getSkillAt(i).isCounter())
                {
                    Main.p("NOT ATTACKED");
                    out_skills.add(c.getSkillAt(i));
                }
            }
        }
        return out_skills;
    }
}
