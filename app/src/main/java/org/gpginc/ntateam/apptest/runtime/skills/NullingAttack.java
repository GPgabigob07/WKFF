package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.InstanciableSkill;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import static org.gpginc.ntateam.apptest.runtime.Main.p;

public class NullingAttack extends ClazzSkill implements InstanciableSkill<NullingAttack>
{

    public NullingAttack(String name, Type type) {
        super(name, type, true);
    }

    public NullingAttack(String name, Type type, int layout) {
        super(name, type, layout, true);
    }

    public NullingAttack(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Player p = (Player) o;
            if(p.attacked && !this.isPassiveRun())
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
                this.setPassiveRun(true);
                p.isProtected = true;
                ((ImageView)this.lastAct.findViewById(R.id.current_player_life)).setBackgroundResource(Util.getPlayerLifeShowner(p));
            } else if(p.attacked)
            {
                final Dialog d = this.lastAct.getDialog(this.lastAct, R.string.dtpywttd);
                d.setContentView(R.layout.dialog_protection_appied);
                d.findViewById(R.id.shield_img_dialog).setBackgroundResource(R.drawable.dead_marker);
                ((TextView)d.findViewById(R.id.dntw_txt)).setText(R.string.tpywtdis);
                d.findViewById(R.id.back_btn_skill_dialog).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                PopupWindow popupWindow = new PopupWindow(d.getContext());
                popupWindow.showAsDropDown(this.lastAct.findViewById(R.id.content_cp_layout));
                d.show();
                this.setPassiveRun(false);
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

    @Override
    public NullingAttack newInstance() {
        Main.p("\n----------------\nNEW INSTANCE OF NULLING ATTACK\n----------------\n");
        return new NullingAttack(this.getName(), this.getType(), this.getLayout());
    }

    @Override
    public NullingAttack base() {
        Clazzs.SKILL_MAP.put(this.getName(), this);
        return this;
    }
}
