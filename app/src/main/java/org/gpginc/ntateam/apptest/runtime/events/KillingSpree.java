package org.gpginc.ntateam.apptest.runtime.events;

import android.app.Activity;
import android.os.Parcel;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Events;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.TargetEvent;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

public class KillingSpree extends Event implements TargetEvent<KillingSpree> {

    private Player killer, target;

    public KillingSpree() {
        super(R.string.event_killing_spree,R.string.evt_descr_ks, R.string.evt_end_descr_ks, Rarity.RARE, 2, EventHandler.ON_DEATH, true);
        super.setNeedPlayers();
    }
    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    protected KillingSpree(Parcel in) {
        super(in);
        this.killer = in.readParcelable(Player.class.getClassLoader());
        this.target = in.readParcelable(Player.class.getClassLoader());
        super.setNeedPlayers();
    }

    @Override
    public boolean check(Player p)
    {
        if(target.getLastAttacker() instanceof Player)return target.isDead && ((Player)target.getLastAttacker()).getCod() == killer.getCod();
        else return false;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(killer, 0);
        dest.writeParcelable(target, 0);
    }

    @Override
    public void exe(Player p, RuntimeActivity a)
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

    @Override
    public Player getTarget() {
        return this.target;
    }

    @Override
    public KillingSpree setTarget(Player p) {
        p.setAttachedToEvent();
        this.target = p;
        return this;
    }

    @Override
    public Player getOwner() {
        return this.killer;
    }

    @Override
    public KillingSpree setOwner(Player p) {
        p.setAttachedToEvent();
        this.killer = p;
        return this;
    }

    @Override
    public KillingSpree newInstance(Player owner, Player target) {
        return new KillingSpree().setOwner(owner).setTarget(target);
    }

    @Override
    public KillingSpree base() {
        Events.EVT_MAP.put(this.getName(), this);
        Events.EVTS.add(this);
        return this;
    }
}
