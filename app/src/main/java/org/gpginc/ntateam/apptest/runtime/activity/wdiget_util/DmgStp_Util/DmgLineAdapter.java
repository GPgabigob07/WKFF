package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.DmgStp_Util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.gpginc.ntateam.apptest.runtime.Dragon;
import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Player;
import org.gpginc.ntateam.apptest.runtime.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DmgLineAdapter extends RecyclerView.Adapter<DmgLineHolder>
{
    private List<Player> deadPlayers = new ArrayList<>();

    public DmgLineAdapter(List<Player> deadPlayers) {
        this.deadPlayers = deadPlayers;
    }

    @NonNull
    @Override
    public DmgLineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new DmgLineHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.holder_dead_player,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull DmgLineHolder holder, int i)
    {
        Player p = this.deadPlayers.get(i);
        holder.kingdom.setImageResource(Util.getKindomFor(p));
        holder.playerClazz.setText(p.getClazz().getName());
        holder.deadInfo.setText(Util.getDeadInfoFor(p) != -1 ? Util.getDeadInfoFor(p) : R.string.bugstr);
        if(p.life() < 0) holder.deadInfo.setText(holder.deadInfo.getText() + " " +
                ((p.getLastAttacker() instanceof Dragon) ?
                        ((Dragon)p.getLastAttacker()).getNameAsString() :
                        ((Player)p.getLastAttacker()).getName())) ;
        holder.playerName.setText(p.getName());
    }

    @Override
    public int getItemCount() {
        return this.deadPlayers != null ? this.deadPlayers.size() : 0;
    }
}
