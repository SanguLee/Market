
package com.example.market;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Market extends AppCompatActivity {
    static Boolean isScholar = false;
    static boolean isSignIn = false;
    private ItemListAdapter itemListAdapter;
    //private Database database = new Database();

    ImageView accountImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        accountImage = (ImageView)findViewById(R.id.accountImage);
        if(isSignIn = FirebaseAuth.getInstance().getCurrentUser() != null){
            accountImage.setBackgroundColor(Color.BLUE);
        }
        else
            accountImage.setBackgroundColor(Color.RED);

        accountImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isSignIn){
                    LoginDialog ld = new LoginDialog(Market.this);
                    ld.show();
                }
                else{
                    Toast.makeText(view.getContext(), "회원정보 미구현입니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        SwitchCompat sc = (SwitchCompat)findViewById((R.id.switch1));
        sc.setOnCheckedChangeListener(new CheckEventListener());

        SearchView sv = (SearchView)findViewById(R.id.searchView);
        sv.setOnQueryTextListener(new SearchEventListener());

        ListView itemListView = (ListView)findViewById(R.id.ItemListContainer);

        itemListAdapter = new ItemListAdapter();
        itemListAdapter.TestMode(this);
        itemListView.setAdapter(itemListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itemPage = new Intent(Market.this, ItemPage.class);
                Data<Item> item = (Data<Item>) parent.getItemAtPosition(position);
                itemPage.putExtra("name", item.text.name);
                itemPage.putExtra("price", item.text.price);
                itemPage.putExtra("context", item.text.context);
                itemPage.putExtra("image", Data.getBytesFromBitmap(item.getImage(0)));
                startActivity(itemPage);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseAuth.getInstance().signOut();
    }

    class LoginDialog extends Dialog {
        TextView title, message;
        Button pos, neg ;
        public LoginDialog(@NonNull Context context) {
            super(context);
            setContentView(R.layout.market_dialog_login);
            title = (TextView)findViewById(R.id.dialog_title);
            message = (TextView)findViewById(R.id.dialog_message);

            title.setText("로그인이 확인되지 않았습니다.");
            message.setText("회원 정보를 확인하기 위하여 로그인이 필요합니다.\n로그인 하시겠습니까?");

            pos = (Button)findViewById(R.id.dialog_positive);
            neg = (Button)findViewById(R.id.dialog_negative);

            pos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(Market.this, Login.class));
                }
            });
            neg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismiss();
                }
            });
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


