
package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ItemList extends AppCompatActivity {
    String id;
    AccountInfo ai;
    static Boolean isScholar = false;
    static boolean isSignIn = false;
    private ItemListAdapter itemListAdapter;
    //private Database database = new Database();

    FloatingActionButton addNewPost;
    ImageView accountImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        accountImage = (ImageView)findViewById(R.id.accountImage);
        setAccountImageColor();

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num;
                if(isSignIn) num = 2; else num = 1;
                AccountDialog ad = new AccountDialog(view.getContext(), num);
                ad.show();
            }
        });

        addNewPost = (FloatingActionButton)findViewById(R.id.addNewPost);
        addNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FirebaseAuth.getInstance().getCurrentUser()!=null){
                    Intent intent = new Intent(ItemList.this, Post.class);
                    intent.putExtra("id", id);
                    intent.putExtra("name", ai.name);
                    startActivity(intent);
                }
                else {
                    AccountDialog ad = new AccountDialog(view.getContext(), 3);
                    ad.show();
                }
            }
        });

        SwitchCompat sc = (SwitchCompat)findViewById((R.id.switch1));
        sc.setOnCheckedChangeListener(new CheckEventListener());

        SearchView sv = (SearchView)findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchEventListener());

        ListView itemListView = (ListView)findViewById(R.id.ItemListContainer);

        itemListAdapter = new ItemListAdapter(ItemList.this);
        itemListView.setAdapter(itemListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itemPage = new Intent(ItemList.this, ItemInfo.class);
                Data<PostItem> item = (Data<PostItem>) parent.getItemAtPosition(position);
                itemPage.putExtra("title", item.text.title);
                itemPage.putExtra("price", item.text.price);
                itemPage.putExtra("context", item.text.context);
                itemPage.putExtra("image", Data.getBytesFromBitmap(item.image));
                startActivity(itemPage);
            }
        });
    }

    private void setAccountImageColor(){
        if(isSignIn = FirebaseAuth.getInstance().getCurrentUser() != null){
            accountImage.setBackgroundColor(Color.BLUE);
        }
        else
            accountImage.setBackgroundColor(Color.RED);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null)
            FirebaseDatabase.getInstance().getReference()
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            id = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            ai = snapshot.child(id).getValue(AccountInfo.class);
                            if (ai == null) {
                                FirebaseAuth.getInstance().signOut();
                                setAccountImageColor();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
    }

    class AccountDialog extends MyDialog {
        int num;
        public AccountDialog(@NonNull Context context, int n) {
            super(context);

            this.num = n;
            switch (num) {
                case 1 :
                    title.setText("로그인이 확인되지 않았습니다.");
                    message.setText("회원 정보를 확인하기 위하여 로그인이 필요합니다.\n로그인 하시겠습니까?");
                    pos.setText("로그인"); neg.setText("취소"); break;
                case 2 :
                    title.setText("로그인 상태입니다.");
                    message.setText("로그아웃하시겠습니까?");
                    pos.setText("로그아웃"); neg.setText("취소"); break;
                case 3 :
                    title.setText("회원이 아닙니다.");
                    message.setText("새 글 등록은 먼저 로그인이 되어야 합니다.\n로그인을 진행하시겠습니까?");
                    pos.setText("로그인"); neg.setText("취소"); break;
            }
        }

        @Override
        protected void OnPosClickListener(View view) {
            Intent intent;
            switch (num) {
                case 1 :
                    intent = new Intent(ItemList.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                case 2 :
                    FirebaseAuth.getInstance().signOut();
                    dismiss();
                    setAccountImageColor(); break;
                case 3 :
                    intent = new Intent(ItemList.this, Login.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
            }
        }

        @Override
        protected void OnNegClickListener(View view) {
            dismiss();
        }
    }

    class CheckEventListener implements CompoundButton.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        }
    }
    class SearchEventListener implements SearchView.OnQueryTextListener {
        @Override
        public boolean onQueryTextSubmit(String query) {
            itemListAdapter.searchItem(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            itemListAdapter.searchItem(newText);
            return false;
        }
    }
}


