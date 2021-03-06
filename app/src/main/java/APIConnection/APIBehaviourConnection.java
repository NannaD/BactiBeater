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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.BehaviourItem;
import Items.SanitizeItem;

public class APIBehaviourConnection extends AppCompatActivity {

    private String LOG = "FIREBASE_API_BEHAVIOR_ITEM";
    private RequestQueue mQueue;
    private String urlAPIBehaviour = "https://bactibeater.azurewebsites.net/api/BehaviourModels";
    private Context context;
    public String userName;
    public String password;

    //interface used in the service
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<BehaviourItem> response);
    }

    public APIBehaviourConnection(Context context) {
        this.context = context;
    }

    //Sending request and getting the behaviouritem
    public void getBehaviours(String _userName, String _password, final APIBehaviourConnection.VolleyResponseListener listener)
    {
        userName = _userName;
        password = _password;

        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlAPIBehaviour, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<BehaviourItem> behaviourItems = new ArrayList<>();

               for (int i = 0; i < responses.length(); i++){
                   try {
                       JSONObject behaviour = responses.getJSONObject(i);
                       String behaviourModelId = behaviour.getString("behaviourModelId");
                       String bactiBeaterId = behaviour.getString("bactiBeaterId");
                       String beaconId = behaviour.getString("beaconId");
                       String beaconInteractionTime = behaviour.getString("beaconInteractionTime"); //burde være datetime
                       String beaconName = behaviour.getString("beaconName");
                       boolean didSanitize = behaviour.getBoolean("didSanitize");

                       BehaviourItem behaviourItem = new BehaviourItem(behaviourModelId, bactiBeaterId, beaconId, beaconInteractionTime, beaconName, didSanitize);
                       behaviourItems.add(behaviourItem);

                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
               }
               listener.onResponse(behaviourItems);
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
