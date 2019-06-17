package org.gpginc.ntateam.apptest.runtime.skills;

import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;

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
        Main.p("ACTIVATED");
        if(o!= null)
        {
            Player p = (Player) o;
            int pField = p.getField();
            int attackingField = 0;
            final List<Object> attackable = new ArrayList<>();
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
                    Main.p(PLAYERS.get(i).getName());
                }
            }
            final ListView list = ((ListView) this.current.findViewById(R.id.players_list));
            list.setAdapter(new PlayerSelectAdapter(this.current, attackable, true, 1, list));
            /*list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    if(list.getCheckedItemCount() ==0 && list.getCount() > 1)
                    {
                        list.setItemChecked(position, !list.isItemChecked(position));
                    } else if (list.getCount() == 1) {
                        Snackbar.make(view, R.string.lancer_atk_info, Snackbar.LENGTH_SHORT)
                                .setActionTextColor(Color.RED)
                                .setAction("Action", null).show();
                    } else {
                        Snackbar.make(view, R.string.swordman_only_two, Snackbar.LENGTH_SHORT)
                                .setAction("Action", null).show();
                    }
                }
            });*/
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
