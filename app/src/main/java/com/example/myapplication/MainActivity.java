package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.Inet4Address;

public class MainActivity extends AppCompatActivity {
    private Button gmail,sms,phone,logout;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gmail = findViewById(R.id.b_gmail);
        sms = findViewById(R.id.b_sms);
        phone = findViewById(R.id.b_phone);
        logout = findViewById(R.id.b_logout);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                sendToLoginActivity();
            }
        });
        gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmail();
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhone();
            }
        });
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSms();
            }
        });
    }


    public void openGmail(){
        String[] emails = new String[]{"shugel2004@gmail.com"};
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.putExtra(intent.EXTRA_EMAIL, emails);
        intent.setData(Uri.parse("mailto:"));//only for emails.
        startActivity(Intent.createChooser(intent,"Choose an email client"));
    }
    public void openPhone(){
        String companyPhoneNumberUri = "tel:052-652-8192";
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(companyPhoneNumberUri));
        startActivity(intent);
    }
    public void openSms(){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms", "052-652-8192", null));
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser==null)
        {
            sendToLoginActivity();
        }
    }

    private void sendToLoginActivity() {
        Intent loginintnet = new Intent(this,LoginActivity.class);
        startActivity(loginintnet);
    }

}