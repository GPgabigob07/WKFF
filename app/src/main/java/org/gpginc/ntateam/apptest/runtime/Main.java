package org.gpginc.ntateam.apptest.runtime;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main 
{
	public static Scanner input = new Scanner(System.in);
	
	private static final List<Clazz> CLASSES = new ArrayList<>();
	private static final List<String> KINGDOMS = new ArrayList<>();
	private static Integer playerInField1=0, playerInField2=0, playerInField3=0, playerInField4=0;
	public static final ArrayList<Player> PLAYERS = new ArrayList<>();
	private static final ArrayList<String> OUT_CLAZZS = new ArrayList<>();
	private static final ArrayList<String> OUT_KINGDOMS = new ArrayList<>();
	private static final ArrayList<Integer> OUT_FIELDS = new ArrayList<>();
	public static final String SETTINGS = Util.getCrypto("SETTING_02365-drawer.file-crypto");
	/*loader*/
	private static int skillLoaderProgress = 0;
	private static int clazzLoaderProgress = 0;
	public static boolean gameLoaded = false;

	@UiThread
	public static void preInit(final Activity a/*, final SharedPreferences prefer,final ProgressBar bar1, final ProgressBar bar2,final TextView par1, final TextView par2*/)
	{
		/*MAYBE BE USED IN A NEAR FUTURE*/

		/*a.runOnUiThread(new Runnable() {
			public void run() {
				p("init start");
				par1.setText("INIT START");
				for (final Clazz c : Clazzs.CLAZZS) {
					par1.setText(c.getName());
					bar1.setMax(c.getSkills().size() + 1);
					Main.p("Loading: " + c.getName()+"\n\n\n\n\n\n\n--------------------------------------------------------------------------------------");
					if(c.hasSkills())
					{
						for(final ClazzSkill sk : c.getSkills()) {
							while (clazzLoaderProgress < bar1.getMax() -1) {
								clazzLoaderProgress += 1;
								bar1.setProgress(clazzLoaderProgress);
								Runnable instantSk = initSkill(a, sk, bar2, par2);
								a.runOnUiThread(instantSk);
								try {
									while(!sk.isLoaded);

									Thread.sleep(100);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
							}
						}
					}
					try {
						// Sleep for 200 milliseconds.
						Thread.sleep(150);
						c.enabled = prefer.getBoolean(c.getName(), true);
						bar1.setProgress(clazzLoaderProgress + 1);
						clazzLoaderProgress = 0;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				Main.gameLoaded = true;
			}

		});*/
		for (Clazz c : Clazzs.CLAZZS)
		{
			c.setResources(a.getResources());
			CLASSES.add(c);
		}
		KINGDOMS.add("OHXER");
		KINGDOMS.add("CAMELOT");
	}
	@UiThread
	public static Runnable initSkill(Activity a, final ClazzSkill skill, final ProgressBar bar2, final TextView par2)
	{
		return new Runnable()
		{
			public void run()
			{
				bar2.setVisibility(View.VISIBLE);
				par2.setVisibility(View.VISIBLE);

				par2.setText(skill.getName());
				bar2.setMax(10);
				bar2.setIndeterminate(false);

				Main.p("Loading :"+skill.getName()+"\n-\n--\n-------------------------------------------------------------------------------");
				while (skillLoaderProgress < 10) {
					skillLoaderProgress += 1;

					bar2.setProgress(skillLoaderProgress);

					try {
						// Sleep for 200 milliseconds.
						Thread.sleep(50);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				skill.isLoaded = true;
				par2.setText("");
				bar2.setProgress(0);
				bar2.setVisibility(View.INVISIBLE);
				par2.setVisibility(View.INVISIBLE);
				skillLoaderProgress = 0;
			}
		};
	}
	
	static void playerSelection()
	{
		p("*******************************");
		p("*      WELCOME TO WKFF? !     *");
		p("*******************************");
		p("");
		p("Enter player names (at least 4), \n than write '#ab#' to start");
		while(true)
		{
			String s = input.next();
			if(!s.equals("#ab#"))
			{
				Player np = new Player(s);
				if(!PLAYERS.contains(np))
				{
					PLAYERS.add(np);
					p("Player " + s + " added!");
				} else p("Player " + s + "is already on!!!\n\n\n");
				
			}
			else
			{
				/*for(String ss : PLAYERS)
				{
					p(ss);
				}*/
				if(PLAYERS.size() >=4)
				{
					/*if(PLAYERS.size() % 2 == 0)
					{
						break;
					} else p("Game need pair player numbers to work");*/
					break;
				}
				else p("Game needs at least 4 players to start");
			}
		}
		p("players done");
	}
	
	public static ArrayList[] postInit(ArrayList<String> players)
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
        int i,i1,i2, cField, sorted = 0;
        String cKgn = KINGDOMS.get(rand.nextInt(KINGDOMS.size()));
        boolean  MSc =false;
        final List<Player> done = new ArrayList<>(),as_super = new ArrayList<>();
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
					} while (done.contains(cp));
					if (cKgn.equals("OHXER")) cKgn = "CAMELOT";
					else cKgn = "OHXER";
					setupPlayer(cp, Clazzs.SUPREME, cKgn, cField);
					done.add(cp);
					as_super.add(cp);
					++sorted;
				}
            	MSc = true;
            }

            cField = rand.nextInt(4) + 1;
			if (cKgn.equals("OHXER")) cKgn = "CAMELOT";
			else cKgn = "OHXER";
			do{ i = rand.nextInt(100); i2 = rand.nextInt(Clazzs.CLAZZS.size()); cCz = Clazzs.CLAZZS.get(i2);}while (!isClazzAcceptable(i, cCz));
			do {
				i2 = rand.nextInt(PLAYERS.size());
				cp = PLAYERS.get(i2);
				cField = rand.nextInt(4) + 1;
			} while (done.contains(cp));
			setupPlayer(cp, cCz, cKgn, cField);
			done.add(cp);
			++sorted;

        } while(sorted < PLAYERS.size());

		return new ArrayList[]{OUT_CLAZZS, OUT_KINGDOMS, OUT_FIELDS, PLAYERS};
	}

	static boolean isClazzAcceptable(int r, Clazz c)
	{
		if(r <= c.getRARITY().getPercent())return c.enabled;
		return false;
	}

	/**
	 *
	 * @param clazz
	 * @param kingdom
	 */
	private static void setupPlayer(Player p, Clazz clazz, String kingdom, int field)
	{
		p.setClazz(clazz).setKingdom(kingdom).setField(field);
		/*OUT_CLAZZS.add(clazz.getNameLikeStr());
		OUT_KINGDOMS.add(kingdom);
		OUT_FIELDS.add(field);*/
		setUpFieldMemory(field);
	}
	public static void p(Object n)
	{
		System.out.println(n);
	}
	
	public static void setUpFieldMemory(int field)
	{
		switch(field)
		{
		case 1:
			++playerInField1;
			break;
		case 2:
			++playerInField2;
			break;
		case 3:
			++playerInField3;
			break;
		case 4:
			++playerInField4;
			break;
		}
	}
	
	public static void setDownFieldMemory(int field)
	{
		switch(field)
		{
		case 1:
			--playerInField1;
			break;
		case 2:
			--playerInField2;
			break;
		case 3:
			--playerInField3;
			break;
		case 4:
			--playerInField4;
			break;
		}
	}
	
	
	/**static void waitPlayerChange(Player p)
	{
		for(int a = 0; a<100; ++a)p("");
		
		p("********************");
		p("*     " + p.getName() + "      *");
		p("*     " + p.getClazz().getPseudoName() + "      *");
		p("*     " + p.getKingdom() + "      *");
		p("********************");
		p("");
		p.showField();
		for(int a = 0; a<5; ++a)p("");
		
	}**/
	
	/*static void firstPhase()
	{
		Random rand = new Random();
		p("FIRST PHASE");
		int counter = 0;
		List<Integer> gonePlayers = new ArrayList<>();
		while(counter < PLAYERS.size())
		{
			p("HUM");
			String s = input.next();
			p(s);
			if(s.equals("next"))
			{
				int i = rand.nextInt(PLAYERS.size());
				while(gonePlayers.contains(i))i = rand.nextInt(PLAYERS.size());
				if(!gonePlayers.contains(i))
				{
					waitPlayerChange(PLAYERS.get(i));
					firstSkillCaller(PLAYERS.get(i));
					gonePlayers.add(i);
					++counter;
				}
				
			}
		}
	}*/
	public static boolean damageStep(final List<Player> players)
	{
		List<Player> playersKilled = new ArrayList<>();
		boolean flag = false;
		for(Player p : players)
		{
			p.damageStep();
			p(p.getName() + " status: ");
			p("Current Life:"+p.life());
			p("Protections: ");
			p(p.isProtected ? "ADC Protection ON" : "ADC Protection OFF" );
			p(p.isDragonProtected ? "Dragon Protection ON" : "Dragon Protection OFF" );
			p("Effects:");
			p(p.isBlind ? "Blinded" : "Can still seeing the sky...");
			p(p.isStunned ? "Stunned" : "Till now, everything seems ok...");
			if(p.attacked)
			{
				p("Attackers");
				for(Player o : p.getAttackers())
				{
					p("Was attacked by " + o.getName());
				}
			}
			if(!p.isDead) {
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
			p("Someone died: "+flag);
			
			p.re_setup();
		}
		return flag;
	}
	static void firstSkillCaller(Player p)
	{
		Random rand = new Random();
		Clazz clazz = p.getClazz();
		clazz.setCurrentPlayer(p);
		if(clazz.hasSkills())
		{
			ClazzSkill sk = skillSelector(clazz);
			if(sk!=null)sk.runSkill(p);
		}
		for(int a = 0; a<5; ++a)p("");
		for(int a = 0; a<5; ++a)p("");
		for(int a = 0; a<5; ++a)p("");
		
	}
	/**
	 * 
	 * @param p Player that should be hide from search, null if none
	 * @return Player selected by indexed array;
	 */
	public static Player getPlayer(Player p)
	{
		int ss = -1;
		for(int i = 0; i < PLAYERS.size(); ++i)
		{
			p(!(p!=null && p == PLAYERS.get(i)) ? "["+i+"] - " + PLAYERS.get(i).getName() : "YOU CAN'T SELECT THAT PLAYER");
		}
		while(true)
		{
			int s = input.nextInt();
			if(s < PLAYERS.size())
			{
				p("** you selected " + PLAYERS.get(s).getName());
				ss =s;
				break;
			}
			else p("Please, select only players above");
		}
		return PLAYERS.get(ss);
	}
	@Nullable
	public static ClazzSkill skillSelector(Clazz c)
	{
	//	c.runPassive();
		Player p = c.getCurrentPlayer();
		p("Select one of your skills to use this phase:");
		int asd = 0;
		for(int i = 0; i < c.getSkills().size(); ++i)
		{
			if(!c.getSkillAt(i).isPassive())
			{
				if(p.attacked)
				{
					p("["+i+"] " + c.getSkillAt(i).getName() + (c.getSkillAt(i).isCounter() ? " *COUNTER!*" : ""));
					++asd;
				}else if(!p.attacked && !c.getSkillAt(i).isCounter())
				{
					p("["+i+"] " + c.getSkillAt(i).getName());
					++asd;
				} 
			}
		}
		if(asd >0)
		{
			int s = input.nextInt();
			return c.getSkillAt(s);
		} else return null;
		
	}
}











































