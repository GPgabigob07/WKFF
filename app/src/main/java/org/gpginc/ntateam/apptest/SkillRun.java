package org.gpginc.ntateam.apptest;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.gpginc.ntateam.apptest.runtime.ClazzSkill;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public class SkillRun extends RuntimeActivity{

    private ClazzSkill currentSkill;

    public SkillRun(){};
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle d = this.getIntent().getExtras();
        this.currentSkill = d.getParcelable("cskill");
        this.currentSkill.setCurrent(this);
        setContentView(this.currentSkill.getLayout());
        this.currentSkill.runSkill(this.CP);

    }
}
