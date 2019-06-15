package org.gpginc.ntateam.apptest.runtime.util;

import android.os.Parcelable;
import android.support.annotation.Nullable;

public interface Skill extends Parcelable
{
    void runSkill(@Nullable Object o);
}
