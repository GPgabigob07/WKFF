package org.gpginc.ntateam.apptest.runtime.util.enums;

import org.gpginc.ntateam.apptest.runtime.Event;

public enum EventHandler
{
    ALWAYS,
    DAMAGE_STEP,
    ON_DEATH;

    public static final EventHandler[] HANDLERS = {ALWAYS, DAMAGE_STEP, ON_DEATH};

    public static EventHandler withName(String name)
    {
        for(EventHandler evt : HANDLERS)
        {
            if(evt.name().equals(name)) return evt;
        }
        return ALWAYS;
    }

}
