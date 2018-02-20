package com.hastatakip.doktor.doktorapp;

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
 * Created by PC on 24.07.2015.
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
    public static List<String> GetProgram_List(){
    //String programı listeye dönüştürür
        return null ;
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
            JSONArray jSonArrayDoktor =jSon.optJSONArray("Kontrol");
            /** Doktor count */
            int length = jSonArrayDoktor.length();

            for (int i = 0; i < length; i++) {
                JSONObject jSonDoktor = (JSONObject) jSonArrayDoktor.get(i);
                //JSONObject jSonIsbn =  jSonDoktor.getJSONObject(DoktorID);
               /* kontrol = new Kontrol();
                kontrol.ID=Integer.parseInt(jSonDoktor.getString(KontrolID));
                kontrol.DoktorID = jSonDoktor.getInt(DoktorID);
                kontrol.HastaID= jSonDoktor.getInt(HastaID);
                kontrol.Turu= jSonDoktor.getInt(Turu);
                kontrol.Program = jSonDoktor.getString(Program);
                kontrol.YeniMi = jSonDoktor.getString(YeniMi);
                kontrol.DoktorID=Integer.parseInt(jSonDoktor.getString(DoktorID));*/
                kontrol=StringToJson(jSon.toString());
                list.add(kontrol);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;

    }
}
