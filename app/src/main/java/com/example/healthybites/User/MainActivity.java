package com.example.healthybites.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthybites.Partner.FoodPartnerFormActivity;
import com.example.healthybites.ForgotPasswordActivity;
import com.example.healthybites.Partner.PartnerDashboardActivity;
import com.example.healthybites.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;
import java.util.Map;
// LOGIN PAGE

public class MainActivity extends AppCompatActivity {

   EditText email , password ;
   Button login , signup ;
   FirebaseAuth auth ;
   TextView txt,txt_forgot;
   public static String txt_email;
   public static String check_url = "https://healthybitesapp.000webhostapp.com/check_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);

        email = (EditText)findViewById(R.id.login_email);
        password = (EditText)findViewById(R.id.login_password);
        signup = (Button)findViewById(R.id.signup);
        login = (Button)findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();
        txt = (TextView)findViewById(R.id.txt_partner);
        txt_forgot= (TextView)findViewById(R.id.txt_forgotpass);

        txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FoodPartnerFormActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txt_forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ForgotPasswordActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               txt_email = email.getText().toString();
                String txt_pass = password.getText().toString();

                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass)){
                    Toast.makeText(MainActivity.this,"One of the fields are empty",Toast.LENGTH_SHORT).show();

                }
                else {
                    auth.signInWithEmailAndPassword(txt_email,txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                check();
                                Toast.makeText(MainActivity.this,"Logging in..",Toast.LENGTH_SHORT).show();

                            }
                            else {
                                Toast.makeText(MainActivity.this,"Email or password is incorrect",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }



            }
        });


    }

    private void check() {
        StringRequest request = new StringRequest(Request.Method.POST,check_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("0")){
                    Toast.makeText(MainActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, UserDashboardActivity.class);
                    startActivity(intent);
                    finish();

                }

                else if(response.equals("1")){
                    Toast.makeText(MainActivity.this,"Logged in Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, PartnerDashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Toast.makeText(MainActivity.this,"Something went wrong , please try again",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("email",txt_email);


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(request);


    }
}