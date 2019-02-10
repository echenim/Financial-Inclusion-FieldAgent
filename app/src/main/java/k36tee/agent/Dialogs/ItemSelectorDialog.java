package k36tee.agent.Dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

import k36tee.agent.Adapters.ItemListAdapter;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 9/10/16.
 */
abstract public class ItemSelectorDialog extends DialogFragment {

    public DialogFragment fragmentInstanceDialogFragment(ArrayList<String> requestObjects, String title) {

        Bundle bundle = new Bundle();
        bundle.putStringArrayList("list", requestObjects);
        bundle.putString("title", title);
        setArguments(bundle);
        return this;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        View rootView = getActivity().getLayoutInflater().inflate(R.layout.item_selector_list, null);
        final ArrayList<String> requestObjects = getArguments().getStringArrayList("list");
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.item_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(new ItemListAdapter(getContext(), requestObjects) {
            @Override
            public void onItemSelected(View view, int position) {
                dismissAllowingStateLoss();
                onListItemSelected(requestObjects.get(position));
            }
        });


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getArguments().getString("title")).setView(rootView);

        return builder.create();
    }

    abstract public void onListItemSelected(String requestObject);
}
