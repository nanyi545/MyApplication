package test1.nh.com.demos1.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;

import test1.nh.com.demos1.R;
import test1.nh.com.demos1.activities.DrawerActivity;

/**
 * Created by Administrator on 15-9-30.
 */
public class SocketIOFragment extends Fragment {

    Button b1_creatTunnel;
    Button b1_writeInt;
    Socket client;    //socket to send data
    DataOutputStream data_out;  //DataOutputStream from the socket


    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    //empty constructor
    public SocketIOFragment(){
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static SocketIOFragment newInstance(int sectionNumber) {
        SocketIOFragment fragment = new SocketIOFragment();
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

        // socket IO fragment
        View rootView = inflater.inflate(R.layout.fragment_socio_drawer, container, false);

        b1_creatTunnel = (Button) rootView.findViewById(R.id.button14);
        b1_writeInt = (Button) rootView.findViewById(R.id.button15);


        // internet operation is not allowed in UI threads
        b1_creatTunnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            client = new Socket("192.168.0.105", 7777);
                            client.setSoTimeout(15*1000);
                            data_out = new DataOutputStream(client.getOutputStream());
                        }catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });

        // internet operation is not allowed in UI threads
        b1_writeInt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            boolean s_bound=client.isBound();
                            boolean s_conn=client.isConnected();
                            boolean s_close=client.isClosed();
                            Log.i("AAA","bound?:"+s_bound+"    connected?:"+s_conn+"    closed?:"+s_close);
                            data_out.writeInt(-100);
                        }catch (SocketException ste){
                            ste.printStackTrace();
                            Log.e("AAA", Log.getStackTraceString(ste));
                        }catch (IOException e) {
                            e.printStackTrace();
//                            Log.e("AAA", Log.getStackTraceString(e));
                        }
                    }
                }).start();
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        //设置activity label
        ((DrawerActivity) activity).onSectionAttached(
                getArguments().getInt(ARG_SECTION_NUMBER));
    }



}
