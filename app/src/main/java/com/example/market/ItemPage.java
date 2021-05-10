package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemPage extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_page);

        intent = getIntent();

        String name = intent.getStringExtra("name");
        int price = intent.getIntExtra("price",0);
        String context = intent.getStringExtra("context");
        byte[] bytes = intent.getByteArrayExtra("image");
        Bitmap image = Item.BytesToBit(bytes);
        ArrayList<Bitmap> images = new ArrayList<>();
        images.add(image);

        Item item = new Item(name, price, context, images);
//        Item item = new Item();
//        item.itemText.name = name;
//        item.itemText.price = price;
//        item.itemText.context = context;

        ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        TextView itemName = (TextView) findViewById(R.id.ItemName);
        TextView itemPrice = (TextView) findViewById(R.id.ItemPrice);
        TextView itemContext = (TextView) findViewById(R.id.ItemContext);

        itemImage.setImageBitmap(item.getImage(0));
        itemName.setText(item.itemText.name);
        itemPrice.setText(Integer.toString(item.itemText.price));
        itemContext.setText(item.itemText.context);

    }
}