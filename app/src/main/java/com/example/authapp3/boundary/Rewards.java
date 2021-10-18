package com.example.authapp3.boundary;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Transition;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.authapp3.R;
import com.example.authapp3.control.VoucherAdapter;
import com.example.authapp3.entity.User;
import com.example.authapp3.entity.Voucher;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Rewards extends AppCompatActivity {
    private FirebaseUser user;
    private FirebaseDatabase reward;
    private String userID;
    private String email;
    private int points;
    private ArrayList<Voucher> voucher_array= new ArrayList<>();
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewards);

        /*Transition*/
        Transition exitTrans = new Fade();
        exitTrans.excludeTarget("@+id/BottomNavigationView",true);
        getWindow().setExitTransition(exitTrans);

        Transition reenterTrans = new Fade();
        reenterTrans.excludeTarget("@+id/BottomNavigationView",true);
        getWindow().setReenterTransition(reenterTrans);

        //To Extract Data from Firebase
        user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        email = user.getEmail();
        userID = user.getUid();

        TextView displayNameTextView = findViewById(R.id.name);
        TextView emailTextView = findViewById(R.id.email_header);
        TextView pointsTextView= findViewById(R.id.pointsBalance);
        emailTextView.setText(email);
        // Set Name of User at the Top//
        reference.child(userID).child("Profile").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    displayNameTextView.setText(userProfile.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Update User Points//
        reference.child(userID).child("Profile").child("Rewards").child("points").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                points = snapshot.getValue(Integer.class);
                pointsTextView.setText(points);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        // Get Vouchers from Voucher Catalogue//
        reference.child(userID).child("RewardCatalogue").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                voucher_array.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    Voucher voucher = snapshot1.getValue(Voucher.class);
                    voucher_array.add(voucher);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        // Display Vouchers in Recycler View//
        recyclerView = findViewById(R.id.recyclerVoucher);
        setAdapter();

        /*BOTTOM NAVIGATION BAR*/

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.BottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        Intent intent = new Intent(Rewards.this, HomePage.class);
                        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(Rewards.this,bottomNavigationView ,"BottomBar");
                        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent, options.toBundle());
                        break;
                    case R.id.ic_navigate:
                        Intent intent1 = new Intent(Rewards.this, Navigate.class);
                        ActivityOptions options1 = ActivityOptions.makeSceneTransitionAnimation(Rewards.this,bottomNavigationView ,"BottomBar");
                        intent1.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent1, options1.toBundle());
                        break;
                    case R.id.ic_ProfileActivity:
                        Intent intent2 = new Intent(Rewards.this, ProfileActivity.class);
                        ActivityOptions options2 = ActivityOptions.makeSceneTransitionAnimation(Rewards.this,bottomNavigationView ,"BottomBar");
                        intent2.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(intent2, options2.toBundle());
                        break;
                    case R.id.ic_Rewards:
                        break;
                }

                return false;
            }
        });

        /*BOTTOM NAV BAR END*/
    }

    private void setAdapter() {
        VoucherAdapter adapter = new VoucherAdapter(voucher_array);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim);
    }

    /*Backbutton Transition Animation*/
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim);
    }
}