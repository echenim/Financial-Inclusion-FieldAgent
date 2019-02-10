package k36tee.agent.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import k36tee.agent.Adapters.ViewHolders.BankOperationAdapterHolder;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
abstract public class BankOperationAdapter extends RecyclerView.Adapter<BankOperationAdapterHolder> {

    ArrayList operationItems;

    public BankOperationAdapter(ArrayList operationItems) {
        this.operationItems = operationItems;

    }

    @Override
    public BankOperationAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BankOperationAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bank_categories_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BankOperationAdapterHolder holder, final int position) {
        holder.item.setText(operationItems.get(position).toString());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemSelected(view, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return operationItems.size();
    }

    protected abstract void onItemSelected(View view, int position);
}
