package org.gpginc.ntateam.apptest.runtime;


import android.os.Parcel;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.gpginc.ntateam.apptest.CurrentPlayer;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.Skill;

import java.io.Serializable;

public abstract class ClazzSkill implements Skill {
	private final String name;
	private boolean passiveRun;
	protected boolean isCounter = false;

	@Nullable
	protected RuntimeActivity lastAct = null;
	protected SkillRun current;
	public final Type type;


	@LayoutRes
	@NonNull
	protected int layout = -1;

	public ClazzSkill(String name, Type type, boolean isCounter)
	{
		this.name = name;
		this.type = type;
		this.isCounter =isCounter;
		Clazzs.SKILL_MAP.put(this.name, this);
	}
	public ClazzSkill(String name, Type type, boolean isCounter, int layout)
	{
		this(name, type, isCounter);
		this.layout = layout;
	}

	public ClazzSkill(Parcel in)
	{
		this(in.readString(), (Type) in.readSerializable(), in.readByte() != 0, in.readInt());
		this.setLastAct((RuntimeActivity) in.readParcelable(RuntimeActivity.class.getClassLoader()));
	}
	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(this.name);
		dest.writeSerializable(this.type);
		dest.writeByte((byte) (this.isCounter ? 1 : 0));
		dest.writeInt(this.layout);
		dest.writeParcelable(this.lastAct, flags);
	}
	public void setCurrent(SkillRun current)
	{
		this.current = current;
	}

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

	public int describeContents()
	{
		return 0;
	}

	@Nullable
	public RuntimeActivity getLastAct() {
		return lastAct;
	}

	public void setLastAct(@Nullable RuntimeActivity lastAct) {
		this.lastAct = lastAct;
	}

	public enum Type implements Serializable
	{
		MAHOU,
		ATTACK,
		PASSIVE,
		ATTACK_TRIGGER;

	}
}
