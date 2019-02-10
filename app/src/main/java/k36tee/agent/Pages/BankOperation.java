package k36tee.agent.Pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import k36tee.agent.Adapters.BankOperationAdapter;
import k36tee.agent.FragmentPages;
import k36tee.agent.Pages.Banks.AccountOpening;
import k36tee.agent.Pages.Banks.BankDeposit;
import k36tee.agent.Pages.Banks.BankWithdrawal;
import k36tee.agent.R;
import k36tee.agent.Utils;

/**
 * Created by myron echenim  on 8/23/16.
 */
public class BankOperation extends FragmentPages {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_categories, container, false);
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.agent_activity_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BankOperationAdapter(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.bank_operation)))) {
            @Override
            protected void onItemSelected(View view, int position) {
                if (position == 0) {
                    Utils.loadPage(getFragmentManager(), new AccountOpening(), true);
                } else if (position == 1) {
                    Utils.loadPage(getFragmentManager(), new BankDeposit(), true);
                } else {
                    Utils.loadPage(getFragmentManager(), new BankWithdrawal(), true);
                }
            }
        });
        return rootView;
    }
}
