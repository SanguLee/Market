package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    public ArrayList<Data<Item>> allDataList = new ArrayList<>();
    private ArrayList<Data<Item>> viewDataList = new ArrayList<>();

    public void TestMode(Context context){
        ArrayList<Bitmap> SampleImages = new ArrayList<>();
        Bitmap SampleImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.a5949a0357f22719655495d70553015b);
        SampleImages.add(SampleImage);

        for(int i=0 ; i<10;i++) {
            Item item = new Item("sample" +i, "1000", "this is not sentence" );
            Data data = new Data(item, SampleImage);
            allDataList.add(data);
        }
        viewDataList.addAll(this.allDataList);
        this.notifyDataSetChanged();
    }

    public void searchItem(String searchText){
        viewDataList.clear();
        if(searchText.length() == 0) {
            viewDataList.addAll(allDataList);
        }
        else {
            for(int i = 0; i< allDataList.size(); i++){
                if(allDataList.get(i).text.name.toLowerCase().contains(searchText)) {
                    viewDataList.add(allDataList.get(i));
                }
            }
        }
        this.notifyDataSetChanged();
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

        itemNameView = (TextView) convertView.findViewById(R.id.itemBriefName);
        itemPriceView = (TextView) convertView.findViewById(R.id.itemBriefPrice);
        itemContextView = (TextView) convertView.findViewById(R.id.itemBriefContext);
        itemImageView = (ImageView) convertView.findViewById(R.id.ItemBriefImage);

        Data<Item> item = allDataList.get(position);

        itemNameView.setText(item.text.name);
        itemPriceView.setText(item.text.price);
        itemContextView.setText(item.text.context);
        itemImageView.setImageBitmap(item.getImage(0));

        return convertView;
    }
}

class Item implements Text{
        public String name;
        public String price;
        public String context;
        public Item(String name, String price, String context){
            this.name = name; this.price = price; this.context = context;
        }
        public Item(String name){
            this(name,"1000","This Sentence is not Sentence.");
        }
}
