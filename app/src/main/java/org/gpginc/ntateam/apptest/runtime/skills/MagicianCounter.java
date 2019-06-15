package org.gpginc.ntateam.apptest.runtime.skills;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;
import static org.gpginc.ntateam.apptest.runtime.Clazzs.*;

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
            Player p = (Player) o;
            if(p.attacked)
            {
                if(p.getAttackers().size() >= 2)
                {
                    p.increaseLifeIn(1);
                    p("You were attacked more than you can counter, only one point of damagewill be restored");
                    p.getLastAttacker().giveDamage(p, 1);
                    p(p.getLastAttacker().getName() + " attack suceffuly countered!");
                } else if (p.getLastAttacker().getClazz().equals(ARCHERY) || p.getLastAttacker().getClazz().equals(SWORDMAN) || p.getLastAttacker().getClazz().equals(LANCER))
                {
                    p.increaseLifeIn(1);
                    p.getLastAttacker().giveDamage(p, 1);
                    p(p.getLastAttacker().getName() + " attack suceffuly countered!");
                }
                else
                {
                    p("You can't counter this player's attack!");
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
