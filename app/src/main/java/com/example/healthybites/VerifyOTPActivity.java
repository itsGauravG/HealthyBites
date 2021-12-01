package com.example.healthybites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthybites.Partner.PartnerDashboardActivity;
import com.example.healthybites.User.UserDashboardActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyOTPActivity extends AppCompatActivity {

    private TextView txv2,txv4;
    private EditText n1,n2,n3,n4,n5,n6;
    private String verificationId;
    private Button verify;
    FirebaseAuth mAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private static String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        Intent i = getIntent();

        txv2= (TextView)findViewById(R.id.txv2);
        txv4 = (TextView) findViewById(R.id.txv4);
        n1=(EditText) findViewById(R.id.n1);
        n2=(EditText) findViewById(R.id.n2);
        n3=(EditText) findViewById(R.id.n3);
        n4=(EditText) findViewById(R.id.n4);
        n5=(EditText) findViewById(R.id.n5);
        n6=(EditText) findViewById(R.id.n6);
        verify=(Button) findViewById(R.id.verify_otp);
        mobile = i.getStringExtra("mobile");

        txv2.setText(mobile);
        mAuth = FirebaseAuth.getInstance();

        setOTPinputs();
        verificationId = getIntent().getStringExtra("verificationId");
        String identify= getIntent().getStringExtra("identify");

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(n1.getText().toString().isEmpty() ||n2.getText().toString().isEmpty() ||n3.getText().toString().isEmpty()
                        ||n4.getText().toString().isEmpty() ||n5.getText().toString().isEmpty() ||
                        n6.getText().toString().isEmpty() ){
                    Toast.makeText(VerifyOTPActivity.this,"One of the fileds are empty",Toast.LENGTH_SHORT).show();

                }

                String code = n1.getText().toString()+n2.getText().toString()+n3.getText().toString()
                        +n4.getText().toString()+n5.getText().toString()+n6.getText().toString();
                if(verificationId!=null){
                    PhoneAuthCredential phoneAuthCredential= PhoneAuthProvider.getCredential(verificationId,code);
                    mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                if(identify.equals("user")) {
                                    Toast.makeText(VerifyOTPActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyOTPActivity.this, UserDashboardActivity.class);
                                    i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("mobile",mobile);
                                    startActivity(i);
                                    finish();
                                }
                                else if(identify.equals("partner") ) {
                                    Toast.makeText(VerifyOTPActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyOTPActivity.this, PartnerDashboardActivity.class);
                                    i.setFlags(i.FLAG_ACTIVITY_CLEAR_TASK | i.FLAG_ACTIVITY_NEW_TASK);
                                    i.putExtra("mobile",mobile);
                                    startActivity(i);
                                    finish();

                                }

                            }
                            else{
                                Toast.makeText(VerifyOTPActivity.this,"Invalid OTP entered",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });

        txv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //resending otp

                mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

                    @Override
                    public void onVerificationCompleted(PhoneAuthCredential credential) {

                    }

                    @Override
                    public void onVerificationFailed(FirebaseException e) {

                        Toast.makeText(VerifyOTPActivity.this,e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCodeSent(@NonNull String verificationId,
                                           @NonNull PhoneAuthProvider.ForceResendingToken token) {

                        Toast.makeText(VerifyOTPActivity.this,"Code sent again",Toast.LENGTH_SHORT).show();

                    }
                };



                PhoneAuthOptions options =
                        PhoneAuthOptions.newBuilder(mAuth)
                                .setPhoneNumber("+91"+i.getStringExtra("mobile"))       // Phone number to verify
                                .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                .setActivity(VerifyOTPActivity.this)                 // Activity (for callback binding)
                                .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                                .build();
                PhoneAuthProvider.verifyPhoneNumber(options);
            }
        });


    }
    
    private void setOTPinputs(){
        n1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                n2.requestFocus();
}
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        n2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    n3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        n3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    n4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        n4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    n5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        n5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    n6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}