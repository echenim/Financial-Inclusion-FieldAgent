package k36tee.agent.Adapters.ViewHolders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class AgentActivityAdapterHolder extends RecyclerView.ViewHolder {


    public AppCompatTextView transactionDate, transactionAmount, transactionName;

    public AgentActivityAdapterHolder(View itemView) {
        super(itemView);
        transactionDate = (AppCompatTextView) itemView.findViewById(R.id.trasaction_date);
        transactionAmount = (AppCompatTextView) itemView.findViewById(R.id.trasaction_amount);
        transactionName = (AppCompatTextView) itemView.findViewById(R.id.trasaction_name);

    }
}
