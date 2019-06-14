package org.gpginc.ntateam.apptest.runtime;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.io.Serializable;

abstract class ClazzSkill implements Serializable {
	private final String name;
	private boolean passiveRun, canBeUsedAsCounter = false;
	protected boolean isCounter = false;
	protected SkillRun current;
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

	public void setCurrent(SkillRun current)
	{
		this.current = current;
	}


	public abstract void runSkill(@Nullable Object o);
	
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
