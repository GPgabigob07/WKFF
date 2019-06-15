package org.gpginc.ntateam.apptest.runtime.skills;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class SpyKnwoledge extends ClazzSkill
{

    public SpyKnwoledge(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public SpyKnwoledge(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public SpyKnwoledge(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Random rand = new Random();
            int i,a,u;
            a=rand.nextInt(PLAYERS.size());
            i=rand.nextInt(PLAYERS.size() - a / 2);
            u=rand.nextInt(i + a < PLAYERS.size() ? i + a : PLAYERS.size());
            int[] iau = {a, i, u};
            for(int k : iau)
            {
                p(PLAYERS.get(k).getName() + (rand.nextInt(5) < 2 ? " is " + PLAYERS.get(k).getClazz().getPseudoName() : " belongs to " + PLAYERS.get(k).getKingdom()) + " in field " + PLAYERS.get(k).getField());
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<SpyKnwoledge> CREATOR = new Creator<SpyKnwoledge>()
    {
        @Override
        public SpyKnwoledge createFromParcel(Parcel source) {
            return new SpyKnwoledge(source);
        }

        @Override
        public SpyKnwoledge[] newArray(int size) {
            return new SpyKnwoledge[size];
        }
    };
}
