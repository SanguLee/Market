package com.example.market;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
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
import java.util.concurrent.TimeUnit;

public class Database  {
    private static final DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
    private static final StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    private static FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private static final long ONE_MEGABYTE = 1024 * 1024;
    private Bitmap bitmap = null;
    private static boolean isSignUpAcceptable = false;
    private static boolean isSignInAcceptable = false;

    public static <T extends Text> void writeData(T texts){
        databaseRef.child(texts.getClass().getSimpleName()).push().setValue(texts);
    }
    public static <T extends Text> void writeData(String id, T texts){
        databaseRef.child(texts.getClass().getSimpleName()).child(id).setValue(texts);
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
    public Bitmap image;

    public Class className;
    public String dataName;

    public Data(T text){
        this.text = text;
        this.className = this.text.getClass();
        this.dataName = this.text.getClass().getSimpleName();
    }
    public Data(T text, Bitmap bitmap){
        this(text); this.image = bitmap;
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