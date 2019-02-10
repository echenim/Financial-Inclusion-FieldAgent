package k36tee.agent.Pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import k36tee.agent.Constants;
import k36tee.agent.Dialogs.RequestObjSelectorDialog;
import k36tee.agent.FragmentPages;
import k36tee.agent.Model.RequestObject;
import k36tee.agent.ProcessDialog;
import k36tee.agent.R;
import k36tee.agent.Request.AgentRequest;
import k36tee.agent.Utils;

/**
 * Created by myron echenim  on 8/14/16.
 */
public class AirtimeVending extends FragmentPages {


    AppCompatTextView telcoSelector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.airtime_vending, container, false);
        telcoSelector = (AppCompatTextView) rootView.findViewById(R.id.telco_selector);
        telcoSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDialog.showDialog(getContext(), "Fetching Telco List");
                new AgentRequest().new RequestObjects() {
                    @Override
                    protected void onRequestComplete(ArrayList<RequestObject> requestObjects) {
                        processDialog.dismiss();
                        new RequestObjSelectorDialog() {
                            @Override
                            public void onListItemSelected(RequestObject requestObject) {
                                selectedOption = requestObject;
                                telcoSelector.setText(requestObject.getName());
                            }
                        }.fragmentInstanceDialogFragment(requestObjects, "Select Telco").show(getFragmentManager(), "");
                    }
                }.getRequestObject(getContext(), Constants.UrlConstant.telco, "Name");
            }
        });

        rootView.findViewById(R.id.vend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedOption == null) {
                    Utils.showMessage(getContext(), "Telco Type has not been selected");
                } else {
                    String phoneNumber = ((AppCompatEditText) rootView.findViewById(R.id.phoneField)).getText().toString().trim();
                    String amount = ((AppCompatEditText) rootView.findViewById(R.id.amountField)).getText().toString().trim();
                    if (phoneNumber.length() >= 11) {
                        if (amount.length() != 0) {
                            int amountValue = Integer.parseInt(amount);
                            if (amountValue > 0) {
                                processDialog.showDialog(getContext(), "transaction in progress");
                                new AgentRequest().new AgentTrasaction() {
                                    @Override
                                    protected void onRequestComplete(boolean status, String message) {
                                        processDialog.dismiss();
                                        if (status) {
                                            Utils.loadPage(getFragmentManager(), new TransactionCompletePage(), false);
                                        } else {
                                            Utils.showMessage(getContext(), message);
                                        }
                                    }
                                }.vendAirtime(getContext(), selectedOption, phoneNumber, amountValue);
                            } else {
                                Utils.showMessage(getContext(), "invalid amount");
                            }
                        } else {
                            Utils.showMessage(getContext(), "amount field can not be empty");
                        }
                    } else {
                        Utils.showMessage(getContext(), "invalid phone number");
                    }
                }
            }
        });
        return rootView;
    }

    ProcessDialog processDialog = new ProcessDialog();
}
