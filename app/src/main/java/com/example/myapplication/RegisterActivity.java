package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.text.TextUtils;
import android.widget.Toast;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    Button register;
    EditText password;
    EditText mail;
    Button AccountExists;
    private FirebaseAuth mAuth;
    private ProgressDialog loadingBar;
    //@SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        register = findViewById(R.id.b_register);
        mail = findViewById(R.id.e_mail);
        password = findViewById(R.id.e_psw);
        AccountExists = findViewById(R.id.b_haaa);
        //loadingBar = new ProgressDialog(this);
        AccountExists.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                sendUserToLoginActivity();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewAccount();
            }
        });
    }

    private void createNewAccount() {
        String email = mail.getText().toString().trim();
        String pwd = password.getText().toString();
        if(TextUtils.isEmpty(email))
        {
            mail.setError("Email is Required");
            //Toast.makeText(this,"Please enter email id",Toast.LENGTH_SHORT).show();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            mail.setError("email must be valid");
        }
        if(TextUtils.isEmpty(pwd))
        {
            password.setError("Password is required");
            //Toast.makeText(this,"Please enter password",Toast.LENGTH_SHORT).show();
        }
        if(pwd.length() < 6)
        {
            password.setError("Password must be at least 6 Characters");
        }
        else
        {
            //loadingBar.setTitle("creating New Account");
            //loadingBar.setMessage("please wait, we are creating new Account");
            //loadingBar.setCanceledOnTouchOutside(true);
            //loadingBar.show();
            mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        sendUserToLoginActivity();
                        Toast.makeText(RegisterActivity.this,"Account created successfully",Toast.LENGTH_SHORT).show();
                        //loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(RegisterActivity.this,"The email already exists, Please try again",Toast.LENGTH_SHORT).show();
                        //String msg = task.getException().toString();
                        //Toast.makeText(RegisterActivity.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                        //loadingBar.dismiss();
                    }
               }
            });
        }
    }
    private void sendUserToLoginActivity() {
        Intent loginIntent = new Intent(this,LoginActivity.class);
        startActivity(loginIntent);

    }
}
