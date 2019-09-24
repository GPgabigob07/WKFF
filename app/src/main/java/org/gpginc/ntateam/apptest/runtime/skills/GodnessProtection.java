package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.INotificationSideChannel;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerSelectAdapter;
import org.gpginc.ntateam.apptest.runtime.util.IntanciableSkill;
import org.gpginc.ntateam.apptest.runtime.util.TurnSkill;

import java.util.ArrayList;
import java.util.List;

public class GodnessProtection extends ClazzSkill implements IntanciableSkill<GodnessProtection>, TurnSkill {

    private final int MAX_USAGES = 2;
    private int USAGES = 0;
    private boolean USED = false;

    public GodnessProtection() {
        super(/*R.string.skill_godness*/"Godness Protection", Type.MAHOU, R.layout.skill_run_player_selection_layout, true);
    }

    public GodnessProtection(Parcel in) {
        super(in);
    }

    @Override
    public GodnessProtection newInstance() {
        return new GodnessProtection();
    }

    @Override
    public GodnessProtection base() {
        Clazzs.SKILL_MAP.put(this.getName(), this);
        return this;
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            /*--LIST SETUP--*/
            List<Object> players = new ArrayList<>();
            players.addAll(this.lastAct.getPlayers());

            final PlayerSelectAdapter adapter = new PlayerSelectAdapter(this.current, players, false, 1, this.current.findViewById(R.id.func_skill_btn));
            ListView list = this.current.findViewById(R.id.players_list);
            list.setAdapter(adapter);
            /*-----------------*/
            /*--DIALOG  SETUP--*/
            final Dialog d = lastAct.getDialog(this.current, R.string.player_was_protected);
            ((TextView)d.findViewById(R.id.dialog_info)).setText(((Player)o).getName() + " " + ((TextView)d.findViewById(R.id.dialog_info)).getText());
            d.findViewById(R.id.doalog_cancel).setVisibility(View.INVISIBLE);
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    lastAct.changePlayer(lastAct.findByCode(adapter.getSelectedCodes()[0]).setProtected());
                    current.finish();
                    lastAct.goNext(current.findViewById(R.id.func_skill_btn));
                }
            });
            /*-----------------*/
            /*--BUTTON  SETUP--*/
            this.current.findViewById(R.id.func_skill_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lastAct.changePlayer(lastAct.findByCode(adapter.getSelectedCodes()[0]).setProtected());
                    d.show();
                }
            });
            /*-----------------*/
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<GodnessProtection> CREATOR = new Creator<GodnessProtection>()
    {
        @Override
        public GodnessProtection createFromParcel(Parcel source) {
            return new GodnessProtection(source);
        }

        @Override
        public GodnessProtection[] newArray(int size) {
            return new GodnessProtection[size];
        }
    };

    @Override
    public boolean checkCanUse()
    {
        return this.USAGES < this.MAX_USAGES && !this.USED;
    }

    @Override
    public boolean onTurnSwipe()
    {
        this.USED = false;
        return true;
    }
}
