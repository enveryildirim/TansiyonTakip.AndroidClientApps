package com.hastatakip.hasta.hastaapp;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.GenericArrayType;
import java.util.List;

public class DetayActivity extends ActionBarActivity {

    ProgressDialog pd;
    Button btn_degerekle;
    ListView listdetay;
    int KontrolTuru,KontrolID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);
        KontrolID=getIntent().getExtras().getInt("KontrolID");
        KontrolTuru=0;
        try {

            listdetay = (ListView) findViewById(R.id.listView2);
            btn_degerekle = (Button) findViewById(R.id.button_deger_ekle);
            btn_degerekle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Dialog d = new Dialog(DetayActivity.this);
                    //Yeni pencere oluşturmak
                    Window window = d.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    d.setTitle("Değer Ekle");
                    Button btn_hastaekle;
                    final EditText ed;
                    final EditText ed1;
                    if (KontrolTuru == 0) {
                        d.setContentView(R.layout.degerekle_tansiyon);
                        btn_hastaekle = (Button) d.findViewById(R.id.button_gonder1);
                        ed = (EditText) d.findViewById(R.id.editText_TansiyonB);
                        ed1 = (EditText) d.findViewById(R.id.editText_TansiyonK);
                    } else {
                        d.setContentView(R.layout.degerekle_seker);
                        btn_hastaekle = (Button) d.findViewById(R.id.button_gonder);
                        ed = (EditText) d.findViewById(R.id.editText_seker);
                        ed1 = null;
                    }
                    btn_hastaekle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ekleme kodlar
                            String deger = "";
                            if (KontrolTuru == 0) deger = ed.getText().toString();
                            else deger = ed.getText().toString() + "/" + ed1.getText().toString();
                            Kontrol.Ekle(KontrolID, deger);
                            //pencereyi kapatır.
                            d.dismiss();
                            //mesaj
                            Toast.makeText(getApplicationContext(), "Eklendi !!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    d.show();

                }
            });

            listdetay.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                   /* final Dialog d = new Dialog(DetayActivity.this);
                    Window window = d.getWindow();
                    window.setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
                    d.setTitle("Değer Guncelle");
                    Button btn_hastaekle;
                    final EditText ed;
                    final EditText ed1;
                    if (KontrolTuru == 0) {
                        d.setContentView(R.layout.degerekle_tansiyon);
                        btn_hastaekle = (Button) d.findViewById(R.id.button_gonder1);
                        ed = (EditText) d.findViewById(R.id.editText_TansiyonB);
                        ed1 = (EditText) d.findViewById(R.id.editText_TansiyonK);

                    } else {
                        d.setContentView(R.layout.degerekle_seker);
                        btn_hastaekle = (Button) d.findViewById(R.id.button_gonder);
                        ed = (EditText) d.findViewById(R.id.editText_seker);
                        ed1 = null;
                    }
                    btn_hastaekle.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //ekleme kodlar
                            String deger = "";
                            if (KontrolTuru == 0) deger = ed.getText().toString();
                            else deger = ed.getText().toString() + "/" + ed1.getText().toString();
                            Kontrol.Guncelle(KontrolID, deger);

                            d.dismiss();
                            //mesaj
                            Toast.makeText(getApplicationContext(), "Guncelle !!", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }
            });
            listdetay.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    return false;
                }
            });
            new AsyncTaskClass().execute();
        }catch (Exception e)
        {
            e.getStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detay, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;
        Kontrol Kontroller;
        @Override
        protected void onPreExecute() {
            //uzun islem oncesi yapilacaklar
            pd = new ProgressDialog(DetayActivity.this);
            pd.setMessage("Veriler Yukleniyor...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
                Kontroller=Kontrol.GetKontrolByID(KontrolID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
            try {
                AdapKontrolDeger adapDetay=new AdapKontrolDeger(DetayActivity.this,Kontroller);
                listdetay.setAdapter(adapDetay);
                pd.dismiss();
            }catch (Exception e)
            {
             e.getStackTrace();
            }

        }
    }
}
