package org.gpginc.ntateam.apptest.runtime.skills;

import android.app.Dialog;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.FieldSliderAdapter;

import java.util.ArrayList;
import java.util.List;

import static org.gpginc.ntateam.apptest.runtime.Main.PLAYERS;
import static org.gpginc.ntateam.apptest.runtime.Main.input;
import static org.gpginc.ntateam.apptest.runtime.Main.p;
import static org.gpginc.ntateam.apptest.runtime.Main.setDownFieldMemory;
import static org.gpginc.ntateam.apptest.runtime.Main.setUpFieldMemory;

public class ChangePosition extends ClazzSkill
{

    public ChangePosition(String name, Type type, boolean isCounter) {
        super(name, type, isCounter);
    }

    public ChangePosition(String name, Type type, boolean isCounter, int layout) {
        super(name, type, isCounter, layout);
    }

    public ChangePosition(Parcel in) {
        super(in);
    }

    @Override
    public void runSkill(@Nullable Object o)
    {
        if(o!=null)
        {
            final ViewPager pager = (ViewPager) this.current.findViewById(R.id.pager);
            final ClazzSkill thisSkill = this;
            pager.setAdapter(new FieldSliderAdapter(this.current.getSupportFragmentManager(), this.current));

            TextView txt = this.current.findViewById(R.id.title);

            final RuntimeActivity r = this.current;
            this.current.findViewById(R.id.change_field_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = r.getDialog("You are in this field");
                    d.findViewById(R.id.doalog_cancel).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            d.dismiss();
                        }
                    });
                    if(r.getCP().getField() == pager.getCurrentItem()+1)
                    {
                        d.show();
                    } else
                    {
                        Main.p("Current:" + (pager.getCurrentItem()+1));
                        setDownFieldMemory(r.getCP().getField());
                        r.getCP().setField(pager.getCurrentItem() + 1);
                        setUpFieldMemory(r.getCP().getField());
                        r.finish();
                        if(!thisSkill.isExternal())
                        {
                            thisSkill.getLastAct().goNext(v);
                            thisSkill.external = false;
                        }
                    }
                }
            });
            Player p = (Player)o;
            pager.setCurrentItem(p.getField()-1);

           /* switch(p.getField())
            {
                case 1:
                    txt.setTextColor(Color.WHITE);
                    break;
                case 2:
                    txt.setTextColor(Color.WHITE);
                    break;
                case 3:
                    txt.setTextColor(Color.WHITE);
                    break;
                case 4:
                    txt.setTextColor(Color.WHITE);
                    break;
            }*/
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
