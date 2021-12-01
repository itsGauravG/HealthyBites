package com.example.healthybites.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthybites.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;

// SIGNUP

public class RegisterActivity extends AppCompatActivity {

    private EditText email ,password ,confpassword ;
    Button signup , back;
    FirebaseAuth auth;
    public static final String url = "https://healthybitesapp.000webhostapp.com/user_reg.php";
    public static String txt_email , txt_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        email = (EditText)findViewById(R.id.reg_email);
        password = (EditText)findViewById(R.id.reg_password);
        confpassword = (EditText)findViewById(R.id.reg_conf_password);
        signup=(Button)findViewById(R.id.reg_signup);
        back = (Button)findViewById(R.id.reg_back);
        auth = FirebaseAuth.getInstance();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText("");
                password.setText("");
                confpassword.setText("");
                Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              txt_email = email.getText().toString();
              txt_pass = password.getText().toString();
              String txt_confpass = confpassword.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass) || TextUtils.isEmpty(txt_confpass)){
                    Toast.makeText(RegisterActivity.this,"One of the fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(!(txt_pass.equals(txt_confpass))){
                    Toast.makeText(RegisterActivity.this,"Password Does not Match",Toast.LENGTH_SHORT).show();

                }

                else if(txt_pass.equals(txt_confpass) && txt_pass.length()<6){
                    Toast.makeText(RegisterActivity.this,"Please choose a strong password",Toast.LENGTH_SHORT).show();
                }

                else {
                    auth.createUserWithEmailAndPassword(txt_email,txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(RegisterActivity.this,"Signing in.. ",Toast.LENGTH_SHORT).show();
                                insertData();
                            }
                            else {
                               Toast.makeText(RegisterActivity.this,"User Already Exists",Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
             }
        });


    }
    public void insertData(){
        //insert into sql
        StringRequest request = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("true")){
                    Toast.makeText(RegisterActivity.this,"Signed in Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(RegisterActivity.this, UserDetailsFormActivity.class);
                    startActivity(intent);
                    finish();

                }

                else {
                    Toast.makeText(RegisterActivity.this,"Something went wrong,Please try again",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RegisterActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("email",txt_email);
                param.put("password",txt_pass);
                param.put("isKitchen","0");


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(request);


    }
}