package test1.nh.com.demos1.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import test1.nh.com.demos1.R;
import test1.nh.com.demos1.activities.DrawerActivity;

/**
 * Created by Administrator on 15-9-30.
 */
public class MultiThreadFrag extends Fragment {

    private Context mContext;
    TextView tv1,tv2,tv3,tv4;
    Button brun;

    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //empty constructor
    public MultiThreadFrag(){
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static MultiThreadFrag newInstance(int sectionNumber) {
        MultiThreadFrag fragment = new MultiThreadFrag();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // get section id from intent
        // getArg--return the Bundle
        int section_number=getArguments().getInt(ARG_SECTION_NUMBER);

        View rootView = inflater.inflate(R.layout.fragment_multithread_drawer, container, false);
        tv1= (TextView) rootView.findViewById(R.id.thread_tv1);
        tv2= (TextView) rootView.findViewById(R.id.thread_tv2);
        tv3= (TextView) rootView.findViewById(R.id.thread_tv3);
        tv4= (TextView) rootView.findViewById(R.id.thread_tv4);

        brun = (Button) rootView.findViewById(R.id.thread_B1);
        brun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timerOn= new Timer();  // each timer starts a new thread---
                timerOff= new Timer();
                timerOn.schedule(new LightON(tv1), 5);  //
                timerOff.schedule(new LightOFF(tv1), 3 * 1000);
            }
        });


        return rootView;
    }



    Timer timerOn;
    Timer timerOff;

    class LightOFF extends TimerTask{
        private LightOFF(TextView tv){
            this.tv=tv;
        }
        TextView tv;
        @Override
        public void run() {
            Log.i("AAA","off--"+Thread.currentThread().getName());
            tv.post(new Runnable() {
                @Override
                public void run() {
                    tv.setBackgroundColor(getResources().getColor(R.color.Teal300));
                }
            });
            timerOff.cancel();  // current timer thread is finished
        }
    }

    class LightON extends TimerTask{
        private LightON(TextView tv){
            this.tv=tv;
        }
        TextView tv;
        @Override
        public void run() {
            Log.i("AAA","on--"+Thread.currentThread().getName());
            tv.post(new Runnable(){
                @Override
                public void run() {
                    tv.setBackgroundColor(getResources().getColor(R.color.Red400));
                }
            });
            timerOn.cancel(); // current timer thread is finished
        }
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {    //  called by DrawerActivity
            ((DrawerActivity) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        } catch (Exception e) {
            e.printStackTrace();
//            Log.i("AAA", "Fragment called not by DrawerActivity");
        }
        mContext=activity;

    }











}
