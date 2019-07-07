package org.gpginc.ntateam.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;

public class PrePlayer extends RuntimeActivity {

    @DrawableRes
    private int currentRes = R.drawable.shields;
    private boolean checked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_player);
        setTitle(R.string.title_activity_pre_player);
        //setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
        ((ImageView)findViewById(R.id.togglePlayer)).setImageResource(this.currentRes);
        findViewById(R.id.show_player).setVisibility(View.INVISIBLE);
        ((TextView)findViewById(R.id.current_player_name_view)).setText(currentPlayer().getName());
    }

    public void switchs(View v)
    {
        ImageView iv = (ImageView) v;
        switch (this.currentRes)
        {
            case R.drawable.shields:
                this.currentRes = R.drawable.shields2;
                this.checked = true;
                break;
            case R.drawable.shields2:
                this.checked = false;
                this.currentRes = R.drawable.shields;
                break;
        }
        ((ImageView)findViewById(R.id.togglePlayer)).setImageResource(this.currentRes);
        findViewById(R.id.show_player).setVisibility(this.checked ? View.VISIBLE : View.INVISIBLE);
    }

    public void checkToGo(View v)
    {
        if(this.checked)
        {
            Intent next = new Intent(this, CurrentPlayer.class);
            next.putExtras(this.enableNext());
            startActivity(next);
            this.finish();
        }
    }
}
