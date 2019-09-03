package org.gpginc.ntateam.apptest.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.view.View;

import org.gpginc.ntateam.apptest.runtime.util.GameEvent;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.io.Serializable;

public abstract class Event implements GameEvent
{
    @StringRes
    private final int name, description;
    private final int maxInGame;
    private final Rarity rarity;
    private boolean needPlayers = false;

    public Event(@StringRes int name, int description, Rarity rarity, int maxInGame) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.maxInGame = maxInGame;
        Events.EVT_MAP.put(name, this);
        Events.EVTS.add(this);
    }
    protected Event(Parcel in) {
        name = in.readInt();
        description = in.readInt();
        rarity = Rarity.withName(in.readString());
        maxInGame = in.readInt();
    }

    protected void setNeedPlayers()
    {
        this.needPlayers =true;
    }
    public Rarity getRarity() {
        return rarity;
    }

    public int getDescription() {
        return description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(description);
        dest.writeString(rarity.R());
    }

    @StringRes
    public int getName() {
        return this.name;
    }
}
