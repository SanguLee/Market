package com.example.market;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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
        String price = intent.getStringExtra("price");
        String context = intent.getStringExtra("context");
        Bitmap image = Data.getBitmapFromBytes(intent.getByteArrayExtra("image"));

        Data<Item> item = new Data(new Item(name, price, context), image);

        item.text.name = name;
        item.text.price = price;
        item.text.context = context;

        ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        TextView itemName = (TextView) findViewById(R.id.ItemName);
        TextView itemPrice = (TextView) findViewById(R.id.ItemPrice);
        TextView itemContext = (TextView) findViewById(R.id.ItemContext);

        itemImage.setImageBitmap(item.getImage(0));
        itemName.setText(item.text.name);
        itemPrice.setText(item.text.price);
        itemContext.setText(item.text.context);

    }
}