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

import Items.SummedBehavioursItem;
import Items.SummedSanitizersItem;

public class APISummedBehaviourConnection extends AppCompatActivity {
    private static final String urlSummedBehaviours = "https://bactibeater.azurewebsites.net/api/SummedBehaviourModels";
    private static final String LOG = "API_SUMMEDBEHAVIOURS";
    private Context context;
    private RequestQueue mQueue;
    private String username;
    private String password;

    public APISummedBehaviourConnection(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<SummedBehavioursItem> response);
    }

    public void getSummedBehaviours(String _username, String _password, final APISummedBehaviourConnection.VolleyResponseListener listener){
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        password = _password;
        username = _username;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlSummedBehaviours, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<SummedBehavioursItem> summedBehavioursItems = new ArrayList<>();

                for (int i = 0; i < responses.length(); i++){
                    try {
                        JSONObject summedBehavioursItemJSON = responses.getJSONObject(i);
                        String date = summedBehavioursItemJSON.getString("date");
                        int locationChangesDidSanitize = summedBehavioursItemJSON.getInt("locationChangesDidSantizie");
                        int locationChangesDidNotSanitize = summedBehavioursItemJSON.getInt("locationChangesDidNotSantizie");

                        SummedBehavioursItem summedBehavioursItem = new SummedBehavioursItem(locationChangesDidSanitize, locationChangesDidNotSanitize, date);
                        summedBehavioursItems.add(summedBehavioursItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(summedBehavioursItems);
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
                headers.put("Authorization", "Basic "+ Base64.encodeToString((username + ":" + password).getBytes(), Base64.DEFAULT));
                return headers;
            }
        };

        Log.d(LOG, "Request sent");
        mQueue.add(jsonRequest);
    }
}
