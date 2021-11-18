package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Post extends AppCompatActivity {
    EditText postTitle, postPrice, postContext;
    Button postButton;
    String id;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        textSetup();

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = postTitle.getText().toString();
                String price = postPrice.getText().toString();
                String context = postContext.getText().toString();

                if (title == null || price == null || context == null)
                    Toast.makeText(view.getContext(), "제목 혹은 가격, 설명이 비었습니다.", Toast.LENGTH_SHORT);
                else {
                    FirebaseDatabase.getInstance().getReference().child("post").push()
                            .setValue(new PostItem(id, name, title, price, context))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Intent intent = new Intent(Post.this, ItemList.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                    }
                                }
                            });
                }
            }
        });
    }
    private void textSetup(){
        postTitle = (EditText)findViewById(R.id.postTitle);
        postPrice = (EditText)findViewById(R.id.postPrice);
        postContext = (EditText)findViewById(R.id.postContext);
        postButton = (Button)findViewById(R.id.postButton);
    }
}
class PostItem implements Text{
    public String id="", name="", title="", price="", context="";
    public PostItem(){}
    public PostItem(String title, String price, String context){
        this("", "", title, price, context);
    }
    public PostItem(String id, String name, String title, String price, String context){
        this.id=id; this.name=name; this.title=title; this.price=price; this.context=context;
    }
}