package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;

public interface TargetEvent<T extends Event>
{
    public Player getTarget();

    public T setTarget(Player p);

    public Player getOwner();

    public T setOwner(Player p);

    T newInstance(Player owner, Player Target);
    T base();
}

