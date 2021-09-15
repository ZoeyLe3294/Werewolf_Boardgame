package com.example.myapplication;

import android.animation.Animator;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends AppCompatActivity {
    public Bundle myBundle;
    public int numPlayer,rolePhaseDuration,morningPhaseDuration,finalID=0,order;
    public String master;
    public ArrayList<Integer> orderList,checkStatusList,buttonList;
    public ArrayList<String> navList= new ArrayList<>();;
    public HashMap<Integer,Player> playerHashMap = new HashMap<Integer, Player>();
    public static final long MORNING_PHASE = 10100;
    private TextView textView,timer;
    private AlertDialog dialog;
    private boolean firstNight = true, morning=false;
    LottieAnimationView animationView;
    AnimationDrawable animationDrawable;
    ConstraintLayout mainLayout;
    ButtonAdapter buttonAdapter;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        myBundle = getIntent().getBundleExtra("setUp");
        numPlayer = myBundle.getInt("numPlayer");
        rolePhaseDuration = myBundle.getInt("rolePhase") *1000+100;
        morningPhaseDuration = myBundle.getInt("morningPhase") *1000+100;
        master = myBundle.getString("master");
        orderList = myBundle.getIntegerArrayList("orders");
        Collections.sort(orderList);

        init();


        //BUTTONS SETUP -> CREATE PLAYER
        for (int i=1; i<=numPlayer;i++){
            final Button myButton = new Button(this);
            myButton.setText(String.valueOf(i));
            myButton.setBackground(getResources().getDrawable(R.drawable.custom_button));
            myButton.setId(i);
            myButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    confirm(myButton.getId());
                }
            });
            Player newPlayer = new Player(99,0,0);
            playerHashMap.put(i,newPlayer);
            int layoutPosition = 2;
            int j = numPlayer/3;
            if(i<=j){
                layoutPosition = R.id.left_list;
            }else if(i <= j * 2){
                layoutPosition = R.id.top_list;
            }else if(i>j*2 && i<=j*3){
                layoutPosition = R.id.right_list;
            }else {
                layoutPosition = R.id.bottom_list;
            }
            LinearLayout ll = (LinearLayout) findViewById(layoutPosition);
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            ll.addView(myButton, lp);
        }

        textView.setText("Mọi người hãy nhắm mắt lại.");
        final Handler handler = new Handler(Looper.getMainLooper());
        backgroundAnim(0);

//GAME LOOP
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkStatusList = checkStatus();
                if (checkStatusList.get(checkStatusList.size()-1) > checkStatusList.get(checkStatusList.size()-2)){
                    //NIGHT LOOP
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(order==0 && !firstNight){backgroundAnim(0);}
                            if (orderList.get(order)<99){
                                morning = false;
                                navList.clear();
                                final ArrayList<MediaPlayer> mediaPlayers = new ArrayList<>();
                                if(firstNight){
                                    navList.add("Mời "+orderList.get(order)+"mở mắt. Hãy chọn vị trí của bạn.");
                                }
                                switch (orderList.get(order)){
                                    case 0: navList.add("Ban la "+ orderList.get(order));break;
                                    case 14: navList.add("Ban muon can ai?");break;
                                    default:navList.add("Ban la "+ orderList.get(order));break;
                                }
                                final int[] i = {0};
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (i[0]<navList.size()){
                                            textView.setText(navList.get(i[0]));
                                            countdown(rolePhaseDuration);
                                            i[0] ++;
                                            handler.postDelayed(this,rolePhaseDuration);
                                        }
                                    }
                                });
                                order++;
                                handler.postDelayed(this, rolePhaseDuration*navList.size());
                            }else {
                                checkStatusList = checkStatus();
                    //MORNING LOOP
                                if(checkStatusList.get(checkStatusList.size()-1) > checkStatusList.get(checkStatusList.size()-2)){
                                    backgroundAnim(1);
                                    morning = true;
                                    firstNight=false;
                                    order = 0;
                                    if(checkStatusList.size()==2){
                                        textView.setText("Khong ai chet");
                                    }else {
                                        String str="";
                                        for (int i = 0; i<checkStatusList.size()-2;i++) {
                                            str += ", "+ checkStatusList.get(i);
                                        }
                                        textView.setText("Nguoi chet la "+ str);
                                    }
                                    countdown(morningPhaseDuration);
                                    handler.postDelayed(this,morningPhaseDuration+1000);
                                }else {textView.setText("Game Over");}
                            }

                        }
                    },2000);
                }else {textView.setText("Game Over");}

            }

        },2000);

    }
