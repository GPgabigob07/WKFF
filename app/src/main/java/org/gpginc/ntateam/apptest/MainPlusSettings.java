package org.gpginc.ntateam.apptest;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.Clazzs;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerListAdapter;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class MainPlusSettings extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final ArrayList<String> PLAYER_NAMES = new ArrayList<>();
    private static final Main main = new Main();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_plus_settings);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setTitle(R.string.wkff_label);
        setSupportActionBar(toolbar);
        this.main.preInit();
        final ListView list = findViewById(R.id.player_list);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);

                if(view.isSelected())
                {
                    Toast.makeText(list.getContext(), "SELECTED", Toast.LENGTH_SHORT).show();
                } else Toast.makeText(list.getContext(), "UNSELECTED", Toast.LENGTH_SHORT).show();
                /*if(list.getCheckedItemCount() > 0)((Button) findViewById(R.id.del_player)).setVisibility(View.VISIBLE);
                else ((Button)findViewById(R.id.del_player)).setVisibility(View.INVISIBLE);*/
            }
        });
        GifImageView gif = findViewById(R.id.title_animation);
    }

    public void addPlayer(View view)
    {
        String name = ((EditText)findViewById(R.id.player_clazz)).getText().toString();
        if(!name.equals("") || name != null) {
            if(!PLAYER_NAMES.contains(name)) {
                PLAYER_NAMES.add(name);

                ExpandableListView listinha = findViewById(R.id.player_list);

                listinha.setAdapter(new PlayerListAdapter<String>(this, PLAYER_NAMES, this));
                ((EditText) findViewById(R.id.player_clazz)).setText("");
            } else {
                Snackbar.make(view, R.string.player_name_justexist, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        } else {
            Snackbar.make(view, R.string.player_name_noname, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
    }
    public void start(View view)
    {
        if(PLAYER_NAMES.size() >=4) {
            final ArrayList[] lists = this.main.postInit(PLAYER_NAMES);
            Bundle bundle = new Bundle();

            bundle.putStringArrayList("PlayerNames", PLAYER_NAMES);
            Random rand = new Random();

            int i = rand.nextInt(PLAYER_NAMES.size());
            bundle.putString("CPN", PLAYER_NAMES.get(i));
            //bundle.putParcelable("CP", (Parcelable) lists[3].get(i));
            bundle.putInt("CurrentPlayerCod", ((Player)lists[3].get(i)).getCod());
            final ArrayList<Integer> a = new ArrayList<>();
            a.add(i);

            Main.p("\n \n \n This is players: ");
            for(Object o : lists[3])
            {
                Main.p(((Player)o).getName());
            }
            bundle.putIntegerArrayList("GonePlayers", a);
            bundle.putStringArrayList("PlayerClazz", lists[0]);
            bundle.putStringArrayList("PlayerKingdoms", lists[1]);
            bundle.putIntegerArrayList("PlayerFields", lists[2]);
            bundle.putParcelableArrayList("Players", lists[3]);

            Intent go = new Intent(this, PrePlayer.class);


            go.putExtras(bundle);
            startActivity(go);
            this.finish();
        } else {
            Snackbar.make(view, R.string.least4players, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



    }

    public void setupClazzsByMainstream() {
        SharedPreferences pref = getSharedPreferences(Main.SETTINGS, 0);
        SharedPreferences.Editor editor = pref.edit();
        for(Clazz c : Clazzs.CLAZZS)
        {
            editor.putBoolean(c.getName(), c.enabled);
        }
    }

    public void loadClazzsByMainstream()
    {
        SharedPreferences pref = getSharedPreferences(Main.SETTINGS, 0);
        for(Clazz c : Clazzs.CLAZZS)
        {
            c.enabled = pref.getBoolean(c.getName(), true);
        }
    }
    public void openDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("Alert!");
        dialog.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        setupClazzsByMainstream();
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_plus_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}