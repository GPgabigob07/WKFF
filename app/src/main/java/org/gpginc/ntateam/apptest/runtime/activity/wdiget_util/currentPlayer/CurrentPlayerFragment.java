package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.DragonEvent;
import org.gpginc.ntateam.apptest.runtime.util.TargetEvent;
import org.gpginc.ntateam.apptest.runtime.util.TurnSkill;
import org.gpginc.ntateam.apptest.runtime.util.Util;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

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
                ((ImageView)v.findViewById(R.id.current_player_life)).setImageResource(Util.getPlayerLifeShowner(currentPlayer));
                ((ImageView)v.findViewById(R.id.field_emblem)).setImageResource(Util.getFieldFor(currentPlayer));

                /**
                 * SetP player skills into the list
                 */
                ListView listinha = v.findViewById(R.id.skill_list);
                final List<ClazzSkill> skills = this.getAplicableSkillsFor(currentPlayer);

                ArrayAdapter<ClazzSkill> adapter = new ArrayAdapter<>(v.getContext(), android.R.layout.simple_list_item_1, skills);
                listinha.setAdapter(adapter);

                listinha.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                v = inflater.inflate(R.layout.current_player_objectives, container, false);

                /*load game events to display*/
                final List<Event> EVENTS = new ArrayList<>();
                Main.p("|*****PLAYER OBJECTIVES*****|");
                for(Parcelable p1 : b.getParcelableArrayList("Events"))
                {
                    Event evt = (Event) p1;
                    Main.p(getResources().getString(evt.getName()));
                    if(evt.getHandler().equals(EventHandler.ALWAYS))
                    {
                        EVENTS.add(evt);
                    } else if (evt.needPlayers)
                    {
                        Main.p("|*****NEED PLAYERS*****|");
                        if(((TargetEvent)evt).getOwner().equals(currentPlayer))
                        {
                            EVENTS.add(evt);
                        }
                    }
                    else ;
                }
                List<Event> clone = new ArrayList<>();
                for(EventHandler r : EventHandler.HANDLERS)
                {
                    for(Event e : EVENTS)if(e.getHandler().equals(r))clone.add(e);
                }
                ObjectivesAdapter mAdapter = new ObjectivesAdapter(clone);
                RecyclerView rM = v.findViewById(R.id.evt_list);
                LinearLayoutManager layoutManager = new LinearLayoutManager(v.getContext(), LinearLayoutManager.VERTICAL,false);
                rM.setLayoutManager(layoutManager);
                rM.setAdapter(new ObjectivesAdapter(EVENTS));
                Main.p("|*****************************|");

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
        //TODO DEBUGGING: Skill name apear, but even isn't possible to use it ingame. Might fix till next commit (2019-09-15)
        final List<ClazzSkill> out_skills = new ArrayList<>();
        int asd = 0;
        for(ClazzSkill sk : p.getClazz().getSkills())
        {
            if(!sk.isPassive() && !sk.isAttackTriggered())
            {
                if(sk instanceof TurnSkill)
                {
                    if(((TurnSkill)sk).checkCanUse())out_skills.add(sk);
                }
                if(p.attacked)
                {
                    if(sk.stillCounter())out_skills.add(sk);
                    else if(!sk.isCounter && !(sk instanceof TurnSkill))out_skills.add(sk);
                } else if(!sk.isCounter && !(sk instanceof TurnSkill))out_skills.add(sk);
            }
        }
        return out_skills;
    }
}
