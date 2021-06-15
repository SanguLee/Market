package com.example.market;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

public abstract class MyDialog extends Dialog {
    protected TextView title, message;
    protected Button pos, neg ;
    private View.OnClickListener posListener, negListener;

    public MyDialog(@NonNull Context context) {
        super(context);
        setContentView(R.layout.my_dialog);

        title = (TextView) findViewById(R.id.dialog_title);
        message = (TextView) findViewById(R.id.dialog_message);
        pos = (Button) findViewById(R.id.dialog_positive);
        neg = (Button) findViewById(R.id.dialog_negative);

        title.setText("title"); message.setText("message");
        pos.setText("ok"); neg.setText("cancle");

        posListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnPosClickListener(view);
            }
        };

        negListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnNegClickListener(view);
            }
        };
        pos.setOnClickListener(posListener);
        neg.setOnClickListener(negListener);
    }

    protected abstract void OnPosClickListener(View view);
    protected abstract void OnNegClickListener(View view);
}