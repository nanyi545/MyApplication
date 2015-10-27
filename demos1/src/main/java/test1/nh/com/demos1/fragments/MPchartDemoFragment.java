package test1.nh.com.demos1.fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

import test1.nh.com.demos1.R;
import test1.nh.com.demos1.activities.DrawerActivity;

/**
 * Created by Administrator on 15-9-30.
 */
public class MPchartDemoFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section4";


    private Context mContext;

    private LineChart mChart;
    private LineData mLdata;


    private LineChart mChart2;
    private LineData mLdata2;


    //empty constructor
    public MPchartDemoFragment(){
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MPchartDemoFragment newInstance(int sectionNumber) {
        MPchartDemoFragment fragment = new MPchartDemoFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mpchart_drawer, container, false);

        // plot 1
        mChart = (LineChart) rootView.findViewById(R.id.chart1);
        initChart(mChart);
        mLdata=initData1();
        plotLine1(mChart,mLdata);

        // plot 2
        mChart2 = (LineChart) rootView.findViewById(R.id.chart2);
        initChart(mChart2);
        mLdata2=initData2();
        plotLine1(mChart2, mLdata2);

        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //设置activity label
        mContext=activity;

        try {
            ((DrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        } catch (Exception e) {
            e.printStackTrace();
//            Log.i("AAA", "MPCHARTfragment called not by DrawerActivity");
        }

    }



    private void initChart(LineChart mChart){
        mChart.setDrawBorders(true);//?? drawing the chart borders (lines surrounding the chart).

        // set up X-axis
        XAxis xAxis = mChart.getXAxis();
        xAxis.setTextSize(12f);
        xAxis.setTextColor(Color.RED);
        xAxis.setDrawGridLines(false);// grid disappear
        xAxis.setDrawAxisLine(false);
        xAxis.setSpaceBetweenLabels(1);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); //x-axis at bottom

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setDrawTopYLabelEntry(false);
        rightAxis.setEnabled(false);// right axis disappear
        rightAxis.setDrawGridLines(false);// grid disappear
        rightAxis.setDrawAxisLine(false); //  ????

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);// grid disappear
    }

    private LineData initData1(){
        LineData mLdata;
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

        Entry c1e1 = new Entry(100.000f, 0); // 0 == quarter 1
        valsComp1.add(c1e1);
        Entry c1e2 = new Entry(50.000f, 1); // 1 == quarter 2 ...
        valsComp1.add(c1e2);
        Entry c1e3 = new Entry(120.000f, 2); // 2 == quarter 3
        valsComp1.add(c1e3);
        Entry c1e4 = new Entry(150.000f, 3); // 3 == quarter 4 ...
        valsComp1.add(c1e4);

        LineDataSet setComp1 = new LineDataSet(valsComp1, "Company 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);

        setComp1.setDrawCubic(true);//smooth lineChart

        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("1.Q");
        xVals.add("");
        xVals.add("3.Q");
        xVals.add("4.Q");

        LineData data = new LineData(xVals, dataSets);
        mLdata=data;
        return mLdata;
    }

    private LineData initData2(){
        LineData mLdata;

        // arraylist of y-x entry
        ArrayList<Entry> valsComp1 = new ArrayList<Entry>();

        // arraylist of x-label
        ArrayList<String> xVals = new ArrayList<String>();

        for(int i=0;i<750;i++){
            Entry c1e1 = new Entry((float)Math.sin(i/10f), i); //  y(float)-x(int) pairs
            valsComp1.add(c1e1);//  add entry ( y(float)-x(int) pair )
            xVals.add(i/10f+"");  //string as labels
        }

        // create line-data-set
        LineDataSet setComp1 = new LineDataSet(valsComp1, "signal 1");
        setComp1.setAxisDependency(YAxis.AxisDependency.LEFT);
//        setComp1.setDrawCubic(true);//smooth lineChart
        setComp1.setDrawCircles(false);// no circles

        // a plot can have multiple lines, by using line-data-set array
        ArrayList<LineDataSet> dataSets = new ArrayList<LineDataSet>();
        dataSets.add(setComp1);

        // creat line-data with x-labels + line-data-set array
        LineData data = new LineData(xVals, dataSets);
        mLdata=data;
        return mLdata;
    }



    private void plotLine1(LineChart mChart,LineData mLdata){
        mChart.setData(mLdata);

        Legend l = mChart.getLegend();  //set legend position
        l.setPosition(Legend.LegendPosition.ABOVE_CHART_RIGHT);

        mChart.invalidate(); // refresh
    }


}
