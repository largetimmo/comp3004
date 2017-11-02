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
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by devray on 2017-09-16.
 */


public class Statistics extends AppCompatActivity {


    ToggleButton setButton;
    LinearLayout settingArea;
    LinearLayout tableArea;

    PieChart piechart;

    TransactionDAO transDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        this.setTitle("Statistics");

        setButton = (ToggleButton) findViewById(R.id.setupTableButton);
        settingArea = (LinearLayout) findViewById(R.id.tableSettingArea);
        tableArea = (LinearLayout) findViewById(R.id.tableArea);
        piechart = (PieChart) findViewById(R.id.piechart);

        transDataBase = new TransactionDAO(getApplicationContext());

        setup(settingArea, setButton);

        maketable(piechart, transDataBase);
    }

    private void maketable(PieChart pieChart, TransactionDAO transDataBase) {

        ArrayList<PieEntry> pieChartEntries = new ArrayList<>();


        pieChartEntries.add(new PieEntry(5.0f, "Saving"));
        pieChartEntries.add(new PieEntry(26.7f, "Credit Card"));
        pieChartEntries.add(new PieEntry(17.0f, "Cash"));
        pieChartEntries.add(new PieEntry(30.8f, "Chequing"));

        PieDataSet dataSet = new PieDataSet(pieChartEntries, "Transaction");

        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);


        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        dataSet.setColors(colors);


        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh


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

