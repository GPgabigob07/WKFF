package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.Intent;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.input;
import static org.gpginc.ntateam.apptest.runtime.Main.p;
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class Reposition extends ClazzSkill
{

    public Reposition(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public Reposition(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public Reposition(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!= null)
        {
            //Variable initialization
            final Player p = (Player) o;
            p.getClazz().setCurrentPlayer(null);
            final String cK = p.getKingdom();
            final SkillRun sk = this.current;
            final Dialog d = this.current.getDialog("You are able to move someone from your kingdom, do you want to?");
            final ClazzSkill thisSkill = this;

            //----------------------DIALOG SETUP----------------------//
            ((Button)d.findViewById(R.id.doalog_ok)).setText(R.string.yes_btn);
            d.findViewById(R.id.doalog_ok).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Set up: if player press the 'ok' button
                    final List<Object> gone = new ArrayList<>();
                    //Get all players that apply to the condition
                    for(Player k : PLAYERS)
                    {
                        if(k.getKingdom().equals(cK) && !k.equals(p))
                        {
                            p("["+PLAYERS.indexOf(k) +"] " + k.getName());
                            gone.add(k);
                        }
                    }

                    //Create and set the Adapter for the ListView
                    ListView list = sk.findViewById(R.id.players_list);
                    final PlayerSelectAdapter adapter = new PlayerSelectAdapter(sk, gone, false, 1, list);
                    adapter.setShowField(true);
                    list.setAdapter(adapter);

                    //Set up: 'Select' button
                    ((Button)sk.findViewById(R.id.func_skill_btn)).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            final Dialog d2 = sk.getDialog("You might select someone...");//New dialog

                            ((Button)d2.findViewById(R.id.doalog_ok)).setText(android.R.string.ok);
                            ((Button)d2.findViewById(R.id.doalog_ok)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    d2.dismiss();
                                }
                            });
                            ((Button)d2.findViewById(R.id.doalog_cancel)).setText(R.string.cancel_reposition_dialog_btn);
                            d2.findViewById(R.id.doalog_cancel).setOnClickListener(sk.dialogDismiss(d2, true));

                            if(adapter.getSelectedCount() < 1)
                            {
                                d2.show();
                            } else if (adapter.getSelectedCount() ==1)
                            {
                                ClazzSkill c = Clazzs.CHANGE_POSITION;
                                c.setLastAct(sk);
                                Intent skill = new Intent(sk, SkillRun.class);
                                skill.putExtra("cskill", c.getName());
                                skill.putExtras(sk.enableNext());
                                sk.startActivity(skill);
                                sk.finish();
                            }
                        }
                    });
                    d.dismiss();
                }
            });
            ((Button)d.findViewById(R.id.doalog_cancel)).setText(R.string.no_btn);
            d.findViewById(R.id.doalog_cancel).setOnClickListener(this.current.dialogDismiss(d, true));
            //----------------------DIALOG SETUP----------------------//
            d.show(); //Shows the dialog

        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<Reposition> CREATOR = new Creator<Reposition>()
    {
        @Override
        public Reposition createFromParcel(Parcel source) {
            return new Reposition(source);
        }

        @Override
        public Reposition[] newArray(int size) {
            return new Reposition[size];
        }
    };
}
