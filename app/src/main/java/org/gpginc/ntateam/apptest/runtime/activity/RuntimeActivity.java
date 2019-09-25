package org.gpginc.ntateam.apptest.runtime.activity;

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
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.DamageStep;
import org.gpginc.ntateam.apptest.runtime.Dragon;
import org.gpginc.ntateam.apptest.PrePlayer;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.enums.EventHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RuntimeActivity extends AppCompatActivity implements Parcelable
{
    protected ArrayList<String> OUT_CLAZZS = new ArrayList<>();
    protected ArrayList<String> OUT_KINGDOMS = new ArrayList<>();
    protected ArrayList<Integer> OUT_FIELDS = new ArrayList<>();
    protected ArrayList<String> PLAYER_NAMES = new ArrayList<>();
    protected ArrayList<String> GONE_PLAYERS = new ArrayList<>();
    protected ArrayList<Dragon> ON_DRAGONS = new ArrayList<>();

    protected ArrayList<Player> ON_PLAYERS = new ArrayList<>();
    protected ArrayList<Event> EVENTS = new ArrayList<>();

    protected Integer currrentPlayerCod = -1;

    /**
     * GLOBAL
     */
    public static final String LAST_STATE = "CODE_HASH-LASTSTATEMANAGE.SYS";

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
        ON_DRAGONS = in.createTypedArrayList(Dragon.CREATOR);
        CURRENT_PLAYER = in.readString();
        layout = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringList(OUT_CLAZZS);
        dest.writeStringList(OUT_KINGDOMS);
        dest.writeStringList(PLAYER_NAMES);
        dest.writeTypedList(ON_PLAYERS);
        dest.writeTypedList(ON_DRAGONS);
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
        setTitle(R.string.wkff_label);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Toolbar toolbar = findViewById(R.id.toolbar);
        if(toolbar!=null) {
            setSupportActionBar(toolbar);
            toolbar.setBackgroundResource(R.drawable.toolbar_shape);
        }

    }

    protected Bundle getNextPlayer()
    {
        final Bundle next = new Bundle();
        Random rand = new Random();
        String nextPlayer;
        int i;
        if(this.GONE_PLAYERS.size() == this.PLAYER_NAMES.size())
        {
            this.finish();
            return Bundle.EMPTY;
        } else {
            do {
                i = rand.nextInt(this.PLAYER_NAMES.size());
                nextPlayer = this.ON_PLAYERS.get(i).getName();
                this.currrentPlayerCod = this.ON_PLAYERS.get(i).getCod();
            }
            while (this.GONE_PLAYERS.contains(nextPlayer) || this.currentPlayer().isDead);

            this.GONE_PLAYERS.add(nextPlayer);
            this.CURRENT_PLAYER = this.PLAYER_NAMES.get(i);
            return this.enableNext();
        }
    }
    protected void load(@Nullable Bundle savedInstanceState)
    {
        if(savedInstanceState!=null)
        {
            this.PLAYER_NAMES.addAll(savedInstanceState.getStringArrayList("PlayerNames"));
            this.GONE_PLAYERS.addAll(savedInstanceState.getStringArrayList("GonePlayers"));
            for(Parcelable p : savedInstanceState.getParcelableArrayList("Players"))
            {
                this.ON_PLAYERS.add((Player)p);
            }
            for(Parcelable p1 : savedInstanceState.getParcelableArrayList("Events"))
            {
                this.EVENTS.add((Event) p1);
            }
            for(Parcelable p2 : savedInstanceState.getParcelableArrayList("Dragons"))
            {
                this.ON_DRAGONS.add((Dragon) p2);
            }
            this.CURRENT_PLAYER = savedInstanceState.getString("CPN");
            this.currrentPlayerCod = savedInstanceState.getInt("CurrentPlayerCod");
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

    public ArrayList<Dragon> getDragons() {
        return ON_DRAGONS;
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
                }else if(!p.attacked && !c.getSkillAt(i).stillCounter())
                {
                    Main.p("NOT ATTACKED");
                    out_skills.add(c.getSkillAt(i));
                }
            }
        }
        return out_skills;
    }
    public Bundle enableNext()
    {
        Bundle next = new Bundle();
        next.putString("CPN", this.CURRENT_PLAYER);
        next.putStringArrayList("PlayerNames", this.PLAYER_NAMES);
        next.putStringArrayList("GonePlayers", this.GONE_PLAYERS);
        next.putParcelableArrayList("Players", this.ON_PLAYERS);
        next.putParcelableArrayList("Events", this.EVENTS);
        next.putInt("CurrentPlayerCod", this.currrentPlayerCod);
        next.putParcelableArrayList("Dragons", this.ON_DRAGONS);
        next.putParcelable("CPA", this);
        return next;
    }
    public void enableNextTo(Bundle i)
    {
        i.putBundle(LAST_STATE, this.enableNext());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        enableNextTo(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        this.load(savedInstanceState.getBundle(LAST_STATE));
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*this.ON_PLAYERS.clear();
        this.GONE_PLAYERS.clear();
        this.EVENTS.clear();*/

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

    public void addDragon(Dragon d)
    {
        this.ON_DRAGONS.add(d);
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
        checkEvents(EventHandler.ALWAYS);
        if (this.GONE_PLAYERS.size() < this.ON_PLAYERS.size()) {
            Intent next = new Intent(this, PrePlayer.class);
            next.putExtras(this.getNextPlayer());
            startActivity(next);
            this.finish();
        } else {
            checkEvents(EventHandler.DAMAGE_STEP);
            this.GONE_PLAYERS.clear();
            Intent next = new Intent(this, DamageStep.class);
            next.putExtras(this.getNextPlayer());
            startActivity(next);
            this.finish();
        }

    }

    public List<Event> getEvents()
    {
        return this.EVENTS;
    }
    public Player currentPlayer()
    {
        System.err.println("CurrentPlayer = "  +this.ON_PLAYERS.get(this.currrentPlayerCod).toString());
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

    protected boolean checkEvents(EventHandler kind)
    {
        for(Event e : getEvents())
        {
            if(e.getHandler().equals(kind))
            {
                for(Player p : getPlayers())
                {
                    if(e.check(p)){
                        e.exe(p,this);
                        return true;
                    }
                }
            }
        }
        return false;
    }
    public void changePlayer(Player p)
    {
        final List<Player> CLONE = new ArrayList<>();
        CLONE.addAll(this.ON_PLAYERS);
        this.ON_PLAYERS.clear();
        for (Player p2 : CLONE) {
            if(p2.equals(p))
            {
                this.ON_PLAYERS.add(p);
            } else this.ON_PLAYERS.add(p2);
        }
    }
}

