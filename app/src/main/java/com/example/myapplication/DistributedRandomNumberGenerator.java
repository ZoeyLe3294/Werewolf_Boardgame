package com.example.myapplication;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DistributedRandomNumberGenerator {
    protected Map<Integer,Double> probability;
    public final static int OUTBOUND = 99;
    public DistributedRandomNumberGenerator(){
        probability = new HashMap<Integer,Double>();
    }
    public void addNumber(int i, double prob){
        probability.put(i,prob);
    }
    public int weightedRandomNumber(boolean replacement){
        double accumulation = 0;
        Random random = new Random();
        double ran = random.nextDouble();
        int sample =0;
        for (int i : probability.keySet()){
            if (ran>accumulation){sample=i;}
            accumulation+=probability.get(sample);
        }
        if (!replacement){
            if (probability.size()==0){sample = OUTBOUND;}
            probability.remove(sample);
            double sum=0;
            for(int i : probability.keySet()){
                sum+=probability.get(i);
            }
            for(int i : probability.keySet()){
                probability.put(i,probability.get(i)/sum);
            }
        }
        return sample;
    }
}