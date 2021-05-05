
package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ScrollView;
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

        sv.setOnQueryTextListener(new SearchEventListener(searchTextSubmit, searchTextChanged));

        ListView itemListView = (ListView)findViewById(R.id.ItemListView);

        for(int i=0 ; i<10;i++) {
            ItemListAdapter.sampleItems.add(new Item("sample " + i));
            Database.writeData("sample", i, ItemListAdapter.sampleItems.get(i).itemText);
        }
        itemListAdapter = new ItemListAdapter(ItemListAdapter.sampleItems);
        itemListView.setAdapter(itemListAdapter);


    }

    class CheckEventListener implements CompoundButton.OnCheckedChangeListener{
        private TextView switchText;
        private CheckEventListener(TextView switchText){
            this.switchText = switchText;
        }
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if(isChecked) switchText.setText("checked");
            else switchText.setText("not..");
        }
    }

    class SearchEventListener implements SearchView.OnQueryTextListener {
        TextView searchTextSubmit;
        TextView searchTextChanged;

        private SearchEventListener(TextView searchTextSubmit, TextView searchTextChanged){
            this.searchTextSubmit = searchTextSubmit;
            this.searchTextChanged = searchTextChanged;
        }
        @Override
        public boolean onQueryTextSubmit(String query) {
            searchTextSubmit.setText(query);
            itemListAdapter.searchItem(query);
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            searchTextChanged.setText(newText);
            itemListAdapter.searchItem(newText);
            return false;
        }
    }
}

