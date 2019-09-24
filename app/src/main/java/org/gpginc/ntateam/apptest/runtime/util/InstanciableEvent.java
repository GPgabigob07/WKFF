package org.gpginc.ntateam.apptest.runtime.util;

import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public interface InstanciableEvent<T extends Event>
{
    T newInstance(@Nullable RuntimeActivity r);
    T base();
}
