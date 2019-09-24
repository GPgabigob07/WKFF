package org.gpginc.ntateam.apptest.runtime.activity.wdiget_util.currentPlayer;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.gpginc.ntateam.apptest.R;

public class ObjectivesLineHolder extends RecyclerView.ViewHolder
{
    public TextView objectiveName, objectiveDescription, objectiveTarget, objectiveKind;
    private LinearLayout target;

    public ObjectivesLineHolder(@NonNull View itemView) {
        super(itemView);
        objectiveName = itemView.findViewById(R.id.o_name);
        objectiveDescription = itemView.findViewById(R.id.o_description);
        objectiveTarget = itemView.findViewById(R.id.target_name);
        objectiveKind = itemView.findViewById(R.id.target_txt);
        target = itemView.findViewById(R.id.objective_target);
    }

    public void asNonTarget()
    {
        this.target.setVisibility(View.INVISIBLE);
    }
}
