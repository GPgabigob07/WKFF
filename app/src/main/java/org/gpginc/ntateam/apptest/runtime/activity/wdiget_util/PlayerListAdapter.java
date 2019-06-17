package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.gpginc.ntateam.apptest.MainActivity;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.List;

public class PlayerListAdapter<T extends Object> extends BaseExpandableListAdapter
{
    private final Context context;
    private final List<T> owners;
    private final MainActivity activity;
   // private final String[][] subowners;

    /**
     * Creates the main player adding list, which you are able to delete or change some players name;
     * P.S. This delete the player, and set a new one, {@link Player} name is final.
     * @param context
     * @param owners
     * @param activity
     */
    public PlayerListAdapter(Context context, List<T> owners, MainActivity activity)
    {
        this.context = context;
        this.owners = owners;
        this.activity = activity;
       // this.subowners = subowners;

    }
    @Override
    public int getGroupCount() {
        return this.owners.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.owners.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition*1000;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        TextView txt = new TextView(this.context);
        txt.setText(this.owners.get(groupPosition).toString());
        txt.setPadding(30, 5, 0 ,5);
        txt.setTextSize(20);
        return txt;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Button delete = new Button(this.context);
        final PlayerListAdapter a = this;
        delete.setText(R.string.del_player);
        delete.setPadding(10, 5, 30 ,5);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "Clicked lol", Toast.LENGTH_SHORT).show();
                a.owners.remove(groupPosition);
                ((ExpandableListView)a.activity.findViewById(R.id.player_list)).setAdapter(a);
            }
        });
        return delete;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
