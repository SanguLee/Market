package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;

public class Database  {
    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static final long ONE_MEGABYTE = 1024 * 1024;
    private Bitmap bitmap = null;
    private Data data = null;

    public static void writeData(T texts){
        //databaseRef.child(texts.getClass().getSimpleName()).push().setValue(texts);
    }
    public static void writeData(String id, T texts){
        //databaseRef.child(texts.getClass().getSimpleName()).child(id).setValue(texts);
    }

    public void writeImage(String field, Bitmap bitmap){

    }

    public Data readData(String field, String id){
        DatabaseReference dataRef = FirebaseDatabase.getInstance().getReference().child(field);
        dataRef.child(id).addChildEventListener(childEventListener);
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

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            Data data = dataSnapshot.getValue(Data.class);
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    }

}

abstract class Data {

//    public static byte[] getBytesFromBitmap(Bitmap bitmap){
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
//        byte[] bytes = stream.toByteArray();
//        return bytes;
//    }
//    public static Bitmap getBitmapFromBytes(byte[] bytes){
//        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//        return bitmap;
//    }
}