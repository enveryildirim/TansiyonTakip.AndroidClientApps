package com.hastatakip.doktor.doktorapp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 24.07.2015.
 */
public class Hasta {
    public int ID;
    public String Ad_Soyad;
    public String TC;
    public String Sifre;
    public String Email;
    public String GSM;
    public int DoktorID;
    public Boolean DoktorMu;
    public List<Kontrol> Kontroller;
    public Hasta()
    {
        DoktorMu=false;
    }

    public static List<Hasta> JsonToList(int doktorid) {
        List<Hasta> list = new ArrayList<Hasta>();
        try {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", doktorid);
        String strJson=WS.İstek("WS_Doktor.svc","GetHastalarByDoktorID","IWS_Doktor",degerler);
        // String strJson="{\"Hasta\":[{\"Kontroller\":[],\"HastaID\":1,\"Ad_Soyad\":null,\"TC\":\"123\",\"Sifre\":null,\"Email\":null,\"GSM\":null,\"DoktorID\":1}]}";

        JSONObject jSon = new JSONObject(strJson);
        /* { "Kontroller": [], "HastaID": 1, "Ad_Soyad": null, "TC": "123", "Sifre": null, "Email": null, "GSM": null, "DoktorID": 1 } ]</string>*/
        String HastaID     = "HastaID";
        String Ad_Soyad      = "Ad_Soyad";
        String TC           = "TC";
        String Sifre     = "Sifre";
        String Email     = "Email";
        String GSM       = "GSM";
        String DoktorID="DoktorID";
        String tagDoktorlist     = "HastaList";
        String tagDoktor="Hasta";

        Hasta hasta = null;
            JSONArray jSonArrayDoktor =jSon.optJSONArray("Hasta");
            /** Doktor count */
            int length = jSonArrayDoktor.length();

            for (int i = 0; i < length; i++) {
                JSONObject jSonDoktor = (JSONObject) jSonArrayDoktor.get(i);
                //JSONObject jSonIsbn =  jSonDoktor.getJSONObject(DoktorID);
                hasta = new Hasta();
                hasta.ID=Integer.parseInt(jSonDoktor.getString(HastaID));
                hasta.Ad_Soyad = jSonDoktor.getString(Ad_Soyad);
                hasta.TC= jSonDoktor.getString(TC);
                hasta.Sifre= jSonDoktor.getString(Sifre);
                hasta.Email = jSonDoktor.getString(Email);
                hasta.GSM = jSonDoktor.getString(GSM);
                hasta.DoktorID=Integer.parseInt(jSonDoktor.getString(DoktorID));
                list.add(hasta);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = null;
        }
        return list;

    }
    public static  List<Hasta> GetAll() {
        ///webservisler

        Kontrol k =new Kontrol();
        k.Program="12/7,15/10,9/7";
        k.DoktorID=1;

        Kontrol k1 =new Kontrol();
        k1.Program="13/9,14/10,9/7";
        k1.DoktorID=1;

        List<Kontrol> listk=new ArrayList<Kontrol>();
        listk.add(k);
        listk.add(k1);

        Hasta h=new Hasta();
        h.ID=1;
        h.DoktorID=1;
        h.Ad_Soyad="ali al";
        h.Kontroller=listk;

        Hasta h1=new Hasta();
        h1.Ad_Soyad="ahmet ali";
        h1.DoktorID=1;
        h1.ID=2;
        h1.Kontroller=listk;
        List<Hasta> hs=new ArrayList<Hasta>();
        hs.add(h);
        hs.add(h1);
        return hs;
    }
    public static  List<Hasta> GetHastalarByDoktor(int DoktorID) {
        List<Hasta> hasta= new ArrayList<Hasta>();
        hasta=JsonToList(DoktorID);
        return hasta;

    }
    public static  List<Kontrol>GetKontrollerByHastaID(int HastaID) {
        return Kontrol.JsonToList(HastaID);
    }
    public static void Guncelle(Hasta h){
        //
    }

    /*Kontrol*/

    public static void KontrolDegerEkle(int ID,String deger){}
    public static void KontrolDegerDuzenle(Kontrol k){}
    public static void KontrolDegerSil(Kontrol k){}
}
