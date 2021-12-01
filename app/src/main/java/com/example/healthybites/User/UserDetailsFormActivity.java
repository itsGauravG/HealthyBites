package com.example.healthybites.User;

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

public class UserDetailsFormActivity extends AppCompatActivity {
    EditText name ,mobile , state , city, address ;
    Button submit;
    ProgressBar pb;
    FirebaseAuth mAuth;
    FirebaseDatabase db;
    DatabaseReference node;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    public static String user_name , user_mobile ,user_state  ,user_city , user_address ,user_txt_email;
    public static String user_det_url ="https://healthybitesapp.000webhostapp.com/user_details.php";
    private static String []nodeemail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details_form);

        name = (EditText) findViewById(R.id.user_name);
        mobile = (EditText) findViewById(R.id.user_mobile);
        state = (EditText) findViewById(R.id.user_state);
        city = (EditText) findViewById(R.id.user_city);
        address = (EditText) findViewById(R.id.user_address);
        submit = (Button) findViewById(R.id.user_submit) ;
        pb = (ProgressBar) findViewById(R.id.pb1);
        mAuth = FirebaseAuth.getInstance();

        db = FirebaseDatabase.getInstance();
        node = db.getReference().child("User");



        pb.setVisibility(View.GONE);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit.setVisibility(View.GONE);
                pb.setVisibility(View.VISIBLE);

                user_name = name.getText().toString().trim();
                user_mobile = mobile.getText().toString().trim();
                user_state = state.getText().toString().trim();
                user_city = city.getText().toString().trim();
                user_address = address.getText().toString().trim();
                user_txt_email = mAuth.getCurrentUser().getEmail();

                nodeemail = user_txt_email.split("@",5);

                if(user_name.isEmpty()||user_mobile.isEmpty()||user_state.isEmpty()||user_city.isEmpty()||user_address.isEmpty()){
                    pb.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    Toast.makeText(UserDetailsFormActivity.this,"One of the fileds are empty",Toast.LENGTH_SHORT).show();


                }
                else{ // save to firebase
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("Name",user_name);
                    map.put("Email",user_txt_email);
                    map.put("State",user_state);
                    map.put("City",user_city);
                    map.put("Address",user_address);
                    map.put("Mobile",user_mobile);

                    node.child(nodeemail[0]).setValue(map);

                    userDetails();
                    Toast.makeText(UserDetailsFormActivity.this,"Storing Details, please wait ...",Toast.LENGTH_SHORT).show();

                }


            }
        });


    }

    private void userDetails() {


        StringRequest request = new StringRequest(Request.Method.POST,user_det_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.equals("true")){

                    Toast.makeText(UserDetailsFormActivity.this,"Successfully Submitted",Toast.LENGTH_SHORT).show();
                    sendOTP();



                }

                else {
                    pb.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                    Toast.makeText(UserDetailsFormActivity.this,"Failed ,Please try again",Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                submit.setVisibility(View.VISIBLE);
                pb.setVisibility(View.GONE);
                Toast.makeText(UserDetailsFormActivity.this,error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param = new HashMap<String, String>();
                param.put("u_email",user_txt_email);
                param.put("u_name",user_name);
                param.put("u_mobile",user_mobile);
                param.put("u_state",user_state);
                param.put("u_city",user_city);
                param.put("u_address",user_address);


                return param;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(UserDetailsFormActivity.this);
        queue.add(request);


    }

    private void sendOTP() {

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                pb.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                Toast.makeText(UserDetailsFormActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                pb.setVisibility(View.GONE);
                submit.setVisibility(View.VISIBLE);
                Intent i = new Intent(UserDetailsFormActivity.this, VerifyOTPActivity.class);
                i.putExtra ( "mobile", user_mobile);
                i.putExtra ( "verificationId", verificationId);
                i.putExtra ( "identify", "user");
                Toast.makeText(UserDetailsFormActivity.this,"OTP sent successfully",Toast.LENGTH_SHORT).show();
                startActivity(i);


            }
        };

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+user_mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }
}