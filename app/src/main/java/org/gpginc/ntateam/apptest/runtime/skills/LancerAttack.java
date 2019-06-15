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

public class LancerAttack extends ClazzSkill
{

    public LancerAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public LancerAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public LancerAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!= null)
        {
            Player p = (Player) o;
            int pField = p.getField();
            int attackingField = 0;
            final List<Player> attackable = new ArrayList<>();
            switch(pField)
            {
                case 1:
                    attackingField = 3;
                    break;
                case 2:
                    attackingField = 4;
                    break;
                case 3:
                    attackingField = 1;
                    break;
                case 4:
                    attackingField = 2;
                    break;
            }

            int asd = 1;
            for(int i = 0; i< PLAYERS.size(); ++i)
            {
                if(!PLAYERS.get(i).equals(p)&& (PLAYERS.get(i).getField() == attackingField | PLAYERS.get(i).getField() == pField))
                {
                    attackable.add(PLAYERS.get(i));
                }
            }
            ((ListView) this.current.findViewById(R.id.players_list)).setAdapter(new ArrayAdapter<>(this.current, android.R.layout.simple_list_item_1, attackable));
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<LancerAttack> CREATOR = new Creator<LancerAttack>()
    {
        @Override
        public LancerAttack createFromParcel(Parcel source) {
            return new LancerAttack(source);
        }

        @Override
        public LancerAttack[] newArray(int size) {
            return new LancerAttack[size];
        }
    };
}
