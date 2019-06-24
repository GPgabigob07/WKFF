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

public class AbsoluteDefense extends ClazzSkill
{

    public AbsoluteDefense(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public AbsoluteDefense(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public AbsoluteDefense(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            Player p = (Player) o;
            if(!this.isPassiveRun())
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
                if(p.attacked)d.show();
                p.isProtected = true;
                this.setPassiveRun(true);
            }
        }
    }

    @Override
    public Creator getCreator() {
        return CREATOR;
    }

    public static final Creator<AbsoluteDefense> CREATOR = new Creator<AbsoluteDefense>()
    {
        @Override
        public AbsoluteDefense createFromParcel(Parcel source) {
            return new AbsoluteDefense(source);
        }

        @Override
        public AbsoluteDefense[] newArray(int size) {
            return new AbsoluteDefense[size];
        }
    };
}
