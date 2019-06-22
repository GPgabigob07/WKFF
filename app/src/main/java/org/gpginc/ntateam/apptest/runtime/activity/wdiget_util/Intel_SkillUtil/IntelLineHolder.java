package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.Intel_SkillUtil;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;

public class IntelLineHolder extends RecyclerView.ViewHolder {

    protected TextView playerName, playerClazz;
    protected ImageView field, kingdom;

    public IntelLineHolder(@NonNull View itemView)
    {
        super(itemView);
        this.playerClazz = itemView.findViewById(R.id.intel_player_clazz);
        this.playerName = itemView.findViewById(R.id.intel_player_name);
        this.field =  itemView.findViewById(R.id.field_showner_intel);
        this.kingdom =  itemView.findViewById(R.id.kingdom_emblem_intel);
    }
}
