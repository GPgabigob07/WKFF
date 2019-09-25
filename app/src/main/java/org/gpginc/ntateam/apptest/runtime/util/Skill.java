package org.gpginc.ntateam.apptest.runtime.util;


import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public interface Skill extends Parcelable
{
    void runSkill(@Nullable Object o);

    Creator getCreator();

    public boolean isPassive();
    public boolean isAttackTriggered();
    public boolean isPassiveRun();
    public boolean isAttack();
    public ClazzSkill setPassiveRun(boolean passiveRun);
    void setLastAct(RuntimeActivity r);
    boolean hasLayout();
    String getName();
}
