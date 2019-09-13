package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;

import java.util.ArrayList;
import java.util.List;

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
            final SkillRun sk = this.current;
            for(int i = 0; i< lastAct.getPlayers().size(); ++i)
            {
                if(!(lastAct.getPlayers().get(i).getName().equals(p.getName())) && lastAct.getPlayers().get(i).getField() == pField)
                {
                    attackable.add(lastAct.getPlayers().get(i));
                    Main.p(lastAct.getPlayers().get(i).getName());
                }
            }
            //Test if there's someone in the player field, if not, advice player to move from field
            if(attackable.size() <=0)
            {
                final Dialog d2 = this.lastAct.getDialog(this.current, R.string.advice_no_players);
                d2.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        sk.finish();
                    }
                });
                d2.show();
            }
            else {
                /* Find and setup list and buttons functions*/
                final ListView list = this.current.findViewById(R.id.players_list);
                final Button btn = this.current.findViewById(R.id.func_skill_btn);
                btn.setHint("single");
                final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, attackable, true, 2, list);
                final RuntimeActivity r = this.lastAct;
                final ClazzSkill thisSkill = this;

                list.setAdapter(adapter);

                final Dialog d = r.getDialog(this.current, R.string.swordman_only_two);
                d.findViewById(R.id.doalog_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
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
                    public void onClick(View v) {
                        ((Button) d.findViewById(R.id.doalog_ok)).setText(R.string.attack_twice);
                        d.findViewById(R.id.doalog_ok).setOnClickListener(this.secondListener);
                        if (adapter.getSelectedCount() < 2 && !btn.getHint().equals("twice")) {
                            d.show();
                        } else if (adapter.getSelectedCount() < 2) {
                            lastAct.findByCode(adapter.getSelectedCodes()[0]).giveDamage(r, 2, false);
                            r.goNext(v);
                        } else {
                            lastAct.findByCode(adapter.getSelectedCodes()[0]).giveDamage(r, 1, false);
                            lastAct.getPlayers().get(adapter.getSelectedCodes()[1]).giveDamage(r, 1, false);
                            r.goNext(v);
                        }

                    }
                });
            }
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
