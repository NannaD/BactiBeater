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

import java.util.HashMap;
import java.util.Map;

import Items.APIBehaviourItem;
import Items.APIUserItem;
import Items.BehaviourItem;
import Items.UserItem;

/*public class FirebaseAPISignInConnection extends AppCompatActivity{
    String LOG = "FIREBASE_API_BEHAVIOR_ITEM";

    RequestQueue mQueue;
    Context context;
    String urlAPISignIn = "https://localhost:44388/api/SignInModels";

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
    public void sendRequest(final String signInId, final VolleyResponseListener listener)
    {
        if (mQueue == null) {
            mQueue = Volley.newRequestQueue(context);
        }

        final String url = urlAPISignIn+signInId; //OPMÆRKSOM PÅ HVORDAN DET SKAL HENTES

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new GsonBuilder().create();
                APIUserItem userApiItem =  gson.fromJson(response.toString(), APIUserItem.class);

                UserItem userItem = new UserItem();
                //animalItem.setName(wordAPIItem.getWord());
                //animalItem.setPronounce(wordAPIItem.getPronunciation());
                //animalItem.setWordRating("1");
                //animalItem.setDescription(wordAPIItem.getDefinitions().get(0).getDefinition());
                //animalItem.setPictureURL(wordAPIItem.getDefinitions().get(0).getImageUrl());

                listener.onResponse(userItem); //HUSK AT VI SKAL LAVE INTERFACE TIL SERVICEN SOM I MIT ANIMALPROJEKT
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("WORDAPI", "JsonRequest did not work");
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

}*/
