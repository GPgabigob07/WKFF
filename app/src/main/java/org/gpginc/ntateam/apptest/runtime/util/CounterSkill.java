package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;

public interface CounterSkill<T extends ClazzSkill> extends IntanciableSkill<T>
{
    T setCounter(int maxCounterTimes);
}
