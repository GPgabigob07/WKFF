package org.gpginc.ntateam.apptest.runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.gpginc.ntateam.apptest.GameEnd;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.events.DefeatSupreme;
import org.gpginc.ntateam.apptest.runtime.util.Util;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Events
{
    public static Map<Integer, Event> EVT_MAP =  new HashMap<>();
    public static List<Event> EVTS = new ArrayList<>();

    public static final Event DEFEAT_SUPREME;

    static
    {
        DEFEAT_SUPREME = new DefeatSupreme();
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
