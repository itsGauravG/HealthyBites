package com.example.healthybites;

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
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StoreDetailsActivity extends AppCompatActivity {
    public EditText k_name, k_state , k_city,k_address,k_mobile;
    public Button k_submit;
    public static String txt_k_name , txt_k_state , txt_k_city , txt_k_address, txt_k_mobile;
    public String k_details = "https://healthybitesapp.000webhostapp.com/k_details.php";
    public static String txt_k_email ;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_details);

        k_name= (EditText) findViewById(R.id.kitchen_name);
        k_state= (EditText) findViewById(R.id.kitchen_state);
        k_city= (EditText) findViewById(R.id.kitchen_city);
        k_address= (EditText) findViewById(R.id.kitchen_address);
        k_mobile= (EditText) findViewById(R.id.kitchen_mobile);
        k_submit = (Button) findViewById(R.id.kitchen_submit);
        auth=FirebaseAuth.getInstance();


        k_submit.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View v) {

                txt_k_name = k_name.getText().toString().trim();
                txt_k_state = k_state.getText().toString().trim();
                txt_k_city = k_city.getText().toString().trim();
                txt_k_address = k_address.getText().toString().trim();
                txt_k_mobile = k_mobile.getText().toString().trim();
                txt_k_email = auth.getCurrentUser().getEmail();

                if(txt_k_name.isEmpty()||txt_k_state.isEmpty()||txt_k_city.isEmpty()||txt_k_address.isEmpty()||txt_k_mobile.isEmpty()){
                    Toast.makeText(StoreDetailsActivity.this,"One of the fileds are empty",Toast.LENGTH_SHORT).show();


                }
                else{
                    kitchenDetails();
                    Toast.makeText(StoreDetailsActivity.this,"Storing Details, please wait ...",Toast.LENGTH_SHORT).show();

                }


            }
        });



    }

    private void kitchenDetails() {
        StringRequest request = new StringRequest(Request.Method.POST,k_details, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("true")){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            "+91"+txt_k_mobile,
                            60,
                            TimeUnit.SECONDS,
                            StoreDetailsActivity.this,
                            new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
                                @Override
                                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                                }

                                @Override
                                public void onVerificationFailed(@NonNull FirebaseException e) {
                                    Toast.makeText(StoreDetailsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();

                                }

                                @Override
                                public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                    Intent intent = new Intent(StoreDetailsActivity.this,VerifyOTPActivity.class);
                                    intent.putExtra ( "mobile", txt_k_mobile);
                                    intent.putExtra ( "verificationId", verificationId);
                                    intent.putExtra ( "identify", "partner");
                                    Toast.makeText(StoreDetailsActivity.this,"OTP sent",Toast.LENGTH_SHORT).show();
                                    startActivity(intent);
                                }
                            }
                    );

                }

                else {
                    Toast.makeText(StoreDetailsActivity.this,"Failed ,Please try again",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(StoreDetailsActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("k_email",txt_k_email);
                param.put("k_name",txt_k_name);
                param.put("k_state",txt_k_state);
                param.put("k_city",txt_k_city);
                param.put("k_address",txt_k_address);
                param.put("k_mobile",txt_k_mobile);

                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(StoreDetailsActivity.this);
        queue.add(request);

    }
}