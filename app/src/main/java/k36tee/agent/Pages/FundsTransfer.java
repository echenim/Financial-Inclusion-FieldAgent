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
public class FundsTransfer extends FragmentPages {


    AppCompatTextView bankSelector;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.funds_transfer, container, false);


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
                if (selectedOption == null) {
                    Utils.showMessage(getContext(), "Select Recipient Bank");
                } else {
                    String accountNumber = ((AppCompatEditText) rootView.findViewById(R.id.acctField)).getText().toString().trim();
                    String remark = ((AppCompatEditText) rootView.findViewById(R.id.remark)).getText().toString().trim();
                    String amount = ((AppCompatEditText) rootView.findViewById(R.id.amountField)).getText().toString().trim();
                    if (accountNumber.length() == 10) {
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
                                }.makeFundTransfer(getContext(), selectedOption, accountNumber, remark, amountValue);
                            } else {
                                Utils.showMessage(getContext(), "invalid amount");
                            }
                        } else {
                            Utils.showMessage(getContext(), "amount field can not be empty");
                        }
                    } else {
                        Utils.showMessage(getContext(), "invalid account number");
                    }
                }

            }
        });

        return rootView;
    }

    ProcessDialog processDialog = new ProcessDialog();
}
