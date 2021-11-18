package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpInfo extends AppCompatActivity {
    EditText signUpInfoName;
    EditText signUpInfoPhone;
    EditText signUpInfoAddress;
    Button signUpInfoRater;
    Button signUpInfoSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_info);

        textSetup();

        signUpInfoRater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUpInfoDialog sid = new SignUpInfoDialog(view.getContext());
                sid.show();
            }
        });
        signUpInfoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String toastMessage = "";

                String name = signUpInfoName.getText().toString();
                String phone = signUpInfoPhone.getText().toString();
                String address = signUpInfoAddress.getText().toString();

                boolean signUpAcceptable = false;

                switch (TextWatcher.checkCorrectSignUpInfoInput(
                        name, phone, address)){
                    case 1: toastMessage = "이름을 입력해주세요."; break;
                    case 3: toastMessage = "전화번호를 입력해주세요."; break;
                    case 5: toastMessage = "주소를 입력해주세요."; break;

                    case 2: toastMessage = "올바른 이름 형식이 아닙니다."; break;
                    case 4: toastMessage = "올바른 전화번호 형식 아닙니다."; break;

                    case 0: signUpAcceptable = true;
                }
                if(signUpAcceptable) {
                    Log.d("add account info", "id= " + name + " pw= " + phone + " adr= " + address);

                    addAccountInfo(new AccountInfo(name, phone, address));
                }
                else Toast.makeText(SignUpInfo.this, toastMessage, Toast.LENGTH_SHORT);
            }
        });
    }

    public void addAccountInfo(AccountInfo ai){
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child(id)
                .setValue(ai).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(SignUpInfo.this, "환영합니다. " + ai.name + " 님", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SignUpInfo.this, ItemList.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        signUpInfoName.setText("");
        signUpInfoName.setText("");
        signUpInfoName.setText("");
    }

    private void textSetup(){
        signUpInfoName = (EditText)findViewById(R.id.signupInfoName);
        signUpInfoPhone = (EditText)findViewById(R.id.signupInfoPhone);
        signUpInfoAddress = (EditText)findViewById(R.id.signupInfoAddress);
        signUpInfoRater = (Button)findViewById(R.id.signupInfoButtonRater);
        signUpInfoSubmit = (Button)findViewById(R.id.signupInfoButtonSubmit);
    }

    class SignUpInfoDialog extends MyDialog {
        public SignUpInfoDialog(@NonNull Context context) {
            super(context);

            title.setText("부가정보 미입력");
            message.setText("부가정보를 나중에 입력하시겠습니까?");
            pos.setText("나중에");
            neg.setText("취소");
        }

        @Override
        protected void OnPosClickListener(View view) {
            addAccountInfo(new AccountInfo("user","",""));
            Intent intent = new Intent(SignUpInfo.this, ItemList.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }

        @Override
        protected void OnNegClickListener(View view) {
            dismiss();
        }

    }
}