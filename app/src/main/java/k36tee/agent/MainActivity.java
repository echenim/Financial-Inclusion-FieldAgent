package k36tee.agent;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k36tee.agent.Adapters.BillsAdapter;
import k36tee.agent.Pages.AgentActivities;
import k36tee.agent.Pages.AirtimeVending;
import k36tee.agent.Pages.BankOperation;
import k36tee.agent.Pages.BillPayment;
import k36tee.agent.Pages.Funds;
import k36tee.agent.Pages.RevenueCollection;
import k36tee.agent.Pref.AppPref;
import k36tee.agent.Request.AgentRequest;

/**
 * Created by myron echenim  on 8/8/16.
 */

public class MainActivity extends AgentLocationActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        findViewById(R.id.bank_operations).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.loadPage(getSupportFragmentManager(), new BankOperation(), true);
            }
        });
        findViewById(R.id.vending).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.loadPage(getSupportFragmentManager(), new AirtimeVending(), true);
            }
        });
        findViewById(R.id.agent_activity).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.loadPage(getSupportFragmentManager(), new AgentActivities(), true);
            }
        });

        findViewById(R.id.log_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this).setMessage("Do you want to sign out of this account ?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getSharedPreferences(AppPref.class.getName(), MODE_PRIVATE).edit().clear().commit();
                        startActivity(new Intent(MainActivity.this, Login.class));
                        finish();
                    }
                }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialog.show();

            }
        });
        RecyclerView bills = (RecyclerView) findViewById(R.id.bills);
        bills.setHasFixedSize(true);
        bills.setLayoutManager(new GridLayoutManager(this, 3));
        bills.setAdapter(new BillsAdapter(this) {
            @Override
            public void onItemSelected(View view, int position) {
                if (position == Constants.BILL_PAYMENT) {
                    Utils.loadPage(getSupportFragmentManager(), new BillPayment(), true);
                } else if (position == Constants.FUNDS) {
                    Utils.loadPage(getSupportFragmentManager(), new Funds(), true);
                } else {
                    Utils.loadPage(getSupportFragmentManager(), new RevenueCollection(), true);
                }
            }
        });

        agentName = (AppCompatTextView) findViewById(R.id.agent_name);
        agentBalance = (AppCompatTextView) findViewById(R.id.agent_balance_value);
        agentTopBalance = (AppCompatTextView) findViewById(R.id.agent_balance);

        agentName.setText(new AppPref().getStringValue(this, AppPref.AGENT_NAME));
       // agentBalance.setText(String.valueOf(new AppPref().getFloatValue(this, AppPref.CURRENT_BALANCE)));
        agentBalance.setText("6499.00");
       // agentTopBalance.setText(String.valueOf(new AppPref().getFloatValue(this, AppPref.CURRENT_BALANCE)));
        agentTopBalance.setText("6499.00");

        findViewById(R.id.agent_balance_request).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onResume();
            }
        });
    }

    private void makeBalanceRequest() {
        new AgentRequest().new BalanceRequest() {
            @Override
            public void onBalanceRetrieved(boolean status, float currentBalance, String agentNameValue) {
                if (status) {
                    new AppPref().setFloatValue(MainActivity.this, AppPref.CURRENT_BALANCE, currentBalance);
                    new AppPref().setStringValue(MainActivity.this, AppPref.AGENT_NAME, agentNameValue);
                    agentName.setText(agentNameValue);
                    agentBalance.setText(String.valueOf(currentBalance));
                    agentTopBalance.setText(String.valueOf(currentBalance));
                } else {
                    Utils.showMessage(MainActivity.this, "could not retrieve balance");
                }
                if (processDialog.getProgressDialog().isShowing()) {
                    processDialog.dismiss();
                }
            }
        }.getAgentBalance(this);

    }

    ProcessDialog processDialog = new ProcessDialog();

    AppCompatTextView agentName;
    AppCompatTextView agentBalance;
    static AppCompatTextView agentTopBalance;

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        makeBalanceRequest();
    }

    @Override
    public void onResume() {
        super.onResume();
        processDialog.showDialog(this, "Updating Balance");
        makeBalanceRequest();
    }


    public static boolean isComplete;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (isComplete) {
            isComplete = false;
            onBackPressed();

        }
    }
}
