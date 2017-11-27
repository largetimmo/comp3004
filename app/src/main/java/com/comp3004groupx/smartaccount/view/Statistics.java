package com.comp3004groupx.smartaccount.view;

import android.animation.ValueAnimator;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.res.Resources;
import android.graphics.Color;
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

import com.comp3004groupx.smartaccount.Core.Account;
import com.comp3004groupx.smartaccount.Core.Transaction;
import com.comp3004groupx.smartaccount.R;
import com.comp3004groupx.smartaccount.module.DAO.AccountDAO;
import com.comp3004groupx.smartaccount.module.DAO.AccountTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.PurchaseTypeDAO;
import com.comp3004groupx.smartaccount.module.DAO.TransactionDAO;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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
    LinearLayout chartArea;
    ViewGroup.LayoutParams chartSizeParams;

//    LinearLayout pieChartArea;
//    LinearLayout barChartArea;
//    LinearLayout lineChartArea;
//    ViewGroup.LayoutParams pieAreaparams;
//    ViewGroup.LayoutParams barAreaparams;
//    ViewGroup.LayoutParams lineAreaparams;
//
//    PieChart piechart;
//    BarChart barchart;
//    LineChart linechart;

    //DataBase
    TransactionDAO transDataBase;
    PurchaseTypeDAO purchaseTypeDataBase;
    AccountDAO accountDataBase;
    AccountTypeDAO accountTypeDataBase;

    int screenWidthdp = pxToDp(getScreenWidth());
    int screenHeightdp = pxToDp(getScreenHeight());

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

