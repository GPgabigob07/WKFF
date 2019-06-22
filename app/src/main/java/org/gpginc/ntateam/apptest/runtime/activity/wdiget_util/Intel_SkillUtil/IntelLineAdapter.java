package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.Intel_SkillUtil;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Player;

import java.util.List;

public class IntelLineAdapter extends RecyclerView.Adapter<IntelLineHolder> {

    private final List<Player> player_list;

    public IntelLineAdapter(List<Player> player_list) {
        this.player_list = player_list;
    }

    @NonNull
    @Override
    public IntelLineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new IntelLineHolder(LayoutInflater.from(viewGroup.getContext())
        .inflate(R.layout.skr_listitem_intel, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull IntelLineHolder holder, int i)
    {
        holder.playerName.setText(this.player_list.get(i).getName());
        holder.playerClazz.setText(this.player_list.get(i).getClazz().getName());
        holder.field.setImageResource(this.getFieldFor(this.player_list.get(i)));
        holder.kingdom.setImageResource(this.getKindomFor(this.player_list.get(i)));
    }

    @Override
    public int getItemCount() {
        return this.player_list != null ? this.player_list.size() : 0;
    }

    protected int getFieldFor(Player p)
    {
        switch (p.getField())
        {
            case 1:
                return R.drawable.field_1;
            case 2:
                return R.drawable.field_2;
            case 3:
                return R.drawable.field_3;
            case 4:
                return R.drawable.field_4;
            default:
                return R.drawable.unkown_e;
        }
    }
    protected int getKindomFor(Player p)
    {
        switch (p.getKingdom())
        {
            case "CAMELOT":
                return R.drawable.camelot_emblem;
            case "OHXER":
                return R.drawable.ohxer_emblem;
            default:
                return R.drawable.unkown_e;
        }
    }
}
