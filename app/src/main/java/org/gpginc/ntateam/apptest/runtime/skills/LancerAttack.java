package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
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

public class LancerAttack extends ClazzSkill
{

    public LancerAttack(String name, Type type) {
        super(name, type, false);
    }

    public LancerAttack(String name, Type type, int layout) {
        super(name, type, layout, false);
    }

    public LancerAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        Main.p("ACTIVATED");
        if(o!= null)
        {
            Player p = (Player) o;
            int pField = p.getField();
            int attackingField = 0;
            final List<Object> attackable = new ArrayList<>();
            switch(pField)
            {
                case 1:
                    attackingField = 3;
                    break;
                case 2:
                    attackingField = 4;
                    break;
                case 3:
                    attackingField = 1;
                    break;
                case 4:
                    attackingField = 2;
                    break;
            }

            int asd = 1;
            for(int i = 0; i< lastAct.getPlayers().size(); ++i)
            {
                if(!lastAct.getPlayers().get(i).equals(p) && (lastAct.getPlayers().get(i).getField() == attackingField || lastAct.getPlayers().get(i).getField() == pField))
                {
                    attackable.add(lastAct.getPlayers().get(i));
                    Main.p(lastAct.getPlayers().get(i).getName());
                }
            }
            final ListView list = this.current.findViewById(R.id.players_list);
            final Button btn = this.current.findViewById(R.id.func_skill_btn);
            btn.setHint("single");
            final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, attackable, true, 1, list);
            final RuntimeActivity r = this.lastAct;
            final Dialog d = r.getDialog(this.current, R.string.select_only_one_player);
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
                    ((Button)d.findViewById(R.id.doalog_ok)).setText(android.R.string.ok);
                    d.findViewById(R.id.doalog_ok).setOnClickListener(this.secondListener);
                    if(adapter.getSelectedCount() < 1)
                    {
                        d.show();
                    } else if (adapter.getSelectedCount() ==1)
                    {
                        lastAct.findByCode(adapter.getSelectedCodes()[0]).giveDamage(r, 1, false);
                        r.goNext(v);
                        current.finish();
                    }
                }
            });
            lastAct.changePlayer(p);
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<LancerAttack> CREATOR = new Creator<LancerAttack>()
    {
        @Override
        public LancerAttack createFromParcel(Parcel source) {
            return new LancerAttack(source);
        }

        @Override
        public LancerAttack[] newArray(int size) {
            return new LancerAttack[size];
        }
    };
}
