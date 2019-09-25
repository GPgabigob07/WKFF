package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.runtime.Dragon;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public interface DragonEvent<T extends Event> extends InstanciableEvent<T>
{
    String getKingdom();
    T setDragon(RuntimeActivity r, Dragon d);
    void obliterate(Player p);
}
