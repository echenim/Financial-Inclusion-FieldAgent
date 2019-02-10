package k36tee.agent.Adapters.ViewHolders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class BankOperationAdapterHolder extends RecyclerView.ViewHolder {
    public AppCompatTextView item;

    public BankOperationAdapterHolder(View itemView) {
        super(itemView);
        item = (AppCompatTextView) itemView.findViewById(R.id.operationText);
    }
}
