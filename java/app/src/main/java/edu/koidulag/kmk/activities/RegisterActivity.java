package edu.koidulag.kmk.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import edu.koidulag.kmk.Queue;
import edu.koidulag.kmk.R;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.name_first) EditText fn;
    @BindView(R.id.name_last) EditText ln;
    @BindView(R.id.name_class) EditText cn;
    @BindView(R.id.register) Button r;

    String s1, s2, s4;

    private String url = "http://192.168.1.188/kmk/03.php"; //Ajutine ip

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        s1 = fn.getText().toString().trim();
        s2 = ln.getText().toString().trim();
        s4 = cn.getText().toString().trim();

        if (!validate()) {
            return;
        }

        JSONObject jr = new JSONObject();
        try {
            jr.put("uuid", MainActivity.deviceid.toString().trim());
            jr.put("f", s1);
            jr.put("l", s2);
            jr.put("c", s4);
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
                        Toast.makeText(getApplicationContext(), "Registreeritud!", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, HomepageActivity.class);
                        startActivity(i);
                    } else if (response.getInt("status") == 1) {
                        Toast.makeText(getApplicationContext(), "Teie seadme UUID on juba andmebaasis olemas!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Vajalikud andmed puuduvad!", Toast.LENGTH_SHORT).show();
                        System.out.println("Missing data");
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
        return;
    }

    private boolean validate() {
        if (s1.equals("")) {
            fn.setError("First name missing!");
            fn.requestFocus();
            return false;
        }
        if (s2.equals("")) {
            ln.setError("Last name missing!");
            ln.requestFocus();
            return false;
        }
        if (s4.equals("")) {
            cn.setError("Class missing!");
            cn.requestFocus();
            return false;
        }
        return true;
    }

}
