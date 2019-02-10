package k36tee.agent;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;

import k36tee.agent.Model.RequestObject;

/**
 * Created by myron echenim  on 8/8/16.
 */
public class FragmentPages extends Fragment {

    protected RequestObject selectedOption;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        selectedOption = null;

        MainActivity.agentTopBalance.setVisibility(View.VISIBLE);

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        MainActivity.agentTopBalance.setVisibility(View.GONE);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        MainActivity.agentTopBalance.setVisibility(View.GONE);
    }
}
