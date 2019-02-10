package k36tee.agent.Adapters.ViewHolders;

import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class BillAdapterHolder extends RecyclerView.ViewHolder {

    public LinearLayoutCompat itemGrp;
    public AppCompatImageButton itemBtn;
    public AppCompatTextView itemName;

    public BillAdapterHolder(View itemView) {
        super(itemView);
        itemGrp = (LinearLayoutCompat) itemView.findViewById(R.id.item_grp);
        itemName = (AppCompatTextView) itemView.findViewById(R.id.bill_label);
        itemBtn = (AppCompatImageButton) itemView.findViewById(R.id.bill_img);
    }
}
