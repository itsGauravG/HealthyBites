package com.example.healthybites.Partner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.healthybites.R;
import com.example.healthybites.User.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

public class PartnerDashboardActivity extends AppCompatActivity {
    private Button logout,additem;
    FirebaseAuth auth;
    private static String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent i = getIntent();
        setContentView(R.layout.activity_partner_dashboard);
        logout= ( Button) findViewById(R.id.partner_logout);
        auth =FirebaseAuth.getInstance();
        additem = findViewById(R.id.add_item);
        mobile = i.getStringExtra("mobile");
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(PartnerDashboardActivity.this, MainActivity.class);
                startActivity(i);
                finish();

            }
        });

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PartnerDashboardActivity.this, AddFoodItemActivity.class);
                intent.putExtra("mobile",mobile);
                startActivity(intent);
            }
        });

    }
}