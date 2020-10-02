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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.APIBehaviourItem;
import Items.BehaviourItem;

public class FirebaseAPIBehaviourConnection extends AppCompatActivity {

    private String LOG = "FIREBASE_API_BEHAVIOR_ITEM";
    private RequestQueue mQueue;
    private String urlAPIBehaviour = "http://bactibeater.azurewebsites.net/api/BehaviourModels";
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

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, urlAPIBehaviour, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject responses) {
                Gson gson = new GsonBuilder().create();
                List<APIBehaviourItem> wordAPIItems = (List<APIBehaviourItem>) gson.fromJson(responses.toString(), APIBehaviourItem.class);
                List<BehaviourItem> behaviourItems = new ArrayList<>();

               for (int i = 0; i < wordAPIItems.size(); i++){

                    long behavoiurModelId = wordAPIItems.get(i).getBehaviourModelId();
                    int bactiBeaterId = wordAPIItems.get(i).getBactiBeaterId();
                    int beaconId = wordAPIItems.get(i).getBeaconId();
                    Date beaconInteractionTime = wordAPIItems.get(i).getBeaconInteractionTime();
                    String beaconName = wordAPIItems.get(i).getBeaconName();
                    boolean didSanitizeBool = wordAPIItems.get(i).isDidSanitizeBool();

                    BehaviourItem behaviourItem = new BehaviourItem(behavoiurModelId, bactiBeaterId, beaconId, beaconInteractionTime, beaconName, didSanitizeBool);
                    behaviourItems.add(behaviourItem);
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
