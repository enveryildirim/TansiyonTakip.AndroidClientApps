package com.hastatakip.hasta.hastaapp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 29.09.2015.
 */
public class Kontrol {
    public int ID;
    public int DoktorID;
    public int HastaID;
    public int Turu;
    public String Program;
    public boolean YeniMi;
    public boolean KontrolOlduMu;
    public Date SonBakilmaTarihi;
    public String[] GetProgram_List(){
        if(Program==null)
        return new String[]{} ;
        return  Program.split(",");
    }

    public static  Kontrol GetKontrolByID(int id){

        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", id);
        String strJson=WS.İstek("WS_Hasta.svc","GetKontrolByID","IWS_Hasta",degerler);
        Kontrol temp= StringToJson(strJson);
        return temp;
    }

    public static Kontrol StringToJson(String strJson) {
        Kontrol d=new Kontrol();
        try {

            JSONObject jSon = new JSONObject(strJson);
            d.ID=jSon.getInt("KontrolID");
            d.DoktorID =jSon.getInt("DoktorID");
            d.HastaID =jSon.getInt("HastaID");
            d.Turu=jSon.getInt("Turu");
            d.Program =jSon.getString("Program");
            d.YeniMi =jSon.getBoolean("YeniMi");
            d.KontrolOlduMu=jSon.getBoolean("KontrolOlduMu");
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH.mm.ss");
            try {
                d.SonBakilmaTarihi= sdf.parse(jSon.getString("SonBakilmaTarihi"));
            } catch (ParseException ex) {

            }

        }catch (Exception e)
        {

            e.getMessage();
        }
        return d;
    }
    public static String ObjectToJson(Kontrol k) {
        JSONObject jo=new JSONObject();
        try {
            jo.put("KontrolID",k.ID);
            jo.put("DoktorID",k.DoktorID);
            jo.put("HastaID",k.HastaID);
            jo.put("Turu",k.Turu);
            jo.put("Program",k.Program);
            jo.put("YeniMi",k.YeniMi);
            jo.put("SonBakilmaTarihi",k.SonBakilmaTarihi);
            jo.put("KontrolOlduMu",k.KontrolOlduMu);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jo.toString();
    }
    public static List<Kontrol> JsonToList(int hastaid) {
        List<Kontrol> list = new ArrayList<Kontrol>();
        try {
            Map<String,Object> degerler=new Hashtable<String, Object>();
            degerler.put("id", hastaid);
            String strJson=WS.İstek("WS_Hasta.svc","GetKontrollerByHastaID","IWS_Hasta",degerler);
            JSONObject jSon = new JSONObject(strJson);

            String KontrolID     = "KontrolID";
            String DoktorID      = "DoktorID";
            String HastaID       = "HastaID";
            String Turu          = "Turu";
            String Program         = "Program";
            String YeniMi           = "YeniMi";
            String SonBakilmaTarihi      ="SonBakilmaTarihi";

            Kontrol kontrol = null;
            JSONArray jSonArray =jSon.optJSONArray("Kontrol");
            /** Doktor count */
            int length = jSonArray.length();

            for (int i = 0; i < length; i++) {
                JSONObject jSonK = (JSONObject) jSonArray.get(i);

                kontrol=StringToJson(jSonK.toString());
                list.add(kontrol);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;

    }
    public static void Ekle(int id,String prg)
    {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", id);
        degerler.put("deger",prg);
        String strJson=WS.İstek("WS_Hasta.svc","DegerEkle","IWS_Hasta",degerler);
    }
    public  static void Guncelle(int id,String prg)
    {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", id);
        degerler.put("program", prg);
        String strJson=WS.İstek("WS_Hasta.svc","DegerGuncelle","IWS_Hasta",degerler);
    }
    public static void Sil(int id,String prg)
    {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", id);
        degerler.put("program", prg);
        String strJson=WS.İstek("WS_Hasta.svc","DegerSil","IWS_Hasta",degerler);
    }
}
