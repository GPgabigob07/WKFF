package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class SoulDescriber extends ClazzSkill
{

    public SoulDescriber(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public SoulDescriber(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public SoulDescriber(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o) {
        Player p = (Player) o;
        int asd = 0;
        final List<Object> attackable = new ArrayList<>();
        for (Player pP : lastAct.getPlayers()) {
            if (!p.getName().equals(pP.getName())) attackable.add(pP);
            Main.p(pP.getName());
        }
        final ListView list = ((ListView) this.current.findViewById(R.id.players_list));
        final Button btn = this.current.findViewById(R.id.func_skill_btn);
        btn.setHint("single");
        final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, attackable, false, 1, list);
        final RuntimeActivity r = this.lastAct;
        final SkillRun sk = this.current;
        final Dialog d = r.getDialog(this.current, R.string.select_only_one_player);
        d.findViewById(R.id.doalog_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        list.setAdapter(adapter);
        btn.setOnClickListener(new View.OnClickListener() {
            public View.OnClickListener secondListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();

                }
            };

            @Override
            public void onClick(View v) {

                ((Button) d.findViewById(R.id.doalog_ok)).setText(android.R.string.ok);
                ((Button) d.findViewById(R.id.doalog_ok)).setOnClickListener(this.secondListener);

                if (adapter.getSelectedCount() < 1) {
                    d.show();
                } else if (adapter.getSelectedCount() == 1) {
                   final Dialog d3 = r.getDialog(sk, R.string.lancer_atk_info);
                   d3.setContentView(R.layout.skr_soul_inspect);
                   TextView playerName, playerClazz;
                   RelativeLayout kingdom;
                   playerClazz = d3.findViewById(R.id.player_clazz);
                   playerName = d3.findViewById(R.id.player_name);
                   kingdom =  d3.findViewById(R.id.kingdom_inspect);
                   playerClazz.setText((lastAct.findByCode(adapter.getSelectedCodes()[0]).getClazz().getName()));
                   playerName.setText((lastAct.findByCode(adapter.getSelectedCodes()[0]).getName()));
                   kingdom.setBackgroundResource(Util.getKindomFor(lastAct.findByCode(adapter.getSelectedCodes()[0])));
                   kingdom.setOnClickListener(lastAct.dialogDismiss(d3, false));
                   d3.setOnDismissListener(new DialogInterface.OnDismissListener() {
                       @Override
                       public void onDismiss(DialogInterface dialog) {
                           lastAct.findViewById(R.id.next_player_btn).performClick();
                       }
                   });
                   d3.show();
                }
            }
        });
    }

    public static final Creator<SoulDescriber> CREATOR = new Creator<SoulDescriber>()
    {
        @Override
        public SoulDescriber createFromParcel(Parcel source) {
            return new SoulDescriber(source);
        }

        @Override
        public SoulDescriber[] newArray(int size) {
            return new SoulDescriber[size];
        }
    };
    @Override
    public Creator getCreator() {
        return CREATOR;
    }
}
