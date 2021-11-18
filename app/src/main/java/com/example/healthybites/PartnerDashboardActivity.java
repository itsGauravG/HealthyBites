package com.example.healthybites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class PartnerDashboardActivity extends AppCompatActivity {
    private Button logout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_dashboard);
        logout= ( Button) findViewById(R.id.partner_logout);
        auth =FirebaseAuth.getInstance();

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(PartnerDashboardActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}