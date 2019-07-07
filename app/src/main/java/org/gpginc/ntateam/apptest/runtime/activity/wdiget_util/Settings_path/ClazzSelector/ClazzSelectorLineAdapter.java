package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.Settings_path.ClazzSelector;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Clazz;
import org.gpginc.ntateam.apptest.runtime.Clazzs;

public class ClazzSelectorLineAdapter extends RecyclerView.Adapter<ClazzSelectorHolder> {

    @NonNull
    @Override
    public ClazzSelectorHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ClazzSelectorHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.holder_clazz_setting, viewGroup, false), Clazzs.CLAZZS.get(i));
    }

    @Override
    public void onBindViewHolder(@NonNull ClazzSelectorHolder holder, int i) {
        final Clazz c = Clazzs.CLAZZS.get(i);
        holder.inheritedClazz.setText(c.getName());
        holder.inheritedClazz.setChecked(c.enabled);

        holder.inheritedClazz.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                c.enabled = isChecked;
            }
        });
        //icon.setImageResource(c.getIcon());
    }

    @Override
    public int getItemCount() {
        return Clazzs.CLAZZS.size();
    }
}