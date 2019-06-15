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

public class AbsoluteDefense extends ClazzSkill
{

    public AbsoluteDefense(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public AbsoluteDefense(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public AbsoluteDefense(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Player p = (Player) o;
            if(p.attacked && !this.isPassiveRun())
            {
                p("You were attacked by "+p.getLastAttacker().getName() + " from field " + p.getField());
                p("No worries, u won't take any damage");
                p.isProtected = true;
                this.setPassiveRun(true);
            } else if(p.attacked)
            {
                p("attacked");
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<AbsoluteDefense> CREATOR = new Creator<AbsoluteDefense>()
    {
        @Override
        public AbsoluteDefense createFromParcel(Parcel source) {
            return new AbsoluteDefense(source);
        }

        @Override
        public AbsoluteDefense[] newArray(int size) {
            return new AbsoluteDefense[size];
        }
    };
}
