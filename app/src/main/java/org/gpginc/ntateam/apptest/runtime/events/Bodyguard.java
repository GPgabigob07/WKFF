package org.gpginc.ntateam.apptest.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Events;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.TargetEvent;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

public class Bodyguard extends Event implements TargetEvent<Bodyguard> 
{

    private Player bodyguard, guarded;

    public Bodyguard() {
        super(R.string.evt_name_bg, R.string.evt_descr_bg, R.string.evt_end_descr_bg, Rarity.ULTRARARE, 2, EventHandler.ON_GAME_END, true);
        super.setNeedPlayers();
    }

    public Bodyguard(Parcel in) {
        super(in);
        this.bodyguard = in.readParcelable(Player.class.getClassLoader());
        this.guarded = in.readParcelable(Player.class.getClassLoader());
        super.setNeedPlayers();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.bodyguard, 0);
        dest.writeParcelable(this.guarded, 0);
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public boolean check(Player p) {
        return !guarded.isDead;
    }

    @Override
    public void exe(Player p, RuntimeActivity a) {
        this.bodyguard.isWinner = true;
    }

    @Override
    public Player getTarget() {
        return guarded;
    }

    @Override
    public Bodyguard setTarget(Player p)
    {
        p.setAttachedToEvent();
        this.guarded = p;
        return this;
    }

    @Override
    public Player getOwner() {
        return bodyguard;
    }

    @Override
    public Bodyguard setOwner(Player p) {
        p.setAttachedToEvent();
        this.bodyguard = p;
        return this;
    }

    @Override
    public Bodyguard newInstance(Player target, Player owner) {
        return new Bodyguard().setOwner(owner).setTarget(target);
    }

    @Override
    public Bodyguard base() {
        Events.EVT_MAP.put(this.getName(), this);
        Events.EVTS.add(this);
        return this;
    }

    public static final Creator<Bodyguard> CREATOR = new Creator<Bodyguard>()
    {
        @Override
        public Bodyguard createFromParcel(Parcel source) {
            return new Bodyguard(source);
        }

        @Override
        public Bodyguard[] newArray(int size) {
            return new Bodyguard[size];
        }
    };
}
