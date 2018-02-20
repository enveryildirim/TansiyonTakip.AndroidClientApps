package com.hastatakip.doktor.doktorapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 24.07.2015.
 */
public class Doktor extends Hasta {
    public Doktor()
    {
        DoktorMu=true;
    }


    public static Doktor StringToJson(String strJson) {
        Doktor d=new Doktor();
        try {
            JSONObject jSon = new JSONObject(strJson);

            d.DoktorID =jSon.getInt("DoktorID");
            d.Ad_Soyad = jSon.getString("Ad_Soyad");
            d.TC=jSon.getString("TC");
            d.Sifre =jSon.getString("Sifre");
            d.Email =jSon.getString("Email");
            d.GSM=jSon.getString("GSM");
        }catch (Exception e)
        {

            e.getMessage();
        }
        return d;
    }
    public static String ObjectToString(Doktor d) {
        JSONObject jo=new JSONObject();
        try {
        jo.put("DoktorID",d.DoktorID);
        jo.put("Ad_Soyad",d.Ad_Soyad);
        jo.put("TC",d.TC);
        jo.put("Sifre",d.Sifre);
        jo.put("Email",d.Email);
        jo.put("GSM",d.GSM);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jo.toString();
    }
    //Doktorun Hastaları
    public List<Hasta> Hastalar;

    //Verdiği Kontroller
    public List<Kontrol> Kontroller;
    public static void Guncelle(Doktor d) {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", d.DoktorID);
        degerler.put("ad",d.Ad_Soyad);
        degerler.put("sifre",d.Sifre);
        String result="";
        result=WS.İstek("WS_Doktor.svc","Guncelle","IWS_Doktor",degerler);

    }
    public static Doktor GetDoktorByID(int id) {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", id);
        String result="";
        result=WS.İstek("WS_Doktor.svc", "GetById", "IWS_Doktor", degerler);
        Doktor a=StringToJson(result);
        return  a;
    }
    public static boolean SifreOnay(String sifre) {

        return true;
    }
    public static void Sil(Doktor d){}

    /*Hasta*/
    public static void HastaEkle(Hasta h,int id) {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("tc", h.TC);
        degerler.put("id",id);
        String result="";
        result=WS.İstek("WS_Doktor.svc","HastaEkle","IWS_Doktor",degerler);

    }
    public static void HastaSil(Hasta h) {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id",h.DoktorID);
        degerler.put("hid",h.ID);
        String result="";
        result=WS.İstek("WS_Doktor.svc", "HastaSil", "IWS_Doktor", degerler);

    }

    /*Kontrol*/
    public static void KontrolEkle(Kontrol  k){
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("hid",k.HastaID);
        degerler.put("id",k.DoktorID);
        degerler.put("turu",k.Turu);
        String result="";
        result=WS.İstek("WS_Doktor.svc","KontrolEkle", "IWS_Doktor", degerler);
    }
    public static void KontrolDuzenle(Kontrol k){}
    public static void KontrolSil(Kontrol k){}


}
