package test1.nh.com.demos1.fragments;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

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

    Button b_startServer;

    TextView localip;

    Button b_creatAndRead;
    DataInputStream data_in; //Data input stream to read data from ServerSocket
    int currentInt;
    Button b_read;

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


        // create socket and write data
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
                            client = new Socket("192.168.1.202", 8090);
                            client.setSoTimeout(15 * 1000);
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


        //-----查找本机局域网ip--------
        localip= (TextView) rootView.findViewById(R.id.textView5);
        localip.setText("本机的ip=" + getLocalIpAddress());


        //------手机上启动一个ServerSocket----PC上的Socket连不上？？
        b_startServer=(Button) rootView.findViewById(R.id.button17);
        b_startServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        ServerSocket ss = null;
                        try {
                            ss = new ServerSocket(8091);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                            System.out.println("server socket error");
                        }


                        Socket socket = null;

                        while (true) {

                            try {
                                socket = ss.accept();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                System.out.println("server accept error");
                            }

                            try {
                                socket.setSoTimeout(20 * 1000);
                            } catch (SocketException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                System.out.println("set time out error");
                            }

                            Log.i("AAA","a client connected!!");

                            DataInputStream dis = null;
                            try {
                                dis = new DataInputStream(socket.getInputStream());
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                                Log.i("AAA", "get input DATA error");
                            }


                            while (true) {

                                int a = 0;
                                try {
                                    a = dis.readInt();
//                                    a = dis.readByte();
                                } catch (SocketTimeoutException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    Log.i("AAA", "read time out");
                                } catch (IOException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                    Log.i("AAA", "other read error");
                                }
                                Log.i("AAA", "input value is"+a);
                            }
                        }
                    }
                }).start();
            }
        });



        // matlab --> ServerSocket
        // cell phone -->Socket
        // read data from matlab
        b_creatAndRead=(Button) rootView.findViewById(R.id.button18);
        b_creatAndRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("AAA","before socket-----");
//                            client = new Socket("192.168.1.208", 8090);
                            client = new Socket("192.168.0.110", 8090);
                            Log.i("AAA", "after socket-----");
                            client.setSoTimeout(60 * 1000);
                            data_in = new DataInputStream(client.getInputStream());
                            currentInt=0;
                            while(true) read1();
                        }catch (IOException e) {
                            e.printStackTrace();
                            Log.e("AAA", Log.getStackTraceString(e));
                        }
                    }
                }).start();
            }
        });


        b_read=(Button) rootView.findViewById(R.id.button19);
        b_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
//                            currentInt = data_in.readInt();
//                            Log.i("AAA", ""+currentInt);
                            read1();
                        }catch (IOException e) {
                            e.printStackTrace();
                            Log.e("AAA", Log.getStackTraceString(e));
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

    private String getLocalIpAddress() {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        // 获取32位整型IP地址
        int ipAddress = wifiInfo.getIpAddress();
        //返回整型地址转换成“*.*.*.*”地址
        return String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff));
    }



    private void resetCurrent(){currentInt=-9999;}


    // read 1 parcel
    // firstInt---- -7007 waveform data------->OK
    // firstInt---- -6006 alarm data
    // firstInt---- -5005 statistics data
    private void read1() throws IOException {
        while (currentInt != -10086) {
            currentInt = data_in.readInt();
            Log.i("AAA", "READ INT" + currentInt);
        }
        resetCurrent(); // so that the next parcel could be read
    }


    private void read2() throws IOException{

        while (true) {
            currentInt = data_in.readInt();
            Log.i("AAA", "READ INT" + currentInt);

            final String str1=""+currentInt;

            localip.post(new Runnable() {
                public void run() {
                    localip.setText(str1);
                    localip.invalidate();
                }
            });

        }


    }






}
