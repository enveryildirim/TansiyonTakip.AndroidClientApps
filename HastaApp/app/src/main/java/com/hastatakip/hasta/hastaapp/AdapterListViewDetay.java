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
 * Created by PC on 29.09.2015.
 */
public class AdapterListViewDetay extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Kontrol> kontroller;

    public AdapterListViewDetay(Activity activity, List<Kontrol> k)
    {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
        kontroller = k;
    }
    @Override
    public int getCount() {
        return kontroller.size();
    }

    @Override
    public Object getItem(int position) {
        return kontroller.get(position);
    }

    @Override
    public long getItemId(int position) {
        return kontroller.get(position).ID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.listviewsatirhastalar, null);
        TextView textViewid =
                (TextView) satirView.findViewById(R.id.textView_satir);
        Kontrol k = kontroller.get(position);
        String a="";
        if(k.Turu==0)a="Tansiyon";
        else a="Şeker";
        textViewid.setText(a);
        return satirView;

    }
}
