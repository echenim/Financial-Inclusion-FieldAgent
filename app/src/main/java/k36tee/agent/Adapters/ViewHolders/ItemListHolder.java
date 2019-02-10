package k36tee.agent.Adapters.ViewHolders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class ItemListHolder extends RecyclerView.ViewHolder {


    public AppCompatTextView itemName;

    public ItemListHolder(View itemView) {
        super(itemView);
        itemName = (AppCompatTextView) itemView.findViewById(R.id.object_name);

    }
}
