package test1.nh.com.demos1.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import test1.nh.com.demos1.R;
import test1.nh.com.demos1.activities.matDesign.MatDesignPanelActivity;
import test1.nh.com.demos1.utils.BitmapUtil;

public class MainActivity_from extends AppCompatActivity {
    private ImageView cursorIV;
    // 图片偏移量
    private int cursorOffset = 0;
    // 当前页卡编号
    private int cursorIndex = 0;
    // 图片宽度
    private int cursorImgWidth = 0;

    Context mContext;

    //测试数组初始化----
    private int[] callbackBuffer;

    //测试布尔初始值---
    private boolean isOK;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity_from);



        // ---test of actionbar activities---
        Button b_appbar1=(Button)findViewById(R.id.button20);
        b_appbar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(MainActivity_from.this, ActionBarActivity1.class);
                startActivity(mIntent);
            }
        });

        // ---good demo of APPbar---
        Button b_appbar2=(Button)findViewById(R.id.button21);
        b_appbar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity_from.this,ActionBarActivity2.class);
                startActivity(mIntent);
            }
        });





        // -------------log 打印测试---String.compareTo 测试
        Button b1=(Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AAA", "log test");
                Log.i("AAA", "1.2.3 compareTo 1.2.1"+"1.2.3".compareTo("1.2.1"));
                Log.i("AAA", "A compareTo B"+"A".compareTo("B"));
            }
        });
        // -end of------------log 打印测试

        // -------------activity跳转+drawer导航测试
        Button b2=(Button)findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity_from.this,DrawerActivity.class);
                startActivity(mIntent);
            }
        });

        Button b_md=(Button)findViewById(R.id.button7);
        b_md.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity_from.this,MatDesignPanelActivity.class);
                startActivity(mIntent);
            }
        });


        //jump with animations
        Button jump_anim1=(Button)findViewById(R.id.button4);
        Button jump_anim2=(Button)findViewById(R.id.button5);

        jump_anim1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity_from.this,DrawerActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.page_bottom_in, R.anim.page_bottom_out);
            }
        });

        jump_anim2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent=new Intent(MainActivity_from.this,DrawerActivity.class);
                startActivity(mIntent);
                overridePendingTransition(R.anim.page_left_in, R.anim.page_right_out);
            }
        });
        // -------------end of activity跳转+drawer导航测试


        // -------------image+matric+offset 测试-----------
        Bitmap mp = BitmapUtil.overlapBitmapColor(BitmapFactory.decodeResource(
                        getResources(), R.drawable.bg_mutual_main_cursorimg),
                getResources().getColor(R.color.Red_));
        cursorIV = (ImageView) findViewById(R.id.imageView1);
        cursorIV.setImageBitmap(mp);

        cursorImgWidth = mp.getWidth();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screentWidth = dm.widthPixels;
//        cursorOffset = (screentWidth / 2 - cursorImgWidth) / 2;
        cursorOffset = 20;
        Matrix matrix = new Matrix();
        matrix.postTranslate(cursorOffset, 0);
        cursorIV.setImageMatrix(matrix);
        // ------------end of -image+matric+offset 测试-----------


        mContext=this;
        // alert dialog 测试-----------------------------
        Button b3=(Button)findViewById(R.id.button3);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(mContext)
                        .setTitle("this is the title")
                        .setMessage("this is the message")
                        .setCancelable(false)
                        .setOnKeyListener(            //onKeyListener有用么？？？？
                                new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(
                                            DialogInterface dialog,
                                            int keyCode,
                                            KeyEvent event) {
                                        if (keyCode == KeyEvent.KEYCODE_SEARCH) {
                                            return true;
                                        }
                                        return false;
                                    }
                                }
                        )
                        .setPositiveButton("positive",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        System.exit(0);
                                    }
                                }).show();
            }
        });
        // end of alert dialog 测试-----------------------------



        // anim 测试-----------------------------
        final Button b_anim=(Button)findViewById(R.id.button_anim);
        final ImageView iv_anim=(ImageView)findViewById(R.id.iv_anim_test);

        final TranslateAnimation animBack = new TranslateAnimation(0, 0, 0, 0);
        animBack.setDuration(20);

        //animation生命周期方法
        final Animation.AnimationListener animationOpenListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                b_anim.setText("--in animation...");
                Log.i("AAA","start");
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.i("AAA","repeat");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                b_anim.setText("start anim");
//                iv_anim.setAnimation(animBack);
//                animBack.startNow();
                Log.i("AAA","end");
            }
        };

        //新建animation
        final TranslateAnimation animOpen = new TranslateAnimation(0, 100, 0, 100);
//        animOpen.setRepeatCount(5);
        animOpen.setFillAfter(false);  // false --> go back to original position , true --> stay at final position
        animOpen.setDuration(1000);
        animOpen.setAnimationListener(animationOpenListener);


        b_anim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("AAA", "animation click start");
//                iv_anim.setAnimation(animOpen);
//                animOpen.startNow();
//                animOpen.start();  // NOT WORKING WELL
                iv_anim.startAnimation(animOpen);  // move  // this is OK

                Log.i("AAA", "animation click end");
            }
        });

        // end of anim 测试-----------------------------



        // 初始化数组测试----------------+位移运算优先级测试-------------
        TextView arrayLshow=(TextView)findViewById(R.id.editText_arraytest);
        //arrayLshow.setText("length:"+callbackBuffer.length); //不赋值无法获取长度
        callbackBuffer = new int[]{1, 2, 33};
//        arrayLshow.setText("length:" + callbackBuffer.length);

//        //优先级测试
//        arrayLshow.setText(""+(1<<1 + 1));
//        arrayLshow.setText(""+((1<<1) + 1));

        //boolean初始测试
        arrayLshow.setText("ok?"+isOK);  //初始false

//        arrayLshow.setText("length:");
        // end of初始化数组测试-----------------------------


        // 时间format测试-----------------------------
        TextView timeShow=(TextView)findViewById(R.id.editText_formatTest);
        DateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        String mDate="2009-12-01 08_12_23";

        try {
            Date dAlarmDate=sdf.parse(mDate);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        timeShow.setText("time:"+mDate);
        // end of  时间format测试-----------------------------


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity_from, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.appbaractivity2) {
            Intent mIntent=new Intent(MainActivity_from.this,ActionBarActivity2.class);
            startActivity(mIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
