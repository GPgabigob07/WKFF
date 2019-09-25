package org.gpginc.ntateam.apptest.runtime.events.effects;

import android.os.Parcel;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Effect;
import org.gpginc.ntateam.apptest.runtime.Events;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.InstanciableEffect;


//TODO: FUTURE IMPLEMENTATION - EFFECTS
public class Poison extends Effect implements InstanciableEffect<Poison> {


    public Poison() {
        super(R.string.efx_poison_name, R.string.efx_poison_description, 2);
    }

    public Poison(Parcel in)
    {
        super(in);
    }
    @Override
    public void apply(Player p) {
        if(this.currentUsages > 0)
            p.effectDamage(1);
        this.consume();
        if(this.currentUsages <= 0)p.antidote(this);
    }

    @Override
    public void consume() {
        this.currentUsages -= 1;
    }

    @Override
    public Poison base() {
        Events.EFX.add(this);
        return this;
    }

    @Override
    public Poison newInstance() {
        return new Poison();
    }

    public static final Creator<Poison> CREATOR = new Creator<Poison>()
    {
        @Override
        public Poison createFromParcel(Parcel source) {
            return new Poison(source);
        }

        @Override
        public Poison[] newArray(int size) {
            return new Poison[size];
        }
    };
}
