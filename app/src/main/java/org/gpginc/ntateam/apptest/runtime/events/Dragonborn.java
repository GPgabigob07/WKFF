package org.gpginc.ntateam.apptest.runtime.events;

import android.os.Parcel;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.Dragon;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Events;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.DragonEvent;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;
import java.util.Random;

public class Dragonborn extends Event implements DragonEvent<Dragonborn>
{

    private int agressivity;
    private Dragon dragon;
    public Dragonborn() {
        super(R.string.evt_name_db, R.string.evt_dscr_db, R.string.evt_end_dscr_db, Rarity.RARE, 2, EventHandler.ALWAYS, true);
    }

    protected Dragonborn(Parcel in) {
        super(in);
        agressivity = in.readInt();
        dragon = in.readParcelable(Dragon.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeInt(agressivity);
        dest.writeParcelable(dragon, flags);
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public boolean check(Player p) {
        if(p.getKingdom().equals(this.dragon.getKingdom())) {
            switch (this.agressivity) {
                case 0:
                    return p.life() <= 0;
                case 1:
                    return p.life() <= 1;
                case 2:
                    return p.life() < 3;
            }
        }
        return false;
    }

    @Override
    public void exe(Player p, RuntimeActivity a)
    {
        switch (this.agressivity) {
            case 0:
                Player p2;
                do {
                    p2 = a.getPlayers().get(new Random().nextInt(a.getPlayers().size()));
                } while(p2.getKingdom()==dragon.getKingdom());
                p2.giveDamage(this.dragon, 1);
                break;
            case 1:
                Player p3, p4;
                do {
                    p3 = a.getPlayers().get(new Random().nextInt(a.getPlayers().size()));
                    p4 = a.getPlayers().get(new Random().nextInt(a.getPlayers().size()));
                } while((p3.getKingdom().equals(dragon.getKingdom()) && p4.getKingdom().equals(dragon.getKingdom())) || p3.equals(p4));
                p3.giveDamage(this.dragon, 1);
                p4.giveDamage(this.dragon, 1);
                break;
            case 2:
                dragon.canHeavyAttack = true;
                dragon.heavyAttack(a, false);
        }
    }

    @Override
    public Dragonborn newInstance(@Nullable RuntimeActivity r) {
        if(r.getDragons().size() == 0) return new Dragonborn().setDragon(r, Dragon.bornToKingdom(new Random().nextInt(100) <= 50 ? "OHXER" : "CAMELOT")).setAgressivity(new Random().nextInt(3));
        else return new Dragonborn().setDragon(r, Dragon.bornToKingdom(r.getDragons().get(0).getKingdom().equals("OHXER") ? "CAMELOT" : "OHXER")).setAgressivity(new Random().nextInt(3));

    }

    @Override
    public Dragonborn base() {
        Events.EVT_MAP.put(this.getName(), this);
        Events.EVTS.add(this);
        return this;
    }

    public static final Creator<Dragonborn> CREATOR = new Creator<Dragonborn>()
    {
        @Override
        public Dragonborn createFromParcel(Parcel source) {
            return new Dragonborn(source);
        }

        @Override
        public Dragonborn[] newArray(int size) {
            return new Dragonborn[size];
        }
    };

    protected Dragonborn setAgressivity(int a)
    {
        Main.p("/*****************************************/\nAgrassivity = "+a+"\n/***********************************************************/");
        this.agressivity = a;
        return this;
    }


    @Override
    public String getKingdom() {
        return this.dragon.getKingdom();
    }

    @Override
    public Dragonborn setDragon(RuntimeActivity r, Dragon d)
    {
        Main.p("DRAGON BORN!! " + d.getNameAsString());
        this.dragon = d;
        r.addDragon(d);
        return this;
    }
    @Override
    public void obliterate(Player p) {

    }
}
