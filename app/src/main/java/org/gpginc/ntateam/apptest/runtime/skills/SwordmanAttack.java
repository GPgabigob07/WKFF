package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.res.ColorStateList;
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

import static android.R.color.holo_red_dark;
import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class SwordmanAttack extends ClazzSkill
{

    public SwordmanAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public SwordmanAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public SwordmanAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        Main.p("ACTIVATED");
        if(o!= null)
        {
            p("You can attack 2 players, that are in your field.");
            Player p = (Player) o;
            int pField = p.getField();
            int asd = 1;
            final List<Object> attackable = new ArrayList<>();
            for(int i = 0; i< PLAYERS.size(); ++i)
            {
                if(!PLAYERS.get(i).equals(p)&& PLAYERS.get(i).getField() == pField)
                {
                    attackable.add(PLAYERS.get(i));
                    Main.p(PLAYERS.get(i).getName());
                }
            }
            /* Find and setup list and buttons functions*/
            final ListView list = ((ListView) this.current.findViewById(R.id.players_list));
            final Button btn = this.current.findViewById(R.id.func_skill_btn);
            btn.setHint("single");
            final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, attackable, true, 2, list);
            final RuntimeActivity r = this.current;
            final ClazzSkill thisSkill = this;

            list.setAdapter(adapter);
            btn.setOnClickListener(new View.OnClickListener() {
                public View.OnClickListener secondListener = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        btn.setHint("twice");
                        Snackbar.make(v, btn.getHint(), Snackbar.LENGTH_SHORT).show();

                    }
                };
                @Override
                public void onClick(View v)
                {
                    Dialog d = r.getDialog("You can select 2 players!");
                    ((Button)d.findViewById(R.id.doalog_ok)).setText("Attack twice");
                    ((Button)d.findViewById(R.id.doalog_ok)).setOnClickListener(this.secondListener);
                    if(adapter.getSelectedCount() < 2 && !btn.getHint().equals("twice"))
                    {
                        d.show();
                    } else if (adapter.getSelectedCount() < 2)
                    {
                        PLAYERS.get(adapter.getSelectedIndexes()[0]).giveDamage(r.getCP(), 2);
                        r.finish();
                        thisSkill.getLastAct().goNext(v);
                    }
                    else
                    {
                        PLAYERS.get(adapter.getSelectedIndexes()[0]).giveDamage(r.getCP(), 1);
                        PLAYERS.get(adapter.getSelectedIndexes()[1]).giveDamage(r.getCP(), 1);
                        r.finish();
                        thisSkill.getLastAct().goNext(v);
                    }

                }
            });

        }
    }

    @Override
    public Creator<SwordmanAttack> getCreator() {
        return CREATOR;
    }

    public static final Creator<SwordmanAttack> CREATOR = new Creator<SwordmanAttack>()
    {
        @Override
        public SwordmanAttack createFromParcel(Parcel source) {
            return new SwordmanAttack(source);
        }

        @Override
        public SwordmanAttack[] newArray(int size) {
            return new SwordmanAttack[size];
        }
    };
}
