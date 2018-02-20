package com.hastatakip.doktor.doktorapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaActionSound;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.CharacterPickerDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    ProgressDialog pd;
    Button btn_hasta,btn_ayarlar,btn_kontrolekle;
    ListView listViewHasta;
    AdaterListViewHastaAnaEkran adap;
    int DoktorID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listViewHasta=(ListView)findViewById(R.id.listView);
        DoktorID=getIntent().getExtras().getInt("DoktorID");
        listViewHasta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i =new Intent(MainActivity.this,DetayActivity.class);
                i.putExtra("HastaID",id);
                i.putExtra("HastaName", ((Hasta) adap.getItem(position)).Ad_Soyad);
                startActivity(i);
            }
        });


        //Verileri web servisten cekilmesi
        (new AsyncTaskClass()).execute();

    }

    public void ProfilActivitiyHandler(View v) {
        Intent i =new Intent(MainActivity.this,ProfilActivity.class);
        i.putExtra("DoktorID",DoktorID);
        startActivity(i);
    }

    public void HastalarActivityHandler(View v){
        Intent i = new Intent(MainActivity.this,HastaActivity.class);
        i.putExtra("DoktorID",DoktorID);
        startActivity(i);
    }
    public void KontrollerActivityHandler(View v){
        Intent i = new Intent(MainActivity.this,HastaActivity.class);
        i.putExtra("DoktorID",DoktorID);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    //Hastaları yukleme
    public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;
        List<Hasta> hastalar;
        //AdapterMarka adapmarka;
        @Override
        protected void onPreExecute() {
            //uzun islem oncesi yapilacaklar
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Veriler Yukleniyor...");
            pd.show();
        }

        @Override
        protected Void doInBackground(Void... strings) {

            try {
                hastalar=Hasta.JsonToList(DoktorID);

            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
             adap=new AdaterListViewHastaAnaEkran(MainActivity.this,hastalar);
            listViewHasta.setAdapter(adap);
            pd.dismiss();
        }
    }
}