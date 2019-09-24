package org.gpginc.ntateam.apptest;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.design.shape.ShapePath;

import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

import java.util.ArrayList;
import java.util.List;

public class Dragon implements Parcelable
{
    @StringRes
    private final int name;

    /*FLAGS FOR ACTIONS*/
    private int[] flags =
            {
                    32/*Is Sleepness*/,
                    33/*Is Protecting*/,
                    34/*both*/,
                    35/*Already Attacked this turn*/,
                    36/*When attack had failed*/
            };
    private final String kingdom;
    private int life, maxDamage, takenDamage;
    public boolean turnAttack, isSleepness, isProtecting;

    private Player protectedOne, dragonHunter;
    /*Dragon hunter shared properties*/
    public boolean isOwned, isSideChanged, canLightAttack, canHeavyAttack, canMultiAttack, canObliterate;


    protected Dragon(@StringRes int name, String kingdom)
    {
        this.name = name;
        this.kingdom = kingdom;
        this.life = 5;
    }

    protected Dragon(Parcel in) {
        name = in.readInt();
        kingdom = in.readString();
        life = in.readInt();
        maxDamage = in.readInt();
        takenDamage = in.readInt();
        turnAttack = in.readByte() != 0;
        isSleepness = in.readByte() != 0;
        isOwned = in.readByte() != 0;
        isSideChanged = in.readByte() != 0;
        canLightAttack = in.readByte() != 0;
        canHeavyAttack = in.readByte() != 0;
        canMultiAttack = in.readByte() != 0;
        canObliterate = in.readByte() != 0;
    }

    public static Dragon bornToKingdom(String kingdom)
    {
        return kingdom == "OHXER" ?
                new Dragon(R.string.dragon_quasar_name, kingdom) : new Dragon(R.string.dragon_lancelot_name, kingdom);
    }

    public int attack(Player player)
    {
        if(!turnAttack) {
            if (!isSleepness && !isProtecting) {
                if(player.dragonAttack(this, false))this.turnAttack = true;
                else return flags[4];
                return 1;
            } else if (isProtecting && !isSleepness) return flags[1];
            else if (!isProtecting && isSleepness) return flags[0];
            else if (isProtecting && isSleepness) return flags[2];

        } else return flags[3];
        return 0;
    }

    public int multiAttack(Player[] player)
    {
        if(!turnAttack) {
            if (!isSleepness && !isProtecting) {
                for(Player p : player)p.dragonAttack(this, true);
                this.turnAttack = true;
                return 1;
            } else if (isProtecting && !isSleepness) return flags[1];
            else if (!isProtecting && isSleepness) return flags[0];
            else if (isProtecting && isSleepness) return flags[2];

        } else return flags[3];
        return 0;
    }

    public void lightAttack(RuntimeActivity r, int field)
    {
        if(canLightAttack) {
            for (Player p : r.getPlayers()) {
                if (p.getField() == field && !p.getKingdom().equals(this.kingdom)) p.giveDamage(this, 1);
            }
        }
    }
    public void heavyAttack(RuntimeActivity r, boolean ignore)
    {
        if(canHeavyAttack)
        {
            for(Player p : r.getPlayers()){
                if(ignore)p.giveDamage(this,1);
                else if(!p.getKingdom().equals(this.getKingdom()))p.giveDamage(this, 1);

            }
        }
        Main.damageStep(r);
    }

    public void giveDamage(int dmg)
    {
        this.life -= dmg;
    }

    public String getKingdom() {
        return kingdom;
    }

    public int getLife() {
        return life;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getTakenDamage() {
        return takenDamage;
    }

    public Player getProtectedOne() {
        return protectedOne;
    }

    public Player getDragonHunter() {
        return dragonHunter;
    }

    public Player protect(Player p)
    {
        p.setDragonProtected();
        this.protectedOne = p;
        return p;
    }

    public Dragon setDragonHunter(Player p)
    {
        this.dragonHunter = p;
        return this;
    }

    /*PARCELABLE IMPLEMENTATION*/
    public static final Creator<Dragon> CREATOR = new Creator<Dragon>() {
        @Override
        public Dragon createFromParcel(Parcel in) {
            return new Dragon(in);
        }

        @Override
        public Dragon[] newArray(int size) {
            return new Dragon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


    public String getNameAsString()
    {
        return this.name == R.string.dragon_lancelot_name ? "Lancelot" : "Quasar";
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(name);
        dest.writeString(kingdom);
        dest.writeInt(life);
        dest.writeInt(maxDamage);
        dest.writeInt(takenDamage);
        dest.writeByte((byte) (turnAttack ? 1 : 0));
        dest.writeByte((byte) (isSleepness ? 1 : 0));
        dest.writeByte((byte) (isOwned ? 1 : 0));
        dest.writeByte((byte) (isSideChanged ? 1 : 0));
        dest.writeByte((byte) (canLightAttack ? 1 : 0));
        dest.writeByte((byte) (canHeavyAttack ? 1 : 0));
        dest.writeByte((byte) (canMultiAttack ? 1 : 0));
        dest.writeByte((byte) (canObliterate ? 1 : 0));
    }
}
