package org.gpginc.ntateam.apptest;

import android.os.Parcelable;

import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * In preparation, if game becomes to heavy.
 */
@Deprecated
public class GameEngine
{
    public int f1, f2, f3, f4;
    public final ArrayList<Player> PLAYERS = new ArrayList<>();
    public final List<String> KINGDOMS = new ArrayList<>();
    public final List<Player> GONE_PLAYERS = new ArrayList<>();

    public GameEngine()
    {

    }

    public void start(List<String> players)
    {
        Random rand = new Random();
        int cod = 0;
        /*Player set*/
        for(String p : players)
        {
            PLAYERS.add(new Player(p).withCod(cod));
            ++cod;
        }
        /*---Value declaration---*/
        int i,i2, cField, sorted = 0;
        String cKgn = KINGDOMS.get(rand.nextInt(KINGDOMS.size()));
        boolean  MSc =false;
        final List<Player> done = new ArrayList<>();
        Player cp = null;
        Clazz cCz = null;
        /*-----------------------*/

        /*Unbreakable game, side by side, equals members, and always one spy if player count isn't par*/
        if(PLAYERS.size() % 2 != 0)
        {
            i2 = rand.nextInt(PLAYERS.size()); cp = PLAYERS.get(i2);
            setupPlayer(cp, Clazzs.SPY, "UNKOWN", 5);
            done.add(cp);
            ++sorted;
        }
        /*Loop through players and set all-of-them*/
        do
        {
            if(!MSc)
            {
                for(int zs = 0; zs<2;++zs) {
                    do {
                        i2 = rand.nextInt(PLAYERS.size());
                        cp = PLAYERS.get(i2);
                        cField = rand.nextInt(4) + 1;
                        if(!done.contains(cp))break;
                    } while (true);
                    if (cKgn.equals("OHXER")) cKgn = "CAMELOT";
                    else cKgn = "OHXER";
                    setupPlayer(cp, Clazzs.SUPREME, cKgn, cField);
                    done.add(cp);
                    ++sorted;
                }
                MSc = true;
            }

            cField = rand.nextInt(4) + 1;
            if (cKgn.equals("OHXER")) cKgn = "CAMELOT";
            else cKgn = "OHXER";
            do{ i = rand.nextInt(100); i2 = rand.nextInt(Clazzs.CLAZZS.size()); cCz = Clazzs.CLAZZS.get(i2);}while (!isClazzAcceptable(i, cCz));
            cp = PLAYERS.get(rand.nextInt(PLAYERS.size()));
            setupPlayer(cp, cCz, cKgn, cField);
            done.add(cp);
            ++sorted;

        } while(sorted < PLAYERS.size());
    }

    static boolean isClazzAcceptable(int r, Clazz c)
    {
        if(r <= c.getRARITY().getPercent())return c.enabled ? true : false;
        return false;
    }

    private void setupPlayer(Player p, Clazz clazz, String kingdom, int field)
    {
        p.setClazz(clazz).setKingdom(kingdom).setField(field);
        /*OUT_CLAZZS.add(clazz.getName());
        OUT_KINGDOMS.add(kingdom);
        OUT_FIELDS.add(field);*/
        setUpFieldMemory(field);
    }

    public void setUpFieldMemory(int field)
    {
        switch(field)
        {
            case 1:
                ++f1;
                break;
            case 2:
                ++f2;
                break;
            case 3:
                ++f3;
                break;
            case 4:
                ++f4;
                break;
        }
    }

    public void setDownFieldMemory(int field)
    {
        switch(field)
        {
            case 1:
                --f1;
                break;
            case 2:
                --f2;
                break;
            case 3:
                --f3;
                break;
            case 4:
                --f4;
                break;
        }
    }

    public boolean damageStep(final List<Player> players) {
        List<Player> playersKilled = new ArrayList<>();
        boolean flag = false;
        for (Player p : players) {
            p.damageStep();
            p(p.getName() + " status: ");
            p("Current Life:" + p.life());
            p("Protections: ");
            p(p.isProtected ? "ADC Protection ON" : "ADC Protection OFF");
            p(p.isDragonProtected ? "Dragon Protection ON" : "Dragon Protection OFF");
            p("Effects:");
            p(p.isBlind ? "Blinded" : "Can still seeing the sky...");
            p(p.isStunned ? "Stunned" : "Till now, everything seems ok...");
            if (p.attacked) {
                p("Attackers");
                for (Player o : p.getAttackers()) {
                    p("Was attacked by " + o.getName());
                }
            }
            if (!p.isDead) {
                if (p.life() == 0) {
                    p("Has died this turn");
                    playersKilled.add(p);
                    flag = true;
                    p.kill();
                } else if (p.life() < 0) {
                    p("Has died, and " + p.getAttackers().get(p.getAttackers().size() - 1).getName() + " had guaranteed that wont come back to this world");
                    playersKilled.add(p);
                    flag = true;
                    p.kill();
                }
            }
            p("Someone died: " + flag);

            p.re_setup();
        }
        return flag;
    }

    static void p(Object n)
    {
        System.out.println(n.toString());
    }
}
