package APIConnection;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Items.AverageBehaviourItem;
import Items.SanitizeItem;
import Service.BackgroundService;

public class FirebaseAPIAverageBehaviorConnection extends AppCompatActivity {
    private String urlSanitizeItem = "https://bactibeater.azurewebsites.net/api/AverageBehaviourModel";
    private String LOG = "FIREBASE_API_AVERAGEBEHAVIOR_ITEM";
    private Context context;
    private RequestQueue mQueue;
    public String userName;
    public String password;

    public FirebaseAPIAverageBehaviorConnection(Context context) {
        this.context = context;
    }

    //interface used in the service
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<AverageBehaviourItem> response);
    }

    public void getSevenDaysOverview(String _password, String _username, final FirebaseAPIAverageBehaviorConnection.VolleyResponseListener listener){
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        password = _password;
        userName = _username;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlSanitizeItem, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<AverageBehaviourItem> averageBehaviourItems = new ArrayList<>();

                for (int i = 0; i < responses.length(); i++){
                    try {
                        JSONObject averageBehaviourItemJSON = responses.getJSONObject(i);
                        String date = averageBehaviourItemJSON.getString("date");
                        int locationChanges = averageBehaviourItemJSON.getInt("locationChanges");
                        int sanitations = averageBehaviourItemJSON.getInt("sanitations");

                        AverageBehaviourItem averageBehaviourItem = new AverageBehaviourItem(locationChanges, sanitations, date);
                        averageBehaviourItems.add(averageBehaviourItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(averageBehaviourItems);
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
