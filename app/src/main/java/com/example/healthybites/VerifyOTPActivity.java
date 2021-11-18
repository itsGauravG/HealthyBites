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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifyOTPActivity extends AppCompatActivity {

    private TextView txv2;
    private EditText n1,n2,n3,n4,n5,n6;
    private String verificationId;
    private Button verify;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_otpactivity);

        Intent i = getIntent();

        txv2= (TextView)findViewById(R.id.txv2);
        n1=(EditText) findViewById(R.id.n1);
        n2=(EditText) findViewById(R.id.n2);
        n3=(EditText) findViewById(R.id.n3);
        n4=(EditText) findViewById(R.id.n4);
        n5=(EditText) findViewById(R.id.n5);
        n6=(EditText) findViewById(R.id.n6);
        verify=(Button) findViewById(R.id.verify_otp);

        txv2.setText(i.getStringExtra("mobile"));
        auth = FirebaseAuth.getInstance();

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
                    auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                if(identify.equals("user")) {
                                    Toast.makeText(VerifyOTPActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyOTPActivity.this, UserDashboardActivity.class);
                                    startActivity(i);
                                    finish();
                                }
                                else if(identify.equals("partner") ) {
                                    Toast.makeText(VerifyOTPActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(VerifyOTPActivity.this, PartnerDashboardActivity.class);
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