//        pieChartArea = (LinearLayout) findViewById(R.id.pieChartArea);
//        pieAreaparams = pieChartArea.getLayoutParams();
//        barChartArea = (LinearLayout) findViewById(R.id.barChartArea);
//        barAreaparams = barChartArea.getLayoutParams();
//        lineChartArea = (LinearLayout) findViewById(R.id.lineChartArea);
//        lineAreaparams = lineChartArea.getLayoutParams();
//
//        piechart = (PieChart) findViewById(R.id.pieChart);
//        barchart = (BarChart) findViewById(R.id.barChart);
//        linechart = (LineChart) findViewById(R.id.lineChart);
        chartArea = (LinearLayout) findViewById(R.id.chartArea);

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

    // setting area event
    //----------------------------------------------------------------------------------------------

    //chart option onchange event
    private void ChartOptionEvent() {
        // AREA CONTROL expand and collapse setting area and update table after collapse
        setButton.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                //expand
                expandOrCollapse(settingArea, "height", 300, screenHeightdp);
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

        //only one select for pie & bar chart
        checkExp.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (selectPie.isChecked() || selectBar.isChecked()) {
                if (isChecked) {
                    checkInc.setChecked(false);
                } else {
                    checkInc.setChecked(true);
                }
            }
        });
        checkInc.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (selectPie.isChecked() || selectBar.isChecked()) {
                if (isChecked) {
                    checkExp.setChecked(false);
                } else {
                    checkExp.setChecked(true);
                }
            }
        });
        checkLiab.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (selectPie.isChecked() || selectBar.isChecked()) {
                if (isChecked) {
                    checkProp.setChecked(false);
                } else {
                    checkProp.setChecked(true);
                }
            }
        });
        checkProp.setOnCheckedChangeListener((ButtonView, isChecked) -> {
            if (selectPie.isChecked() || selectBar.isChecked()) {
                if (isChecked) {
                    checkLiab.setChecked(false);
                } else {
                    checkLiab.setChecked(true);
                }
            }
        });

        //change CHART TYPE select chart
        chartTypeGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.selectPieChart) {
                checkInc.setChecked(false);
                checkExp.setChecked(true);
                checkLiab.setChecked(false);
                checkProp.setChecked(true);
                chaType = "Pie";
            }
            if (checkedId == R.id.selectBarChart) {
                checkInc.setChecked(false);
                checkExp.setChecked(true);
                checkLiab.setChecked(false);
                checkProp.setChecked(true);
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

    // chart area event
    //----------------------------------------------------------------------------------------------

    //update chart
    private void updateChart() {
        //make new chart
        if (chaType.equals("Bar")) {
            makeBarChart();
        } else if (chaType.equals("Pie")) {
            makePieChart();
        } else if (chaType.equals("Line")) {
            makeLineChart();
        }
    }

    //
//    private void chartAreaControl(String type, int heightdp, int widthdp) {
//        int heightpx = dpToPx(heightdp);
//        int widthpx = dpToPx(widthdp);
//        //change back to default 0,0      set 10,10 for test
//        pieAreaparams.height = 0;
//        pieAreaparams.width = 0;
//        lineAreaparams.height = 0;
//        lineAreaparams.width = 0;
//        barAreaparams.height = 0;
//        barAreaparams.width = 0;
//        if (type.equals("bar")) {
//            barAreaparams.height = heightpx;
//            barAreaparams.width = widthpx;
//        }
//        if (type.equals("pie")) {
//            pieAreaparams.height = heightpx;
//            pieAreaparams.width = widthpx;
//        }
//        if (type.equals("line")) {
//            lineAreaparams.height = heightpx;
//            lineAreaparams.width = widthpx;
//        }
//        pieChartArea.setLayoutParams(pieAreaparams);
//        lineChartArea.setLayoutParams(lineAreaparams);
//        barChartArea.setLayoutParams(barAreaparams);
//
//    }

    private void makePieChart() {

        //hide& show
//        chartAreaControl("pie", screenHeightdp - 120, screenWidthdp);

        //make chart
        //clear chart area
        chartArea.removeAllViews();
        //make new chart
        PieChart piechart = new PieChart(getApplicationContext());
        ArrayList<PieEntry> pieChartEntries = new ArrayList<>();
        PieDataSet dataSet;
        //check data setup
        if (selectBalance.isChecked()) {
            ArrayList<Account> accountList = accountDataBase.getAllAccount();
            if (checkLiab.isChecked()) {
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).getBalance() < 0) {
                        pieChartEntries.add(new PieEntry(((float) Math.abs(accountList.get(i).getBalance())), accountList.get(i).getName()));
                    }
                }
            } else if (checkProp.isChecked()) {
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).getBalance() >= 0) {
                        pieChartEntries.add(new PieEntry(((float) Math.abs(accountList.get(i).getBalance())), accountList.get(i).getName()));
                    }
                }
            }
        } else if (selectTrans.isChecked()) {
            ArrayList<Transaction> transList = transDataBase.getAllTransaction();
            ArrayList<String> typeList;
            if (checkInc.isChecked()) {
                typeList = purchaseTypeDataBase.getAllIncomeType();
            } else if (checkExp.isChecked()) {
                typeList = purchaseTypeDataBase.getALLExpenseType();
            } else {
                typeList = new ArrayList<>();
            }
            for (int i = 0; i < typeList.size(); i++) {
                int amount = 0;
                for (int j = 0; j < transList.size(); j++) {
                    if (transList.get(j).getType().equals(typeList.get(i))) {
                        amount += transList.get(j).getAmount();
                    }
                }
                pieChartEntries.add(new PieEntry(((float) Math.abs(amount)), typeList.get(i)));
            }
        }

        //dataSet label setup
        if (checkExp.isChecked()) {
            dataSet = new PieDataSet(pieChartEntries, "Expense Transaction");
        } else if (checkInc.isChecked()) {
            dataSet = new PieDataSet(pieChartEntries, "Income Transaction");
        } else if (checkProp.isChecked()) {
            dataSet = new PieDataSet(pieChartEntries, "Property");
        } else if (checkLiab.isChecked()) {
            dataSet = new PieDataSet(pieChartEntries, "Liability");
        } else {
            dataSet = new PieDataSet(pieChartEntries, "No Data");
        }

        //click effect
        dataSet.setSliceSpace(7f);
        dataSet.setSelectionShift(7f);
        //set color
        ArrayList<Integer> colors = new ArrayList<>();
        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        //set label
        piechart.setEntryLabelColor(Color.BLACK);
        piechart.setEntryLabelTextSize(12f);

        piechart.setDrawHoleEnabled(false);
        piechart.setData(data);
        piechart.invalidate(); // refresh

        chartArea.addView(piechart);

        //update size
        chartSizeParams = piechart.getLayoutParams();
        chartSizeParams.width = dpToPx(screenWidthdp);
        chartSizeParams.height = dpToPx(screenHeightdp - 150);
        piechart.setLayoutParams(chartSizeParams);

    }

    private void makeBarChart() {
        //hide& show
//        chartAreaControl("bar", screenHeightdp - 80, screenWidthdp);


        //make chart
        //clear chart area
        chartArea.removeAllViews();
        //make new chart
        BarChart barchart = new BarChart(getApplicationContext());
        barchart.setMaxVisibleValueCount(60);
        barchart.setPinchZoom(false);
        barchart.setDrawGridBackground(true);

        // X axis
        XAxis xAxis = barchart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        //y axis
        YAxis yAxis = barchart.getAxisLeft();
        yAxis.setTextSize(12f);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(15f);
        yAxis.setAxisMinimum(0f);
        barchart.getAxisRight().setEnabled(false);//left axis only

        //label list
        ArrayList<String> xLabel = new ArrayList<>();

        // add data
        List<BarEntry> barEntries = new ArrayList<>();
        BarDataSet set;

        if (selectTrans.isChecked()) {
            ArrayList<Transaction> transList = transDataBase.getAllTransaction();
            ArrayList<String> typeList;
            if (checkInc.isChecked()) {
                typeList = purchaseTypeDataBase.getAllIncomeType();
            } else if (checkExp.isChecked()) {
                typeList = purchaseTypeDataBase.getALLExpenseType();
            } else {
                typeList = new ArrayList<>();
            }
            for (int i = 0; i < typeList.size(); i++) {
                int amount = 0;
                for (int j = 0; j < transList.size(); j++) {
                    if (transList.get(j).getType().equals(typeList.get(i))) {
                        amount += transList.get(j).getAmount();
                    }
                }
                barEntries.add(new BarEntry((float) i, (float) Math.abs(amount)));
                xLabel.add(typeList.get(i));
            }
        } else if (selectBalance.isChecked()) {
            ArrayList<Account> accountList = accountDataBase.getAllAccount();
            if (checkLiab.isChecked()) {
                int count = 0;
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).getBalance() < 0) {
                        barEntries.add(new BarEntry((float) count, (float) Math.abs(accountList.get(i).getBalance())));
                        xLabel.add(accountList.get(i).getName());
                        count++;
                    }
                }
            } else if (checkProp.isChecked()) {
                int count = 0;
                for (int i = 0; i < accountList.size(); i++) {
                    if (accountList.get(i).getBalance() >= 0) {
                        barEntries.add(new BarEntry((float) count, (float) Math.abs(accountList.get(i).getBalance())));
                        xLabel.add(accountList.get(i).getName());
                        count++;
                    }
                }
            }
        } else {

        }

        //dataSet label setup
        if (checkExp.isChecked()) {
            set = new BarDataSet(barEntries, "Expense Transaction");

        } else if (checkInc.isChecked()) {
            set = new BarDataSet(barEntries, "Income Transaction");

        } else if (checkProp.isChecked()) {
            set = new BarDataSet(barEntries, "Property ");

        } else if (checkLiab.isChecked()) {
            set = new BarDataSet(barEntries, "Liability");

        } else {
            set = new BarDataSet(barEntries, "No Data");
        }

        xAxis.setValueFormatter((value, axis) -> xLabel.get((int) value));
        xAxis.setGranularity(1f);
        xAxis.setGranularityEnabled(true);

        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        barchart.setData(data);
        barchart.setFitBars(true); // make the x-axis fit exactly all bars
        barchart.invalidate(); // refresh

        chartArea.addView(barchart);

        //update size
        chartSizeParams = barchart.getLayoutParams();
        chartSizeParams.width = dpToPx(screenWidthdp);
        chartSizeParams.height = dpToPx(screenHeightdp - 150);
        barchart.setLayoutParams(chartSizeParams);

    }

    private void makeLineChart() {

        //hide& show
//        chartAreaControl("line", screenHeightdp - 80, screenWidthdp);

        //make chart
        //clear chart area
        chartArea.removeAllViews();
        //make new chart
        LineChart linechart = new LineChart(getApplicationContext());
        linechart.setMaxVisibleValueCount(60);
        linechart.setPinchZoom(false);
        linechart.setDrawGridBackground(true);

        // X axis
        XAxis xAxis = linechart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.BLACK);
        xAxis.setDrawAxisLine(true);
        xAxis.setDrawGridLines(false);

        //y axis
        YAxis yAxis = linechart.getAxisLeft();
        yAxis.setTextSize(12f);
        yAxis.setTextColor(Color.BLACK);
        yAxis.setDrawAxisLine(true);
        yAxis.setDrawGridLines(false);
        yAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        yAxis.setSpaceTop(15f);
        yAxis.setAxisMinimum(0f);
        linechart.getAxisRight().setEnabled(false);//left axis only

        //add data
        List<Entry> expEntries = new ArrayList<>();
        List<Entry> incEntries = new ArrayList<>();
        LineDataSet expSet;
        LineDataSet incSet;

        ArrayList<String> xLabel = new ArrayList<>();


        int startYear = 0;
        int startMonth = 0;
        int endYear = 0;
        int endMonth = 0;

