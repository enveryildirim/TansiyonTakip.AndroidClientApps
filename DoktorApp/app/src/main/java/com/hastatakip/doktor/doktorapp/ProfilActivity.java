package com.hastatakip.doktor.doktorapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class ProfilActivity extends ActionBarActivity {
    ProgressDialog pd;
    int DoktorID;
    Doktor doktor;
    EditText ed_AdSoyad,ed_Sifre,ed_Tekrar,ed_YeniSifre;

    Button btn_guncelle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        DoktorID=getIntent().getExtras().getInt("DoktorID");
        Doktor d=new Doktor();
        ed_AdSoyad=(EditText)findViewById(R.id.editText_AdSoyad);
        ed_Sifre=(EditText)findViewById(R.id.editText_SifreOnay);
        ed_YeniSifre=(EditText)findViewById(R.id.editText_YeniSifre);
        ed_Tekrar=(EditText)findViewById(R.id.editText_SifreOnay);

        btn_guncelle=(Button)findViewById(R.id.button_guncelle);
        btn_guncelle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    if(ed_Sifre.getText().length()>0&&ed_Tekrar.getText().length()>0&&ed_YeniSifre.getText().length()>0) {
                        if(Doktor.SifreOnay(ed_Sifre.getText().toString())) {
                            doktor.Ad_Soyad=ed_AdSoyad.getText().toString();
                            doktor.Sifre=ed_YeniSifre.getText().toString();
                            Doktor.Guncelle(doktor);
                            Toast.makeText(ProfilActivity.this,"Güncellendi!!!",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(ProfilActivity.this,"Boş Bırakmayın",Toast.LENGTH_SHORT).show();
                    }

                }catch (Exception e)
                {
                  String a=  e.getMessage();
                }

            }
        });
        new AsyncTaskClass().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profil, menu);
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

        @Override
        protected void onPreExecute() {
            //uzun islem oncesi yapilacaklar
            pd = new ProgressDialog(ProfilActivity.this);
            pd.setMessage("Veriler Yukleniyor...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
               doktor=Doktor.GetDoktorByID(DoktorID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
            ed_AdSoyad.setText(doktor.Ad_Soyad);
            pd.dismiss();
        }
    }
}
