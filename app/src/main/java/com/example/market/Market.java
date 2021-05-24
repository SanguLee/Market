
package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

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


