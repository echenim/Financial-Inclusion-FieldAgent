package k36tee.agent.Pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;

import k36tee.agent.Adapters.BankOperationAdapter;
import k36tee.agent.Constants;
import k36tee.agent.FragmentPages;
import k36tee.agent.Pages.FundsPages.Acct2Acct;
import k36tee.agent.Pages.FundsPages.Acct2Cash;
import k36tee.agent.Pages.FundsPages.CashToAcct;
import k36tee.agent.Pages.FundsPages.CashToCash;
import k36tee.agent.R;
import k36tee.agent.Utils;

/**
 * Created by myron echenim  on 8/14/16.
 */
public class Funds extends FragmentPages {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bank_categories, container, false);
        ((AppCompatTextView) rootView.findViewById(R.id.title)).setText("Fund Transfer");
        final RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.agent_activity_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BankOperationAdapter(new ArrayList(Arrays.asList(getResources().getStringArray(R.array.funds)))) {
            @Override
            protected void onItemSelected(View view, int position) {
                if (position == Constants.FUNDS_A2A) {
                    Utils.loadPage(getFragmentManager(), new Acct2Acct(), true);
                } else if (position == Constants.FUNDS_A2C) {
                    Utils.loadPage(getFragmentManager(), new Acct2Cash(), true);
                } else if (position == Constants.FUNDS_C2C) {
                    Utils.loadPage(getFragmentManager(), new CashToCash(), true);
                } else if (position == Constants.FUNDS_C2A) {
                    Utils.loadPage(getFragmentManager(), new CashToAcct(), true);
                }
            }
        });
        return rootView;
    }
}
