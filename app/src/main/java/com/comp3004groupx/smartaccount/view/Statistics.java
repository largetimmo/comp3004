package com.comp3004groupx.smartaccount.view;

import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.view.animation.DecelerateInterpolator;

import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.comp3004groupx.smartaccount.R;

/**
 * Created by devray on 2017-09-16.
 */


public class Statistics extends AppCompatActivity {


    ToggleButton setButton;
    LinearLayout settingArea;
    LinearLayout tableArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        this.setTitle("Statistics");

        setButton = (ToggleButton) findViewById(R.id.setupTableButton);
        settingArea = (LinearLayout) findViewById(R.id.tableSettingArea);
        tableArea = (LinearLayout) findViewById(R.id.tableArea);


        setup(settingArea, setButton);


    }

    private void setup(final LinearLayout settingArea, ToggleButton setButton) {

        setButton.setChecked(false);

        settingAreaControl(settingArea, setButton);

    }


    private void settingAreaControl(final LinearLayout settingArea, ToggleButton setButton) {

        setButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    expand(settingArea, 300, dpToPx(180));
                } else {
                    collapse(settingArea, 300, dpToPx(40));
                }
            }
        });
    }

    public static void expand(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static void collapse(final View v, int duration, int targetHeight) {
        int prevHeight = v.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevHeight, targetHeight);
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
                v.requestLayout();
            }
        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }


    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }
}

