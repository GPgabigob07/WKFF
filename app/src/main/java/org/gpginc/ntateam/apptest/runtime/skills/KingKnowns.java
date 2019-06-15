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

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class KingKnowns extends ClazzSkill
{

    public KingKnowns(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public KingKnowns(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public KingKnowns(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!= null)
        {
            Player p = (Player) o;
            String cK = p.getKingdom();
            p("Those Players Belongs to your kingdom:");
            for(Player k : PLAYERS)
            {
                if(k.getKingdom().equals(cK) && !k.equals(p))
                {
                    p(k.getName()+ " in field: "+k.getField());
                }
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<KingKnowns> CREATOR = new Creator<KingKnowns>()
    {
        @Override
        public KingKnowns createFromParcel(Parcel source) {
            return new KingKnowns(source);
        }

        @Override
        public KingKnowns[] newArray(int size) {
            return new KingKnowns[size];
        }
    };
}
