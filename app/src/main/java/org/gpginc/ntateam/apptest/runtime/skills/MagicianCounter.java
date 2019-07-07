package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.util.ArrayList;
import java.util.List;

public class MagicianCounter extends ClazzSkill
{

    public MagicianCounter(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public MagicianCounter(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public MagicianCounter(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            final Player p = (Player) o;
            if(p.attacked)
            {
                final List<Object> attackable = new ArrayList<>();
                final SkillRun sk = this.current;
                attackable.addAll(p.getAttackers());
                final Player countered = p.getAttackers().get(0);
                Main.p("COUNTER TARGET: " +countered.getName());

                final RuntimeActivity r = this.lastAct;
                final ClazzSkill thisSkill = this;


                //Dialog that says wo's attack was countered:
                final Dialog counterDialog = lastAct.getDialog(lastAct, (countered.getName()) + lastAct.getResources().getString(R.string.countered));

                counterDialog.findViewById(R.id.doalog_cancel).setOnClickListener(r.dialogDismiss(counterDialog, false));
                counterDialog.findViewById(R.id.doalog_ok).setOnClickListener(r.dialogDismiss(counterDialog, false));
                counterDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        countered.giveDamage(lastAct, 1);
                        p.increaseLifeIn(1);
                        lastAct.goNext(counterDialog.findViewById(R.id.doalog_ok));
                    }
                });
                //Dialog that says you're at leats twice:
                final Dialog dzin = lastAct.getDialog(lastAct, R.string.magician_counter_info_attackers);
                dzin.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        counterDialog.show();
                    }
                });

                //Dialog that alerts that cannot counter an certain attack
                final Dialog alertz = lastAct.getDialog(lastAct, R.string.you_cant_counter);
                alertz.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        lastAct.goNext(counterDialog.findViewById(R.id.doalog_ok));
                    }
                });

                if(p.getAttackers().size() >= 2)
                {
                    dzin.show();
                } else if (countered.getClazz().equals(Clazzs.ARCHERY) || countered.getClazz().equals(Clazzs.SWORDMAN) || countered.getClazz().equals(Clazzs.LANCER))
                {
                    counterDialog.show();
                }
                else
                {
                    alertz.show();
                }
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<MagicianCounter> CREATOR = new Creator<MagicianCounter>()
    {
        @Override
        public MagicianCounter createFromParcel(Parcel source) {
            return new MagicianCounter(source);
        }

        @Override
        public MagicianCounter[] newArray(int size) {
            return new MagicianCounter[size];
        }
    };
}
