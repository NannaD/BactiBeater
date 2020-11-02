package APIConnection;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

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

import Items.SanitizeItem;

public class APISanitizeItemConnection {

    private String urlSanitizeItem = "https://bactibeater.azurewebsites.net/api/SanitizeItemModel";
    private String LOG = "FIREBASE_API_SANITIZE_ITEM";
    private Context context;
    private RequestQueue mQueue;
    public String userName;
    public String password;

    public APISanitizeItemConnection(Context context) {
        this.context = context;
    }

    //interface used in the service
    public interface VolleyResponseListener{
        void onError(String message);
        void onResponse(List<SanitizeItem> response);
    }

    public void getLocationAndDateSpecificData(String _password, String _username, final APISanitizeItemConnection.VolleyResponseListener listener){
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        password = _password;
        userName = _username;

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, urlSanitizeItem, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray responses) {
                List<SanitizeItem> sanitizeItems = new ArrayList<>();

                for (int i = 0; i < responses.length(); i++){
                    try {
                        JSONObject sanitizeItemJSON = responses.getJSONObject(i);
                        String location = sanitizeItemJSON.getString("location");
                        String date = sanitizeItemJSON.getString("date");
                        int visitorCount = sanitizeItemJSON.getInt("visitorCount");
                        int sanitizeCount = sanitizeItemJSON.getInt("sanitizeCount");

                        SanitizeItem sanitizeItem = new SanitizeItem(location, date, visitorCount, sanitizeCount);
                        sanitizeItems.add(sanitizeItem);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                listener.onResponse(sanitizeItems);
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

        Log.d(LOG, "SanitizeItem request sent");
        mQueue.add(jsonRequest);
    }

}
