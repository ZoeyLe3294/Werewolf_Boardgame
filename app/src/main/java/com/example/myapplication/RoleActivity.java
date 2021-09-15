package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.myapplication.DistributedRandomNumberGenerator.OUTBOUND;

public class RoleActivity extends AppCompatActivity{
    public Button button;
    public GridView listRole;
    public ImageView refresh,back;

    public ArrayList <String> roleList = new ArrayList<>();
    public ArrayList<Integer> orderList = new ArrayList<>();
    public ArrayList<String> navigatorList = new ArrayList<>();
//    public ArrayList <Player> playerList = new ArrayList<>();

    public int numPlayer,numAbnormal,numPeople;
    DistributedRandomNumberGenerator wolfProb,mutantProb,peopleProb,wolves,vil;
    Bundle myBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);

        Button button = (Button) findViewById(R.id.button);
        ImageView refresh = (ImageView) findViewById(R.id.refresh);
        ImageView back = (ImageView) findViewById(R.id.back);
        listRole = (GridView) findViewById(R.id.listRole);

        myBundle = getIntent().getBundleExtra("setUp");
        numPlayer = myBundle.getInt("numPlayer");
        roleRun(numPlayer);

        Collections.sort(this.roleList);
        ArrayAdapter adapter = new ArrayAdapter(RoleActivity.this,R.layout.gridview,roleList);
        listRole.setAdapter(adapter);
        listRole.setNumColumns(2);
    }
    public void back (View view){
        finish();
    }
    public void refresh(View view){
        roleList.clear();
        orderList.clear();
//        playerList.clear();
        roleRun(numPlayer);
        Collections.sort(this.roleList);
        ArrayAdapter adapter = new ArrayAdapter(RoleActivity.this,R.layout.gridview,roleList);
        listRole.setAdapter(adapter);
    }
    public void play(View view){
        Intent intent = new Intent(RoleActivity.this,MainActivity.class);
//        myBundle.putStringArrayList("roles",roleList);
        myBundle.putIntegerArrayList("orders",orderList);
        intent.putExtra("setUp", myBundle);
        startActivity(intent);
    }
    public void roleRun(int numPlayer){
        //SET UP PARAMETERS
        numAbnormal = (int) numPlayer/3; //wolf + mutant
        numPeople = (numPlayer +1) - numAbnormal*2;
        wolves = randomSetup(Source.wolfP);
        vil = randomSetup(Source.vilP);
        if(numAbnormal<=2){
            wolfProb = randomSetup(Source.wolf_small_prob);
            mutantProb = randomSetup(Source.mutant_small_prob);
            peopleProb = randomSetup(Source.people_small_prob);
        }
        else if(numAbnormal==3){
            wolfProb = randomSetup(Source.wolf_medium_prob);
            mutantProb = randomSetup(Source.mutant_medium_prob);
            peopleProb = randomSetup(Source.people_medium_prob);
        }else {
            wolfProb = randomSetup(Source.wolf_large_prob);
            mutantProb = randomSetup(Source.mutant_large_prob);
            peopleProb = randomSetup(Source.people_large_prob);
        }
        //GENERATE ABNORMAL = WOLVES + MUTANT
        for (int i=0; i<numAbnormal;i++){
            int numWolf = wolves.weightedRandomNumber(true);
            int ranWolf = wolfProb.weightedRandomNumber(false);
            int ranMutant = mutantProb.weightedRandomNumber(false);
            if (numWolf==0||numWolf==OUTBOUND){
                roleList.add("Sói");
                if (orderList.indexOf(14)==-1){orderList.add(14);}
            }
            else {
                roleList.add(Source.wolf[ranWolf]);
                if (orderList.indexOf(Source.orderWolf[ranWolf])==-1){orderList.add(Source.orderWolf[ranWolf]);}

            }
            roleList.add(Source.mutant[ranMutant]);
            if (orderList.indexOf(Source.orderMutant[ranMutant])==-1){orderList.add(Source.orderMutant[ranMutant]);}
        }
        //GENERATE PEOPLE + THIRD PARTY
        for (int i=0; i<numPeople; i++){
            int numPeo = vil.weightedRandomNumber(true);
            if (numPeo==0||numPeo==OUTBOUND){
                roleList.add("Dân thường");
                if (orderList.indexOf(99)==-1){orderList.add(99);}
            }
            else {
                int ranPeople = peopleProb.weightedRandomNumber(false);
                roleList.add(Source.people[ranPeople]);
                if (orderList.indexOf(Source.orderPeople[ranPeople])==-1){orderList.add(Source.orderPeople[ranPeople]);}
            }
        }

    }

    public DistributedRandomNumberGenerator randomSetup(double[] probabilityArray){
        DistributedRandomNumberGenerator drng = new DistributedRandomNumberGenerator();
        for (int i=0; i<probabilityArray.length;i++){
            drng.addNumber(i, probabilityArray[i]); // Adds the numerical value v with a probability p -> v = position of role
        }
//        int random = drng.weightedRandomNumber(false); // Generate a random number
        return drng;
    }

}
