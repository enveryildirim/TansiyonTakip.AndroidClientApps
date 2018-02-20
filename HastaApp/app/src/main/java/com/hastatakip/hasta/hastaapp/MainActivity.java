package com.hastatakip.hasta.hastaapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends ActionBarActivity {

    ProgressDialog pd;
    int HastaID;
    TextView tx_bilgiler;
    ListView list_Detaylar;
    Button btn_profil;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        HastaID=getIntent().getExtras().getInt("HastaID");
        list_Detaylar=(ListView)findViewById(R.id.listView_Detay);
        list_Detaylar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this,DetayActivity.class);
                i.putExtra("KontrolID",(int)id);
                i.putExtra("HastaID",HastaID);
                startActivity(i);
            }
        });
        btn_profil=(Button)findViewById(R.id.button_profil);
        btn_profil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,ProfilActivity.class);
                i.putExtra("HastaID",HastaID);
                startActivity(i);
            }
        });

        new AsyncTaskClass().execute();
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
    public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;
        List<Kontrol> Kontroller;
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
               Kontroller=Kontrol.JsonToList(HastaID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
            AdapterListViewDetay adapDetay=new AdapterListViewDetay(MainActivity.this,Kontroller);
            list_Detaylar.setAdapter(adapDetay);
            pd.dismiss();
        }
    }
}
