package k36tee.agent.Pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import k36tee.agent.Adapters.AgentActivityAdapter;
import k36tee.agent.FragmentPages;
import k36tee.agent.Model.AgentModel;
import k36tee.agent.ProcessDialog;
import k36tee.agent.R;
import k36tee.agent.Request.AgentRequest;

/**
 * Created by myron echenim  on 8/24/16.
 */
public class AgentActivities extends FragmentPages {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.agent_activity, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.agent_activity_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        processDialog.showDialog(getContext(), "Retrieving Trasaction details");
        new AgentRequest().new AgentTransactions() {
            @Override
            public void onRequestComplete(ArrayList<AgentModel> agentModels) {
                processDialog.dismiss();
                recyclerView.setAdapter(new AgentActivityAdapter(getContext(), agentModels) {
                    @Override
                    public void onItemSelected(View view, int position) {

                    }
                });
            }
        }.getAllTransactionDetails(getContext());

        return rootView;
    }

    ProcessDialog processDialog = new ProcessDialog();
}
