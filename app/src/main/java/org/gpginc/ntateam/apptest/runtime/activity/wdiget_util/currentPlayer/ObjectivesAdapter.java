package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import org.gpginc.ntateam.apptest.R;
import org.gpginc.ntateam.apptest.runtime.Event;
import org.gpginc.ntateam.apptest.runtime.events.Dragonborn;
import org.gpginc.ntateam.apptest.runtime.util.DragonEvent;
import org.gpginc.ntateam.apptest.runtime.util.GameEvent;
import org.gpginc.ntateam.apptest.runtime.util.TargetEvent;

import java.util.List;

public class ObjectivesAdapter extends RecyclerView.Adapter<ObjectivesLineHolder> {

    private final List<Event> EVENTS;

    public ObjectivesAdapter(List<Event> EVENTS) {
        this.EVENTS = EVENTS;
    }

    @NonNull
    @Override
    public ObjectivesLineHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        return new ObjectivesLineHolder(LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.holder_current_player_objectives, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ObjectivesLineHolder holder, int i) {
        Event evt = EVENTS.get(i);
        holder.objectiveName.setText(evt.getName());
        holder.objectiveDescription.setText(evt.getDescription());
        if(evt.needPlayers)holder.objectiveTarget.setText(((TargetEvent)evt).getTarget().getName());
        else if(evt instanceof DragonEvent)
        {
            holder.objectiveKind.setText(R.string.dragon_kingdom);
            holder.objectiveTarget.setText(((DragonEvent)evt).getKingdom());
        }
        else holder.asNonTarget();
    }

    @Override
    public int getItemCount() {
        return this.EVENTS.size();
    }
}
