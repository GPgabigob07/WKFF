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
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class ArcherAttack extends ClazzSkill
{

    public ArcherAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public ArcherAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public ArcherAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        Random rand = new Random();
        final List<Player> attackable = new ArrayList<>();
        if(o!= null)
        {
            Player p = (Player) o;
            int asd = 0;
            for(Player pP : PLAYERS)
            {
                attackable.add(pP);
            }
            ((ListView) this.current.findViewById(R.id.players_list)).setAdapter(new ArrayAdapter<Player>(this.current, android.R.layout.simple_list_item_1, attackable));
            int a = p.getField();
            while(a==p.getField())a = rand.nextInt(4) + 1;
            setDownFieldMemory(p.getField());
            setUpFieldMemory(a);
            p.setField(a);
            p("You'd moved to field " + a);
        }

    }
    public static final Creator<ArcherAttack> CREATOR = new Creator<ArcherAttack>()
    {
        @Override
        public ArcherAttack createFromParcel(Parcel source) {
            return new ArcherAttack(source);
        }

        @Override
        public ArcherAttack[] newArray(int size) {
            return new ArcherAttack[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
