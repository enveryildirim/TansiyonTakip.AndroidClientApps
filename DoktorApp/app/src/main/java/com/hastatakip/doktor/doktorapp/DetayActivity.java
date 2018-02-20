package com.hastatakip.doktor.doktorapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class DetayActivity extends ActionBarActivity {

    ProgressDialog pd;
    int HastaID;
    TextView tx_bilgiler;

    ListView list_Detaylar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        list_Detaylar=(ListView)findViewById(R.id.listView_Detay);
        tx_bilgiler=(TextView)findViewById(R.id.textView2);
        HastaID=getIntent().getExtras().getInt("HastaID");
        String HastaName=getIntent().getExtras().getString("HastaName");
        tx_bilgiler.setText(HastaName);


        new AsyncTaskClass().execute();
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

    //Verileri cekme
    public class AsyncTaskClass extends AsyncTask<Void, Void, Void> {
        ProgressDialog dg;
        List<Kontrol> Kontroller;
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
                Kontroller=Hasta.GetKontrollerByHastaID(HastaID);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;

        }

        @Override
        protected void onPostExecute(Void s) {
            //uzun islem bitince yapilacaklar
            AdapterListViewDetay adapDetay=new AdapterListViewDetay(DetayActivity.this,Kontroller);
            list_Detaylar.setAdapter(adapDetay);
            pd.dismiss();
        }
    }
}
