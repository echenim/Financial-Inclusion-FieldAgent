package k36tee.agent.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import k36tee.agent.AgentApplication;
import k36tee.agent.Constants;
import k36tee.agent.Model.AgentModel;
import k36tee.agent.Model.RequestObject;
import k36tee.agent.Pref.AppPref;

/**
 * Created by myron echenim  on 8/14/16.
 */
public class AgentRequest {


    abstract public class BalanceRequest {


        public void getAgentBalance(Context context) {

            String link = Constants.UrlConstant.balance + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/";

            String tag_json_obj = "balance";

            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    link, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray responseArray) {

                            JSONObject response = null;
                            try {
                                response = responseArray.getJSONObject(0);
                                onBalanceRetrieved(true, (float)response.optDouble("CurrentBalance"), response.optString("Name"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("current balanace", e.getMessage());
                                onBalanceRetrieved(false, -1, "");

                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    onBalanceRetrieved(false, -1, error.getMessage());
                }
            });

           AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        }

        abstract public void onBalanceRetrieved(boolean status, float currentBalance, String agentName);
    }

    abstract public class AgentTransactions {


        public void getAllTransactionDetails(Context context) {
            String link = Constants.UrlConstant.agentActivities + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/";

            String tag_json_obj = "agentTransation";


            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    link, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray responseArray) {
                            ArrayList<AgentModel> agentModels = new ArrayList<>();
                            for (int index = 0; index < responseArray.length(); index++) {
                                try {
                                    JSONObject responseObject = responseArray.getJSONObject(index);
                                    AgentModel agentModel = new AgentModel();
                                    agentModel.setAmount(responseObject.optLong("Amount"));
                                    agentModel.setId(responseObject.optString("Id"));
                                    agentModel.setName(responseObject.optString("Name"));
                                    agentModel.setEmail(responseObject.optString("Email"));
                                    agentModel.setTrasaction(responseObject.optString("Transactions"));
                                    agentModel.setDescription(responseObject.optString("Descriptions"));
                                    agentModel.setTrasactionTime(responseObject.optString("CreatedOn"));
                                    agentModels.add(agentModel);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            onRequestComplete(agentModels);

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {

                    onRequestComplete(new ArrayList<AgentModel>());
                }
            });

// Adding request to request queue
            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        abstract protected void onRequestComplete(ArrayList<AgentModel> agentModels);
    }

    abstract public class RequestObjects {


        public void getRequestObject(Context context, String link, final String key) {
            String tag_json_obj = "reqObj";


            JsonArrayRequest jsonObjReq = new JsonArrayRequest(Request.Method.GET,
                    link, null,
                    new Response.Listener<JSONArray>() {

                        @Override
                        public void onResponse(JSONArray responseArray) {
                            ArrayList<RequestObject> requestObjects = new ArrayList<>();
                            for (int index = 0; index < responseArray.length(); index++) {
                                try {
                                    JSONObject responseObject = responseArray.getJSONObject(index);
                                    RequestObject requestObject = new RequestObject();
                                    requestObject.setId(responseObject.optString("Id"));
                                    requestObject.setName(responseObject.optString(key));
                                    requestObjects.add(requestObject);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            onRequestComplete(requestObjects);

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(new ArrayList<RequestObject>());
                }
            });

// Adding request to request queue
            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        abstract protected void onRequestComplete(ArrayList<RequestObject> requestObjects);
    }


    abstract public class AgentTrasaction {


        public void makeFundTransfer(Context context, RequestObject requestObject, String acctNo, String narration, int amount) {
            String tag_json_obj = "fundTransfer";

            String link = Constants.UrlConstant.fund_transfer + requestObject.getId() + "/" + acctNo + "/" + narration + "/" + amount + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

// Adding request to request queue
            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        public void vendAirtime(Context context, RequestObject requestObject, String phone, int amount) {
            String tag_json_obj = "vendAirtime";

            String link = Constants.UrlConstant.airtime_vending + requestObject.getId() + "/" + phone + "/" + amount + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

// Adding request to request queue
            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        public void payBill(Context context, RequestObject requestObject, String customerID, int amount) {
            String tag_json_obj = "payBill";

            String link = Constants.UrlConstant.bill_payment + requestObject.getId() + "/" + customerID + "/" + amount + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

// Adding request to request queue
            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        public void collectRevenue(Context context, RequestObject requestObject, String customerID, int amount) {
            String tag_json_obj = "payBill";

            String link = Constants.UrlConstant.revenue_collection + requestObject.getId() + "/" + customerID + "/" + amount + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        public void bankDeposit(Context context, RequestObject requestObject, String accountNumber, String amount, String agentPin) {
            String tag_json_obj = "bankDeposit";

            String link = Constants.UrlConstant.bank_deposit + requestObject.getId() + "/" + accountNumber + "/" + amount + "/" + agentPin + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }

        //        http://192.52.243.153/platform/Banks/withdrawal/{bankId}/{accountnumber}/{customerpin}/{otp}/{amount}/{agentpin}/{username}/{latitude}/{longitude}/
        public void bankWithdrawal(Context context, RequestObject requestObject, String accountNumber, String customerPin, String amount, String otp, String agentPin) {
            String tag_json_obj = "bankWithdrawal";

            String link = Constants.UrlConstant.bank_withdrawal + requestObject.getId() + "/" + accountNumber + "/" + customerPin + "/" + otp + "/" + amount + "/" + agentPin + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }


        //        http://192.52.243.153/platform/Banks/accountopening/{firstname}/{lastname}/{othername}/{gender}/{phonenumber}/{address}/{dateofbirth}/{placeofbirth}/{nextofkinname}/{nextofkinphone}/{nextofkinaddress}/{product}/{cardserialnumber}/{username}/{latitude}/{longitude}/
        public void acctOpening(Context context, String details) {
            String tag_json_obj = "acctOpening";

            String link = Constants.UrlConstant.acct_opening + details + "/" + new AppPref().getStringValue(context, AppPref.AGENT_EMAIL) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LAT) + "/" + new AppPref().getFloatValue(context, AppPref.AGENT_LNG) + "/";

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    link, null,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            onRequestComplete(response.optBoolean("Success"), response.optString("Message"));

                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    onRequestComplete(false, error.getMessage());
                }
            });

            AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
        }


        abstract protected void onRequestComplete(boolean status, String message);
    }
}
