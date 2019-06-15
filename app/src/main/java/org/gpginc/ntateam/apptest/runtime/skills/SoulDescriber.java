package org.gpginc.ntateam.apptest.runtime.skills;

import android.os.Parcel;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class SoulDescriber extends ClazzSkill
{

    public SoulDescriber(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public SoulDescriber(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public SoulDescriber(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Player p = Main.getPlayer((Player) o);
            Random rand = new Random();
            p("This playes is from " + p.getKingdom());
            if(rand.nextInt(25) == 5)p("And is  " + p.getClazz());
            if(rand.nextInt(25)  <5)p("And is in field " + p.getField());
        }
    }
    public static final Creator<SoulDescriber> CREATOR = new Creator<SoulDescriber>()
    {
        @Override
        public SoulDescriber createFromParcel(Parcel source) {
            return new SoulDescriber(source);
        }

        @Override
        public SoulDescriber[] newArray(int size) {
            return new SoulDescriber[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
