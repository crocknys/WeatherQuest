package fr.wcs.testapi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private final static String API_KEY = "c36e4606546e5f31dda1111da0a6f537";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView view = findViewById(R.id.view);
        

        // Crée une file d'attente pour les requêtes vers l'API
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        // URL de la requête vers l'API
        String url = "https://api.openweathermap.org/data/2.5/forecast?q=Toulouse&appid=" + API_KEY;

        // Création de la requête vers l'API, ajout des écouteurs pour les réponses et erreurs possibles
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {


                            JSONArray list = response.getJSONArray("list");

                            for (int c = 0; c < 40; c = c + 8) {
                                JSONObject listinfo = list.getJSONObject(c);
                                JSONArray tableWeather = listinfo.getJSONArray("weather");

                                for (int i = 0; i<tableWeather.length(); i++){
                                    JSONObject elemtMeteo = tableWeather.getJSONObject(i);
                                    String meteo = elemtMeteo.getString("description");
                                    Toast.makeText(MainActivity.this, meteo, Toast.LENGTH_SHORT).show();
                                }
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Afficher l'erreur
                        Log.d("VOLLEY_ERROR", "onErrorResponse: " + error.getMessage());
                    }
                }
        );

        // On ajoute la requête à la file d'attente
        requestQueue.add(jsonObjectRequest);
    }

}
