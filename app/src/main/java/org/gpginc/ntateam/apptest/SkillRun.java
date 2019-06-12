package org.gpginc.ntateam.apptest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public class SkillRun extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle d = this.getIntent().getExtras();
        setContentView(d.getInt("layout"));
    }

}
