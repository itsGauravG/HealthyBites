package com.example.healthybites;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private ImageView send;
    private EditText email;
    FirebaseAuth auth ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        send= findViewById(R.id.forgot_send);
        email = findViewById(R.id.forgot_email);
        auth = FirebaseAuth.getInstance();


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_for_email = email.getText().toString();

                if (txt_for_email.isEmpty()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                } else{

                    auth.sendPasswordResetEmail(txt_for_email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPasswordActivity.this, "Password Reset Link successfully sent", Toast.LENGTH_SHORT).show();


                            } else {
                                Toast.makeText(ForgotPasswordActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }


            }
        });


    }
}