package org.rina.myrecipe.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;

import net.khirr.android.privacypolicy.PrivacyPolicyDialog;

import org.rina.myrecipe.R;

public class TermsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_terms);

        PrivacyPolicyDialog dialog = new PrivacyPolicyDialog(TermsActivity.this,
                "https://retrofit.flycricket.io/terms.html",
                "https://retrofit.flycricket.io/privacy.html");

        final Intent intent = new Intent(this, HomeActivity.class);

        dialog.setOnClickListener(new PrivacyPolicyDialog.OnClickListener() {
            @Override
            public void onAccept(boolean isFirstTime) {
                Log.e("TermsActivity", "Policies accepted");
                startActivity(intent);
                finish();
            }

            @Override
            public void onCancel() {
                Log.e("TermsActivity", "Policies not accepted");
                finish();
            }
        });

        dialog.addPoliceLine("This application uses a unique user identifier for advertising purposes, it is shared with third-party companies.");
        dialog.addPoliceLine("This application sends error reports, installation and send it to a server of the Fabric.io company to analyze and process it.");
        dialog.addPoliceLine("This application requires internet access and must collect the following information: Installed applications and history of installed applications, ip address, unique installation id, token to send notifications, version of the application, time zone and information about the language of the device.");
        dialog.addPoliceLine("All details about the use of data are available in our Privacy Policies, as well as all Terms of Service links below.");

        //  Customizing (Optional)
        dialog.setTitleTextColor(Color.parseColor("#222222"));
        dialog.setAcceptButtonColor(ContextCompat.getColor(this, R.color.colorAccent));

        //  Title
        dialog.setTitle("Terms of Service");

        //  {terms}Terms of Service{/terms} is replaced by a link to your terms
        //  {privacy}Privacy Policy{/privacy} is replaced by a link to your privacy policy
        dialog.setTermsOfServiceSubtitle("If you click on {accept}, you acknowledge that it makes the content present and all the content of our {terms}Terms of Service{/terms} and implies that you have read our {privacy}Privacy Policy{privacy}.");

        //  Set Europe only
//        dialog.setEuropeOnly(true);
        dialog.show();
    }
}
