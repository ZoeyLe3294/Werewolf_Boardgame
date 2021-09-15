package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

import org.apache.commons.math3.distribution.EnumeratedDistribution;
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.util.CombinatoricsUtils;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class SetupActivity extends AppCompatActivity {
    private EditText numPlayer,rolePhase,morningPhase;
    private Switch gameMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);
        init();
    }

    public void confirm(View view){
        Intent intent = new Intent(SetupActivity.this,RoleActivity.class);
        Bundle myBundle = new Bundle();
        myBundle.putString("master",gameMaster.getText().toString());
        myBundle.putInt("numPlayer",Integer.parseInt(numPlayer.getText().toString()));
        myBundle.putInt("rolePhase",Integer.parseInt(rolePhase.getText().toString()));
        myBundle.putInt("morningPhase",Integer.parseInt(morningPhase.getText().toString()));

        intent.putExtra("setUp", myBundle);
        startActivity(intent);
    }
    public void init(){
        numPlayer = (EditText) findViewById(R.id.numPlayer);
        gameMaster = (Switch) findViewById(R.id.gameMaster);
        rolePhase = (EditText) findViewById(R.id.rolePhase);
        morningPhase = (EditText) findViewById(R.id.morningPhase);
    }


}
