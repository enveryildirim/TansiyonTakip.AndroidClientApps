package com.hastatakip.hasta.hastaapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by PC on 30.09.2015.
 */
public class AdapKontrolDeger extends BaseAdapter {
    private LayoutInflater mInflater;
    private String[] kontroller;

    public AdapKontrolDeger(Activity activity, Kontrol k)
    {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        kontroller = k.Program.split(",");
    }
    @Override
    public int getCount() {
        return kontroller.length;
    }

    @Override
    public Object getItem(int position) {

        return kontroller[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.listviewsatirhastalar, null);
        TextView textViewid =
                (TextView) satirView.findViewById(R.id.textView_satir);
        String k ="";
        if(kontroller[position].length()>0)
        k=kontroller[position]+"\n";
        else
        k="Boş";
            textViewid.setText(k);

        return satirView;

    }
}
