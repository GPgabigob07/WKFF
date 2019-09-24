package org.gpginc.ntateam.apptest.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.view.View;

import org.gpginc.ntateam.apptest.runtime.util.GameEvent;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.io.Serializable;

public abstract class Event implements GameEvent
{
    @StringRes
    private final int name, description, endDescription;
    private final int maxInGame;
    private final Rarity rarity;
    private final EventHandler handler;
    public boolean needPlayers = false;

    /**
     * USed to create some events that might set who wins in the end;
     * @param name Resource name
     * @param endDescription Resource endDescription
     * @param rarity {@link Rarity} rarity
     * @param maxInGame How many equals events can be in one game;
     * @param handler Basically, the runtime will call this event depending on it handler.
     * @param needBase This is set as true when the event need to be duplicated, as {@link org.gpginc.ntateam.apptest.runtime.events.KillingSpree}
     */
    public Event(@StringRes int name, @StringRes int description,@StringRes int endDescription, Rarity rarity, int maxInGame, EventHandler handler, boolean needBase) {
        this.name = name;
        this.rarity = rarity;
        this.description = description;
        this.endDescription = endDescription;
        this.maxInGame = maxInGame;
        this.handler = handler;
        if(!needBase)
        {
            Events.EVT_MAP.put(name, this);
            Events.EVTS.add(this);
        }
    }
    protected Event(Parcel in) {
        name = in.readInt();
        description = in.readInt();
        endDescription = in.readInt();
        rarity = Rarity.withName(in.readString());
        handler = EventHandler.withName(in.readString());
        maxInGame = in.readInt();
    }

    protected void setNeedPlayers()
    {
        this.needPlayers =true;
    }
    public Rarity getRarity() {
        return rarity;
    }

    public int getEndDescription() {
        return endDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(description);
        dest.writeInt(endDescription);
        dest.writeString(rarity.R());
        dest.writeString(handler.name());
        dest.writeInt(maxInGame);
    }

    @StringRes
    public int getName() {
        return this.name;
    }

    public EventHandler getHandler() {
        return handler;
    }

    public int getMax() {
        return maxInGame;
    }

    public int getDescription() {
        return description;
    }

    public abstract Creator getCreator();
}
