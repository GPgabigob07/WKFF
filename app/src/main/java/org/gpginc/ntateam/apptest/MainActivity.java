package org.gpginc.ntateam.apptest;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.activity.RuntimeActivity;
import org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.PlayerListAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final ArrayList<String> PLAYER_NAMES = new ArrayList<>();
    private static final Main main = new Main();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.main.preInit();
        ((Button)findViewById(R.id.del_player)).setVisibility(View.INVISIBLE);
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

    }
    public void addPlayer(View view)
    {
        String name = ((EditText)findViewById(R.id.player_name)).getText().toString();
        if(!name.equals("") || name != null) {
            PLAYER_NAMES.add(name);

            ExpandableListView listinha = findViewById(R.id.player_list);

            listinha.setAdapter(new PlayerListAdapter<String>(this, PLAYER_NAMES, this));
            ((EditText)findViewById(R.id.player_name)).setText("");
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
            bundle.putParcelable("CP", (Parcelable) lists[3].get(i));

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

            Intent go = new Intent(this, CurrentPlayer.class);


            go.putExtras(bundle);
            startActivity(go);
        } else {
            Snackbar.make(view, R.string.least4players, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }



    }
    public void openDialog()
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_demo);
        dialog.setTitle("Alert!");
        dialog.show();
    }

}
