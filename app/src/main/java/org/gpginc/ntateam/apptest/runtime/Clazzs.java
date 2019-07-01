package org.gpginc.ntateam.apptest.runtime;

import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.skills.AbsoluteDefense;
import org.gpginc.ntateam.apptest.runtime.skills.ArcherAttack;
import org.gpginc.ntateam.apptest.runtime.skills.ChangePosition;
import org.gpginc.ntateam.apptest.runtime.skills.KingKnowns;
import org.gpginc.ntateam.apptest.runtime.skills.LancerAttack;
import org.gpginc.ntateam.apptest.runtime.skills.MagicianCounter;
import org.gpginc.ntateam.apptest.runtime.skills.NullingAttack;
import org.gpginc.ntateam.apptest.runtime.skills.Reposition;
import org.gpginc.ntateam.apptest.runtime.skills.SoulDescriber;
import org.gpginc.ntateam.apptest.runtime.skills.SpyKnwoledge;
import org.gpginc.ntateam.apptest.runtime.skills.SwordmanAttack;

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
     public static final Map<String, Clazz> CLAZZ_MAP = new HashMap<>();
     public static final Map<String, ClazzSkill> SKILL_MAP = new HashMap<>();

    /**
     * Classes Declaration
     */
    public static  Clazz ARCHERY; //Arqueiro
    public static  Clazz SWORDMAN; //Espadachim
    public static  Clazz LANCER; //Lanceiro
    public static  Clazz SUPREME; //Mago supremo
    public static  Clazz SPY; // Espião
    public static  Clazz DRAGON_HUNTER; //Caçador de Dragões
    public static  Clazz ADC; // Feiticeiro

    /**
     * Skills Declaration
     */
    public static  ClazzSkill SOUL_DESCRIBER;//Done
    //public static  ClazzSkill KING_KNOWNS;
    public static  ClazzSkill ARCHERY_ATTACK;//Done
    public static  ClazzSkill LANCER_ATTACK;//Done
    public static  ClazzSkill SWORDMAN_ATTACK;//Done
    public static  ClazzSkill SPY_KNOWLEDGE;//Done
    public static  ClazzSkill NULLING_ATTACK;//Done
    public static  ClazzSkill ABSOLUTE_DEFENSE;//Done
    public static  ClazzSkill MADICIAN_COUNTER;//Attempt done
    public static  ClazzSkill CHANGE_POSITION;//Done
    public static  ClazzSkill REPOSITION;//Done

    /**
     * Classes init
     */
    static{

        /**
         * Skills
         */
        SOUL_DESCRIBER = new SoulDescriber("Soul Describer", ClazzSkill.Type.MAHOU, false, R.layout.skill_run_player_selection_layout);
       // KING_KNOWNS = new KingKnowns("SEE ALL", ClazzSkill.Type.MAHOU, false);
        ARCHERY_ATTACK = new ArcherAttack("Use Bow", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        LANCER_ATTACK = new LancerAttack("Use Lance", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        SWORDMAN_ATTACK = new SwordmanAttack("Use Sword", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        SPY_KNOWLEDGE = new SpyKnwoledge("INTEL", ClazzSkill.Type.PASSIVE, false, R.layout.skill_run_intel);
        NULLING_ATTACK = new NullingAttack("Nulling Direct Attack", ClazzSkill.Type.ATTACK_TRIGGER, false);
        ABSOLUTE_DEFENSE = new AbsoluteDefense("ABSOLUTE DEFENSE", ClazzSkill.Type.PASSIVE, false, R.layout.empty_skillrun);
        MADICIAN_COUNTER = new MagicianCounter("Magician Counter", ClazzSkill.Type.MAHOU, true);
        CHANGE_POSITION = new ChangePosition("Change Position", ClazzSkill.Type.MAHOU, false, R.layout.skill_run_field_change);
        REPOSITION = new Reposition("Reposition", ClazzSkill.Type.PASSIVE, false, R.layout.skill_run_player_selection_layout);


        ARCHERY = new Clazz("Archery Magician").bindSkill(SOUL_DESCRIBER).bindSkill(ARCHERY_ATTACK).bindSkill(NULLING_ATTACK);
        SWORDMAN = new Clazz("Knight Magician").bindSkill(SWORDMAN_ATTACK).bindSkill(CHANGE_POSITION);
        SUPREME = new Clazz("Supreme Magician").bindSkill(SOUL_DESCRIBER).bindSkill(MADICIAN_COUNTER).bindSkill(CHANGE_POSITION).bindSkill(REPOSITION);
        ADC = new Clazz("Support Magician").bindSkill(ABSOLUTE_DEFENSE);
        DRAGON_HUNTER = new Clazz("Dragon Hunter");
        LANCER = new Clazz("Lancer Magician").bindSkill(LANCER_ATTACK).bindSkill(CHANGE_POSITION);;
        SPY = new Clazz().bindSkill(SPY_KNOWLEDGE);
    }


    public static Clazz getClazzByInheritedName(String s)
    {
        return CLAZZ_MAP.get(s);
    }
}
