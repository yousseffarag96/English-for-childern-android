package com.example.hp.ssa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ChangPassword extends AppCompatActivity {
private Button bacck_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chang_password);
        bacck_btn=(Button)findViewById(R.id.back_btn);
        bacck_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(ChangPassword.this,Main_menu.class);
                startActivity(in);
            }
        });
    }
}
