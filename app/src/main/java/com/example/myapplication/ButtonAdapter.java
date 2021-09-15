package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ButtonAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Integer> buttonIDList;

    public ButtonAdapter(Context context, int layout, List<Integer> buttonIDList) {
        this.context = context;
        this.layout = layout;
        this.buttonIDList = buttonIDList;
    }

    @Override
    public int getCount() {
        return buttonIDList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout,null);

        TextView buttonId = (TextView) view.findViewById(R.id.position);
        buttonId.setText(buttonIDList.get(i));

        return view;
    }
}
