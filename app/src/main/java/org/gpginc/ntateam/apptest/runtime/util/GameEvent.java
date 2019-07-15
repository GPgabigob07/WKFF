package org.gpginc.ntateam.apptest.runtime.util;

import android.app.Activity;
import android.os.Parcelable;

import org.gpginc.ntateam.apptest.runtime.Player;

public interface GameEvent extends Parcelable
{

    boolean check(Player p);

    void exe(Player p, Activity a);
}