//        "yyyy-MM-dd"
        String start = sdf.format(startDate.getTime());
        startYear = Integer.parseInt(start.substring(0, 4));
        startMonth = Integer.parseInt(start.substring(5, 7));
        String end = sdf.format(startDate.getTime());
        endYear = Integer.parseInt(end.substring(0, 3));
        endMonth = Integer.parseInt(end.substring(0, 3));

        int count = 0;
        while (startYear == endYear && startMonth == endMonth) {

            ArrayList<Transaction> transList = transDataBase.getAllTransThisMonth(startYear, startMonth);
            ArrayList<String> expTypeList = purchaseTypeDataBase.getALLExpenseType();

            int expAmount = 0;
            int incAmount = 0;
            for (int i = 0; i < transList.size(); i++) {
                boolean isExp = false;
                for (int j = 0; j < expTypeList.size(); j++) {
                    if (transList.get(i).getType().equals(expTypeList.get(j))) {
                        isExp = true;
                        break;
                    }
                }
                if (isExp) {
                    expAmount += transList.get(i).getAmount();
                } else {
                    incAmount += transList.get(i).getAmount();
                }

            }


            expEntries.add(new Entry((float) count, (float) Math.abs(expAmount)));
            incEntries.add(new Entry((float) count, (float) Math.abs(incAmount)));

            xLabel.add(startYear+"-"+startMonth);


            count++;
            startMonth++;
            if (startMonth == 13) {
                startMonth = 1;
                startYear++;
            }
        }


        //dataSet label setup
        List<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();

        if (checkExp.isChecked()) {
            expSet = new LineDataSet(expEntries, "Expense Transaction");
            dataSets.add(expSet);

        } else if (checkInc.isChecked()) {
            incSet = new LineDataSet(incEntries, "Income Transaction");
            dataSets.add(incSet);

        }  else {
        }


        xAxis.setValueFormatter((value, axis) -> xLabel.get((int) value));


        LineData data = new LineData(dataSets);

        linechart.setData(data);
        linechart.invalidate(); // refresh

        chartArea.addView(linechart);


        //update size
        chartSizeParams = linechart.getLayoutParams();
        chartSizeParams.width = dpToPx(screenWidthdp);
        chartSizeParams.height = dpToPx(screenHeightdp - 150);
        linechart.setLayoutParams(chartSizeParams);
    }


    // tools for help
    //----------------------------------------------------------------------------------------------

    public static int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public static int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    private static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    // type must be "height" or "width"
    private static void expandOrCollapse(final View v, final String type, int duration, int targetLengthdp) {
        int prevLength;

        if (type.equals("height")) {
            prevLength = v.getHeight();

        } else if (type.equals("width")) {
            prevLength = v.getWidth();
        } else {
            prevLength = 0;
        }

        //dp to px
        targetLengthdp = dpToPx(targetLengthdp);

        v.setVisibility(View.VISIBLE);
        ValueAnimator valueAnimator = ValueAnimator.ofInt(prevLength, targetLengthdp);
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

