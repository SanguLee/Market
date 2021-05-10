package com.example.market;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ItemListAdapter extends BaseAdapter {
    private ImageView itemImageView;
    private TextView itemNameView;
    private TextView itemPriceView;
    private TextView itemContextView;

    public ArrayList<Item> allItemList = new ArrayList<Item>();
    private ArrayList<Item> items = new ArrayList<Item>();

    public void TestMode(Context context){
        ArrayList<Bitmap> SampleImages = new ArrayList<>();
        SampleImages.add(BitmapFactory.decodeResource(context.getResources(), R.drawable.a5949a0357f22719655495d70553015b));
        for(int i=0 ; i<10;i++) {
            allItemList.add(new Item("sample" +i, 1000, "this is not sentence", SampleImages ));
        }
        items.addAll(this.allItemList);
        this.notifyDataSetChanged();
    }

    public void searchItem(String searchText){
        items.clear();
        if(searchText.length() == 0) {
            items.addAll(allItemList);
        }
        else {
            for(int i = 0; i< allItemList.size(); i++){
                if(allItemList.get(i).itemText.name.toLowerCase().contains(searchText)) {
                    items.add(allItemList.get(i));
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

        Item item = items.get(position);

        itemNameView.setText(item.itemText.name);
        itemPriceView.setText(Integer.toString(item.itemText.price));
        itemContextView.setText(item.itemText.context);
        itemImageView.setImageBitmap(item.getImage(0));

        return convertView;
    }
}

class Item {
    public int id=0;

    class ItemText{
        public String name = "name";
        public int price = 1000;
        public String context = "script";

        public ItemText(){}
        public ItemText(String name){this.name = name;}
        public ItemText(String name, int price, String context){
            this.name = name; this.price = price; this.context = context;
        }
    }
    public ItemText itemText;
    public ArrayList<Bitmap> images = new ArrayList<>();

    public Item(){this.itemText = new ItemText(); }
    public Item(String name){ this.itemText = new ItemText(name); }
    public Item(String name, int price, String context, ArrayList<Bitmap> bitmap) {
        this.itemText = new ItemText(name,price, context);
        this.images.addAll(bitmap);
    }

    public Bitmap getImage(int index){
        if(images.size()>0)
            return this.images.get(index);
        return null;
    }

    public static byte[] BitToBytes(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    public static Bitmap BytesToBit(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
}