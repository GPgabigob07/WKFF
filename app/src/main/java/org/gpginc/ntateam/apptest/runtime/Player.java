package org.gpginc.ntateam.apptest.runtime;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;

import org.gpginc.ntateam.apptest.Dragon;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("UnusedReturnValue")
public class Player implements Parcelable
{
	private int lifePoints = 3, damageTaken, currentField, cod;
	public boolean isStunned, attacked, isBlind, isProtected, isDragonProtected, isDead, isWinner, attachedToEvent;
	private String kingdom;
	private Clazz clazz;
	private List<Player> attackers = new ArrayList<>();
	private Parcelable lastAttacker;
	private final String name;
	//private boolean[] bools = new boolean[]{this.isBlind, this.isProtected, this.isStunned, this.isDragonProtected};
	
	public Player(String name, Clazz clazz, String kingdom) 
	{
		this.clazz = clazz;
		this.kingdom = kingdom;
		this.name = name;
		Main.PLAYERS.add(this);
	}
	public Player(String name)
	{
		this.name = name;
	}

    protected Player(Parcel in) {
        lifePoints = in.readInt();
        damageTaken = in.readInt();
        currentField = in.readInt();
        cod = in.readInt();
        isStunned = in.readByte() != 0;
        attacked = in.readByte() != 0;
        isBlind = in.readByte() != 0;
        isProtected = in.readByte() != 0;
        isDragonProtected = in.readByte() != 0;
        isDead = in.readByte() != 0;
        isWinner = in.readByte() != 0;
        attachedToEvent = in.readByte() != 0;
        kingdom = in.readString();
        clazz = in.readParcelable(Clazz.class.getClassLoader());
        attackers = in.createTypedArrayList(Player.CREATOR);
        lastAttacker = in.readParcelable(Player.class.getClassLoader());
        name = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(lifePoints);
        dest.writeInt(damageTaken);
        dest.writeInt(currentField);
        dest.writeInt(cod);
        dest.writeByte((byte) (isStunned ? 1 : 0));
        dest.writeByte((byte) (attacked ? 1 : 0));
        dest.writeByte((byte) (isBlind ? 1 : 0));
        dest.writeByte((byte) (isProtected ? 1 : 0));
        dest.writeByte((byte) (isDragonProtected ? 1 : 0));
        dest.writeByte((byte) (isDead ? 1 : 0));
        dest.writeByte((byte) (isWinner ? 1 : 0));
        dest.writeByte((byte) (attachedToEvent ? 1 : 0));
        dest.writeString(kingdom);
        dest.writeParcelable(clazz, flags);
        dest.writeTypedList(attackers);
        dest.writeParcelable(lastAttacker, flags);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };

    public Clazz getClazz()
	{
		//this.clazz.setCurrentPlayer(this);
		return this.clazz;
	}

	public int getDamage() {
		return damageTaken;
	}

	public String getKingdom()
	{
		return this.kingdom;
	}
	public String getName()
	{
		return this.name;
	}
	public Player setClazz(Clazz clazz)
	{
		this.clazz = clazz;
		return this;
	}
	public Player setKingdom(String kingdom)
	{
		this.kingdom = kingdom;
		return this;
	}
	public Player setField(int i)
	{
		this.currentField = i;
		return this;
	}
	public Player withCod(int cod)
	{
		this.cod = cod;
		return this;
	}

	public void setDragonProtected()
	{
		this.isDragonProtected = true;
	}
	public int getCod() {
		return cod;
	}

	public static String withName(Player p)
	{
		return p.getName();
	}
	public boolean hasKingdom()
	{
		return this.kingdom != null;
	}
	public boolean hasClazz()
	{
		return this.clazz != null;
	}
	
	public void showField()
	{
		switch(this.currentField)
		{
		case 1:
			Main.p("    *    ");
			Main.p(" X  *    ");
			Main.p("    *    ");
			Main.p("*********");
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("    *    ");
			break;
		case 2:
			Main.p("    *    ");
			Main.p("    *  X ");
			Main.p("    *    ");
			Main.p("*********");
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("    *    ");
			break;
		case 3:
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("*********");
			Main.p("    *    ");
			Main.p("    *  X ");
			Main.p("    *    ");
			break;
		case 4:
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("    *    ");
			Main.p("*********");
			Main.p("    *    ");
			Main.p(" X  *    ");
			Main.p("    *    ");
			break;
		case 5:
			break;
		}
		
	}

	public boolean isEnemyFrom(Player p)
    {
        return this.clazz.getName() == R.string.clazz_spy || p.getKingdom() != this.kingdom;
    }

    public void win()
    {
        this.isWinner = true;
    }

	public int getField()
	{
		return this.currentField;
	}
	public void giveDamage(RuntimeActivity player, int i, boolean asCounter)
	{
		if(!asCounter) {
			this.attacked = true;
			this.lastAttacker = player.currentPlayer();
			this.attackers.add(player.currentPlayer());

			Main.p(this.getName() + " was attacked by " + player.currentPlayer().getName() + "\n life:" + this.lifePoints + "" +
					"\n damage taken:" + this.damageTaken +
					"\n Attacked: " + this.attacked
			);
		}
		this.damageTaken += i;
	}

	public void giveDamage(Dragon d, int i)
	{
		this.attacked = true;
		this.lastAttacker = d;
		this.lifePoints-=i;

		Main.p(this.getName() + " was attacked by " + d.getNameAsString() + "\n life:" + this.lifePoints + "" +
				"\n Attacked: " + this.attacked
		);

	}

	public boolean dragonAttack(Dragon d, boolean ignore)
	{
		if(!ignore && d.getKingdom() != this.kingdom) {
			this.lifePoints = isDragonProtected ? life() : 0;
			this.isDead = true;
			return true;
		} else {
			this.lifePoints = isDragonProtected ? life() : 0;
			this.isDead = true;
			return true;
		}
	}

	public Player damageStep(RuntimeActivity r)
	{
		if(this.isDragonProtected)
		{
			for(Dragon d : r.getDragons())
			{
				if(d.isProtecting && d.getProtectedOne().equals(this))
				{
					d.giveDamage(this.damageTaken);
				}
			}
		}
		else
		{
			this.lifePoints -= isProtected ? 0 : this.damageTaken;
		}
		return this;
	}
	public int life()
	{
		return this.lifePoints;
	}
	public Player re_setup()
	{
		this.attacked = false;
		this.attackers.clear();
		this.damageTaken = 0;
		this.isProtected = false;
		this.clazz.renew();
		return this;
	}
	@Nullable
	public List<Player> getAttackers()
	{
		final List<Player> players = new ArrayList<>();
		for(Parcelable p : this.attackers)
		{
			players.add((Player)p);
		}
		return players;
	}
	public Parcelable getLastAttacker()
	{
		return this.lastAttacker;
	}
	public Player increaseLifeIn(int i)
	{
		this.damageTaken -=i;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Player player = (Player) o;
		return name.equals(player.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	public String toString()
	{
		return this.name;
	}

    public void kill() {
	    this.isDead = (!this.isDragonProtected) ? true : false;
    }

    public void setAttachedToEvent() {
        this.attachedToEvent = true;
    }

    public Player setProtected()
	{
		this.isProtected = true;
		return this;
	}
}