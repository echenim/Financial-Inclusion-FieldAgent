package k36tee.agent.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import k36tee.agent.Adapters.ViewHolders.AgentActivityAdapterHolder;
import k36tee.agent.Model.AgentModel;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
abstract public class AgentActivityAdapter extends RecyclerView.Adapter<AgentActivityAdapterHolder> {

    private Context mContext;

    ArrayList<AgentModel> agentModels;

    public AgentActivityAdapter(Context context, ArrayList<AgentModel> agentModels) {
        mContext = context;
        this.agentModels = agentModels;
    }

    @Override
    public AgentActivityAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AgentActivityAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_activity_item, parent, false));
    }

    @Override
    public void onBindViewHolder(AgentActivityAdapterHolder holder, final int position) {
        holder.transactionDate.setText(agentModels.get(position).getTrasactionTime());
        holder.transactionAmount.setText(String.valueOf(agentModels.get(position).getAmount()));
        holder.transactionName.setText(agentModels.get(position).getTrasaction());
    }

    @Override
    public int getItemCount() {
        return agentModels.size();
    }

    public abstract void onItemSelected(View view, int position);
}
