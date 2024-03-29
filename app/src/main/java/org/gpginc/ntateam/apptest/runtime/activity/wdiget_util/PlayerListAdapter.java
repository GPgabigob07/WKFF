package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.MainPlusSettings;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.List;


public class PlayerListAdapter<T> extends BaseExpandableListAdapter
{
    private final Context context;
    private final List<T> owners;
    private final MainPlusSettings activity;
    private LayoutInflater inflater;
   // private final String[][] subowners;

    /**
     * Creates the main player adding list, which you are able to delete or change some players name;
     * P.S. This delete the player, and set a new one, {@link Player} name is final.
     * @param context
     * @param owners
     * @param activity
     */
    public PlayerListAdapter(Context context, List<T> owners, MainPlusSettings activity)
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
        View viewRow = convertView;
        if(viewRow == null)
        {
            this.inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(R.layout.pla_child_layout, null, true);
        }
        final T o = this.owners.get(groupPosition);
        if(o != null)
        {
            final EditText namae = viewRow.findViewById(R.id.rename);
            final PlayerListAdapter<T> AD = this;
            namae.setText(o.toString());

            viewRow.findViewById(R.id.rename_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String txt = namae.getText().toString();
                    AD.owners.set(groupPosition, (T)txt);
                    ((ExpandableListView)AD.activity.findViewById(R.id.player_list)).setAdapter(AD);
                }
            });
            viewRow.findViewById(R.id.delete_player).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AD.owners.remove(groupPosition);
                    ((ExpandableListView)AD.activity.findViewById(R.id.player_list)).setAdapter(AD);
                }
            });
        }
        return viewRow;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
