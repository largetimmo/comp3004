package com.comp3004groupx.smartaccount.view;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import android.app.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by devray on 2017-09-16.
 */


public class Statistics extends AppCompatActivity {

    //setting Area element
    LinearLayout settingArea;
    ToggleButton setButton;

    RadioGroup chartInfoGroup;
    RadioButton selectTrans;
    RadioButton selectBalance;

    LinearLayout transArea;
    CheckBox checkExp;
    CheckBox checkInc;

    LinearLayout balArea;
    CheckBox checkProp;
    CheckBox checkLiab;

    CheckBox checkTimeLine;
    static Date startDate;
    static Date endDate;
    static Calendar calendar;
    static String textOnClick;
    static TextView startDateText;
    static TextView endDateText;

    static final String myFormat = "yyyy-MM-dd";
    static final SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.CANADA);

    RadioGroup chartTypeGroup;
    RadioButton selectBar;
    RadioButton selectPie;
    RadioButton selectLine;


    //table Area element
    LinearLayout tableArea;

    PieChart piechart;

    TransactionDAO transDataBase;
    AccountDAO accountDataBase;
    AccountTypeDAO accountTypeDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        this.setTitle("Statistics");

        settingAreaEvent();
        setup();
    }


    // setup default condition
    //----------------------------------------------------------------------------------------------
    private void setup() {
        //setting area expend as default
        setButton = (ToggleButton) findViewById(R.id.setupTableButton);
        setButton.setChecked(true);

        //select transaction as default
        selectTrans = (RadioButton) findViewById(R.id.selectTransaction);
        selectTrans.setChecked(true);

        //select bar chart as default
        selectBar = (RadioButton) findViewById(R.id.selectBarChart);
        selectBar.setChecked(true);

        //default date for timeline option
        calendar = Calendar.getInstance();
        endDate = new Date(calendar.getTime().getTime());
        calendar.set(Calendar.MONTH, Calendar.getInstance().get(Calendar.MONTH) - 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        startDate = new Date(calendar.getTime().getTime());
        //default date text
        startDateText.setText(sdf.format(startDate.getTime()));
        endDateText.setText(sdf.format(endDate.getTime()));

    }

    // setting area event listener
    //----------------------------------------------------------------------------------------------
    private void settingAreaEvent() {
        settingAreaControl();
        ChartOptionEvent();
    }

    //expand and collapse setting area and update table after collapse
    private void settingAreaControl() {
        setButton = (ToggleButton) findViewById(R.id.setupTableButton);
        settingArea = (LinearLayout) findViewById(R.id.tableSettingArea);

        selectTrans = (RadioButton) findViewById(R.id.selectTransaction);
        selectBalance = (RadioButton) findViewById(R.id.selectBalance);
        checkExp = (CheckBox) findViewById(R.id.checkExp);
        checkInc = (CheckBox) findViewById(R.id.checkInc);
        checkProp = (CheckBox) findViewById(R.id.checkProperty);
        checkLiab = (CheckBox) findViewById(R.id.checkLiability);
        selectBar = (RadioButton) findViewById(R.id.selectBarChart);
        selectPie = (RadioButton) findViewById(R.id.selectPieChart);
        selectLine = (RadioButton) findViewById(R.id.selectLineChart);
        checkTimeLine = (CheckBox) findViewById(R.id.checkTimeLine);

        String chaType;
        if (selectBar.isChecked()) {
            chaType = "Bar";
        } else if (selectLine.isChecked()) {
            chaType = "Line";
        } else if (selectPie.isChecked()) {
            chaType = "Pie";
        } else {
            chaType = "";
        }

        setButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //expand
                expandOrCollapse(settingArea, "height", 300, dpToPx(600));
            } else {
                //update chart
                updateChart(selectTrans.isChecked(), checkExp.isChecked(), checkInc.isChecked(),
                        checkProp.isChecked(), checkLiab.isChecked(), chaType, checkTimeLine.isChecked());
                //collapse
                expandOrCollapse(settingArea, "height", 300, dpToPx(40));

            }
        });
    }

    //chart option onchange event
    private void ChartOptionEvent() {
        chartInfoGroup = (RadioGroup) findViewById(R.id.chartInfoGroup);
        transArea = (LinearLayout) findViewById(R.id.checkTransLayout);
        balArea = (LinearLayout) findViewById(R.id.checkBalanceLayout);

        checkTimeLine = (CheckBox) findViewById(R.id.checkTimeLine);
        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);

        chartTypeGroup = (RadioGroup) findViewById(R.id.chartTypeGroup);
        selectBar = (RadioButton) findViewById(R.id.selectBarChart);
        selectPie = (RadioButton) findViewById(R.id.selectPieChart);
        selectLine = (RadioButton) findViewById(R.id.selectLineChart);

        //area control of two chart
        chartInfoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // expend and collapse area
            if (checkedId == R.id.selectTransaction) {
                expandOrCollapse(balArea, "width", 300, dpToPx(0));
                expandOrCollapse(transArea, "width", 300, dpToPx(250));
            } else if (checkedId == R.id.selectBalance) {
                expandOrCollapse(transArea, "width", 300, dpToPx(0));
                expandOrCollapse(balArea, "width", 300, dpToPx(250));
            }

        });

        //pie chart disabled for timeline
        checkTimeLine.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                selectLine.setChecked(true);
                selectPie.setClickable(false);
            } else {
                selectPie.setClickable(true);
            }

        });

        //click on date text
        startDateText.setOnClickListener((v) -> {
            textOnClick = "start";
            DialogFragment datepickerDialog = new DatePickerFragment();
            datepickerDialog.show(getFragmentManager(), "datePicker");
        });
        endDateText.setOnClickListener((v) -> {
            textOnClick = "end";
            DialogFragment datepickerDialog = new DatePickerFragment();
            datepickerDialog.show(getFragmentManager(), "datePicker");
        });

    }


    // chart area event listener
    //----------------------------------------------------------------------------------------------

    //update chart
    private void updateChart(boolean isTrans,
                             boolean checkExp, boolean checkInc, boolean checkProp, boolean checkLiab,
                             String chartType, boolean checkTime) {
        tableArea = (LinearLayout) findViewById(R.id.tableArea);


        transDataBase = new TransactionDAO(getApplicationContext());
        accountDataBase = new AccountDAO(getApplicationContext());
        accountTypeDataBase = new AccountTypeDAO((getApplicationContext()));


        if (chartType.equals("Bar") && checkExp && checkInc) {

        } else if (chartType.equals("Bar") && checkExp && !checkInc) {

        } else if (chartType.equals("Bar") && !checkExp && checkInc) {

        } else if (chartType.equals("Bar") && !checkExp && !checkInc) {

        } else {

        }


        if (tableArea.getChildCount() > 0) {
            tableArea.removeAllViews();
        }
        //TODO: add chart

    }

    //make a new chart
    private void makeChart() {


        tableArea = (LinearLayout) findViewById(R.id.tableArea);
        piechart = (PieChart) findViewById(R.id.piechart);


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
        piechart.setData(data);
        piechart.invalidate(); // refresh


    }


    // tools for help
    //----------------------------------------------------------------------------------------------
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    // type must be "height" or "width"
    public static void expandOrCollapse(final View v, final String type, int duration, int targetLength) {
        int prevLength;

        if (type.equals("height")) {
            prevLength = v.getHeight();

        } else if (type.equals("width")) {
            prevLength = v.getWidth();
        } else {
            prevLength = 0;
        }

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevLength, targetLength);
        valueAnimator.addUpdateListener((animation) -> {
            if (type.equals("height")) {
                v.getLayoutParams().height = (int) animation.getAnimatedValue();
            } else if (type.equals("width")) {
                v.getLayoutParams().width = (int) animation.getAnimatedValue();
            }
            v.requestLayout();

        });
        valueAnimator.setInterpolator(new DecelerateInterpolator());
        valueAnimator.setDuration(duration);
        valueAnimator.start();
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            calendar = Calendar.getInstance();
            int year;
            int month;
            int day;
            DatePickerDialog picker;

            if (textOnClick.equals("start")) {
                calendar.setTime(startDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(getActivity(), this, year, month, day);
                picker.getDatePicker().setMaxDate(endDate.getTime());

            } else if (textOnClick.equals("end")) {
                calendar.setTime(endDate);
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);
                picker = new DatePickerDialog(getActivity(), this, year, month, day);
                picker.getDatePicker().setMinDate(startDate.getTime());
                picker.getDatePicker().setMaxDate(Calendar.getInstance().getTime().getTime());
            } else {
                picker = new DatePickerDialog(getActivity(), this, 0, 0, 0);
            }
            return picker;
        }

        //textOnClick must be "start" or "end"
        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            calendar = Calendar.getInstance();

            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);

            if (textOnClick.equals("start")) {
                startDate.setTime(calendar.getTime().getTime());
                startDateText.setText(sdf.format(calendar.getTime()));
            } else if (textOnClick.equals("end")) {
                endDate.setTime(calendar.getTime().getTime());
                endDateText.setText(sdf.format(calendar.getTime()));
            }
        }
    }

}

