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
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class Reposition extends ClazzSkill
{

    public Reposition(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public Reposition(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public Reposition(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!= null)
        {
            Player p = (Player) o;
            String cK = p.getKingdom();
            p("Do you want to move someone of your kindom? [y, n]:");
            String ans = input.next();
            if(ans.equals("y"))
            {
                final List<Integer> gone = new ArrayList<>();
                p("Select one to move: ");
                for(Player k : PLAYERS)
                {
                    if(k.getKingdom().equals(cK) && !k.equals(p))
                    {
                        p("["+PLAYERS.indexOf(k) +"] " + k.getName());
                        gone.add(PLAYERS.indexOf(k));
                    }

                }
                int i = input.nextInt();
                while(!gone.contains(i))
                {
                    p("U can only select players above!!");
                    i = input.nextInt();
                }
                p("Where this player will be?");
                int a = input.nextInt();
                while(a < 0 && a > 5 || a==PLAYERS.get(i).getField())
                {
                    p("U can only select players above!!");
                    a = input.nextInt();
                }
                p(PLAYERS.get(i).getName() + " moved to field "+a);
                setDownFieldMemory(PLAYERS.get(i).getField());
                PLAYERS.get(i).setField(a);
                setUpFieldMemory(a);
            } else ;
        }
    }
    public static final Creator<Reposition> CREATOR = new Creator<Reposition>()
    {
        @Override
        public Reposition createFromParcel(Parcel source) {
            return new Reposition(source);
        }

        @Override
        public Reposition[] newArray(int size) {
            return new Reposition[size];
        }
    };
}
