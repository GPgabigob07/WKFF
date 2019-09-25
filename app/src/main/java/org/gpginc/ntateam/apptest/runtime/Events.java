package org.gpginc.ntateam.apptest.runtime;

import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.gpginc.ntateam.apptest.runtime.events.Bodyguard;
import org.gpginc.ntateam.apptest.runtime.events.DefeatSupreme;
import org.gpginc.ntateam.apptest.runtime.events.Dragonborn;
import org.gpginc.ntateam.apptest.runtime.events.KillingSpree;
import org.gpginc.ntateam.apptest.runtime.events.DarkWitch;
import org.gpginc.ntateam.apptest.runtime.events.effects.Poison;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events
{
    public static Map<Integer, Event> EVT_MAP =  new HashMap<>();
    public static List<Effect> EFX = new ArrayList<>();
    public static List<Event> EVTS = new ArrayList<>();

    public static final Event DEFEAT_SUPREME;
    public static final Event KILLING_SPREE;
    public static final Event BODYGUARD;
    public static final Event DRAGONBORN;
    public static final Event DARK_WITCH;


    public static final Effect POISON;
    static {
        DEFEAT_SUPREME = new DefeatSupreme();
        KILLING_SPREE = new KillingSpree().base();
        BODYGUARD = new Bodyguard().base();
        DRAGONBORN = new Dragonborn().base();
        DARK_WITCH = new DarkWitch();

        POISON = new Poison().base();
    }

    @Nullable
    public static Event byName(@StringRes int name)
    {
        if(EVT_MAP.containsKey(name))
        {
            return EVT_MAP.get(name);
        } else {
            Main.p("Event not found");
            return null;
        }
    }

}
