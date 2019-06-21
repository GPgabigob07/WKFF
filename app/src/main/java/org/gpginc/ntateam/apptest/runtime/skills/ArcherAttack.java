package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class ArcherAttack extends ClazzSkill
{

    public ArcherAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public ArcherAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public ArcherAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        Random rand = new Random();
        Main.p("ACTIVATED");
        final List<Object> attackable = new ArrayList<>();
        if(o!= null)
        {
            Player p = (Player) o;
            int asd = 0;
            for(Player pP : PLAYERS)
            {
                attackable.add(pP);
                Main.p(pP.getName());
            }
            final ListView list = ((ListView) this.current.findViewById(R.id.players_list));
            final Button btn = this.current.findViewById(R.id.func_skill_btn);
            btn.setHint("single");
            final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, attackable, false, 1, list);
            final RuntimeActivity r = this.current;
            final ClazzSkill thisSkill = this;
            final Dialog d = r.getDialog("You can select just 1 player!");
            d.findViewById(R.id.doalog_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            list.setAdapter(adapter);
            btn.setOnClickListener(new View.OnClickListener() {
                public View.OnClickListener secondListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setHint("twice");
                        Snackbar.make(v, btn.getHint(), Snackbar.LENGTH_SHORT).show();
                        d.dismiss();

                    }
                };
                @Override
                public void onClick(View v)
                {

                    ((Button)d.findViewById(R.id.doalog_ok)).setText("Attack twice");
                    ((Button)d.findViewById(R.id.doalog_ok)).setOnClickListener(this.secondListener);
                    if(adapter.getSelectedCount() < 1)
                    {
                        d.show();
                    } else if (adapter.getSelectedCount() ==1)
                    {
                        PLAYERS.get(adapter.getSelectedIndexes()[0]).giveDamage(r.getCP(), 1);
                        r.finish();
                        thisSkill.getLastAct().goNext(v);
                    }
                }
            });
        }

    }
    public static final Creator<ArcherAttack> CREATOR = new Creator<ArcherAttack>()
    {
        @Override
        public ArcherAttack createFromParcel(Parcel source) {
            return new ArcherAttack(source);
        }

        @Override
        public ArcherAttack[] newArray(int size) {
            return new ArcherAttack[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
