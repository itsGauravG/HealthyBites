package com.example.healthybites.Partner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.healthybites.R;
import com.example.healthybites.VerifyOTPActivity;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class StoreDetailsActivity extends AppCompatActivity {
    public EditText k_name, k_state , k_city,k_address,k_mobile;
    public Button k_submit;
    private ProgressBar pb_store;
    public static String txt_k_name , txt_k_state , txt_k_city , txt_k_address, txt_k_mobile;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference node;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public String k_details = "https://healthybitesapp.000webhostapp.com/k_details.php";
    public static String txt_k_email ;
    public static String[] child_email;


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
        pb_store=(ProgressBar) findViewById(R.id.pb_store);

        mAuth=FirebaseAuth.getInstance();
        db = FirebaseDatabase.getInstance();
        node= db.getReference().child("Partner");
        pb_store.setVisibility(View.GONE);


        k_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                k_submit.setVisibility(View.GONE);
                pb_store.setVisibility(View.VISIBLE);

                txt_k_name = k_name.getText().toString().trim();
                txt_k_state = k_state.getText().toString().trim();
                txt_k_city = k_city.getText().toString().trim();
                txt_k_address = k_address.getText().toString().trim();
                txt_k_mobile = k_mobile.getText().toString().trim();
                txt_k_email = mAuth.getCurrentUser().getEmail();

                child_email = txt_k_email.split("@",5);

                if(txt_k_name.isEmpty()||txt_k_state.isEmpty()||txt_k_city.isEmpty()||txt_k_address.isEmpty()||txt_k_mobile.isEmpty()){
                    Toast.makeText(StoreDetailsActivity.this,"One of the fileds are empty",Toast.LENGTH_SHORT).show();
                    k_submit.setVisibility(View.VISIBLE);
                    pb_store.setVisibility(View.GONE);


                }
                else{//send data to firebase

                    HashMap<String,Object>map = new HashMap<>();
                    map.put("name",txt_k_name);
                    map.put("email",txt_k_email);
                    map.put("state",txt_k_state);
                    map.put("city",txt_k_city);
                    map.put("address",txt_k_address);
                    map.put("mobile",txt_k_mobile);

                   node.child(child_email[0]).setValue(map);
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

                    Toast.makeText(StoreDetailsActivity.this,"Successfully Submitted",Toast.LENGTH_SHORT).show();
                    sendOTP();

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

    private void sendOTP() {
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                pb_store.setVisibility(View.GONE);
                k_submit.setVisibility(View.VISIBLE);
                Toast.makeText(StoreDetailsActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                pb_store.setVisibility(View.GONE);
                k_submit.setVisibility(View.VISIBLE);
                Intent i = new Intent(StoreDetailsActivity.this, VerifyOTPActivity.class);
                i.putExtra ( "mobile", txt_k_mobile);
                i.putExtra ( "verificationId", verificationId);
                i.putExtra ( "identify", "partner");
                Toast.makeText(StoreDetailsActivity.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
                startActivity(i);


            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+txt_k_mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);


    }
}