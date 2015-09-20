package com.example.Test;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class MainActivity extends Activity  {
    RadioGroup rg;
    public int quesAmount;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }
        setContentView(R.layout.main_layout);

        rg = (RadioGroup) findViewById(R.id.rg);
    }

    public void newActivity (View view) {
        if (rg.getCheckedRadioButtonId() != -1) {
            RadioButton rb = (RadioButton) findViewById(rg.getCheckedRadioButtonId());
            quesAmount = Integer.parseInt(rb.getText().toString());
            switch (view.getId()) {
                case R.id.button:
                    Intent intent = new Intent(this, NewActivity.class);
                    intent.putExtra("quesAmount", quesAmount);
                    startActivity(intent);
                    break;
                default:
                    break;
            }
        }
    }
}
