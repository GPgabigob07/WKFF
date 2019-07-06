package org.gpginc.ntateam.apptest.runtime.util.enums;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.util.IntInterval;

import java.io.Serializable;

public enum Rarity
    {
    ALWAYS(0),
    COMMON(100),
    RARE(35),
    ULTRARARE(15),
    MASTERRARE(1);


    public static final Rarity[] RARITIES = {COMMON, RARE, ULTRARARE, MASTERRARE};

    private final int p100t;

    Rarity(@Nullable int  percent) {
        this.p100t = percent;
    }

    public int getPercent() {
        return p100t;
    }

    public static Rarity withName(String name)
    {
        for(Rarity r : RARITIES)
        {
            if(r.R().equals(name)) return r;
        }
        return Rarity.ALWAYS;
    }

    public String R()
    {
        return this.name();
    }
}
