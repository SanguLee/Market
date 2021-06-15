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

        String title = intent.getStringExtra("title");
        String price = intent.getStringExtra("price");
        String context = intent.getStringExtra("context");
        Bitmap image = Data.getBitmapFromBytes(intent.getByteArrayExtra("image"));

        ImageView itemImage = (ImageView) findViewById(R.id.itemImage);
        TextView itemTitle = (TextView) findViewById(R.id.ItemName);
        TextView itemPrice = (TextView) findViewById(R.id.ItemPrice);
        TextView itemContext = (TextView) findViewById(R.id.ItemContext);

        itemImage.setImageBitmap(image);
        itemTitle.setText(title);
        itemPrice.setText(price);
        itemContext.setText(context);

    }
}