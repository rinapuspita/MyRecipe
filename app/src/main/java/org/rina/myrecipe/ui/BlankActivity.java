package org.rina.myrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.startapp.android.publish.adsCommon.StartAppAd;

import org.rina.myrecipe.R;

public class BlankActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blank);
        StartAppAd.showAd(BlankActivity.this);
    }
}
