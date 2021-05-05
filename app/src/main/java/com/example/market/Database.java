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
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class Database {
    private static DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    private static StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static final long ONE_MEGABYTE = 1024 * 1024;
    private static Bitmap bitmap = null;
    private static int id = 0;

    public static void writeData(String field, int id, Object data){
        //incrementCounter(databaseRef.child(field));
        databaseRef.child(field).child(Integer.toString(id)).setValue(data)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("write","success to write ");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("write","failed to write ");
                    }
                });
    }

    public static Object readData(String field, String name){
        DatabaseReference readDataField = databaseRef.child(field);
        return null;
    }

    public static void incrementCounter(DatabaseReference database) {
        database.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(final MutableData currentData) {
                if (currentData.getValue() == null) {
                    currentData.setValue(1);
                } else {
                    long currentId = (long)currentData.getValue();
                    currentData.setValue((long)currentData.getValue()+1);
                }
                return Transaction.success(currentData);
            }

            @Override
            public void onComplete(@Nullable DatabaseError error, boolean committed, @Nullable DataSnapshot currentData) {
                if (error != null) {
                    Log.d("auto-increment","Firebase counter increment failed.");
                } else {
                    Log.d("auto-increment","Firebase counter increment succeeded.");
                }
            }
        });
    }
    public static void increment(DatabaseReference databaseRef){

    }

    public static void writeImage(String field, Bitmap bitmap){

    }
    public static Bitmap readImage(@Nullable String field, String name) {
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

