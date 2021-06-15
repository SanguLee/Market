package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {
    private ImageView itemImageView;
    private TextView itemTitleView;
    private TextView itemPriceView;
    private TextView itemContextView;

    public ArrayList<Data<PostItem>> allDataList = new ArrayList<>();
    private ArrayList<Data<PostItem>> viewDataList = new ArrayList<>();

    public ItemListAdapter(Context context){
        getItemList(context);
    }

    private void getItemList(Context context) {
        Bitmap SampleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.a5949a0357f22719655495d70553015b);

        allDataList.clear();

        FirebaseDatabase.getInstance().getReference().child("post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                        Data<PostItem> postItemData = new Data<PostItem>(postSnapshot.getValue(PostItem.class), SampleImage);
                        Log.d("post", postSnapshot.getValue(PostItem.class).toString());
                        allDataList.add(postItemData);
                    }
                    repaint();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void repaint(){
        viewDataList.clear();
        viewDataList.addAll(allDataList);
        this.notifyDataSetChanged();
    }

    public void searchItem(String searchText){
        viewDataList.clear();
        if(searchText.length() == 0) {
            viewDataList.addAll(allDataList);
        }
        else {
            for(int i = 0; i< allDataList.size(); i++){
                if(allDataList.get(i).text.title.toLowerCase().contains(searchText)) {
                    viewDataList.add(allDataList.get(i));
                }
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return viewDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        itemTitleView = (TextView) convertView.findViewById(R.id.itemBriefTitle);
        itemPriceView = (TextView) convertView.findViewById(R.id.itemBriefPrice);
        itemContextView = (TextView) convertView.findViewById(R.id.itemBriefContext);
        itemImageView = (ImageView) convertView.findViewById(R.id.ItemBriefImage);

        Data<PostItem> item = viewDataList.get(position);

        itemTitleView.setText(item.text.title);
        itemPriceView.setText(item.text.price);
        itemContextView.setText(item.text.context);
        itemImageView.setImageBitmap(item.image);

        return convertView;
    }
}