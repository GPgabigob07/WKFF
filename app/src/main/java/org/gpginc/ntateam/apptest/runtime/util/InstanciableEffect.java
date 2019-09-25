package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.runtime.Effect;

public interface InstanciableEffect<T extends Effect>
{
    T base();
    T newInstance();

}
