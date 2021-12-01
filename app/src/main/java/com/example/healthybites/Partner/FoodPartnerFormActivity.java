package com.example.healthybites.Partner;

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

public class FoodPartnerFormActivity extends AppCompatActivity {

    private EditText email , password , confpass ;
    private Button submit;
    private FirebaseAuth auth;
    public static String k_url = "https://healthybitesapp.000webhostapp.com/kitchen_reg.php";
    public String txt_email ,txt_pass , txt_confpass ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_partner_form);

        email = (EditText)findViewById(R.id.partner_email);
        password=(EditText) findViewById(R.id.partner_pass);
        confpass=(EditText) findViewById(R.id.partner_conf_pass);
        submit = (Button) findViewById(R.id.partner_reg_submit);
        auth = FirebaseAuth.getInstance();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_email = email.getText().toString().trim();
                txt_pass = password.getText().toString().trim();
                txt_confpass = confpass.getText().toString().trim();
                if(TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_pass) || TextUtils.isEmpty(txt_confpass)){
                    Toast.makeText(FoodPartnerFormActivity.this,"One of the fields are empty",Toast.LENGTH_SHORT).show();
                }
                else if(!(txt_pass.equals(txt_confpass))){
                    Toast.makeText(FoodPartnerFormActivity.this,"Password Does not Match",Toast.LENGTH_SHORT).show();

                }

                else if(txt_pass.equals(txt_confpass) && txt_pass.length()<6){
                    Toast.makeText(FoodPartnerFormActivity.this,"Please choose a strong password",Toast.LENGTH_SHORT).show();
                }

                else {

                    auth.createUserWithEmailAndPassword(txt_email,txt_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(FoodPartnerFormActivity.this,"Signing in..",Toast.LENGTH_SHORT).show();
                            kitchen();

                        }
                        else {
                            Toast.makeText(FoodPartnerFormActivity.this,"User already exists",Toast.LENGTH_SHORT).show();
                        }


                        }
                    });

                }


            }
        });





    }

    private void kitchen() {
        StringRequest request = new StringRequest(Request.Method.POST,k_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("true")){
                    Toast.makeText(FoodPartnerFormActivity.this,"Signed in successfully",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(FoodPartnerFormActivity.this, StoreDetailsActivity.class);
                    startActivity(intent);

                }

                else {
                    Toast.makeText(FoodPartnerFormActivity.this,"Please try again",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FoodPartnerFormActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("email",txt_email);
                param.put("password",txt_pass);
                param.put("isKitchen","1");


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(FoodPartnerFormActivity.this);
        queue.add(request);


    }
}