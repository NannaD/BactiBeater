package APIConnection;

import android.content.Context;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.APIBehaviourItem;
import Items.APIUserItem;
import Items.BehaviourItem;
import Items.UserItem;

public class FirebaseAPISignInConnection extends AppCompatActivity{
    String LOG = "FIREBASE_API_BEHAVIOR_ITEM";

    RequestQueue mQueue;
    Context context;
    String urlAPISignIn = "https://bactibeater.azurewebsites.net/api/BehaviourModels/TestSignIn";
    public String userName;
    public String password;

    //interface used in the service
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(UserItem response);
    }

    public FirebaseAPISignInConnection(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Sending request and getting the behaviouritem
    public void getSignIns(final FirebaseAPIBehaviourConnection.VolleyResponseListener listener)
    {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlAPISignIn, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<UserItem> userItems = new ArrayList<>();

                for (int i = 0; i < responses.length(); i++){
                    try {
                        JSONObject user = responses.getJSONObject(i);
                        String signInModelId = user.getString("signInModelId");
                        String username = user.getString("username");
                        String password = user.getString("password");
                        boolean canAddUser = user.getBoolean("canAddUser");

                        UserItem userItem = new UserItem(signInModelId, username, password, canAddUser);
                        userItems.add(userItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                //listener.onResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FirebaseAPI", "JsonRequest did not work. " + error.getMessage());
            }
        })  {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Basic "+ Base64.encodeToString((userName + ":" + password).getBytes(), Base64.DEFAULT));
                return headers;
            }
        };

        Log.d(LOG, "Request sent");
        mQueue.add(jsonRequest);
    }

}
