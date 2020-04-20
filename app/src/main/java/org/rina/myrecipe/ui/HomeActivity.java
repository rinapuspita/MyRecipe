package org.rina.myrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.startapp.android.publish.adsCommon.AutoInterstitialPreferences;
import com.startapp.android.publish.adsCommon.StartAppAd;
import com.startapp.android.publish.adsCommon.StartAppSDK;

import org.rina.myrecipe.R;

public class HomeActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST = 100;
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartAppSDK.init(this, "203460553", true);
//        StartAppAd.enableAutoInterstitial();
//        StartAppAd.setAutoInterstitialPreferences(
//                new AutoInterstitialPreferences()
//                        .setSecondsBetweenAds(60)
//                        .setActivitiesBetweenAds(3)
//        );
        StartAppSDK.setUserConsent(this, "pas", System.currentTimeMillis(), true);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        //add event
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                Toast.makeText(HomeActivity.this, "On loaded", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Toast.makeText(HomeActivity.this, "On failed to load with error code "+errorCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdOpened() {
                // Code to be executed when the ad is displayed.
            }

            @Override
            public void onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            @Override
            public void onAdLeftApplication() {
                // Code to be executed when the user has left the app.
            }

            @Override
            public void onAdClosed() {
                // Code to be executed when the interstitial ad is closed.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

        setContentView(R.layout.activity_home);
        check();

    }
    private void showInterstial(){
        if(mInterstitialAd != null && mInterstitialAd.isLoaded()){
            mInterstitialAd.show();
        } else{
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }



    public void handleOpen(View view) {
        if(check()==true){
            Intent intent = new Intent(HomeActivity.this, BlankActivity.class);

            startActivity(intent);
            if(mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
                showInterstial();
            } else{
                Log.d("TAG","The interstial wasn't loaded yet");
            }
        }


    }
    public boolean check(){
        if(ContextCompat.checkSelfPermission(HomeActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(HomeActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    MY_PERMISSIONS_REQUEST);
        }
        return true;
    }
}
