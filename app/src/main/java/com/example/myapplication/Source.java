package com.example.myapplication;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Source {
    public static final double[] wolfP = {0.5,0.5};//Prob Soi vs Spec Soi
    public static final double[] vilP = {0.5,0.5};//Prob Dan vs Spec Dan
    public static final double[] wolf_small_prob = {0.25,0.25,0.25,0.25};
    public static final double[] mutant_small_prob = {0.2,0.2,0.2,0.2,0.2};
    public static final double[] people_small_prob = {0.2,0.2,0.4,0.2};
    public static final double[] wolf_medium_prob = {0.2,0.1,0.1,0.1,
            0.1,0.1,0.1,0.1,0.1};
    public static final double[] mutant_medium_prob = {0.3,0.1,0.1,0.1,0.1,
            0.1,0.1,0.1};
    public static final double[] people_medium_prob = {0.2,0.1,0.2,0.1,
            0.2,0.1,0.1};
    public static final double[] wolf_large_prob = {0.08,0.08,0.08,0.1,
            0.08,0.08,0.08,0.08,0.1,
            0.08,0.08,0.08};
    public static final double[] mutant_large_prob = {0.1,0.09,0.09,0.09,0.09,
            0.09,0.09,0.09,
            0.09,0.09,0.09};
    public static final double[] people_large_prob = {0.1,0.1,0.2,0.1,
            0.1,0.1,0.1,
            0.1,0.1};

    public static int[] orderWolf = {3,17,18,15,2,6,19,4,20,1,32,5};
    public static int[] orderMutant = {29,23,16,8,9,10,22,24,28,30,25};
    public static int[] orderPeople = {26,7,11,0,12,21,13,27,31};

    public static String[] wolf =    {"Sói Lười","Sói nam","Sói nữ","Sói băng",
            "Sói cô đơn","Sói con","Pháp sư sói","Sói Sida","Sói nguyền",
            "Sói thánh thiện","Sói tiên tri","Sói già"};
    public static String[] mutant =  {"Tiên tri","Bảo vệ","Kỹ nữ","Người hóa sói","Người đá",
            "Người bệnh","Thợ săn","Phù thủy",
            "Tiên tri tập sự","Tiên tri hào quang","Thám tử"};
    public static String[] people =  {"Câm lặng","Du côn","Chán đời","Nostradamus",
            "Cupid","Kẻ bị nguyền","Trưởng giáo phái",
            "Kẻ phá rối","Nhân bản"};

    public static String[] navWolf = {"Sói Lười","Sói nam","Sói nữ","Sói băng",
            "Sói cô đơn","Sói con","Pháp sư sói","Sói Sida","Sói nguyền",
            "Sói thánh thiện","Sói tiên tri","Sói già"};
    public static String[] navMutant = {"Tiên tri","Bảo vệ","Kỹ nữ","Người hóa sói","Người đá",
            "Người bệnh","Thợ săn","Phù thủy",
            "Tiên tri tập sự","Tiên tri hào quang","Thám tử"};
    public static String[] navPeople = {"Câm lặng","Du côn","Chán đời","Nostradamus",
            "Cupid","Kẻ bị nguyền","Trưởng giáo phái",
            "Kẻ phá rối","Nhân bản"};
}
