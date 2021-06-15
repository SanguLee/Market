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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText signUpTextEmail;
    EditText signUpTextPassword;
    EditText signUpTextPasswordRe;
    Button signUpButtonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        textSetup();

        signUpButtonSubmit.setOnClickListener(new submitButtonListener());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            FirebaseAuth.getInstance().signOut();
            Log.d("SignUp on start", "sign out");
        }else Log.d("SignUp on start", "null start");
        signUpTextEmail.getText().clear();
        signUpTextPassword.setText("");
        signUpTextPasswordRe.setText("");
    }

    private void textSetup(){
        signUpTextEmail = (EditText)findViewById(R.id.signupTextEmail);
        signUpTextPassword = (EditText)findViewById(R.id.signupTextPassword);
        signUpTextPasswordRe = (EditText)findViewById(R.id.signupTextPasswordRe);
        signUpButtonSubmit = (Button)findViewById(R.id.signupButtonSubmit);
    }

    class submitButtonListener implements View.OnClickListener{
        private submitButtonListener(){}

        @Override
        public void onClick(View v) {
            String toastMessage = "";

            String email = signUpTextEmail.getText().toString();
            String password = signUpTextPassword.getText().toString();
            String passwordRe = signUpTextPasswordRe.getText().toString();

            boolean signUpAcceptable = false;

            switch (TextWatcher.checkCorrectSignUpInput(
                    email, password, passwordRe)){
                case 1: toastMessage = "이메일을 입력해주세요."; break;
                case 3: toastMessage = "비밀번호를 입력해주세요."; break;
                case 5: toastMessage = "비밀번호 확인을 입력해주세요."; break;

                case 2: toastMessage = "올바른 이메일 형식이 아닙니다."; break;
                case 4: toastMessage = "올바른 비밀번호가 아닙니다. 특수문자와 숫자를 반드시 포함시켜주세요."; break;
                case 6: toastMessage = "비밀번호와 비밀번호 확인이 일치하지 않습니다."; break;

                case 0: signUpAcceptable = true;
            }
            if(signUpAcceptable){
                Log.d("sign up with email", "id= "+email+" pw= "+password );

                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(!task.isSuccessful()) return;
                                Log.d("signIn", "is Successful");
                                Toast.makeText(v.getContext(),"계정이 생성되었습니다.", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUp.this, SignUpInfo.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(SignUp.this, new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d("signIn", "is Failure at:" + e.getMessage());
                                Toast.makeText(v.getContext(),"계정 생성에 실패했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else Toast.makeText(v.getContext(),toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

}

class TextWatcher{
    private static String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$";
    private static String namePattern = "^[a-zA-Z0-9가-힣]+$";
    private static String phonePattern = "^01(?:0|1|[6-9])-(\\d{3,4})-(\\d{4})";
    private static String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";

    private static boolean checkTextType(String pattern, String inputText){
        return Pattern.matches(pattern, inputText);
    }

    public static int checkCorrectSignUpInput(
            String email, String password, String passwordRe){
        if(email.isEmpty()) return 1;
        else if(!checkTextType(emailPattern, email)) return 2;

        if(password.isEmpty()) return 3;
        else if(!checkTextType(passwordPattern, password)) return 4;

        if(passwordRe.isEmpty()) return 5;
        else if(!passwordRe.equals(password)) return 6;

        return 0;
    }
    public static int checkCorrectSignUpInfoInput(
            String name, String phone, String address){
        if(name.isEmpty()) return 1;
        else if(!checkTextType(namePattern, name)) return 2;

        if(phone.isEmpty()) return 3;
        else if(!checkTextType(phonePattern, phone)) return 4;

        if(address.isEmpty()) return 5;
        else ;

        return 0;
    }
}