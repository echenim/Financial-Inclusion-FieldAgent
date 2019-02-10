package k36tee.agent.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import k36tee.agent.Adapters.ViewHolders.BillAdapterHolder;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
abstract public class BillsAdapter extends RecyclerView.Adapter<BillAdapterHolder> {

    private Context mContext;

    TypedArray typedArray;


    public BillsAdapter(Context context) {
        mContext = context;
        typedArray = context.getResources().obtainTypedArray(R.array.ic_bills);
    }

    @Override
    public BillAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BillAdapterHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.bills_items, parent, false));
    }

    @Override
    public void onBindViewHolder(BillAdapterHolder holder, final int position) {
        holder.itemGrp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemSelected(view, position);
            }
        });
        holder.itemBtn.setImageDrawable(ContextCompat.getDrawable(mContext, typedArray.getResourceId(position, -1)));
        holder.itemName.setText(mContext.getResources().getStringArray(R.array.bills)[position]);
    }

    @Override
    public int getItemCount() {
        return typedArray.length();
    }

    public abstract void onItemSelected(View view, int position);
}
