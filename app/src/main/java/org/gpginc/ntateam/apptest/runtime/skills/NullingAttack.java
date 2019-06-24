package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class NullingAttack extends ClazzSkill
{

    public NullingAttack(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public NullingAttack(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public NullingAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        //TODO implementing attack trigger
        if(o!=null)
        {
            Player p = (Player) o;
            if(p.attacked && !this.isPassiveRun() || this.isPassiveRun())
            {
                final Dialog d = this.lastAct.getDialog(this.lastAct, R.string.dtpywttd);
                d.setContentView(R.layout.dialog_protection_appied);
                d.findViewById(R.id.back_btn_skill_dialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                PopupWindow popupWindow = new PopupWindow(d.getContext());
                popupWindow.showAsDropDown(this.lastAct.findViewById(R.id.content_cp_layout));
                d.show();
            } else if(p.attacked)
            {
                p("This phase you will take damage, I'm sorry...");
            } else
            {
                p("U weren't attacked... till now...");
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<NullingAttack> CREATOR = new Creator<NullingAttack>()
    {
        @Override
        public NullingAttack createFromParcel(Parcel source) {
            return new NullingAttack(source);
        }

        @Override
        public NullingAttack[] newArray(int size) {
            return new NullingAttack[size];
        }
    };
}
