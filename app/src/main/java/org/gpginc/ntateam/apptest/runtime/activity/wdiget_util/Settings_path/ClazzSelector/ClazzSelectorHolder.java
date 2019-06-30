package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.Settings_path.ClazzSelector;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Switch;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Clazz;

public class ClazzSelectorHolder extends RecyclerView.ViewHolder
{

    public ImageView icon;
    public Switch inheritedClazz;

    public ClazzSelectorHolder(@NonNull View itemView, Clazz c) {
        super(itemView);
        icon = itemView.findViewById(R.id.holder_set_clazz_icon);
        inheritedClazz = itemView.findViewById(R.id.holder_set_clazz_name_switch);
    }
}
