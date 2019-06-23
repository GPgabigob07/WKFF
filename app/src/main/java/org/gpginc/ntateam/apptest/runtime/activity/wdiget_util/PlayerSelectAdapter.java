package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.SkillRun;
import org.gpginc.ntateam.apptest.runtime.Main;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.ArrayList;
import java.util.List;

public class PlayerSelectAdapter extends ArrayAdapter<Object>
{
    private List<Object> inf = new ArrayList<>();
    private final List<Boolean> selectedIndexes = new ArrayList<>();
    private LayoutInflater inflater;

    public boolean isShowField() {
        return showField;
    }

    public void setShowField(boolean showField) {
        this.showField = showField;
    }

    private boolean showField;
    private final int limit;
    protected final View infos;

    /**
     *
     * Creates a ArrayAdapter of {@link Object}, can be safely everything, used to turn players to unknown if needed;
     *
     * @param context Default Array Adapter context
     * @param objects Players list, treated as {@code List<Object>}
     * @param noNames True to hide player names, but showing it index.
     * @param limitOfChecks How many ´players can be selected.
     * @param infos A usually {@link View} to show {@link Snackbar}
     */
    public PlayerSelectAdapter(@NonNull SkillRun context, @NonNull List<Object> objects, boolean noNames, int limitOfChecks, View infos)
    {
        super(context, R.layout.player_adapter_view, objects);
        this.limit = limitOfChecks;
        this.inf = objects;
        if(noNames)
        {
            final List<Object> list =  new ArrayList<>();
            for(Object p : objects)
            {
                list.add("Player " + context.getSkill().getLastAct().getPlayers().indexOf(p));
            }
            this.inf = list;
        }

        this.infos = infos;
        for(Object o :objects)
        {
            this.selectedIndexes.add(false);
        }

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View viewRow = convertView;
        if(viewRow == null)
        {
            this.inflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewRow = inflater.inflate(R.layout.player_adapter_view, null, true);
        }
        Object o = this.inf.get(position);
        final PlayerSelectAdapter a = this;
        if(o!=null)
        {
            final CheckBox box = viewRow.findViewById(R.id.player_checkable);
            if(box!= null)
            {
                Snackbar.make(a.infos, R.string.player_info_attack_single_or_twice, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
                box.setText(o.toString() + (this.showField ? " in field: "+((Player)o).getField() : ""));
                box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
                    {
                        a.selectedIndexes.set(position, isChecked);
                        if(a.getSelectedCount() > limit) {
                            Snackbar.make(a.infos
                                    , "You can select just " + limit + " player(s)!", Snackbar.LENGTH_SHORT)
                                    .setAction("Action", null).show();
                            box.setChecked(false);
                            a.selectedIndexes.set(position, false);
                        }
                    }
                });
            }
        }
        return viewRow;
    }

    public boolean isItemCheckedAt(int position)
    {
        return this.selectedIndexes.get(position);
    }

    public int[] getSelectedIndexes()
    {
        int[] a = new int[this.limit];
        int aux = 0;
        for(int i = 0; i < this.selectedIndexes.size(); ++i)
        {
           if(this.selectedIndexes.get(i))
           {
               a[aux] = i;
               ++aux;
           }
        }
        return a;
    }
    public int getSelectedCount()
    {
        int a = 0;
        for(boolean b : this.selectedIndexes)
            a = b ? ++a : a;
        return a;
    }
}
