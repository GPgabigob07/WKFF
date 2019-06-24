package org.gpginc.ntateam.apptest.runtime.util;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Player;

public class Util
{
    public static int getFieldFor(Player p)
    {
        switch (p.getField())
        {
            case 1:
                return R.drawable.field_1;
            case 2:
                return R.drawable.field_2;
            case 3:
                return R.drawable.field_3;
            case 4:
                return R.drawable.field_4;
            default:
                return R.drawable.unkown_e;
        }
    }
    public static int getKindomFor(Player p)
    {
        switch (p.getKingdom())
        {
            case "CAMELOT":
                return R.drawable.camelot_emblem;
            case "OHXER":
                return R.drawable.ohxer_emblem;
            default:
                return R.drawable.unkown_e;
        }
    }
}
