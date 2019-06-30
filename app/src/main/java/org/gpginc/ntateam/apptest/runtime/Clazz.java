package org.gpginc.ntateam.apptest.runtime;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.CurrentPlayer;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Clazz implements Parcelable
{
	private final String name, pseudoName;
	@Nullable
	private Player cPlayer;
	private final ArrayList<ClazzSkill> SKILLS = new ArrayList<>();

	public boolean enabled = true;
	public Clazz(String name)
	{
		this.name = name;
		String[] namep = name.split(" ");
		String a = "";
		for(String s : namep)
		{
			a+= s.split("")[0];
			Main.p("\n\n\n\n\n\n\n\n\n\n"+s);
		}
		this.pseudoName = a;
		Main.p(this.name +" "+ this.pseudoName);
		Clazzs.CLAZZS.add(this);
		Clazzs.CLAZZ_MAP.put(this.name, this);
	}
	/**
	 * Use this constructor for SPY based classes
	 */
	public Clazz()
	{
		this.name = "SPY";
		this.pseudoName = "SPY";
	}


	protected Clazz(Parcel in) {
		name = in.readString();
		pseudoName = in.readString();
		cPlayer = in.readParcelable(Player.class.getClassLoader());
		ArrayList<String> names =  new ArrayList();
		in.readStringList(names);
		for(String name :  names)
		{
			this.SKILLS.add(Clazzs.SKILL_MAP.get(name));
		}
		enabled = in.readByte() != 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeString(pseudoName);
		dest.writeParcelable(cPlayer, flags);
		ArrayList<String> names =  new ArrayList();
		for(ClazzSkill c : this.SKILLS)
		{
			names.add(c.getName());
		}
		dest.writeStringList(names);
		dest.writeByte((byte) (enabled ? 1 : 0));
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
	public String getPseudoName()
	{
		return !this.pseudoName.equals(null) ? this.pseudoName : "namebug";
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

	public String getName()
	{
		return this.name;
	}
}
