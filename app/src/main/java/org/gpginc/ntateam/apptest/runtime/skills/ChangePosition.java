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
import static org.gpginc.ntateam.apptest.runtime.Main.input;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class ChangePosition extends ClazzSkill
{

    public ChangePosition(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public ChangePosition(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public ChangePosition(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Player p = (Player) o;
            int lastField = p.getField();
            int newField = lastField;
            p("Type the field you want to go, [1, 4]:");
            while(newField == lastField)
            {
                newField = input.nextInt();
                if(newField == lastField)p("You need to choose a different field");
            }
        }
    }

    public static final Creator<ChangePosition> CREATOR = new Creator<ChangePosition>()
    {
        @Override
        public ChangePosition createFromParcel(Parcel source) {
            return new ChangePosition(source);
        }

        @Override
        public ChangePosition[] newArray(int size) {
            return new ChangePosition[size];
        }
    };

    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
