package org.gpginc.ntateam.apptest.runtime.activity;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RuntimeActivity extends AppCompatActivity implements Parcelable
{
    protected ArrayList<String> OUT_CLAZZS = new ArrayList<>();
    protected ArrayList<String> OUT_KINGDOMS = new ArrayList<>();
    protected ArrayList<Integer> OUT_FIELDS = new ArrayList<>();
    protected ArrayList<String> PLAYER_NAMES = new ArrayList<>();
    protected ArrayList<Integer> GONE_PLAYERS = new ArrayList<>();

    protected ArrayList<Player> ON_PLAYERS = new ArrayList<>();

    protected String CURRENT_PLAYER ="Suposed to A Player Name here..";
    protected Player CP = null;
    @Nullable
    @LayoutRes
    protected int layout = -1;
    public RuntimeActivity()
    {

    }

    protected RuntimeActivity(Parcel in) {
        OUT_CLAZZS = in.createStringArrayList();
        OUT_KINGDOMS = in.createStringArrayList();
        PLAYER_NAMES = in.createStringArrayList();
        ON_PLAYERS = in.createTypedArrayList(Player.CREATOR);
        CURRENT_PLAYER = in.readString();
        CP = in.readParcelable(Player.class.getClassLoader());
        layout = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(OUT_CLAZZS);
        dest.writeStringList(OUT_KINGDOMS);
        dest.writeStringList(PLAYER_NAMES);
        dest.writeTypedList(ON_PLAYERS);
        dest.writeString(CURRENT_PLAYER);
        dest.writeParcelable(CP, flags);
        dest.writeInt(layout);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RuntimeActivity> CREATOR = new Creator<RuntimeActivity>() {
        @Override
        public RuntimeActivity createFromParcel(Parcel in) {
            return new RuntimeActivity(in);
        }

        @Override
        public RuntimeActivity[] newArray(int size) {
            return new RuntimeActivity[size];
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getIntent().getExtras().get("Players")!=null)
        {
            this.load(getIntent().getExtras());
        }
    }

    protected Bundle getNextPlayer()
    {
        final Bundle next = new Bundle();
        Random rand = new Random();
        int nextPlayerI;
        if(this.GONE_PLAYERS.size() == this.PLAYER_NAMES.size())this.finish();
        do {
            nextPlayerI = rand.nextInt(this.PLAYER_NAMES.size());
            this.CURRENT_PLAYER = this.PLAYER_NAMES.get(nextPlayerI);
            this.CP = this.ON_PLAYERS.get(nextPlayerI);
        }while(this.GONE_PLAYERS.contains(nextPlayerI));
        this.GONE_PLAYERS.add(nextPlayerI);
        next.putString("CPN", this.CURRENT_PLAYER);
        next.putParcelable("CP", this.CP);
        next.putStringArrayList("PlayerNames", this.PLAYER_NAMES);
        next.putStringArrayList("PlayerClazz", this.OUT_CLAZZS);
        next.putStringArrayList("PlayerKingdoms", this.OUT_KINGDOMS);
        next.putIntegerArrayList("PlayerFields", this.OUT_FIELDS);
        next.putIntegerArrayList("GonePlayers", this.GONE_PLAYERS);
        next.putParcelableArrayList("Players", this.ON_PLAYERS);
        return next;
    }
    protected void load(@Nullable Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            for(String player : savedInstanceState.getStringArrayList("PlayerNames"))
            {
                this.PLAYER_NAMES.add(player);
               // System.out.println(player);
            }
            for(String clazz : savedInstanceState.getStringArrayList("PlayerClazz"))
            {
                this.OUT_CLAZZS.add(clazz);
               // System.out.println(clazz);
            }
            for(String KGN : savedInstanceState.getStringArrayList("PlayerKingdoms"))
            {
                this.OUT_KINGDOMS.add(KGN);
              //  System.out.println(KGN);
            }
            for(Integer i : savedInstanceState.getIntegerArrayList("PlayerFields"))
            {
                this.OUT_FIELDS.add(i);
               // System.out.println("Field: "+i);
            }
            for(Integer i : savedInstanceState.getIntegerArrayList("GonePlayers"))
            {
                this.GONE_PLAYERS.add(i);
                //System.out.println("gone " +i);
            }
            for(Parcelable p : savedInstanceState.getParcelableArrayList("Players"))
            {
                this.ON_PLAYERS.add((Player)p);
            }
            this.CURRENT_PLAYER = savedInstanceState.getString("CPN");
            this.CP = savedInstanceState.getParcelable("CP");
            System.out.println(this.CURRENT_PLAYER);
            System.out.println(this.CP.getName());
            Main.p(this.CP.getClazz().getName());
            if(this.CP.getClazz().hasSkills()) {
                for (ClazzSkill c : this.CP.getClazz().getSkills()) {
                    Main.p("Skill:");
                    Main.p(c.getName());
                }
            } else Main.p("No skills here");
        } else System.out.println("bugbugsbugs");
    }

    protected List<ClazzSkill> getAplicableSkillsFor(Clazz c)
    {
        final List<ClazzSkill> out_skills = new ArrayList<>();
        //c.runPassive();
        Clazzs clazzs = new Clazzs();
        Player p = c.getCurrentPlayer();

        int asd = 0;
        for(int i = 0; i < c.getSkills().size(); ++i)
        {
            if(!c.getSkillAt(i).isPassive())
            {
                if(p.attacked)
                {
                    out_skills.add(c.getSkillAt(i));
                    Main.p("ATTACKED");
                }else if(!p.attacked && !c.getSkillAt(i).isCounter())
                {
                    Main.p("NOT ATTACKED");
                    out_skills.add(c.getSkillAt(i));
                }
                //c.getSkillAt(i).setCurrent(this);
            }
        }
        return out_skills;
    }
    protected Bundle enableNext()
    {
        Bundle next = new Bundle();
        next.putString("CPN", this.CURRENT_PLAYER);
        next.putParcelable("CP", this.CP);
        next.putStringArrayList("PlayerNames", this.PLAYER_NAMES);
        next.putStringArrayList("PlayerClazz", this.OUT_CLAZZS);
        next.putStringArrayList("PlayerKingdoms", this.OUT_KINGDOMS);
        next.putIntegerArrayList("PlayerFields", this.OUT_FIELDS);
        next.putIntegerArrayList("GonePlayers", this.GONE_PLAYERS);
        next.putParcelableArrayList("Players", this.ON_PLAYERS);
        return next;
    }
   /* @Nullable
    protected Clazz getClazzByName(String playerName)
    {
        return this.PLAYER_NAMES.contains(playerName) ? Main.Clazzs.getClazzByInheritedName(this.OUT_CLAZZS.get(this.PLAYER_NAMES.indexOf(playerName))) : null;
    }*/

}

