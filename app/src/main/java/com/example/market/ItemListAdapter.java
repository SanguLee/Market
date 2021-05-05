package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {
    private ImageView itemImageView;
    private TextView itemNameView;
    private TextView itemPriceView;
    private TextView itemContextView;

    public static ArrayList<Item> sampleItems = new ArrayList<Item>();

    private ArrayList<Item> allItems;
    private ArrayList<Item> items = new ArrayList<Item>();

    public ItemListAdapter(ArrayList<Item> items) {
        this.allItems = items;
        this.items.addAll(allItems);
    }

    public void searchItem(String searchText){
        items.clear();
        if(searchText.length() == 0) {
            items.addAll(allItems);
        }

        else {
            for(int i=0; i<allItems.size();i++){
                if(allItems.get(i).itemText.name.toLowerCase().contains(searchText)) {
                    items.add(allItems.get(i));
                }
            }
        }
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater)
                    context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        itemNameView = (TextView) convertView.findViewById(R.id.ItemNameView);
        itemPriceView = (TextView) convertView.findViewById(R.id.ItemPriceView);
        itemContextView = (TextView) convertView.findViewById(R.id.ItemContextView);
        itemImageView = (ImageView) convertView.findViewById(R.id.ItemImageView);

        Item item = items.get(position);

        itemNameView.setText(item.itemText.name);
        itemPriceView.setText(Integer.toString(item.itemText.price));
        itemContextView.setText(item.itemText.context);
        itemImageView.setImageBitmap(item.getHeadImage());

        return convertView;
    }
}

class Item {
    int id=0;
    class ItemText{
        public String name = "name";
        public int price = 1000;
        public String context = "script";

        public ItemText(String name){this.name = name;}
    }
    public ItemText itemText;
    public ArrayList<Bitmap> images = new ArrayList<>();

    public Item(String name){ this.itemText = new ItemText(name); }
    public int getId(){ return this.id; }

    public Bitmap getHeadImage(){
        if(images.size()>0)
            return this.images.get(0);
        return null;
    }
}