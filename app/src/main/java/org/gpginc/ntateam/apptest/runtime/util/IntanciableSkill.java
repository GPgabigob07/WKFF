package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;

public interface IntanciableSkill<T extends ClazzSkill>
{
    T newInstance();
    T base();
}
