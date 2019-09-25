package org.gpginc.ntateam.apptest.runtime;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;

import org.gpginc.ntateam.apptest.CurrentPlayer;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.util.GameClazz;
import org.gpginc.ntateam.apptest.runtime.util.InstanciableSkill;
import org.gpginc.ntateam.apptest.runtime.util.Skill;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.ArrayList;
import java.util.List;


public class Clazz implements GameClazz<Clazz>
{
	@StringRes
	private final int name;
	@Nullable
	private Player cPlayer;
	@DrawableRes
	private final int icon;
	public final boolean needInstance;
	private final ArrayList<ClazzSkill> SKILLS = new ArrayList<>();

	private Resources resources;

	public void setResources(Resources resources) {
		this.resources = resources;
	}

	private final Rarity RARITY;


	public boolean enabled = true;

	public int getIcon() {
		return icon;
	}

	public Clazz(@StringRes int name, Rarity r, @DrawableRes int icon, boolean needBase){
		this.name = name;
		this.RARITY = r;
		this.icon = icon;
		Main.p(this.getName());
		if(!needBase)
		{
			Clazzs.CLAZZS.add(this);
			Clazzs.CLAZZ_MAP.put(this.name, this);
		}
		this.needInstance = needBase;
	}

	protected Clazz setAllSkills(List<ClazzSkill> skills)
	{
		for (ClazzSkill sk : skills)
		{
			this.bindSkill(sk, 0);
		}
		return this;}

	/**
	 * Use this constructor for SPY based clazzs
	 */
	public Clazz()
	{
		this.needInstance = false;
		this.name = R.string.clazz_spy;
		this.icon= R.drawable.botao_adicionar;
		this.RARITY = Rarity.MASTERRARE;
	}


	public Rarity getRARITY() {
		return RARITY;
	}

	protected Clazz(Parcel in) {
		name = in.readInt();
		cPlayer = in.readParcelable(Player.class.getClassLoader());
		this.needInstance = in.readByte() ==1;
		enabled = in.readByte() != 0;
		RARITY = Rarity.withName(in.readString());
		icon = in.readInt();
		Bundle b = in.readBundle();
		for(String s : b.getStringArrayList("NAMES"))
		{
			this.SKILLS.add((ClazzSkill)b.getParcelable(s));
		}
	}


	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(name);
		dest.writeParcelable(cPlayer, flags);
		dest.writeByte((byte) (this.needInstance ? 1 : 0));
		dest.writeByte((byte) (enabled ? 1 : 0));
		dest.writeString(this.RARITY.R());
		dest.writeInt(this.icon);
		Bundle b = new Bundle();
		ArrayList<String> names = new ArrayList<>();
		for(ClazzSkill sk : this.SKILLS)
		{
			names.add(sk.getName());
			b.putParcelable(sk.getName(), sk);
		}
		b.putStringArrayList("NAMES", names);
		dest.writeBundle(b);
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
	public Clazz bindSkill(ClazzSkill skill, int flag)
	{
		if(flag == 0)
		{
			if(skill instanceof InstanciableSkill)
			{
				this.SKILLS.add(((InstanciableSkill) skill).newInstance());
			}
			else
			{
				this.SKILLS.add(skill);
			}
		} else this.SKILLS.add(skill);
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

	@Override
	public Clazz newInstance()
	{
		return new Clazz(this.getName(), this.RARITY, this.icon, this.needInstance).setAllSkills(this.SKILLS);
	}

	@Override
	public Clazz base() {
		Clazzs.CLAZZS.add(this);
		Clazzs.CLAZZ_MAP.put(this.name, this);
		return this;
	}
}

