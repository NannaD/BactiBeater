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
import Items.SummedSanitizersItem;

public class APISummedSanitizerConnection extends AppCompatActivity {

    private static final String urlSummedSanitizer = "https://bactibeater.azurewebsites.net/api/SanitizeItemModel";
    private static final String LOG = "API_SUMMEDSANITIZER";
    private Context context;
    private RequestQueue mQueue;
    private String username;
    private String password;

    public APISummedSanitizerConnection(Context context) {
        this.context = context;
    }

    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<SummedSanitizersItem> response);
    }

    public void getSummedSanitizerData(String _username, String _password, final APISummedSanitizerConnection.VolleyResponseListener listener){
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        password = _password;
        username = _username;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlSummedSanitizer, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<SummedSanitizersItem> summedSanitizersItems = new ArrayList<>();

                for (int i = 0; i < responses.length(); i++){
                    try {
                        JSONObject summedSanitizerItemJSON = responses.getJSONObject(i);
                        String date = summedSanitizerItemJSON.getString("date");
                        int sanitationLocationChangeTrue = summedSanitizerItemJSON.getInt("sanitationLocationChangeTrue");
                        int sanitationLocationChangeFalse = summedSanitizerItemJSON.getInt("sanitationLocationChangeFalse");

                        SummedSanitizersItem summedSanitizersItem = new SummedSanitizersItem(sanitationLocationChangeTrue, sanitationLocationChangeFalse, date);
                        summedSanitizersItems.add(summedSanitizersItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(summedSanitizersItems);
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
