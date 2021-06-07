package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignUp extends AppCompatActivity {
    EditText signUpTextEmail;
    EditText signUpTextName;
    EditText signUpTextPhone;
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
        signUpTextEmail.setText(null);
        signUpTextName.setText(null);
        signUpTextPhone.setText(null);
        signUpTextPassword.setText(null);
        signUpTextPasswordRe.setText(null);
    }

    private void textSetup(){
        signUpTextEmail = (EditText)findViewById(R.id.signupTextEmail);
        signUpTextName = (EditText)findViewById(R.id.signupTextName);
        signUpTextPhone = (EditText)findViewById(R.id.signupTextPhone);
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
            String name = signUpTextName.getText().toString();
            String phone = signUpTextPhone.getText().toString();
            String password = signUpTextPassword.getText().toString();
            String passwordRe = signUpTextPasswordRe.getText().toString();

            boolean signUpAcceptable = false;

            switch (TextWatcher.checkCorrectSignUpInput(
                    email, name, phone, password, passwordRe)){
                case 1: toastMessage = "이메일을 입력해주세요."; break;
                case 3: toastMessage = "이름(별명)을 입력해주세요."; break;
                case 5: toastMessage = "전화번호를 입력해주세요."; break;
                case 7: toastMessage = "비밀번호를 입력해주세요."; break;
                case 9: toastMessage = "비밀번호 확인을 입력해주세요."; break;

                case 2: toastMessage = "올바른 이메일 형식이 아닙니다."; break;
                case 4: toastMessage = "이름(별명)을 다시 적어주세요. \n한영 및 숫자만 허용됩니다."; break;
                case 6: toastMessage = "전화번호를 다시 입력해주세요. \n하이폰을 포함하여 입력해야 합니다."; break;
                case 8: toastMessage = "올바른 비밀번호가 아닙니다. 특수문자와 숫자를 반드시 포함시켜주세요."; break;
                case 10: toastMessage = "비밀번호와 비밀번호 확인이 일치하지 않습니다."; break;

                case 0: signUpAcceptable = true;
            }
            if(signUpAcceptable){
                if(Database.signUpWithEmail(SignUp.this, email, password, name)){
                    toastMessage = "계정이 생성되었습니다.";
                    startActivity(new Intent(SignUp.this, Login.class));
                }
                else toastMessage = "이미 같은 계정이 있습니다.";
            }

            Toast.makeText(v.getContext(),toastMessage, Toast.LENGTH_SHORT).show();
        }
    }

}

class TextWatcher{
    private static String emailPattern = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9]+.[a-zA-Z0-9]+$";
    private static String namePattern = "^[a-zA-Z0-9가-힣]+$";
    private static String phonePattern = "^01(?:0|1|[6-9])-(\\d{3,4})-(\\d{4})";
//    private static String phonePattern = "^01(?:0|1|[6-9])[ -]?(\\d{3,4})[ -]?(\\d{4})";
    private static String passwordPattern = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*]).{8,}$";

    private static boolean checkTextType(String pattern, String inputText){
        return Pattern.matches(pattern, inputText);
    }

    public static int checkCorrectSignUpInput(
            String email, String name, String phone, String password, String passwordRe){
        if(email.isEmpty()) return 1;
        else if(!checkTextType(emailPattern, email)) return 2;

        if(name.isEmpty()) return 3;
        else if(!checkTextType(namePattern, name)) return 4;

//        if(phone.isEmpty()) return 5;
//        else if(!checkTextType(phonePattern, phone)) return 6;

        if(password.isEmpty()) return 7;
        else if(!checkTextType(passwordPattern, password)) return 8;

        if(passwordRe.isEmpty()) return 9;
        else if(!passwordRe.equals(password)) return 10;

        return 0;
    }
}