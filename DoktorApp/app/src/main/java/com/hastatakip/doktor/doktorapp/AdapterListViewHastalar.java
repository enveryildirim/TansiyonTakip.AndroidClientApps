package com.hastatakip.doktor.doktorapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by PC on 27.07.2015.
 */
public class AdapterListViewHastalar extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Hasta> hastalar;
    public  AdapterListViewHastalar(Activity activity, List<Hasta> h)
    {
        mInflater = (LayoutInflater) activity.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);

        List<Hasta> isimliler=new ArrayList<Hasta>();
        List<Hasta> isimsizler=new ArrayList<Hasta>();
        for (Hasta a:h) {
            if(a.Ad_Soyad.equalsIgnoreCase("null"))
                isimsizler.add(a);
            else
                isimliler.add(a);
        }
        hastalar =new ArrayList<Hasta>();
        hastalar.addAll(isimliler);
        hastalar.addAll(isimsizler);
    }
    @Override
    public int getCount() {
        return hastalar.size();
    }

    @Override
    public Object getItem(int position) {
        return hastalar.get(position);
    }

    @Override
    public long getItemId(int position) {
        return hastalar.get(position).ID;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View satirView;
        satirView = mInflater.inflate(R.layout.listviewsatirhastalar, null);
        TextView textViewid =
                (TextView) satirView.findViewById(R.id.textView_satir);

        Hasta kisi = hastalar.get(position);
        String yazi="";
        if(kisi.Ad_Soyad.equalsIgnoreCase("null"))
            yazi=kisi.TC+"Adı Guncellememiş";
        else
            yazi=kisi.Ad_Soyad;
        textViewid.setText("->  "+yazi);
        textViewid.setTextColor(Color.BLACK);
        return satirView;
    }
}
