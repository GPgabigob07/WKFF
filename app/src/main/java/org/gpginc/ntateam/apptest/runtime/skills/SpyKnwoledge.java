package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.Intel_SkillUtil.IntelLineAdapter;

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
            Player p = (Player)o;
            Random rand = new Random();
            int cod = p.getCod();
            int i = p.getCod(),a = i,u = a;
            /*while(PLAYERS.get(a) != p)*/
            p(PLAYERS.indexOf(p));
            List<Player> iau = new ArrayList<>();
            List<Integer> gone = new ArrayList<>();
            while(a==cod){a=rand.nextInt(PLAYERS.size());p("a:"+a);}
            gone.add(a);
            while(i==cod){i=rand.nextInt(PLAYERS.size());p("i:"+i);}
            gone.add(i);
            while(u==cod){u=rand.nextInt(PLAYERS.size());p("u:"+u);}
            iau.add(PLAYERS.get(a));
            iau.add(PLAYERS.get(i));
            iau.add(PLAYERS.get(u));

            LinearLayoutManager layoutManager = new LinearLayoutManager(this.current, LinearLayoutManager.VERTICAL,false);

            RecyclerView mR = this.current.findViewById(R.id.intel_list);
            mR.setLayoutManager(layoutManager);

            IntelLineAdapter lAdp = new IntelLineAdapter(iau);
            mR.setAdapter(lAdp);
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
