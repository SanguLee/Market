
package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class Market extends AppCompatActivity {
    static Boolean isScholar = false;
    private ItemListAdapter itemListAdapter;
    //private Database database = new Database();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market);

        TextView switchText = (TextView)findViewById(R.id.test);
        SwitchCompat sc = (SwitchCompat)findViewById((R.id.switch1));

        sc.setOnCheckedChangeListener(new CheckEventListener(switchText));

        TextView searchTextSubmit = (TextView)findViewById((R.id.searchTextSubmit));
        TextView searchTextChanged = (TextView)findViewById(R.id.searchTextChanged);
        SearchView sv = (SearchView)findViewById(R.id.searchView);

        sv.setOnQueryTextListener(new SearchEventListener(searchTextSubmit, searchTextChanged));

        ListView itemListView = (ListView)findViewById(R.id.ItemListContainer);

        itemListAdapter = new ItemListAdapter();
        itemListAdapter.TestMode(this);
        itemListView.setAdapter(itemListAdapter);

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent itemPage = new Intent(Market.this, ItemPage.class);
                Item item = (Item) parent.getItemAtPosition(position);
                itemPage.putExtra("name", item.itemText.name);
                itemPage.putExtra("price", item.itemText.price);
                itemPage.putExtra("context", item.itemText.context);
                itemPage.putExtra("image", Item.BitToBytes(item.getImage(0)));
                startActivity(itemPage);
            }
        });
    }

    class CheckEventListener implements CompoundButton.OnCheckedChangeListener{
        private TextView switchText;
        private CheckEventListener(TextView switchText){
            this.switchText = switchText;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) switchText.setText("checked");
            else switchText.setText("");
        }
    }

    class SearchEventListener implements SearchView.OnQueryTextListener {
        TextView searchTextSubmit;
        TextView searchTextChanged;

        private SearchEventListener(TextView searchTextSubmit, TextView searchTextChanged){
            //this.searchTextSubmit = searchTextSubmit;
            //this.searchTextChanged = searchTextChanged;
        }
        @Override
        public boolean onQueryTextSubmit(String query) {
            //searchTextSubmit.setText(query);
            itemListAdapter.searchItem(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            //searchTextChanged.setText(newText);
            itemListAdapter.searchItem(newText);
            return false;
        }
    }
}

