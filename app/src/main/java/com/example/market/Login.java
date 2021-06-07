package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.HashMap;

public class Login extends AppCompatActivity {
    Button Login, SignUp, Forgot;
    EditText loginTextEmail;
    EditText loginTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.loginButtonLogin);
        SignUp = (Button) findViewById(R.id.loginButtonSignup);
        Forgot = (Button) findViewById(R.id.loginButtonForgot);

        loginTextEmail = (EditText) findViewById(R.id.loginTextEmail);
        loginTextPassword = (EditText) findViewById(R.id.loginTextPassword);

        /* 로그인 버튼 입력 시, 확인해서 마켓 페이지로 넘기는 것 */
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = loginTextEmail.getText().toString();
                String password = loginTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(view.getContext(), "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(Database.signInWithEmail(Login.this, email, password)){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    String message = "어서오세요 " + user.getDisplayName() + "님";
                    Toast.makeText(view.getContext(),message, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Market.class));
                }
                else Toast.makeText(view.getContext(), "등록되지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, SignUp.class));
            }
        });

        Forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"아직 미구현입니다 ^^;;", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        loginTextEmail.setText(null);
        loginTextPassword.setText(null);
        FirebaseAuth.getInstance().signOut();
    }
}