//
    //Initiate
    public void init(){
        timer = (TextView) findViewById(R.id.timer);
        textView = (TextView) findViewById(R.id.textView);
        animationView = (LottieAnimationView) findViewById(R.id.anim1);
        mainLayout = (ConstraintLayout) findViewById(R.id.main_layout);
        mainLayout.setBackgroundResource(R.drawable.background_anim);
        animationDrawable = (AnimationDrawable) mainLayout.getBackground();

    }

//CountDownTimer
    public void countdown(long t){
        timer.setText(""+String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(t),TimeUnit.MILLISECONDS.toSeconds(t)));
        final CountDownTimer cTimer = new CountDownTimer(t,1000) {
            @Override
            public void onTick(long l) {
                timer.setText(""+String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(l),TimeUnit.MILLISECONDS.toSeconds(l)));
            }
            @Override
            public void onFinish() {
//                Player newPlayer = new Player(99,0,0);
                if (dialog!=null){dialog.dismiss();}
                if(finalID!=0){
                    if (textView.getText().toString().endsWith(".")){
                        playerHashMap.get(finalID).setRoleCode(orderList.get(order-1));

                    }else {
                        //morning votes
                        if(morning){playerHashMap.get(finalID).setStatus(1);}
                        //night actions
                        switch (orderList.get(order-1)){
                            case 0: playerHashMap.get(finalID).setStatus(0);break;
                            case 14: playerHashMap.get(finalID).setStatus(1);break;
                            default:playerHashMap.get(finalID).setStatus(0);break;
                        }
                    }
                }
//                Toast toast = Toast.makeText(MainActivity.this, "Player: "+String.valueOf(finalID)+"Rolecode: "+String.valueOf(playerHashMap.get(finalID).getRoleCode()), LENGTH_SHORT);
//                toast.show();
                finalID=0;
            }
        }.start();
    }

//Confirm Dialog
    private void confirm(final int ID){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Xac nhan!");
        alertDialog.setIcon(R.mipmap.ic_launcher);
        alertDialog.setMessage("Ban da chac chan chua?");
        alertDialog.setPositiveButton("Co", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalID = ID;
            }
        });
//        alertDialog.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//            }
//        });
        dialog=alertDialog.show();
    }

    //Check status -> ID deads + numWolfAlive + numManAlive
    private ArrayList<Integer> checkStatus(){
        ArrayList<Integer> status = new ArrayList<>();
        int numWolfAlive = 0;
        int numManAlive = 0;
        for (Map.Entry entry :playerHashMap.entrySet()){
            Player p = (Player) entry.getValue();
            if(p.getStatus()!=1){//if alive
                if (indexOf(Source.orderWolf,p.getRoleCode())!=-1 || p.getRoleCode()==14){numWolfAlive++;
                }else {numManAlive++;}
            }else {status.add((Integer) entry.getKey());}//if dead
        }
        status.add(numWolfAlive);
        status.add(numManAlive);
        return status;
    }

    //indexOf
    private int indexOf(int[] array,int val){
        int position = -1;
        for (int i =0;i<array.length;i++){
            if (array[i]==val){ position = i;}
        }
        return position;
    }

    //Background change
    private void backgroundAnim(int phase){
        switch (phase){
            case 0:
                animationView.setMinAndMaxProgress(0f,0.5f);
                mainLayout.setBackgroundResource(R.drawable.background_anim);
                break;
            case 1:
                animationView.setMinAndMaxProgress(0.5f,1f);
                mainLayout.setBackgroundResource(R.drawable.background_reverse_anim);
                break;
        }
        animationDrawable = (AnimationDrawable) mainLayout.getBackground();
        animationView.playAnimation();
        animationDrawable.start();
        animationDrawable.setEnterFadeDuration((int) animationView.getDuration()/3);
        animationDrawable.setExitFadeDuration((int) animationView.getDuration()/3);
        animationDrawable.setOneShot(true);

    }

}
