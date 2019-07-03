package org.gpginc.ntateam.apptest.runtime.util;


import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import org.gpginc.ntateam.apptest.CurrentPlayer;
public interface Skill extends Parcelable
{
    void runSkill(@Nullable Object o);

    Creator getCreator();
}
