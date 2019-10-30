package edu.koidulag.kmk.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

import edu.koidulag.kmk.BuildConfig;
import edu.koidulag.kmk.Queue;
import edu.koidulag.kmk.R;

public class MainActivity extends AppCompatActivity {

    private String url = "http://192.168.203.121/kmk/04.php";
    public static UUID deviceid;

    final String prefKey = "edu.koidulag.kmk.prefFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);

        checkFirstRun();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        JSONObject jr = new JSONObject();
        try {
            jr.put("uuid", deviceid.toString().trim());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jor = new JsonObjectRequest(
                Request.Method.POST, url, jr,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getInt("status") == 0) {
                                Intent i = new Intent(MainActivity.this, HomepageActivity.class);
                                startActivity(i);
                            } else if (response.getInt("status") == 1) {
                                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                                startActivity(i);
                            } else {
                                //
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                System.out.println(error.getMessage());
                error.printStackTrace();
            }
        });
        Queue.i(this).add(jor);
    }

    private void checkFirstRun() {
        Context context = this;
        SharedPreferences sp = context.getSharedPreferences(prefKey, Context.MODE_PRIVATE);

        int currentVersion = BuildConfig.VERSION_CODE;
        int savedVersion = sp.getInt("versionCode", -1);
        if (currentVersion == savedVersion) {
            deviceid = UUID.fromString(sp.getString("devideid", ""));
            return;
        } else if (savedVersion == -1) {
            UUID uuid = UUID.randomUUID();
            deviceid = uuid;
            sp.edit().putString("devideid", uuid.toString()).apply();
        }
        sp.edit().putInt("versionCode", currentVersion).apply();
    }

}
