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

public class SwordmanAttack extends ClazzSkill
{

    public SwordmanAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public SwordmanAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public SwordmanAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!= null)
        {
            p("You can attack 2 players, that are in your field.");
            Player p = (Player) o;
            int pField = p.getField();
            int asd = 1;
            final List<Player> attackable = new ArrayList<>();
            for(int i = 0; i< PLAYERS.size(); ++i)
            {
                if(!PLAYERS.get(i).equals(p)&& PLAYERS.get(i).getField() == pField)
                {
                    attackable.add(PLAYERS.get(i));
                }
            }
            ((ListView) this.current.findViewById(R.id.players_list)).setAdapter(new ArrayAdapter<Player>(this.current, android.R.layout.simple_list_item_1, attackable));
							/*int sP = input.nextInt();
							int sP2 = input.nextInt();
							PLAYERS.get(sP).giveDamage(p, 1);
							PLAYERS.get(sP2).giveDamage(p, 1);*/

        }
    }

    @Override
    public Creator<SwordmanAttack> getCreator() {
        return CREATOR;
    }

    public static final Creator<SwordmanAttack> CREATOR = new Creator<SwordmanAttack>()
    {
        @Override
        public SwordmanAttack createFromParcel(Parcel source) {
            return new SwordmanAttack(source);
        }

        @Override
        public SwordmanAttack[] newArray(int size) {
            return new SwordmanAttack[size];
        }
    };
}
