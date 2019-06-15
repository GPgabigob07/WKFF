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


    /**
     * Classes Declaration
     */
    public static final Clazz ARCHERY;
    public static final Clazz SWORDMAN;
    public static final Clazz LANCER;
    public static final Clazz SUPREME;
    public static final Clazz SPY;
    public static final Clazz DRAGON_HUNTER;
    public static final Clazz ADC;


    /**
     * Classes init
     */
    static {

        /**
         * Skills
         */
        final ClazzSkill SOUL_DESCRIBER = new SoulDescriber("Soul Describer", ClazzSkill.Type.MAHOU, false);
        final ClazzSkill KING_KNOWNS = new KingKnowns("SEE ALL", ClazzSkill.Type.MAHOU, false);
        final ClazzSkill ARCHERY_ATTACK = new ArcherAttack("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        final ClazzSkill LANCER_ATTACK = new LancerAttack("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        final ClazzSkill SWORDMAN_ATTACK = new SwordmanAttack("Attack", ClazzSkill.Type.ATTACK, false, R.layout.skill_run_player_selection_layout);
        final ClazzSkill SPY_KNOWLEDGE = new SpyKnwoledge("INTEL", ClazzSkill.Type.PASSIVE, false);
        final ClazzSkill NULLING_ATTACK = new NullingAttack("Nulling Direct Attack", ClazzSkill.Type.ATTACK_TRIGGER, false);
        final ClazzSkill ABSOLUTE_DEFENSE = new AbsoluteDefense("ABSOLUTE DEFENSE", ClazzSkill.Type.PASSIVE, false);
        final ClazzSkill MADICIAN_COUNTER = new MagicianCounter("Madician Counter", ClazzSkill.Type.MAHOU, true);
        final ClazzSkill CHANGE_POSITION = new ChangePosition("Change Position", ClazzSkill.Type.MAHOU, false);
        final ClazzSkill REPOSITION = new Reposition("Reposition", ClazzSkill.Type.PASSIVE, false);


        ARCHERY = new Clazz("Archery Madician").bindSkill(SOUL_DESCRIBER).bindSkill(ARCHERY_ATTACK).bindSkill(NULLING_ATTACK);
        SWORDMAN = new Clazz("Knight Madician").bindSkill(SWORDMAN_ATTACK).bindSkill(CHANGE_POSITION);
        SUPREME = new Clazz("Supreme Madician").bindSkill(SOUL_DESCRIBER).bindSkill(KING_KNOWNS).bindSkill(MADICIAN_COUNTER).bindSkill(CHANGE_POSITION).bindSkill(REPOSITION);
        ADC = new Clazz("Adc Support Madician").bindSkill(ABSOLUTE_DEFENSE);
        DRAGON_HUNTER = new Clazz("Dragon Hunter");
        LANCER = new Clazz("Lancer Madician").bindSkill(LANCER_ATTACK).bindSkill(CHANGE_POSITION);;
        SPY = new Clazz().bindSkill(SPY_KNOWLEDGE);
    }


    public static Clazz getClazzByInheritedName(String s)
    {
        return CLAZZ_MAP.get(s);
    }
}
