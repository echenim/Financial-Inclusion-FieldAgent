package k36tee.agent.Pages.FundsPages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import k36tee.agent.FragmentPages;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/25/16.
 */
public class CashToAcct extends FragmentPages {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.cash2acct, container, false);
        return rootView;
    }
}
