package com.hastatakip.hasta.hastaapp;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Created by PC on 29.09.2015.
 */
public class Hasta {
    public int ID;
    public String Ad_Soyad;
    public String TC;
    public String Sifre;
    public String Email;
    public String GSM;
    public String Hakkinda;
    public int DoktorID;
    public Boolean DoktorMu;
    public List<Kontrol> Kontroller;
    public Hasta()
    {
        DoktorMu=false;
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
    public static void Ekle(String tc) {

    }
    public static Hasta GetHastaByID(int ID) {

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

        return  h;
    }
    public static List<Hasta>GetHastalarByDoktor(int DoktorID) {
        return new ArrayList<Hasta>();
    }
    public static  List<Kontrol>GetKontrollerByHastaID(int HastaID) {
        Map<String,Object> degerler=new Hashtable<String, Object>();
        degerler.put("id", HastaID);
        String strJson=WS.İstek("WS_Hasta.svc","GetKontrollerByHastaID","IWS_Hasta",degerler);

        List<Kontrol> listk=new ArrayList<Kontrol>();
        return listk;
    }
    public static void Sil(int HastaID){
        //
    }
    public static boolean SifreOnay(String sifre)
    {
        return true;
    }
    public static void Guncelle(Hasta h){}
}
