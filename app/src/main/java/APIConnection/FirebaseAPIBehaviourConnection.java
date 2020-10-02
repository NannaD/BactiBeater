package APIConnection;

import android.content.Context;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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

import Items.BehaviourItem;

public class FirebaseAPIBehaviourConnection extends AppCompatActivity {

    private String LOG = "FIREBASE_API_BEHAVIOR_ITEM";
    private RequestQueue mQueue;
    private String urlAPIBehaviour = "https://bactibeater.azurewebsites.net/api/BehaviourModels";
    private Context context;

    //interface used in the service
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(BehaviourItem response);
    }

    public FirebaseAPIBehaviourConnection(Context context) {
        this.context = context;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    //Sending request and getting the behaviouritem
    public void sendRequest(final VolleyResponseListener listener)
    {
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
               //listener.onResponse();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("FirebaseAPI", "JsonRequest did not work");
            }
        })  {
            @Override //VED IKKE MED DETTE
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token ");
                return headers;
            }
        };

        Log.d(LOG, "Request sent");
        mQueue.add(jsonRequest);
    }

}
