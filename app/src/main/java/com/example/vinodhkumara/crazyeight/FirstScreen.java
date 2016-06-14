package com.example.vinodhkumara.crazyeight;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Created by vinodh.kumara on 12/11/2015.
 */
public class FirstScreen extends Activity {

    Button mBtnPlay = null;
    Button mBtnRules = null;
    private Context mContext = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_screen);
        mContext = getApplicationContext();
        initUI();
    }

    private void initUI() {
        mBtnPlay = (Button) findViewById(R.id.play);
        mBtnRules = (Button) findViewById(R.id.rules);
        mBtnPlay.setText("PLAY");
        mBtnRules.setText("RULES");
        mBtnPlay.setOnClickListener(mBtnOnClickListener);
        mBtnRules.setOnClickListener(mBtnOnClickListener);
    }

    private final View.OnClickListener mBtnOnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.play:
                    playActivity();
                    break;
                case R.id.rules:
                    rulesActivity();
                    break;
                default:
                    break;
            }
        }
    };

    private void playActivity() {
        Intent intent = new Intent(mContext,MainActivity.class);
        startActivity(intent);
    }

    private void rulesActivity() {
        Log.d("TAG", "showPopUp");
        final Dialog dialog = new Dialog(FirstScreen.this);
        dialog.setContentView(R.layout.rules_popup);
        dialog.setTitle("Crazy Eight Rules");
        dialog.setCancelable(false);
        Button mBtnOk = (Button) dialog.findViewById(R.id.ok);
        mBtnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
