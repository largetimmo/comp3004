package com.comp3004groupx.smartaccount.view;

import android.animation.ValueAnimator;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
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
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
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
    String chaType;


    //chart Area element
    LinearLayout pieChartArea;
    LinearLayout barChartArea;
    LinearLayout lineChartArea;
    ViewGroup.LayoutParams pieAreaparams;
    ViewGroup.LayoutParams barAreaparams;
    ViewGroup.LayoutParams lineAreaparams;

    PieChart piechart;
    BarChart barchart;
    LineChart linechart;

    //DataBase
    TransactionDAO transDataBase;
    PurchaseTypeDAO purchaseTypeDataBase;
    AccountDAO accountDataBase;
    AccountTypeDAO accountTypeDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        this.setTitle("Statistics");
        //init database
        transDataBase = new TransactionDAO(getApplicationContext());
        purchaseTypeDataBase = new PurchaseTypeDAO(getApplicationContext());
        accountDataBase = new AccountDAO(getApplicationContext());
        accountTypeDataBase = new AccountTypeDAO((getApplicationContext()));

        //link element
        settingArea = (LinearLayout) findViewById(R.id.tableSettingArea);
        setButton = (ToggleButton) findViewById(R.id.setupTableButton);

        chartInfoGroup = (RadioGroup) findViewById(R.id.chartInfoGroup);
        selectTrans = (RadioButton) findViewById(R.id.selectTransaction);
        selectBalance = (RadioButton) findViewById(R.id.selectBalance);

        transArea = (LinearLayout) findViewById(R.id.checkTransLayout);
        checkExp = (CheckBox) findViewById(R.id.checkExp);
        checkInc = (CheckBox) findViewById(R.id.checkInc);

        balArea = (LinearLayout) findViewById(R.id.checkBalanceLayout);
        checkProp = (CheckBox) findViewById(R.id.checkProperty);
        checkLiab = (CheckBox) findViewById(R.id.checkLiability);

        chartTypeGroup = (RadioGroup) findViewById(R.id.chartTypeGroup);
        selectBar = (RadioButton) findViewById(R.id.selectBarChart);
        selectPie = (RadioButton) findViewById(R.id.selectPieChart);
        selectLine = (RadioButton) findViewById(R.id.selectLineChart);

        checkTimeLine = (CheckBox) findViewById(R.id.checkTimeLine);
        startDateText = (TextView) findViewById(R.id.startDateText);
        endDateText = (TextView) findViewById(R.id.endDateText);

        pieChartArea = (LinearLayout) findViewById(R.id.pieChartArea);
        pieAreaparams = pieChartArea.getLayoutParams();
        barChartArea = (LinearLayout) findViewById(R.id.barChartArea);
        barAreaparams = barChartArea.getLayoutParams();
        lineChartArea = (LinearLayout) findViewById(R.id.lineChartArea);
        lineAreaparams = lineChartArea.getLayoutParams();

        piechart = (PieChart) findViewById(R.id.pieChart);
        barchart = (BarChart) findViewById(R.id.barChart);
        linechart = (LineChart) findViewById(R.id.lineChart);

        //setup option event
        ChartOptionEvent();

        //default environment init
        setup();
    }


    // setup default condition
    //----------------------------------------------------------------------------------------------
    private void setup() {
        //setting area expend as default
        setButton.setChecked(true);

        //select transaction as default
        selectTrans.setChecked(true);

        //select bar chart as default
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

    //chart option onchange event
    private void ChartOptionEvent() {
        // AREA CONTROL expand and collapse setting area and update table after collapse
        setButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //expand
                expandOrCollapse(settingArea, "height", 300, 600);
            } else {
                //update chart
                updateChart();
                //collapse
                expandOrCollapse(settingArea, "height", 300, 40);
            }
        });

        //AREA CONTROL two chart info check option
        chartInfoGroup.setOnCheckedChangeListener((group, checkedId) -> {
            // expend and collapse area
            if (checkedId == R.id.selectTransaction) {
                expandOrCollapse(balArea, "width", 300, 0);
                expandOrCollapse(transArea, "width", 300, 250);
            } else if (checkedId == R.id.selectBalance) {
                expandOrCollapse(transArea, "width", 300, 0);
                expandOrCollapse(balArea, "width", 300, 250);
            }

        });

        //both check not available for pie chart
        checkExp.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                if (selectPie.isChecked()) {
                    checkInc.setChecked(false);
                }
            }
        });
        checkInc.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                if (selectPie.isChecked()) {
                    checkExp.setChecked(false);
                }
            }
        });
        checkLiab.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                if (selectPie.isChecked()) {
                    checkProp.setChecked(false);
                }
            }
        });
        checkProp.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                if (selectPie.isChecked()) {
                    checkLiab.setChecked(false);
                }
            }
        });

        //change CHART TYPE select chart
        chartTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.selectPieChart) {
                //both check double check
                if (checkExp.isChecked() && checkInc.isChecked()) {
                    checkInc.setChecked(false);
                }
                if (checkLiab.isChecked() && checkProp.isChecked()) {
                    checkLiab.setChecked(false);
                }
                chaType = "Pie";
            }
            if (checkedId == R.id.selectBarChart) {
                chaType = "Bar";
            }
            if (checkedId == R.id.selectLineChart) {
                chaType = "Line";
            }
        });

        //only line chart & transaction work for timeline
        checkTimeLine.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (isChecked) {
                selectLine.setChecked(true);
                selectPie.setClickable(false);
                selectBar.setClickable(false);

                selectTrans.setChecked(true);
                selectBalance.setClickable(false);
            } else {
                selectPie.setClickable(true);
                selectBar.setClickable(true);

                selectBalance.setClickable(true);
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
    private void updateChart() {
        if (chaType.equals("Bar")) {
            makeBarChart();
        } else if (chaType.equals("Pie")) {
            makePieChart();
        } else if (chaType.equals("Line")) {
            makeLineChart();
        }
    }

    private void makePieChart() {

        //hide& show
        chartAreaControl("pie", 490, 200);


        //make chart
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

    private void makeBarChart() {
        chartAreaControl("bar", 490, 385);

        //hide& show


    }

    private void makeLineChart() {
        chartAreaControl("line", 490, 385);

        //hide& show


    }


    private void chartAreaControl(String type, int height, int width) {
        height = dpToPx(height);
        width = dpToPx(width);
        //TODO: change back to default 0,0      set 10,10 for test
        pieAreaparams.height = 0;
        pieAreaparams.width = 0;
        lineAreaparams.height = 0;
        lineAreaparams.width = 0;
        barAreaparams.height = 0;
        barAreaparams.width = 0;
        if (type.equals("bar")) {
            barAreaparams.height = height;
            barAreaparams.width = width;
        }
        if (type.equals("pie")) {
            pieAreaparams.height = height;
            pieAreaparams.width = width;
        }
        if (type.equals("line")) {
            lineAreaparams.height = height;
            lineAreaparams.width = width;
        }
        pieChartArea.setLayoutParams(pieAreaparams);
        lineChartArea.setLayoutParams(lineAreaparams);
        barChartArea.setLayoutParams(barAreaparams);

    }


    // tools for help
    //----------------------------------------------------------------------------------------------
    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);

    }

    private static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    // type must be "height" or "width"
    private static void expandOrCollapse(final View v, final String type, int duration, int targetLength) {
        int prevLength;

        if (type.equals("height")) {
            prevLength = v.getHeight();

        } else if (type.equals("width")) {
            prevLength = v.getWidth();
        } else {
            prevLength = 0;
        }

        //dp to px
        targetLength = dpToPx(targetLength);

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

