package org.gpginc.ntateam.apptest.runtime;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main 
{
	public static Scanner input = new Scanner(System.in);
	
	private static final List<Clazz> CLASSES = new ArrayList<>();
	private static final List<String> KINGDOMS = new ArrayList<>();
	private static Integer playerInField1=0, playerInField2=0, playerInField3=0, playerInField4=0;
	public static final ArrayList<Player> PLAYERS = new ArrayList<Player>();
	private final ArrayList<String> OUT_CLAZZS = new ArrayList<>();
	private final ArrayList<String> OUT_KINGDOMS = new ArrayList<>();
	private final ArrayList<Integer> OUT_FIELDS = new ArrayList<>();

	private static final Clazzs clazzs = new Clazzs();
	private static boolean GAMING = false;
	
	
	
	/*public static void main(String[] args)
	{
		preInit();
		playerSelection();
		postInit();
		while(GAMING)
		{
			firstPhase();
			damageStep();
		}
	}*/
	
	public Main preInit()
	{
		p("init start");


		CLASSES.addAll(clazzs.CLAZZS);
		for (Clazz c:
			 clazzs.CLAZZS) {
			p(c.getName());
			for (ClazzSkill cs:
				 c.getSkills()) {
					p("Has skill: "+cs.getName());
			}
		}
		KINGDOMS.add("OHXER");
		KINGDOMS.add("CAMELOT");
		p("sucess init");
		return this;
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
	
	public ArrayList[] postInit(ArrayList<String> players)
	{
		Random rand = new Random();
		for(String p : players)
		{
			PLAYERS.add(new Player(p));
		}

		int i = rand.nextInt(2);
		int cField = rand.nextInt(4) + 1;
		String cKgn = KINGDOMS.get(i);
		int sorted = 0;
		boolean  MSc =false;
		final List<Player> done = new ArrayList<>();
		final List<Clazz> doneclazzs = new ArrayList<>();
		Player cp = null;
		p("post init start");
		if(PLAYERS.size() % 2 != 0)
		{
			int i2 = rand.nextInt(PLAYERS.size());
			cp = PLAYERS.get(i2);
			while(done.contains(cp))
			{
				i2 = rand.nextInt(PLAYERS.size());
				cp = PLAYERS.get(i2);
			}
			setupPlayer(cp, clazzs.SPY, "UNKOWN", 5);
			done.add(cp);
			++sorted;
		}
		do
		{
			int i2 = rand.nextInt(PLAYERS.size());
			int i3 = rand.nextInt(CLASSES.size());
			cp = PLAYERS.get(i2);
			while(done.contains(cp))
			{
			    i2 = rand.nextInt(PLAYERS.size());
				cp = PLAYERS.get(i2);
			}
			Clazz cCls = CLASSES.get(i3);

			if(MSc == false)
			{
				MSc = true;
				setupPlayer(cp, clazzs.SUPREME, cKgn, cField);
				done.add(cp);
				cField = rand.nextInt(4) + 1;
				if(cKgn.equals("OHXER")) cKgn = "CAMELOT";
				else cKgn = "OHXER";
				i2 = rand.nextInt(PLAYERS.size());
				i3 = rand.nextInt(CLASSES.size());
				cp = PLAYERS.get(i2);
				while(done.contains(cp))
				{
				    i2 = rand.nextInt(PLAYERS.size());
					cp = PLAYERS.get(i2);
				}
				setupPlayer(cp, clazzs.SUPREME, cKgn, cField);
				done.add(cp);
				++sorted;
				++sorted;
				cField = rand.nextInt(4) + 1;
				p(cField);
			}
			else if(!cCls.equals(clazzs.SUPREME) && !cCls.equals(clazzs.DRAGON_HUNTER)&& !cp.hasClazz() && !cp.hasKingdom())
			{
				setupPlayer(cp, rand.nextInt(1000) < 3 ? clazzs.DRAGON_HUNTER : cCls, cKgn, cField);
				done.add(cp);
				if(cKgn.equals("OHXER")) cKgn = "CAMELOT";
				else cKgn = "OHXER";
				++sorted;
				cField = rand.nextInt(4) + 1;
				p(cField);
			}
				//p(sorted);
		} while(sorted < PLAYERS.size());
		
		for(Player s : PLAYERS)
		{
			if(s.getName()!=null)p(s.getName());
			if(s.getClazz()!=null)p("is a "+s.getClazz().getName());
			if(s.getKingdom()!=null)p("and belongs to "+ s.getKingdom()+"\n");
		}
		GAMING = true;
		return new ArrayList[]{OUT_CLAZZS, OUT_KINGDOMS, OUT_FIELDS, PLAYERS};
	}
	/**
	 *
	 * @param clazz
	 * @param kingdom
	 */
	private void setupPlayer(Player p, Clazz clazz, String kingdom, int field)
	{
		p.setClazz(clazz).setKingdom(kingdom).setField(field);
		OUT_CLAZZS.add(clazz.getName());
		OUT_KINGDOMS.add(kingdom);
		OUT_FIELDS.add(field);
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
	
	
	static void waitPlayerChange(Player p)
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
		
	}
	
	static void firstPhase()
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
	}
	public static final void damageStep(List<Player> players)
	{
		List<Player> playersKilled = new ArrayList<>();
		
		for(Player p : PLAYERS)
		{
			p.damageStep();
			p(p.getName() + " status: ");
			p(p.life());
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
			if(p.life()==0)
			{
				p("Has died this turn");
				playersKilled.add(p);
			} else if (p.life() < 0)
			{
				p("Has died, and " + p.getAttackers().get(p.getAttackers().size()-1).getName() + " had guaranteed that wont come back to this world");
				playersKilled.add(p);
			}
			p("");
			
			p.re_setup();
		}
		for(Player pp : playersKilled)
		{
			players.remove(pp);
		}
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
		c.runPassive();
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











































