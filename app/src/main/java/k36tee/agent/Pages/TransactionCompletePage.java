package k36tee.agent.Pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import k36tee.agent.FragmentPages;
import k36tee.agent.MainActivity;
import k36tee.agent.R;

/**
 * Created by myron echenim  on 8/14/16.
 */
public class TransactionCompletePage extends FragmentPages {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MainActivity.isComplete = true;
        View rootView = inflater.inflate(R.layout.transaction_complete, container, false);
        rootView.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
        return rootView;
    }
}
