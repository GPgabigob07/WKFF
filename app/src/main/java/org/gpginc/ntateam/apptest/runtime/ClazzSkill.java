package org.gpginc.ntateam.apptest.runtime;


import android.app.Activity;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.io.Serializable;

public class ClazzSkill implements Parcelable
{
	private final String name;
	private boolean passiveRun, canBeUsedAsCounter = false;
	protected boolean isCounter = false;
	protected RuntimeActivity current;
	public final Type type;


	@LayoutRes
	@NonNull
	protected int layout;

	public ClazzSkill(String name, Type type, boolean isCounter)
	{
		this.name = name;
		this.type = type;
		this.isCounter =isCounter;
	}
	public ClazzSkill(String name, Type type, boolean isCounter, int layout)
	{
		this(name, type, isCounter);
		this.layout = layout;
	}

	protected ClazzSkill(Parcel in) {
		name = in.readString();
		passiveRun = in.readByte() != 0;
		canBeUsedAsCounter = in.readByte() != 0;
		isCounter = in.readByte() != 0;
		current = in.readParcelable(RuntimeActivity.class.getClassLoader());
		layout = in.readInt();
		this.type =(Type) in.readSerializable();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeByte((byte) (passiveRun ? 1 : 0));
		dest.writeByte((byte) (canBeUsedAsCounter ? 1 : 0));
		dest.writeByte((byte) (isCounter ? 1 : 0));
		dest.writeParcelable(current, flags);
		dest.writeInt(layout);
		dest.writeSerializable(this.type);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<ClazzSkill> CREATOR = new Creator<ClazzSkill>() {
		@Override
		public ClazzSkill createFromParcel(Parcel in) {
			return new ClazzSkill(in);
		}

		@Override
		public ClazzSkill[] newArray(int size) {
			return new ClazzSkill[size];
		}
	};

	public void setCurrent(RuntimeActivity current)
	{
		this.current = current;
	}
	public native void runSkill(@Nullable Object o);
	
	public String getName()
	{
		return this.name;
	}
	public boolean isPassive()
	{
		return this.type.equals(Type.PASSIVE);
	}
	public boolean isAttackTriggered()
	{
		return this.type.equals(Type.ATTACK_TRIGGER);
	}
	public boolean isPassiveRun() {
		return passiveRun;
	}
	public boolean isAttack(){return this.type.equals(Type.ATTACK);}
	public void setPassiveRun(boolean passiveRun) {
		this.passiveRun = passiveRun;
	}
	
	public Type getType()
	{
		return this.type;
	}
	public boolean isCounter() {return this.isCounter;}

	public String toString()
	{
		return this.name;
	}

	public int getLayout()
	{
		return this.layout;
	}

	public enum Type implements Serializable
	{
		MAHOU,
		ATTACK,
		PASSIVE,
		ATTACK_TRIGGER;
	}
}
