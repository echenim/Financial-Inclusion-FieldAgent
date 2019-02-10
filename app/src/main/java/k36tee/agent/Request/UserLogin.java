package k36tee.agent.Request;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import k36tee.agent.AgentApplication;
import k36tee.agent.Constants;

/**
 * Created by myron echenim  on 8/14/16.
 */
abstract public class UserLogin {


    public void loginRequest(String password, String username) {
        String tag_json_obj = "login";

        String url = Constants.UrlConstant.login + username + "/" + password + "/";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        onLoginRequestComplete(response.optBoolean("Success"), response.optString("Message"));
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                onLoginRequestComplete(false, error.getMessage());
            }
        });

// Adding request to request queue
        AgentApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    abstract protected void onLoginRequestComplete(boolean status, String message);
}
