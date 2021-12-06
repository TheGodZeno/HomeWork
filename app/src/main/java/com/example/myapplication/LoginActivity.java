package com.example.myapplication;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

//import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText mail,password;
    Button login,regp;
    FirebaseUser currentUser;
    FirebaseAuth mAuth;
    //ProgressDialog loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mail = findViewById(R.id.e_mail2);
        password = findViewById(R.id.e_psw2);
        login = findViewById(R.id.b_login);
        regp = findViewById(R.id.b_regp);
        mAuth = FirebaseAuth.getInstance();
        //loadingBar = new ProgressDialog(this);
        currentUser = mAuth.getCurrentUser();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                allowUserToLogin();
            }
        });
        regp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToRegister();
            }
        });
    }

    private void sendUserToRegister(){
        Intent registerIntent = new Intent(this,RegisterActivity.class);
        startActivity(registerIntent);
    }

    private void allowUserToLogin(){
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
            //loadingBar.setTitle("Sign In");
            //loadingBar.setMessage("Please wait ,Because good things always take time");
            mAuth.signInWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        sendToMainActivity();
                        Toast.makeText(LoginActivity.this,"Welcome to Reference Center",Toast.LENGTH_SHORT).show();
                        //loadingBar.dismiss();
                    }
                    else
                    {
                        Toast.makeText(LoginActivity.this,"The email & password not exists or the credentials not correct ",Toast.LENGTH_SHORT).show();
                        //String msg =task.getException().toString();
                        //Toast.makeText(LoginActivity.this,"Error: "+msg,Toast.LENGTH_SHORT).show();
                        //loadingBar.dismiss();
                    }
                }
            });
        }
    }

    protected  void onStart() {
        super.onStart();
        if(currentUser!=null)
        {
            sendToMainActivity();
        }
    }

    private void sendToMainActivity(){
        Intent mainIntent = new Intent(this,MainActivity.class);
        startActivity(mainIntent);
    }


}