package org.gpginc.ntateam.apptest.runtime;

import android.app.Activity;
import android.content.Intent;
import android.os.Parcel;

import org.gpginc.ntateam.apptest.GameEnd;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.util.Util;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.HashMap;
import java.util.Map;

public class Events
{
    public static Map<Integer, Event> EVT_MAP =  new HashMap<>();

    public static final Event DEFEAT_SUPREME;

    static
    {
        DEFEAT_SUPREME = new Event(R.string.event_defeat_supreme, Rarity.ALWAYS).setCondition(new Event.WhenHappend() {
            @Override
            public boolean matchSircunstances(Object... params)
            {
                Player arch = (Player) params[0];
                return arch.getClazz().getName() == Clazzs.SUPREME.getName() && arch.isDead;
            }
        }).setWhatWillDo(new Event.OnHappened() {
            @Override
            public void happends(Object... params) {
                Activity a = (Activity) params[0];
                Intent as = new Intent(a, GameEnd.class);
                as.putExtras(Util.getEndGame(Events.DEFEAT_SUPREME, (Player)params[1]));
                a.startActivity(as);
            }
        });
    }
}
