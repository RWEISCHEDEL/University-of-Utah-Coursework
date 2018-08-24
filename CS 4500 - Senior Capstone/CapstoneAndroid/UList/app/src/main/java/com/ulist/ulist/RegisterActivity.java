package com.ulist.ulist;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

/**
 * This class creates the RegisterActivity, which allows the user  to register and create their own account.
 */
public class RegisterActivity extends AppCompatActivity {
    Button b_register;
    EditText e_username, e_pass, e_confirmpass, e_email, e_realname, e_uniemail;

    /**
     * This function creates the activity along with all its widgets and listeners.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        b_register = (Button)findViewById(R.id.bRegister);

        e_username = (EditText)findViewById((R.id.txtUsername));
        e_pass = (EditText)findViewById((R.id.txtPass));
        e_confirmpass = (EditText)findViewById((R.id.txtConfirmPass));
        e_email = (EditText)findViewById((R.id.txtEmail));
        e_realname = (EditText)findViewById(R.id.txtRealName);
        e_uniemail = (EditText)findViewById(R.id.txtUniversityEmail);

        b_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName = e_username.getText().toString();
                String uPass = e_pass.getText().toString();
                String uConfirmPass = e_confirmpass.getText().toString();
                String uEmail = e_email.getText().toString();

                if(uPass.equals(uConfirmPass)){
                    // Submit info to database, and check that username has not been taken
                    RestRequest rr = new RestRequest();
                    StringBuffer sb;
                    JSONObject register = new JSONObject();

                    try {
                        sb = rr.execute("users/signup", "POST",
                                "username", e_username.getText().toString(),
                                "password", e_pass.getText().toString(),
                                "fullname", e_realname.getText().toString(),
                                "email", e_email.getText().toString(),
                                "umail", e_uniemail.getText().toString()).get();
                        if (sb == null || sb.toString().compareTo("ERROR") == 0) {
                            Snackbar.make(findViewById(android.R.id.content), "Connection Error", Snackbar.LENGTH_SHORT).show();
                            return;
                        }
                        register = new JSONObject(sb.toString());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        if (register.getString("Message").compareTo("successful") == 0) {
                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            Snackbar.make(findViewById(android.R.id.content), "Registration Failed: Username taken.", Snackbar.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else{
                    Snackbar.make(findViewById(android.R.id.content), "Registration Failed: Passwords don't match.", Snackbar.LENGTH_SHORT).show();
                }
            }
        });

    }
}
