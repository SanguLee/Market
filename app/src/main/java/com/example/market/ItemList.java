package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemList extends LinearLayout{
    Context context;
    ArrayList<ItemView> itemViews;

    public ItemList(Context context) {
        super(context);
        this.context = context;
        this.setOrientation(LinearLayout.VERTICAL);
        itemViews = new ArrayList<ItemView>();
    }

    public void addItem(){
        ItemView itemView = new ItemView(this.context, new Item());
        this.itemViews.add(itemView);
        this.addView(itemView);
    }
    public void addItem(Item item){
        ItemView itemView = new ItemView(this.context, item);
        this.itemViews.add(itemView);
        this.addView(itemView);
    }
    public void removeItem(int index){
        if(!(itemViews.size()>index)) return;
        itemViews.remove(index);
        this.removeView(itemViews.get(index));
    }
}

class ItemView extends LinearLayout {
    int index;
    ImageView image;
    LinearLayout textLayout;
    TextView name;
    TextView price;
    TextView script;
    Context c;

    public ItemView(Context context, Item item){
        super(context);
        c = context;

        this.addView(this.image = new ImageView(context));
        this.addView(this.textLayout = new LinearLayout(context));
        this.textLayout.addView(this.name = new TextView(context));
        this.textLayout.addView(this.price = new TextView(context));
        this.textLayout.addView(this.script = new TextView(context));

        this.image.setImageResource(R.drawable.a5949a0357f22719655495d70553015b);

        this.name.setText(item.name);
        this.price.setText(item.price);
        this.script.setText(item.script);

        sizer();
    }
    private void sizer(){
        this.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT, fn.dp(120,c)
        ));
        this.setPadding(fn.dp(5,c), fn.dp(5,c),
                fn.dp(5,c), fn.dp(5,c));
        this.setOrientation(LinearLayout.HORIZONTAL);

        image.setLayoutParams(new LayoutParams(
                fn.dp(110,c), fn.dp(110,c)
        ));

        textLayout.setOrientation(LinearLayout.VERTICAL);
        LayoutParams textLayoutParams = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
        );
        textLayoutParams.setMarginStart(fn.dp(10,c));
        textLayoutParams.setMargins(fn.dp(12,c),
                fn.dp(12,c), fn.dp(12,c), fn.dp(12,c));
        textLayout.setLayoutParams(textLayoutParams);
    }
}

class Item {
    Bitmap image = null;
    String name = "name";
    String price = "1000";
    String script = "script";
    public Item(){}
    public Item(String name){this.name = name;}
}