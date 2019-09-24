package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.FieldSliderAdapter;

import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class ChangePosition extends ClazzSkill
{

    public ChangePosition(String name, Type type) {
        super(name, type, false);
    }

    public ChangePosition(String name, Type type, int layout) {
        super(name, type, layout, false);
    }

    public ChangePosition(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            final Player p = (Player)o;
            final ViewPager pager = this.current.findViewById(R.id.pager);
            final ClazzSkill thisSkill = this;
            pager.setAdapter(new FieldSliderAdapter(this.current.getSupportFragmentManager(), this.current));

            final RuntimeActivity r = this.lastAct;
            final SkillRun sk = this.current;
            this.current.findViewById(R.id.change_field_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = r.getDialog(sk,R.string.same_field);
                    d.findViewById(R.id.doalog_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                            sk.finish();
                        }
                    });
                    if(p.getField() == pager.getCurrentItem()+1)
                    {
                        d.show();
                    } else
                    {
                        Main.p("Current:" + (pager.getCurrentItem()+1));
                        setDownFieldMemory(r.currentPlayer().getField());
                        p.setField(pager.getCurrentItem() + 1);
                        r.changePlayer(p);
                        setUpFieldMemory(r.currentPlayer().getField());
                        if(thisSkill.isExternal())
                        {
                            sk.finish();
                            thisSkill.external = false;
                        }
                        else {
                            r.goNext(v);
                            current.finish();
                        }
                    }
                }
            });

          pager.setCurrentItem(p.getField()-1);
        }
    }

    public static final Creator<ChangePosition> CREATOR = new Creator<ChangePosition>()
    {
        @Override
        public ChangePosition createFromParcel(Parcel source) {
            return new ChangePosition(source);
        }

        @Override
        public ChangePosition[] newArray(int size) {
            return new ChangePosition[size];
        }
    };

    @Override
    public Creator getCreator() {
        return CREATOR;
    }


}
