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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {
    Button Login, Guest, SignUp, Forgot;
    EditText loginTextEmail;
    EditText loginTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Login = (Button) findViewById(R.id.loginButtonLogin);
        Guest = (Button) findViewById(R.id.loginButtonGuest);
        SignUp = (Button) findViewById(R.id.loginButtonSignUp);
        Forgot = (Button) findViewById(R.id.loginButtonForgot);

        loginTextEmail = (EditText) findViewById(R.id.loginTextEmail);
        loginTextPassword = (EditText) findViewById(R.id.loginTextPassword);

        /* 로그인 버튼 입력 시, 확인해서 마켓 페이지로 넘기는 것 */
        Login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String email = loginTextEmail.getText().toString();
                Log.d("email", email);
                String password = loginTextPassword.getText().toString();

                if(email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(view.getContext(), "아이디 또는 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }

                else {

                }

//                else{
//                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
//                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if(task.isSuccessful()){
//                                        Log.d("signIn", "is Successful");
//
//                                        if (FirebaseAuth.getInstance().getCurrentUser() != null)
//                                            FirebaseDatabase.getInstance().getReference()
//                                                    .addValueEventListener(new ValueEventListener() {
//                                                        @Override
//                                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                            String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                                                            AccountInfo ai = snapshot.child(id).getValue(AccountInfo.class);
//                                                            Toast.makeText(Login.this, "환영합니다. " + ai.name + " 님", Toast.LENGTH_SHORT).show();
//                                                        }
//
//                                                        @Override
//                                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                                        }
//                                                    });
//
//
//                                        Intent intent = new Intent(Login.this, ItemList.class);
//                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                        startActivity(intent);
//                                    } else {
//                                        Log.d("signIn", "is Failure");
//                                        Toast.makeText(view.getContext(), "등록되지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
            }
        });

        Guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        loginTextEmail.setText("");
        loginTextPassword.setText("");
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("Login on start","sign out");
            FirebaseAuth.getInstance().signOut();
        }
        else
            Log.d("Login on start", "null start");
    }
}