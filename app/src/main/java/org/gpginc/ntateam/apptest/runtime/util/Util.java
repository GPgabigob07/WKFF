package org.gpginc.ntateam.apptest.runtime.util;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.view.View;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.enums.Rarity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @DrawableRes
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
    @StringRes
    public static int getDeadInfoFor(Player p)
    {
        if(!p.isDead) {
            if (p.life() == 0) {
                return R.string.dead_info_1;
            } else if (p.life() < 0) {
                return R.string.dead_info_2;
            }
        }
        return -1;
    }

    public static String getCrypto(String s)
    {
        String[] sp = s.split("");
        StringBuilder output = new StringBuilder();
        for(String crypted : sp)
        {
            output.append(crypt(crypted));
        }
        return output.toString();
    }
    public static String getDecrypt(String s)
    {
        String[] sp = s.split("");
        StringBuilder output = new StringBuilder();
        for(String crypted : sp)
        {
            output.append(decrypt(crypted));
        }
        return output.toString();
    }


    static String crypt(String s)
    {
        List<String> alpha_minos = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> alpha_MAJOR = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        Map<String, String> intRegex = new HashMap<>();
        /*map init*/
        intRegex.put("0", "§-i§");
        intRegex.put("1", "§i§");
        intRegex.put("2", "§ii§");
        intRegex.put("3", "§iii§");
        intRegex.put("4", "§iv§");
        intRegex.put("5", "§v§");
        intRegex.put("6", "§vi§");
        intRegex.put("7", "§vii§");
        intRegex.put("8", "§viii§");
        intRegex.put("9", "§ix§");
        /*map end*/
        if(alpha_minos.contains(s))
        {
            if(alpha_minos.indexOf(s) > alpha_minos.size() -3)
            {
                return alpha_minos.get(alpha_minos.indexOf(s) + 3 - 26);
            } else return alpha_minos.get(alpha_minos.indexOf(s) + 3);
        } else if (alpha_MAJOR.contains(s))
        {
            if(alpha_MAJOR.indexOf(s) > alpha_MAJOR.size() -3)
            {
                return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) + 3 - 26);
            } else return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) + 3);

        } else if (intRegex.containsKey(s))
        {
            return intRegex.get(s);
        }else return s;
    }
    static String decrypt(String s)
    {
        List<String> alpha_minos = Arrays.asList("abcdefghijklmnopqrstuvwxyz".split(""));
        List<String> alpha_MAJOR = Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split(""));
        Map<String, String> intRegex = new HashMap<>();
        /*map init*/
        intRegex.put("0", "§-i§");
        intRegex.put("1", "§i§");
        intRegex.put("2", "§ii§");
        intRegex.put("3", "§iii§");
        intRegex.put("4", "§iv§");
        intRegex.put("5", "§v§");
        intRegex.put("6", "§vi§");
        intRegex.put("7", "§vii§");
        intRegex.put("8", "§viii§");
        intRegex.put("9", "§ix§");
        /*map end*/
        if(alpha_minos.contains(s))
        {
            if(alpha_minos.indexOf(s) < alpha_minos.size() +3)
            {
                return alpha_minos.get(alpha_minos.indexOf(s) - 3 + 26);
            } else return alpha_minos.get(alpha_minos.indexOf(s) - 3);
        } else if (alpha_MAJOR.contains(s))
        {
            if(alpha_MAJOR.indexOf(s) < alpha_MAJOR.size() +3)
            {
                return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) - 3 + 26);
            } else return alpha_MAJOR.get(alpha_MAJOR.indexOf(s) - 3);

        } else if (intRegex.containsKey(s))
        {
            return intRegex.get(s);
        }else return s;
    }

    public static String[] settingsDefault()
    {
        return new String[]{
                getCrypto("//-SETTINGS-FILE--DO--NOT--MODIFY-//"),
                getCrypto("-|§BOOL§autoLoadSavedPlayers→0§----|"),
                getCrypto("-|§BOOL§checkServer→0§-------------|"),
                getCrypto("-|§CLASS§ARCHERY→1§----------------|"),
                getCrypto("-|§CLASS§SWORDMAN→1§---------------|"),
                getCrypto("-|§CLASS§LANCER→1§-----------------|"),
                getCrypto("-|§CLASS§DRAGONH→1§----------------|"),
                getCrypto("-|§CLASS§SUPPORT→1§----------------|"),
                getCrypto("-|§CLASS§SPY→1§--------------------|"),
                getCrypto("//--------------------------------//")

        };
    }

    public static Dialog dismissed(final Dialog d)
    {
        View.OnClickListener out = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        };
        d.findViewById(R.id.doalog_cancel).setOnClickListener(out);
        d.findViewById(R.id.doalog_ok).setOnClickListener(out);
        return d;
    }

    public static Bundle getEndGame(Event evt, Player player)
    {
        Bundle out = new Bundle();
        out.putInt("EVT", evt.getName());
        out.putParcelable("Target", player);
        return out;
    }

}
