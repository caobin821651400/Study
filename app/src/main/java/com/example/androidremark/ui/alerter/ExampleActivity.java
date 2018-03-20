package com.example.androidremark.ui.alerter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.example.androidremark.R;


public class ExampleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.btnAlertDefault).setOnClickListener(this);
        findViewById(R.id.btnAlertColoured).setOnClickListener(this);
        findViewById(R.id.btnAlertCustomIcon).setOnClickListener(this);
        findViewById(R.id.btnAlertTextOnly).setOnClickListener(this);
        findViewById(R.id.btnAlertOnClick).setOnClickListener(this);
        findViewById(R.id.btnAlertVerbose).setOnClickListener(this);
        findViewById(R.id.btnAlertCallback).setOnClickListener(this);
        findViewById(R.id.btnAlertInfiniteDuration).setOnClickListener(this);
        findViewById(R.id.btnAlertWithProgress).setOnClickListener(this);
//        findViewById(R.id.btnAlertWithCustomFont).setOnClickListener(this);
        findViewById(R.id.btnAlertSwipeToDismissEnabled).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAlertColoured: {
                showAlertColoured();
                break;
            }
            case R.id.btnAlertCustomIcon: {
                showAlertWithIcon();
                break;
            }
            case R.id.btnAlertTextOnly: {
                showAlertTextOnly();
                break;
            }
            case R.id.btnAlertOnClick: {
                showAlertWithOnClick();
                break;
            }
            case R.id.btnAlertVerbose: {
                showAlertVerbose();
                break;
            }
            case R.id.btnAlertCallback: {
                showAlertCallbacks();
                break;
            }
            case R.id.btnAlertInfiniteDuration: {
                showAlertInfiniteDuration();
                break;
            }
            case R.id.btnAlertWithProgress: {
                showAlertWithProgress();
                break;
            }
//            case R.id.btnAlertWithCustomFont: {
//                showAlertWithCustomFont();
//                break;
//            }
            case R.id.btnAlertSwipeToDismissEnabled: {
                showAlertSwipeToDismissEnabled();
                break;
            }
            default: {
                showAlertDefault();
            }
        }
    }

    private void showAlertDefault() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .disableOutsideTouch()
                .show();
    }

    private void showAlertColoured() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setBackgroundColorRes(R.color.colorAccent)
                .show();
    }

    private void showAlertWithIcon() {
        Alerter.create(ExampleActivity.this)
                .setText("Alert text...")
                .setIcon(R.drawable.app_logo)
                .show();
    }

    private void showAlertTextOnly() {
        Alerter.create(ExampleActivity.this)
                .setText("Alert text...")
                .show();
    }

    private void showAlertWithOnClick() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(ExampleActivity.this, "OnClick Called", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void showAlertVerbose() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text. " +
                        "The alert scales to accommodate larger bodies of text.")
                .show();
    }

    private void showAlertCallbacks(){
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .setDuration(10000)
                .setOnShowListener(new OnShowAlertListener() {
                    @Override
                    public void onShow() {
                        Toast.makeText(ExampleActivity.this, "Show Alert", Toast.LENGTH_LONG).show();
                    }
                })
                .setOnHideListener(new OnHideAlertListener() {
                    @Override
                    public void onHide() {
                        Toast.makeText(ExampleActivity.this, "Hide Alert", Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void showAlertInfiniteDuration() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableInfiniteDuration(true)
                .show();
    }

    private void showAlertWithProgress() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableProgress(true)
                .show();
    }

//    private void showAlertWithCustomFont() {
//        Alerter.create(ExampleActivity.this)
//                .setTitle("Alert Title")
//                .setTitleAppearance(R.style.AlertTextAppearance_Title)
//                .setTitleTypeface(Typeface.createFromAsset(getAssets(), "Pacifico-Regular.ttf"))
//                .setText("Alert text...")
//                .setTextAppearance(R.style.AlertTextAppearance_Text)
//                .setTextTypeface(Typeface.createFromAsset(getAssets(), "ScopeOne-Regular.ttf"))
//                .show();
//    }

    private void showAlertSwipeToDismissEnabled() {
        Alerter.create(ExampleActivity.this)
                .setTitle("Alert Title")
                .setText("Alert text...")
                .enableSwipeToDismiss()
                .show();
    }
}
