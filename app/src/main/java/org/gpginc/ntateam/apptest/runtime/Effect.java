package org.gpginc.ntateam.apptest.runtime;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class Effect implements Parcelable
{
    protected final int name, description, turnsDuration;
    protected int currentUsages;

    public Effect(int name, int description, int turnsDuration) {
        this.name = name;
        this.description = description;
        this.turnsDuration = turnsDuration;
        this.currentUsages = turnsDuration;
    }

    protected Effect(Parcel in) {
        name = in.readInt();
        description = in.readInt();
        turnsDuration = in.readInt();
        currentUsages = in.readInt();
    }

    public abstract void apply(Player p);
    public abstract void consume();


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeInt(description);
        dest.writeInt(turnsDuration);
        dest.writeInt(currentUsages);
    }
}
