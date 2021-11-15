package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button b_gmail;
    private Button b_sms;
    private Button b_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        b_gmail = findViewById(R.id.button);
        b_sms = findViewById(R.id.button2);
        b_phone = findViewById(R.id.button3);
        b_gmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGmail();
            }
        });
        b_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openPhone();
            }
        });
        b_sms.setOnClickListener(new View.OnClickListener() {
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
}