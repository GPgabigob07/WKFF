package org.gpginc.ntateam.apptest.runtime.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.CurrentPlayer;
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
    protected Integer currrentPlayerCod = -1;

    /*public Player getCP() {
        return CP;
    }

    public void setCP(Player CP) {
        this.CP = CP;
    }*/

    protected String CURRENT_PLAYER ="Suposed to A Player Name here..";
    //protected Player CP = null;
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
        if (in.readByte() == 0) {
            currrentPlayerCod = null;
        } else {
            currrentPlayerCod = in.readInt();
        }
        CURRENT_PLAYER = in.readString();
        layout = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(OUT_CLAZZS);
        dest.writeStringList(OUT_KINGDOMS);
        dest.writeStringList(PLAYER_NAMES);
        dest.writeTypedList(ON_PLAYERS);
        if (currrentPlayerCod == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(currrentPlayerCod);
        }
        dest.writeString(CURRENT_PLAYER);
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
        this.load(getIntent().getExtras());
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    protected Bundle getNextPlayer()
    {
        final Bundle next = new Bundle();
        Random rand = new Random();
        int nextPlayerI =-1;
        if(this.GONE_PLAYERS.size() == this.PLAYER_NAMES.size())this.finish();
        else do {
            nextPlayerI = rand.nextInt(this.PLAYER_NAMES.size());
            this.CURRENT_PLAYER = this.PLAYER_NAMES.get(nextPlayerI);
            //this.CP = this.ON_PLAYERS.get(nextPlayerI);
            this.currrentPlayerCod = this.ON_PLAYERS.get(nextPlayerI).getCod();
        }while(this.GONE_PLAYERS.contains(nextPlayerI));
        this.GONE_PLAYERS.add(nextPlayerI);
        next.putString("CPN", this.CURRENT_PLAYER);
        //next.putParcelable("CP", this.CP);
        next.putStringArrayList("PlayerNames", this.PLAYER_NAMES);
        next.putStringArrayList("PlayerClazz", this.OUT_CLAZZS);
        next.putStringArrayList("PlayerKingdoms", this.OUT_KINGDOMS);
        next.putIntegerArrayList("PlayerFields", this.OUT_FIELDS);
        next.putIntegerArrayList("GonePlayers", this.GONE_PLAYERS);
        next.putParcelableArrayList("Players", this.ON_PLAYERS);
        next.putInt("CurrentPlayerCod", this.currrentPlayerCod);
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
            this.currrentPlayerCod = savedInstanceState.getInt("CurrentPlayerCod");
            //this.CP = savedInstanceState.getParcelable("CP");
            Main.p("LOAD INFO");
            System.out.println(this.CURRENT_PLAYER);
            System.out.println(this.currentPlayer().getName());
            Main.p(this.currentPlayer().getClazz().getName());
            if(this.currentPlayer().getClazz().hasSkills()) {
                for (ClazzSkill c : this.currentPlayer().getClazz().getSkills()) {
                    Main.p("Skill:");
                    Main.p(c.getName());
                }
            } else Main.p("No skills here");

        } else System.out.println("bugbugsbugs");
        Main.p("LOAD END");
    }

    protected List<ClazzSkill> getAplicableSkillsFor()
    {
        final List<ClazzSkill> out_skills = new ArrayList<>();
        //c.runPassive();

        Player p = this.currentPlayer();
        Clazz c = p.getClazz();
        int asd = 0;
        for(int i = 0; i < c.getSkills().size(); ++i)
        {
            if(!c.getSkillAt(i).isPassive() && !c.getSkillAt(i).isAttackTriggered())
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
    public Bundle enableNext()
    {
        Bundle next = new Bundle();
        next.putString("CPN", this.CURRENT_PLAYER);
        //next.putParcelable("CP", this.CP);
        next.putStringArrayList("PlayerNames", this.PLAYER_NAMES);
        next.putStringArrayList("PlayerClazz", this.OUT_CLAZZS);
        next.putStringArrayList("PlayerKingdoms", this.OUT_KINGDOMS);
        next.putIntegerArrayList("PlayerFields", this.OUT_FIELDS);
        next.putIntegerArrayList("GonePlayers", this.GONE_PLAYERS);
        next.putParcelableArrayList("Players", this.ON_PLAYERS);
        next.putInt("CurrentPlayerCod", this.currrentPlayerCod);
        return next;
    }

   public Dialog getDialog(Context t, @StringRes int info)
   {
       final Dialog d = new Dialog(t);
       d.setContentView(R.layout.dialog_demo);
       ((TextView)d.findViewById(R.id.dialog_info)).setText(info);
       d.findViewById(R.id.doalog_cancel).setOnClickListener(this.dialogDismiss(d, false));
       d.findViewById(R.id.doalog_ok).setOnClickListener(this.dialogDismiss(d, false));
       return d;
   }
    public Dialog getDialog(Context t, String info)
    {
        final Dialog d = new Dialog(t);
        d.setContentView(R.layout.dialog_demo);
        ((TextView)d.findViewById(R.id.dialog_info)).setText(info);
        d.findViewById(R.id.doalog_cancel).setOnClickListener(this.dialogDismiss(d, false));
        d.findViewById(R.id.doalog_ok).setOnClickListener(this.dialogDismiss(d, false));
        return d;
    }
   public void openDialog(Dialog d)
   {
       d.show();
   }

   public View.OnClickListener dialogDismiss(final Dialog d, final Boolean endAct)
   {
       final RuntimeActivity r = this;
       return new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               d.dismiss();
               if(endAct)r.finish();
           }
       };
   }
    public void goNext(View view)
    {
        if(this.GONE_PLAYERS.size() < this.ON_PLAYERS.size()) {
            Intent next = new Intent(this, CurrentPlayer.class);
            next.putExtras(this.getNextPlayer());
            startActivity(next);
            this.finish();
        } else {
            Main.damageStep(this.ON_PLAYERS);
            this.GONE_PLAYERS.clear();
            Intent next = new Intent(this, CurrentPlayer.class);
            next.putExtras(this.getNextPlayer());
            startActivity(next);
            this.finish();
        }
    }
    public Player currentPlayer()
    {
        return this.ON_PLAYERS.get(this.currrentPlayerCod);
    }

    public List<Player> getPlayers()
    {
        return this.ON_PLAYERS;
    }
    @Nullable
    public Player findByCode(int code)
    {
        for(Player p : this.getPlayers())
        {
            if(p.getCod() == code)return p;
        }
        return null;
    }
}

