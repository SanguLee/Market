
package com.example.market;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;

public class Market extends AppCompatActivity {
    static Boolean isScholar = false;
    final ItemList itemList = new ItemList(this.getApplicationContext());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView switchText = (TextView)findViewById(R.id.test);
        SwitchCompat sc = (SwitchCompat)findViewById((R.id.switch1));

        sc.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                isScholar = true;
                //tv.setText("checked");
                return false;
            }
        });
        sc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) switchText.setText("checked");
                else switchText.setText("not..");
            }
        });

        TextView searchTextSubmit = (TextView)findViewById((R.id.searchTextSubmit));
        TextView searchTextChanged = (TextView)findViewById(R.id.searchTextChanged);
        SearchView sv = (SearchView)findViewById(R.id.searchView);

        sv.setOnQueryTextListener(new SearchEventListener(
                searchTextSubmit, searchTextChanged
        ));

        ScrollView scrollView = (ScrollView)findViewById(R.id.ScrollPage);
        scrollView.addView(itemList);
        for(int i=0 ; i<10;i++)
            itemList.addItem(new Item("sample" + i));
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
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            searchTextChanged.setText(newText);
            itemList.removeAllViews();

            return false;
        }
    }
}

class fn{
    static public int dp(int i, Context c){
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, i,
                c.getResources().getDisplayMetrics());
    }
}
