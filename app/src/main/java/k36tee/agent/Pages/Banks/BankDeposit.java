package k36tee.agent.Pages.Banks;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
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
 * Created by myron echenim  on 8/25/16.
 */
public class BankDeposit extends FragmentPages {


    AppCompatTextView bankSelector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.bank_deposit, container, false);

        bankSelector = (AppCompatTextView) rootView.findViewById(R.id.bank_selector);
        bankSelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processDialog.showDialog(getContext(), "Fetching Banks");
                new AgentRequest().new RequestObjects() {
                    @Override
                    protected void onRequestComplete(ArrayList<RequestObject> requestObjects) {
                        processDialog.dismiss();
                        new RequestObjSelectorDialog() {
                            @Override
                            public void onListItemSelected(RequestObject requestObject) {
                                selectedOption = requestObject;
                                bankSelector.setText(requestObject.getName());
                            }
                        }.fragmentInstanceDialogFragment(requestObjects, "Select Bank").show(getFragmentManager(), "");
                    }
                }.getRequestObject(getContext(), Constants.UrlConstant.banks, "BankName");
            }
        });


        rootView.findViewById(R.id.makeTransfer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acctNumber = ((AppCompatEditText) rootView.findViewById(R.id.accountNumber)).getText().toString().trim();
                String amount = ((AppCompatEditText) rootView.findViewById(R.id.amountField)).getText().toString().trim();
                String agentPin = ((AppCompatEditText) rootView.findViewById(R.id.agentPin)).getText().toString().trim();
                if (acctNumber.length() == 10) {
                    if (amount.isEmpty()) {
                        Utils.showMessage(getContext(), "invalid amount");
                    } else {
                        if (Integer.parseInt(amount) > 0) {
                            if (agentPin.isEmpty()) {
                                Utils.showMessage(getContext(), "invalid agent pin");
                            } else {
                                //
                                processDialog.showDialog(getContext(), "making deposit");
                                new AgentRequest().new AgentTrasaction() {
                                    @Override
                                    protected void onRequestComplete(boolean status, String message) {
                                        processDialog.dismiss();
                                        Log.e("update", message);
                                    }
                                }.bankDeposit(getContext(), selectedOption, acctNumber, amount, agentPin);
                            }
                        } else {
                            Utils.showMessage(getContext(), "invalid amount");
                        }
                    }
                } else {
                    Utils.showMessage(getContext(), "invalid account number");
                }
            }
        });
        return rootView;
    }


    ProcessDialog processDialog = new ProcessDialog();
}
