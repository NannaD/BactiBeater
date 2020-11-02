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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class APISignInConnection extends AppCompatActivity {

    private String urlAPISignIn = "https://bactibeater.azurewebsites.net/api/BehaviourModels/TestSignIn";
    private String LOG = "API_SIGNIN_CONNECTION";
    private RequestQueue mQueue;
    private Context context;
    public String userName;
    public String password;

    public APISignInConnection(Context context) {
        this.context = context;
    }

    public void isSignedIn(final Callback callback)
    {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, urlAPISignIn, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    if (Boolean.parseBoolean(response) == true)
                    {
                        callback.onSuccess(response);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
        mQueue.add(stringRequest);
    }
}
