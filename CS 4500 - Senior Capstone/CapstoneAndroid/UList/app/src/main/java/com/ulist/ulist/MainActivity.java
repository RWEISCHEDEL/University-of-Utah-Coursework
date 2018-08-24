package com.ulist.ulist;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.support.design.widget.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.concurrent.ExecutionException;

import static android.widget.Toast.LENGTH_SHORT;

/**
 * This class creates the MainActivity, which is the maing login page for the project.
 */
public class MainActivity extends AppCompatActivity {
    Button b_login, b_guest, b_register;
    EditText e_uName, e_pass;

    /**
     * Create the MainActivity with all the button listeners and text edit boxes.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_login = (Button)findViewById(R.id.bLogin);
        b_guest = (Button)findViewById(R.id.bGuest);
        b_register = (Button)findViewById(R.id.bRegister);

        e_uName = (EditText)findViewById(R.id.txtUserName);
        e_pass = (EditText)findViewById(R.id.txtPassword);

        b_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RestRequest rr = new RestRequest();
                StringBuffer sb;
                JSONObject login = new JSONObject();

                try {
                    sb = rr.execute("users/login", "POST", "username", e_uName.getText().toString(), "password", e_pass.getText().toString()).get();
                    if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                        Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                        return;
                    }
                    login = new JSONObject(sb.toString());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    if (login.getString("message").compareTo("successful") == 0) {
                        Intent intent = new Intent();

                        UserSession.getInstance().user = login.getString("User");

                        intent.setClass(MainActivity.this, SearchActivity.class);

                        startActivity(intent);
                    }
                    else {
                        Snackbar.make(findViewById(android.R.id.content), "Login Failed: Invalid Username/Password", Snackbar.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        b_guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                Bundle b = new Bundle();
                b.putSerializable("username", (Serializable) "guest");
                intent.putExtras(b);

                intent.setClass(MainActivity.this, SearchActivity.class);

                startActivity(intent);
            }
        });

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }
}
