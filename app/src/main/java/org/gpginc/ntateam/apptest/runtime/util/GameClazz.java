package org.gpginc.ntateam.apptest.runtime.util;

import android.os.Bundle;
import android.os.Parcelable;

public interface GameClazz<T> extends Parcelable
{
    T newInstance();
    T base();
}
