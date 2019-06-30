package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.DmgStp_Util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;

public class DmgLineHolder extends RecyclerView.ViewHolder
{
    protected TextView playerName, playerClazz, deadInfo;
    protected ImageView kingdom;

    public DmgLineHolder(@NonNull View itemView) {
        super(itemView);
        this.playerName = itemView.findViewById(R.id.current_player_name_view);
        this.playerClazz = itemView.findViewById(R.id.current_player_clazz);
        this.deadInfo = itemView.findViewById(R.id.dmg_info);
        this.kingdom = itemView.findViewById(R.id.dmg_kingdom);
    }
}
