package org.gpginc.ntateam.apptest.runtime;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.gpginc.ntateam.apptest.CurrentPlayer;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.ArrayList;


public class Clazz implements Parcelable
{
	@StringRes
	private final int name;
	@Nullable
	private Player cPlayer;
	private final ArrayList<ClazzSkill> SKILLS = new ArrayList<>();

	private Resources resources;

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	private final Rarity RARITY;


	public boolean enabled = true;
	public Clazz(@StringRes int name, Rarity r){
		this.name = name;
		this.RARITY = r;
		Clazzs.CLAZZS.add(this);
		Clazzs.CLAZZ_MAP.put(this.name, this);
		Main.p(this.getName());
/*		if(this.getClass().getDeclaringClass().getAnnotation(RarityHandler.class) == null)
		{
			throw new AnnotationTypeMismatchException(this.getClass().getMethod("Clazz", String.class), "Clazz declaration error, no rarity handler attached!" +
					"\n at " + this.getClass().getDeclaringClass());
		} else Main.p("Clazz was created perfectly!\n");*/
	}
	/**
	 * Use this constructor for SPY based classes
	 */
	public Clazz()
	{
		this.name = R.string.clazz_spy;

		this.RARITY = Rarity.MASTERRARE;
//		Main.p(this.getClass().getDeclaringClass().getAnnotation(RarityHandler.class).rarity());
	}

/*
ArrayList<String> names =  new ArrayList();
		in.readStringList(names);
for(String name :  names)
		{
			this.SKILLS.add(Clazzs.SKILL_MAP.get(name));
		}

		for(ClazzSkill c : this.SKILLS)
		{
			names.add(c.getName());
		}
		dest.writeStringList(names);
 */

	public Rarity getRARITY() {
		return RARITY;
	}

	protected Clazz(Parcel in) {
		name = in.readInt();
		cPlayer = in.readParcelable(Player.class.getClassLoader());
		ArrayList<String> names =  new ArrayList<>();
		in.readStringList(names);
		for(String name :  names)
		{
			this.SKILLS.add(Clazzs.SKILL_MAP.get(name));
		}
		enabled = in.readByte() != 0;
		RARITY = Rarity.withName(in.readString());
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(name);
		dest.writeParcelable(cPlayer, flags);
		ArrayList<String> names =  new ArrayList<>();
		for(ClazzSkill c : this.SKILLS)
		{
			names.add(c.getName());
		}
		dest.writeStringList(names);
		dest.writeByte((byte) (enabled ? 1 : 0));
		dest.writeString(this.RARITY.R());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Clazz> CREATOR = new Creator<Clazz>() {
		@Override
		public Clazz createFromParcel(Parcel in) {
			return new Clazz(in);
		}

		@Override
		public Clazz[] newArray(int size) {
			return new Clazz[size];
		}
	};

	public Clazz bindSkill(ClazzSkill skill)
	{
		this.SKILLS.add(skill);
		Main.p("Skill: " + skill.getName() + " added to " + this.getName());
		return this;
	}
	public ArrayList<ClazzSkill> getSkills() {
		// TODO Auto-generated method stub
		return this.SKILLS;
	}
	public ClazzSkill getSkillAt(int index)
	{
		return index >=0 && index < this.SKILLS.size() ? this.SKILLS.get(index) : null;
	}
	public boolean hasSkills() {
		// TODO Auto-generated method stub
		return this.SKILLS.size() > 0;
	}
	public void runPassive(CurrentPlayer player)
	{
		for(ClazzSkill s : this.SKILLS)
		{
			if(s.isPassive())
			{
				s.setLastAct(player);
				if(s.hasLayout()) {
					Intent skill = new Intent(player, SkillRun.class);
					skill.putExtra("cskill", s.getName());
					skill.putExtra("PLAYER_EXECUTE", player.currentPlayer());
					player.startActivity(skill);
				} else {
					s.runSkill(player.currentPlayer());
				}
			}
		}
	}
	public void runAttackTrigger(RuntimeActivity player)
	{
		for(ClazzSkill s : this.SKILLS)
		{
			if(s.isAttackTriggered())
			{
				s.setLastAct(player);
				if(s.hasLayout()) {
					Intent skill = new Intent(player, SkillRun.class);
					skill.putExtra("cskill", s.getName());
					skill.putExtra("PLAYER_EXECUTE", player.currentPlayer());
					player.startActivity(skill);
				} else {
					s.runSkill(player.currentPlayer());
				}
			}
		}
	}
	public Player getCurrentPlayer()
	{
		return this.cPlayer;
	}
	public Clazz setCurrentPlayer(@Nullable Player p)
	{
		this.cPlayer = p;
		return this;
	}
	public void renew()
	{
		for(ClazzSkill s : this.SKILLS)
		{
			s.setPassiveRun(false);
		}
	}
	@StringRes
	public int getName()
	{
		return this.name;
	}

	public String getNameLikeStr(Resources rs){return rs.getString(this.name);}

	@NonNull
	@Override
	public String toString() {
		return String.valueOf(this.getName());
	}

	public boolean equals(Clazz clazz)
	{
		return clazz.getName() == this.getName();
	}
}

