package org.gpginc.ntateam.apptest.runtime.events;

import android.os.Parcel;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Events.EFX;

public class DarkWitch extends Event
{
    public DarkWitch() {
        super(R.string.evt_name_dw, R.string.evt_descr_dw, R.string.evt_end_descr_dw, Rarity.RARE, 2, EventHandler.DAMAGE_STEP, false);
    }

    public DarkWitch(Parcel in) {
        super(in);
    }


    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    @Override
    public boolean check(Player p) {
        return !p.isAffected();
    }

    @Override
    public void exe(Player p, RuntimeActivity a)
    {
        a.getPlayers().get(new Random().nextInt(a.getPlayers().size())).affect(EFX.get(new Random().nextInt(EFX.size())));
    }

    public static final Creator<DarkWitch> CREATOR = new Creator<DarkWitch>() {
        @Override
        public DarkWitch createFromParcel(Parcel in) {
            return new DarkWitch(in);
        }

        @Override
        public DarkWitch[] newArray(int size) {
            return new DarkWitch[size];
        }
    };
}
