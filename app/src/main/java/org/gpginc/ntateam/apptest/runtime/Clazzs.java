package org.gpginc.ntateam.apptest.runtime;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.input;
import static org.gpginc.ntateam.apptest.runtime.Main.p;
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class Clazzs
{
    public static List<Clazz> CLAZZS = new ArrayList<>();
    public static final Map<String, Clazz> CLAZZ_MAP = new HashMap<String, Clazz>();

    /**
     * Skills
     */
    public  final ClazzSkill SEE_THROUGH = new ClazzSkill("See Through", ClazzSkill.Type.MAHOU, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Player p = Main.getPlayer((Player) o);
                Random rand = new Random();
                p("This playes is from " + p.getKingdom());
                if(rand.nextInt(25) == 5)p("And is  " + p.getClazz());
                if(rand.nextInt(25)  <5)p("And is in field " + p.getField());
            }
        }
    };
    public  final ClazzSkill SEE_ALL = new ClazzSkill("SEE ALL", ClazzSkill.Type.MAHOU, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!= null)
            {
                Player p = (Player) o;
                String cK = p.getKingdom();
                p("Those Players Belongs to your kingdom:");
                for(Player k : PLAYERS)
                {
                    if(k.getKingdom().equals(cK) && !k.equals(p))
                    {
                        p(k.getName()+ " in field: "+k.getField());
                    }
                }
            }
        }
    };
    public  final ClazzSkill ARCHERY_ATTACK = new ClazzSkill("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {

            Random rand = new Random();
            final List<Player> attackable = new ArrayList<>();
            if(o!= null)
            {
                Player p = (Player) o;
                int asd = 0;
                for(Player pP : PLAYERS)
                {
                    attackable.add(pP);
                }
                ((ListView) this.current.findViewById(R.id.players)).setAdapter(new ArrayAdapter<Player>(this.current.getApplicationContext(), android.R.layout.simple_list_item_1, attackable));
                int a = p.getField();
                while(a==p.getField())a = rand.nextInt(4) + 1;
                setDownFieldMemory(p.getField());
                setUpFieldMemory(a);
                p.setField(a);
                p("You'd moved to field " + a);
            }
        }
    };
    public  final ClazzSkill LANCER_ATTACK = new ClazzSkill("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!= null)
            {
                Player p = (Player) o;
                int pField = p.getField();
                int attackingField = 0;
                final List<Player> attackable = new ArrayList<>();
                switch(pField)
                {
                    case 1:
                        attackingField = 3;
                        break;
                    case 2:
                        attackingField = 4;
                        break;
                    case 3:
                        attackingField = 1;
                        break;
                    case 4:
                        attackingField = 2;
                        break;
                }

                int asd = 1;
                for(int i = 0; i< PLAYERS.size(); ++i)
                {
                    if(!PLAYERS.get(i).equals(p)&& (PLAYERS.get(i).getField() == attackingField | PLAYERS.get(i).getField() == pField))
                    {
                        attackable.add(PLAYERS.get(i));
                    }
                }
                ((ListView) this.current.findViewById(R.id.players)).setAdapter(new ArrayAdapter<Player>(this.current.getApplicationContext(), android.R.layout.simple_list_item_1, attackable));
            }
        }
    };
    public  final ClazzSkill SWORDMAN_ATTACK = new ClazzSkill("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!= null)
            {
                p("You can attack 2 players, that are in your field.");
                Player p = (Player) o;
                int pField = p.getField();
                int asd = 1;
                final List<Player> attackable = new ArrayList<>();
                for(int i = 0; i< PLAYERS.size(); ++i)
                {
                    if(!PLAYERS.get(i).equals(p)&& PLAYERS.get(i).getField() == pField)
                    {
                        attackable.add(PLAYERS.get(i));
                    }
                }
                ((ListView) this.current.findViewById(R.id.players)).setAdapter(new ArrayAdapter<Player>(this.current.getApplicationContext(), android.R.layout.simple_list_item_1, attackable));
							/*int sP = input.nextInt();
							int sP2 = input.nextInt();
							PLAYERS.get(sP).giveDamage(p, 1);
							PLAYERS.get(sP2).giveDamage(p, 1);*/

            }
        }
    };
    public  ClazzSkill SPY_KNOWLEDGE = new ClazzSkill("INTEL", ClazzSkill.Type.PASSIVE, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Random rand = new Random();
                int i,a,u;
                a=rand.nextInt(PLAYERS.size());
                i=rand.nextInt(PLAYERS.size() - a / 2);
                u=rand.nextInt(i + a < PLAYERS.size() ? i + a : PLAYERS.size());
                int[] iau = {a, i, u};
                for(int k : iau)
                {
                    p(PLAYERS.get(k).getName() + (rand.nextInt(5) < 2 ? " is " + PLAYERS.get(k).getClazz().getPseudoName() : " belongs to " + PLAYERS.get(k).getKingdom()) + " in field " + PLAYERS.get(k).getField());
                }
            }
        }
    };

    public  ClazzSkill NULLING_ATTACK = new ClazzSkill("Nulling Direct Attack", ClazzSkill.Type.ATTACK_TRIGGER, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Player p = (Player) o;
                if(p.attacked && !this.isPassiveRun() || this.isPassiveRun())
                {
                    p("During this phase you won't take damage");
                    p.isProtected = true;
                    this.setPassiveRun(true);
                } else if(p.attacked)
                {
                    p("This phase you will take damage, I'm sorry...");
                } else
                {
                    p("U weren't attacked... till now...");
                }
            }
        }
    };
    public  final ClazzSkill ABSOLUTE_DEFENSE = new ClazzSkill("ABSOLUTE DEFENSE", ClazzSkill.Type.PASSIVE, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Player p = (Player) o;
                if(p.attacked && !this.isPassiveRun())
                {
                    p("You were attacked by "+p.getLastAttacker().getName() + " from field " + p.getField());
                    p("No worries, u won't take any damage");
                    p.isProtected = true;
                    this.setPassiveRun(true);
                } else if(p.attacked)
                {
                    p("attacked");
                }
            }
        }
    };
    public  final ClazzSkill MADICIAN_COUNTER = new ClazzSkill("Madician Counter", ClazzSkill.Type.MAHOU, true)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Player p = (Player) o;
                if(p.attacked)
                {
                    if(p.getAttackers().size() >= 2)
                    {
                        p.increaseLifeIn(1);
                        p("You were attacked more than you can counter, only one point of damagewill be restored");
                        p.getLastAttacker().giveDamage(p, 1);
                        p(p.getLastAttacker().getName() + " attack suceffuly countered!");
                    } else if (p.getLastAttacker().getClazz().equals(ARCHERY) || p.getLastAttacker().getClazz().equals(SWORDMAN) || p.getLastAttacker().getClazz().equals(LANCER))
                    {
                        p.increaseLifeIn(1);
                        p.getLastAttacker().giveDamage(p, 1);
                        p(p.getLastAttacker().getName() + " attack suceffuly countered!");
                    }
                    else
                    {
                        p("You can't counter this player's attack!");
                    }
                }
            }
        }
    };
    public  final ClazzSkill CHANGE_POSITION = new ClazzSkill("Change Position", ClazzSkill.Type.MAHOU, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!=null)
            {
                Player p = (Player) o;
                int lastField = p.getField();
                int newField = lastField;
                p("Type the field you want to go, [1, 4]:");
                while(newField == lastField)
                {
                    newField = input.nextInt();
                    if(newField == lastField)p("You need to choose a different field");
                }
            }
        }

    };
    public  final ClazzSkill REPOSITION = new ClazzSkill("Reposition", ClazzSkill.Type.PASSIVE, false)
    {
        @Override
        public void runSkill(@Nullable Object o)
        {
            if(o!= null)
            {
                Player p = (Player) o;
                String cK = p.getKingdom();
                p("Do you want to move someone of your kindom? [y, n]:");
                String ans = input.next();
                if(ans.equals("y"))
                {
                    final List<Integer> gone = new ArrayList<>();
                    p("Select one to move: ");
                    for(Player k : PLAYERS)
                    {
                        if(k.getKingdom().equals(cK) && !k.equals(p))
                        {
                            p("["+PLAYERS.indexOf(k) +"] " + k.getName());
                            gone.add(PLAYERS.indexOf(k));
                        }

                    }
                    int i = input.nextInt();
                    while(!gone.contains(i))
                    {
                        p("U can only select players above!!");
                        i = input.nextInt();
                    }
                    p("Where this player will be?");
                    int a = input.nextInt();
                    while(a < 0 && a > 5 || a==PLAYERS.get(i).getField())
                    {
                        p("U can only select players above!!");
                        a = input.nextInt();
                    }
                    p(PLAYERS.get(i).getName() + " moved to field "+a);
                    setDownFieldMemory(PLAYERS.get(i).getField());
                    PLAYERS.get(i).setField(a);
                    setUpFieldMemory(a);
                } else ;
            }
        }
    };



    /**
     * Classes and binding;
     */
    public  final Clazz ARCHERY = new Clazz("Archery Madician").bindSkill(SEE_THROUGH).bindSkill(ARCHERY_ATTACK).bindSkill(NULLING_ATTACK);
    public  final Clazz SWORDMAN = new Clazz("Knight Madician").bindSkill(SWORDMAN_ATTACK).bindSkill(CHANGE_POSITION);
    public  final Clazz SUPREME = new Clazz("Supreme Madician").bindSkill(SEE_THROUGH).bindSkill(SEE_ALL).bindSkill(MADICIAN_COUNTER).bindSkill(CHANGE_POSITION).bindSkill(REPOSITION);
    public  final Clazz ADC = new Clazz("Adc Support Madician").bindSkill(ABSOLUTE_DEFENSE);
    public  final Clazz DRAGON_HUNTER = new Clazz("Dragon Hunter");
    public  final Clazz LANCER = new Clazz("Lancer Madician").bindSkill(LANCER_ATTACK).bindSkill(CHANGE_POSITION);;
    public  final Clazz SPY = new Clazz().bindSkill(SPY_KNOWLEDGE);

    public  Clazz getClazzByInheritedName(String s)
    {
        return CLAZZ_MAP.get(s);
    }
}
