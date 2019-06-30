package org.gpginc.ntateam.apptest;

import android.app.Dialog;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.FieldSliderFragment;

public class SkillRun extends AppCompatActivity{

    private ClazzSkill currentSkill;
    public Player ongoingPlayer;

    public SkillRun(){};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Bundle d = this.getIntent().getExtras();
        super.onCreate(savedInstanceState);
        this.currentSkill = getTypedSkill(d.getString("cskill"));
        this.ongoingPlayer = d.getParcelable("PLAYER_EXECUTE");
        setContentView(this.currentSkill.getLayout());
        setTitle(this.currentSkill.getName());
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        this.currentSkill.setCurrent(this);
        this.currentSkill.runSkill(this.ongoingPlayer);

    }

    protected ClazzSkill getTypedSkill(String skillName)
    {
        Main.p("TypedSkill: "+skillName);
        return Clazzs.SKILL_MAP.get(skillName);
    }
    public ClazzSkill getSkill()
    {
        return this.currentSkill;
    }

    public View.OnClickListener dialogDismiss(final Dialog d, final Boolean endAct)
    {
        final SkillRun r = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
                if(endAct)r.finish();
            }
        };
    }
}
