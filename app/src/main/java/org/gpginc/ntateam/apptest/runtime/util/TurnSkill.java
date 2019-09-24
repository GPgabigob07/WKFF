package org.gpginc.ntateam.apptest.runtime.util;

public interface TurnSkill
{

    /**
     * Called once skill list created;
     * --MUST override with same code as {@link org.gpginc.ntateam.apptest.runtime.skills.GodnessProtection}--
     * @return true if present <>USAGES</> into skill object is minor than <>MAX_USAGES</>
     */
    boolean checkCanUse();

    /**
     * Called once in {@link org.gpginc.ntateam.apptest.DamageStep} to apply changes into the skill object
     * @return
     */
    boolean onTurnSwipe();
}
