package org.gpginc.ntateam.apptest.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.view.View;

import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.io.Serializable;

public class Event implements Parcelable
{
    protected Event(Parcel in) {
        name = in.readInt();
        rarity = Rarity.withName(in.readString());
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeString(rarity.R());
        dest.writeInterfaceToken("OnHappend");
    }

    @StringRes
    public int getName() {
        return this.name;
    }

    public interface WhenHappend
    {
        boolean matchSircunstances(Object... params);
    }

    public interface OnHappened
    {
        void happends(Object... params);
    }

    @StringRes
    private final int name;
    private final Rarity rarity;

    private WhenHappend condition;
    private OnHappened whatWillDo;

    public Event(@StringRes int name, Rarity rarity) {
        this.name = name;
        this.rarity = rarity;
    }

    public Event setCondition(WhenHappend condition) {
        this.condition = condition;
        return this;
    }

    public Event setWhatWillDo(OnHappened whatWillDo) {
        this.whatWillDo = whatWillDo;
        return this;
    }
}
