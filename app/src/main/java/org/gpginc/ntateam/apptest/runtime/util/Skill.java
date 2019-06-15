package org.gpginc.ntateam.apptest.runtime.util;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;

public interface Skill extends Parcelable
{
    void runSkill(@Nullable Object o);

    Creator getCreator();
}
