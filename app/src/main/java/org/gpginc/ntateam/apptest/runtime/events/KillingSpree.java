package org.gpginc.ntateam.apptest.runtime.events;

import android.app.Activity;
import android.os.Parcel;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

public class KillingSpree extends Event {

    private Player killer, target;

    public Player getKiller() {
        return killer;
    }

    public void setKiller(Player killer) {
        this.killer = killer;
    }

    public Player getTarget() {
        return target;
    }

    public void setTarget(Player target) {
        this.target = target;
    }

    public KillingSpree() {
        super(R.string.event_killing_spree, R.string.evt_descr_ks, Rarity.RARE, 2);
        super.setNeedPlayers();
    }

    protected KillingSpree(Parcel in) {
        super(in);
        super.setNeedPlayers();
    }

    @Override
    public boolean check(Player p) {
        return target.isDead && target.getLastAttacker().getCod() == killer.getCod();
    }

    @Override
    public void exe(Player p, Activity a)
    {
        killer.isWinner = true;
    }

    public static final Creator<KillingSpree> CREATOR = new Creator<KillingSpree>()
    {
        @Override
        public KillingSpree createFromParcel(Parcel source) {
            return new KillingSpree(source);
        }

        @Override
        public KillingSpree[] newArray(int size) {
            return new KillingSpree[size];
        }
    };
}
