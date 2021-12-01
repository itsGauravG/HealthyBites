package com.example.healthybites.User;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.healthybites.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UserDashboardActivity extends AppCompatActivity {
    private Button logout;
    BottomNavigationView bottom_nav;

   // Activity refActivity;
   // View parentholder;
    RecyclerView recyclerView;
    DatabaseReference database;
    MyAdapter_Kitchen adapter_kitchen;
    ArrayList<Kitchens> list;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdashboard);

        bottom_nav = findViewById(R.id.bottom_nav);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container, new HomeFragment());




        bottom_nav.setOnItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment= null;
                switch(item.getItemId()){
                    case R.id.home: {
                        fragment = new HomeFragment();

                    }
                    case R.id.account: {
                        fragment = new AccountFragment();

                    }
                    case R.id.cart: {
                        fragment = new CartFragment();

                    }
                    case R.id.search:{
                        fragment = new SearchFragment();

                    }
                    case R.id.track: {
                        fragment = new TrackFragment();

                    }

                }
                getSupportFragmentManager().beginTransaction().replace(R.id.main_container, fragment);
                return true;
            }
        });
    }
}