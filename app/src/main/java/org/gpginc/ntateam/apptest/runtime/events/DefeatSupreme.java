package org.gpginc.ntateam.apptest.runtime.events;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;

import org.gpginc.ntateam.apptest.GameEnd;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.skills.SwordmanAttack;
import org.gpginc.ntateam.apptest.runtime.util.Util;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

public class DefeatSupreme extends Event
{
    public DefeatSupreme() {
        super(R.string.event_defeat_supreme, R.string.evt_descr_eds, Rarity.ALWAYS, 1, EventHandler.ALWAYS, false);
    }

    public DefeatSupreme(Parcel in) {
        super(in);
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public boolean check(Player p) {
        if(p.getClazz().equals(Clazzs.SUPREME))
        {
            if(p.isDead)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public void exe(Player p, RuntimeActivity a)
    {
        Bundle bn = Util.getEndGame(this, p);
        Intent in = new Intent(a, GameEnd.class);
        in.putExtras(bn);
        in.putExtras(a.enableNext());
        for(Player p2 : a.getPlayers())
        {
            if(p2.isEnemyFrom(p))p2.win();
        }
        a.startActivity(in);
        a.finish();
    }

    public static final Creator<DefeatSupreme> CREATOR = new Creator<DefeatSupreme>()
    {
        @Override
        public DefeatSupreme createFromParcel(Parcel source) {
            return new DefeatSupreme(source);
        }

        @Override
        public DefeatSupreme[] newArray(int size) {
            return new DefeatSupreme[size];
        }
    };
}

