package org.gpginc.ntateam.apptest.runtime.util.enums;

import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.util.IntInterval;

public enum Rarity
{
    ALWAYS(),
    COMMOM(IntInterval.HUNDRED_BOUND, 2),
    RARE(new IntInterval(0, 50), 10),
    ULTRARARE(new IntInterval(0,5), 50),
    MASTERRARE(IntInterval.THOUSAND_BOUND, 500);

    @Nullable
    private IntInterval interval;
    @Nullable
    private final int fixedValue;

    Rarity(@Nullable IntInterval interval, @Nullable int fixedValue) {
        this.interval = interval;
        this.fixedValue = fixedValue;
    }
    Rarity()
    {
        this.interval = null;
        this.fixedValue = 0;
    }

    public IntInterval getInterval() {
        return interval;
    }

    public int getFixedValue() {
        return fixedValue;
    }
}
