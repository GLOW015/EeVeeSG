package com.example.authapp3.boundary;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.authapp3.R;

public class VoucherQR extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher_qr);

        Button backButton = findViewById(R.id.back_button);
        backButton.setOnClickListener(view -> VoucherQR.this.finish());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.nav_default_enter_anim,R.anim.nav_default_exit_anim);
    }
}
