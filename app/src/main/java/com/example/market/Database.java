package com.example.market;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class Database  {
    private static final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static final long ONE_MEGABYTE = 1024 * 1024;
    private Bitmap bitmap = null;

    public static <T extends Text> void writeData(T texts){
        databaseRef.child(texts.getClass().getSimpleName()).push().setValue(texts);
    }
    public static <T extends Text> ArrayList<T> readDataInto(Class<T> ClassName){
        ArrayList<T> textList = new ArrayList<>();
        databaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot != null) {
                    for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                        textList.add(snapshot.getValue(ClassName));
                    }
                    Log.d("dataSnapshot", "reading data " + textList.toString());
                }
                else
                    Log.d("dataSnapshot", "null data");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("dataSnapshot",error.getMessage());
            }
        });
        return textList;
    }

    public void writeImage(String field, Bitmap bitmap){

    }
    public Bitmap readImage(@Nullable String field, String name) {
        String path= name + ".jpg";
        if(field != null) path = field + "/" + path;
        path = "images/" + path;
        storageRef.child(path).getBytes(ONE_MEGABYTE)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        Log.d("read Image","Success to read image " + name);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        bitmap = null;
                        Log.d("read Image","Failed to read image " + name);
                    }
                });
        return bitmap;
    }
}

class Data<T extends Text> {
    String id;
    public T text;
    public ArrayList<Bitmap> images = new ArrayList<>();

    public Class className;
    public String dataName;

    public Data(T text){
        this.text = text;
        this.className = this.text.getClass();
        this.dataName = this.text.getClass().getSimpleName();
    }
    public Data(T text, Bitmap... bitmaps){
        this(text); addImages(bitmaps);
    }

    public void addImages(Bitmap... bitmaps){
        for(Bitmap bitmap : bitmaps){
            images.add(bitmap);
        }
    }
    public Bitmap getImage(int index){
        if(this.images.size()>0)
            return this.images.get(index);
        return null;
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }
    public static Bitmap getBitmapFromBytes(byte[] bytes){
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }
}
interface Text {
}