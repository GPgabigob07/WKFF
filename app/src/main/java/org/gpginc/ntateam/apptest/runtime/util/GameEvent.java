package org.gpginc.ntateam.apptest.runtime.util;

import android.app.Activity;
import android.os.Parcelable;

import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public interface GameEvent extends Parcelable
{

    boolean check(Player p);

    void exe(Player p, RuntimeActivity a);
}
